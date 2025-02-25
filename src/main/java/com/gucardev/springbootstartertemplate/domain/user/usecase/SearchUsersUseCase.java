package com.gucardev.springbootstartertemplate.domain.user.usecase;

import com.gucardev.springbootstartertemplate.domain.user.entity.User;
import com.gucardev.springbootstartertemplate.domain.user.mapper.UserMapper;
import com.gucardev.springbootstartertemplate.domain.user.model.dto.UserDto;
import com.gucardev.springbootstartertemplate.domain.user.model.request.UserFilterRequest;
import com.gucardev.springbootstartertemplate.domain.user.repository.UserRepository;
import com.gucardev.springbootstartertemplate.domain.user.repository.specification.UserSpecification;
import com.gucardev.springbootstartertemplate.infrastructure.usecase.UseCaseWithParamsAndReturn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchUsersUseCase implements UseCaseWithParamsAndReturn<UserFilterRequest, Page<UserDto>> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Page<UserDto> execute(UserFilterRequest params) {

        Sort.Direction direction = params.getSortDir().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), Sort.by(direction, params.getSortBy()));

        Specification<User> spec = Specification.where(UserSpecification.hasNameLike(params.getName()))
                .and(UserSpecification.hasSurnameLike(params.getSurname()))
                .and(UserSpecification.hasEmailLike(params.getEmail()))
                .and(UserSpecification.hasRole(params.getRole()))
                .and(UserSpecification.hasAuthority(params.getAuthority()))
                .and(UserSpecification.createdBetween(params.getStartDate(), params.getEndDate()));

        Page<User> usersPage = userRepository.findAll(spec, pageable);
        return usersPage.map(userMapper::toDto);
    }

}
