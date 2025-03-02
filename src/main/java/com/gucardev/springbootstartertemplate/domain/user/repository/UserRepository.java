package com.gucardev.springbootstartertemplate.domain.user.repository;

import com.gucardev.springbootstartertemplate.domain.common.repository.BaseRepository;
import com.gucardev.springbootstartertemplate.domain.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {

    boolean existsByUsername(String username);

}
