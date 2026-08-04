package com.bldev.billings.controller;

import com.bldev.billings.dto.user.UserCreateDto;
import com.bldev.billings.dto.user.UserDetailDto;
import com.bldev.billings.dto.user.UserListDto;
import com.bldev.billings.dto.user.UserUpdateDto;
import com.bldev.billings.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Elenco di tutti gli utenti", description = "Ritorna l'elenco di tutti gli utenti. Usa la paginazione")
    @GetMapping
    public Page<UserListDto> getAllUsers(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    @Operation(summary = "Ricerca per id dello user")
    @GetMapping("/{id}")
    public UserDetailDto getUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @Operation(summary = "Ricerca per attributo attivo a true", description = "Ritorna gli utenti attivi. Usa la paginazione")
    @GetMapping("/active")
    public Page<UserListDto> getActiveUsers(Pageable pageable) {
        return userService.findByAttivo(true, pageable);
    }

    @Operation(summary = "Ricerca per attributo attivo a false", description = "Ritorna gli utenti non attivi. Usa la paginazione")
    @GetMapping("/inactive")
    public Page<UserListDto> getInactiveUsers(Pageable pageable) {
        return userService.findByAttivo(false, pageable);
    }

    @Operation(summary = "Ricerca per nome", description = "E' una ricerca per LIKE e usa la paginazione")
    @GetMapping("/search/name")
    public Page<UserListDto> searchByName(@RequestParam String name, Pageable pageable) {
        return userService.findByName(name, pageable);
    }

    @Operation(summary = "Ricerca per e-mail", description = "E' una ricerca per LIKE e usa la paginazione")
    @GetMapping("/search/email")
    public Page<UserListDto> searchByEmail(@RequestParam String email, Pageable pageable) {
        return userService.findByEmail(email, pageable);
    }

    @Operation(summary = "Crea un nuovo utente", description = "Solo gli admin possono creare utenti")
    @PostMapping
    public UserDetailDto createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        return userService.createUser(userCreateDto);
    }

    @Operation(summary = "Aggiorna utente con specifico ID", description = "Solo gli admin possono aggiornare tutti gli utenti, l'utente con ruolo user può aggiornare solo le sue info")
    @PutMapping("/{id}")
    public UserDetailDto updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateUser(id, userUpdateDto);
    }
    @Operation(summary = "Elimina utente con specifico ID", description = "Solo gli admin possono eliminare gli utenti")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
