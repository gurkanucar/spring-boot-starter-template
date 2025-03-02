package com.gucardev.springbootstartertemplate.domain.account.repository.specification;

import com.gucardev.springbootstartertemplate.domain.account.entity.Account;
import com.gucardev.springbootstartertemplate.domain.account.enumeration.AccountType;
import com.gucardev.springbootstartertemplate.domain.common.enumeration.DeletedStatus;
import com.gucardev.springbootstartertemplate.domain.common.repository.specification.BaseSpecification;
import com.gucardev.springbootstartertemplate.domain.user.entity.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecification extends  BaseSpecification {

    public static Specification<Account> hasUsernameLike(String username) {
        return (root, query, cb) -> {
            if (username == null) return null;
            Join<Account, User> userJoin = root.join("user", JoinType.INNER);
            return cb.like(cb.lower(userJoin.get("username")), "%" + username.toLowerCase() + "%");
        };
    }

    public static Specification<Account> hasAccountNumberLike(String accountNumber) {
        return BaseSpecification.like("accountNumber", accountNumber);
    }

    public static Specification<Account> hasAccountType(AccountType accountType) {
        return (root, query, cb) -> accountType == null ? null :
                cb.equal(root.get("accountType"), accountType);
    }


}
