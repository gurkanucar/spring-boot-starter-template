package com.gucardev.springbootstartertemplate.domain.user.usecase;

import com.gucardev.springbootstartertemplate.domain.user.mapper.UserMapper;
import com.gucardev.springbootstartertemplate.domain.user.model.dto.UserDto;
import com.gucardev.springbootstartertemplate.domain.user.model.request.UserCreateRequest;
import com.gucardev.springbootstartertemplate.domain.user.repository.UserRepository;
import com.gucardev.springbootstartertemplate.infrastructure.exception.ExceptionMessage;
import com.gucardev.springbootstartertemplate.infrastructure.usecase.UseCaseWithParamsAndReturn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.gucardev.springbootstartertemplate.infrastructure.exception.helper.ExceptionUtil.buildException;
import static com.gucardev.springbootstartertemplate.infrastructure.exception.helper.ExceptionUtil.buildSilentException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateUserUseCase implements UseCaseWithParamsAndReturn<UserCreateRequest, UserDto> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto execute(UserCreateRequest params) {
        if (userRepository.existsByUsername(params.getUsername())) {
            throw buildException(ExceptionMessage.ALREADY_EXISTS_EXCEPTION);
//            throw buildSilentException(ExceptionMessage.ALREADY_EXISTS_EXCEPTION);
        }
        var newUser = userMapper.toEntity(params);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        var savedUser = userRepository.save(newUser);
        return userMapper.toDto(savedUser);
    }

}
