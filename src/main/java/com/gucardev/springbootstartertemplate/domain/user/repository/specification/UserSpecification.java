package com.gucardev.springbootstartertemplate.domain.user.repository.specification;

import com.gucardev.springbootstartertemplate.domain.common.repository.specification.BaseSpecification;
import com.gucardev.springbootstartertemplate.domain.user.entity.User;
import com.gucardev.springbootstartertemplate.domain.user.enumeration.Role;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;

public class UserSpecification extends BaseSpecification {

    public static Specification<User> hasNameLike(String name) {
        return (root, query, cb) -> name == null ? null :
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<User> hasSurnameLike(String surname) {
        return (root, query, cb) -> surname == null ? null :
                cb.like(cb.lower(root.get("surname")), "%" + surname.toLowerCase() + "%");
    }

    public static Specification<User> hasEmailLike(String email) {
        return (root, query, cb) -> email == null ? null :
                cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    public static Specification<User> hasRole(Role role) {
        return (root, query, cb) -> role == null ? null :
                cb.isMember(role, root.get("roles"));
    }


    public static Specification<User> hasAuthority(String authorityName) {
        return (root, query, cb) -> {
            if (authorityName == null) return null;

            // Get all roles that have this authority
            List<Role> rolesWithAuthority = Arrays.stream(Role.values())
                    .filter(role -> role.getAuthorities().stream()
                            .anyMatch(auth -> auth.getAuthority().equals(authorityName)))
                    .toList();

            if (rolesWithAuthority.isEmpty()) {
                return cb.equal(cb.literal(1), 2);
            }

            // For each role, check if it's a member of the user's roles collection
            // and combine with OR
            Predicate[] predicates = rolesWithAuthority.stream()
                    .map(role -> cb.isMember(role, root.get("roles")))
                    .toArray(Predicate[]::new);

            return cb.or(predicates);
        };
    }

}
