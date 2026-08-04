package com.bldev.billings.service.user;

import com.bldev.billings.dto.user.UserCreateDto;
import com.bldev.billings.dto.user.UserDetailDto;
import com.bldev.billings.dto.user.UserListDto;
import com.bldev.billings.dto.user.UserUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserListDto> getAllUsers(Pageable pageable);

    Page<UserListDto> findByName(String name, Pageable pageable);

    Page<UserListDto> findByAttivo(boolean attivo, Pageable pageable);

    Page<UserListDto> findByEmail(String email, Pageable pageable);

    UserDetailDto findUserById(Long id);

    UserDetailDto createUser(UserCreateDto dto);

    UserDetailDto updateUser(Long id, UserUpdateDto dto);

    void deleteUser(Long id);
}
