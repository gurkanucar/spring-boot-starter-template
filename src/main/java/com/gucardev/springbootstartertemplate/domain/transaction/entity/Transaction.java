package com.gucardev.springbootstartertemplate.domain.transaction.entity;

import com.gucardev.springbootstartertemplate.domain.account.entity.Account;
import com.gucardev.springbootstartertemplate.domain.category.entity.Category;
import com.gucardev.springbootstartertemplate.domain.common.entity.BaseEntity;
import com.gucardev.springbootstartertemplate.domain.transaction.enumeration.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
public class Transaction extends BaseEntity {

    @Column(nullable = false)
    private BigDecimal amount;

    private String description;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "transaction_categories",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();
}