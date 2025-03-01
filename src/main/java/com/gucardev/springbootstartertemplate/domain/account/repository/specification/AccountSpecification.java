package com.gucardev.springbootstartertemplate.domain.account.repository.specification;

import com.gucardev.springbootstartertemplate.domain.account.entity.Account;
import com.gucardev.springbootstartertemplate.domain.user.entity.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AccountSpecification {

    public static Specification<Account> hasUsernameLike(String username) {
        return (root, query, cb) -> {
            if (username == null) return null;
            Join<Account, User> userJoin = root.join("user", JoinType.INNER);
            return cb.like(cb.lower(userJoin.get("username")), "%" + username.toLowerCase() + "%");
        };
    }

    public static Specification<Account> hasAccountNumberLike(String accountNumber) {
        return (root, query, cb) -> accountNumber == null ? null :
                cb.like(cb.lower(root.get("accountNumber")), "%" + accountNumber.toLowerCase() + "%");
    }

    public static Specification<Account> createdBetween(LocalDate start, LocalDate end) {
        return (root, query, cb) -> {
            if (start == null || end == null) return null;
            // Convert LocalDate to LocalDateTime for proper comparison
            LocalDateTime startDateTime = start.atStartOfDay();
            LocalDateTime endDateTime = end.atTime(23, 59, 59, 999999999);
            return cb.between(root.get("createdDate"), startDateTime, endDateTime);
        };
    }

}
