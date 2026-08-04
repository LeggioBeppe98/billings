package com.bldev.billings.service.user;

import com.bldev.billings.dto.user.UserCreateDto;
import com.bldev.billings.dto.user.UserDetailDto;
import com.bldev.billings.dto.user.UserListDto;
import com.bldev.billings.dto.user.UserUpdateDto;
import com.bldev.billings.exception.EmailAlreadyExistsException;
import com.bldev.billings.exception.UnauthorizedException;
import com.bldev.billings.exception.UserNotFoundException;
import com.bldev.billings.mapper.user.UserMapper;
import com.bldev.billings.model.Role;
import com.bldev.billings.model.User;
import com.bldev.billings.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<UserListDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toListDto);
    }

    @Override
    public Page<UserListDto> findByAttivo(boolean attivo, Pageable pageable) {
        return userRepository.findByAttivo(attivo, pageable).map(userMapper::toListDto);
    }

    @Override
    public Page<UserListDto> findByEmail(String email, Pageable pageable) {
        return userRepository.findByEmailContainingIgnoreCase(email, pageable).map(userMapper::toListDto);
    }

    @Override
    public Page<UserListDto> findByName(String name, Pageable pageable) {
        return userRepository.findByNomeContainingIgnoreCase(name, pageable).map(userMapper::toListDto);
    }

    @Override
    public UserDetailDto findUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDetailDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public UserDetailDto createUser(UserCreateDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException(dto.getEmail());
        }
        User user = userMapper.toEntity(dto);

        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        User saved = userRepository.save(user);

        return userMapper.toDetailDto(saved);
    }

    @Override
    public UserDetailDto updateUser(Long id, UserUpdateDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        String authenticatedEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User authenticatedUser = userRepository.findByEmail(authenticatedEmail).orElseThrow();

        boolean isAdmin = authenticatedUser.getRole() == Role.ROLE_ADMIN;

        if (!isAdmin && !authenticatedUser.getId().equals(id)) {
            throw new UnauthorizedException();
        }


        // Se l’email è cambiata, controlla duplicati
        if (!user.getEmail().equals(dto.getEmail()) &&
                userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException(dto.getEmail());
        }

        // Aggiorna i campi tramite mapper
        userMapper.updateEntityFromDto(dto, user);

        User saved = userRepository.save(user);

        return userMapper.toDetailDto(saved);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(user);
    }
}

