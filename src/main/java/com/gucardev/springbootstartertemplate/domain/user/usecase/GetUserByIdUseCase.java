package com.gucardev.springbootstartertemplate.domain.user.usecase;

import com.gucardev.springbootstartertemplate.domain.user.entity.User;
import com.gucardev.springbootstartertemplate.domain.user.repository.UserRepository;
import com.gucardev.springbootstartertemplate.infrastructure.usecase.UseCaseWithParamsAndReturn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetUserByIdUseCase implements UseCaseWithParamsAndReturn<UUID, Optional<User>> {

    private final UserRepository userRepository;

    @Override
    public Optional<User> execute(UUID uuid) {
        return userRepository.findById(uuid);
    }

}
