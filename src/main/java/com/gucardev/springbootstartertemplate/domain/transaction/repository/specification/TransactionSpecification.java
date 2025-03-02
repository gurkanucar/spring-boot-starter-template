package com.gucardev.springbootstartertemplate.domain.transaction.repository.specification;

import com.gucardev.springbootstartertemplate.domain.common.repository.specification.BaseSpecification;
import com.gucardev.springbootstartertemplate.domain.transaction.entity.Transaction;
import com.gucardev.springbootstartertemplate.domain.transaction.enumeration.TransactionType;
import org.springframework.data.jpa.domain.Specification;

public class TransactionSpecification extends BaseSpecification {

    public static Specification<Transaction> hasTransactionType(TransactionType transactionType) {
        return (root, query, cb) -> transactionType == null ? null :
                cb.equal(root.get("type"), transactionType);
    }

}
