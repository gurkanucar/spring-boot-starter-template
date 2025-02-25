package com.gucardev.springbootstartertemplate.domain.user.controller;

import com.gucardev.springbootstartertemplate.domain.user.model.dto.UserDto;
import com.gucardev.springbootstartertemplate.domain.user.model.request.UserCreateRequest;
import com.gucardev.springbootstartertemplate.domain.user.model.request.UserFilterRequest;
import com.gucardev.springbootstartertemplate.domain.user.usecase.CreateUserUseCase;
import com.gucardev.springbootstartertemplate.domain.user.usecase.SearchUsersUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Controller for user operations")
public class UserControllerV1 {

    private final CreateUserUseCase createUserUseCase;
    private final SearchUsersUseCase searchUsersUseCase;

    @Operation(
            summary = "Create a new user",
            description = "This api creates a new user and return created user"
    )
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        return ResponseEntity.ok(createUserUseCase.execute(userCreateRequest));
    }

    @GetMapping("/search")
    public Page<UserDto> searchUsers(@Valid @ParameterObject UserFilterRequest filterRequest) {
        return searchUsersUseCase.execute(filterRequest);
    }

}
