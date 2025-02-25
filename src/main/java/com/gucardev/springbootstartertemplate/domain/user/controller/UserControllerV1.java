package com.gucardev.springbootstartertemplate.domain.user.controller;

import com.gucardev.springbootstartertemplate.domain.user.model.dto.UserDto;
import com.gucardev.springbootstartertemplate.domain.user.model.request.UserCreateRequest;
import com.gucardev.springbootstartertemplate.domain.user.usecase.CreateUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Controller for user operations")
public class UserControllerV1 {

    private final CreateUserUseCase createUserUseCase;

    @Operation(
            summary = "Create a new user",
            description = "This api creates a new user and return created user"
    )
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        return ResponseEntity.ok(createUserUseCase.execute(userCreateRequest));
    }


}
