package com.gucardev.springbootstartertemplate.domain.user.controller;

import com.gucardev.springbootstartertemplate.domain.user.mapper.UserMapper;
import com.gucardev.springbootstartertemplate.domain.user.model.dto.UserDto;
import com.gucardev.springbootstartertemplate.domain.user.model.request.UserCreateRequest;
import com.gucardev.springbootstartertemplate.domain.user.model.request.UserFilterRequest;
import com.gucardev.springbootstartertemplate.domain.user.usecase.CreateUserUseCase;
import com.gucardev.springbootstartertemplate.domain.user.usecase.GetUserByIdUseCase;
import com.gucardev.springbootstartertemplate.domain.user.usecase.SearchUsersUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Controller for user operations")
public class UserControllerV1 {

    private final CreateUserUseCase createUserUseCase;
    private final SearchUsersUseCase searchUsersUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final UserMapper userMapper;

    @Operation(
            summary = "Create a new user",
            description = "This api creates a new user and return created user"
    )
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        return ResponseEntity.ok(createUserUseCase.execute(userCreateRequest));
    }

    @Operation(
            summary = "Search users",
            description = "This api allows you to search and filter users with pagination"
    )
    @GetMapping("/search")
    public ResponseEntity<Page<UserDto>> searchUsers(@Valid @ParameterObject UserFilterRequest filterRequest) {
        return ResponseEntity.ok(searchUsersUseCase.execute(filterRequest));
    }

    @Operation(
            summary = "Get user by id",
            description = "This api retrieves user by id"
    )
    @GetMapping("/{uuid}")
    public ResponseEntity<UserDto> getUserById(@Valid @NotNull @PathVariable UUID uuid) {
        return getUserByIdUseCase.execute(uuid)
                .map(userMapper::toDto).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
