package com.gucardev.springbootstartertemplate.domain.user.repository;

import com.gucardev.springbootstartertemplate.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    boolean existsByUsername(String username);

}
