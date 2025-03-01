package com.gucardev.springbootstartertemplate.domain.category.entity;

import com.gucardev.springbootstartertemplate.domain.common.entity.BaseEntity;
import com.gucardev.springbootstartertemplate.domain.transaction.entity.Transaction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    private String displayName;

    private String description;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private Set<Transaction> transactions = new HashSet<>();
}