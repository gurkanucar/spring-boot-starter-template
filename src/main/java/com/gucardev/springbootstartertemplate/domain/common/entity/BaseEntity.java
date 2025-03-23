package com.gucardev.springbootstartertemplate.domain.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

//    Long id
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    // uuid works with postgresql
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
//    @EqualsAndHashCode.Include
//    @ToString.Include
//    private UUID id;

    // uuid works with mysql
    @EqualsAndHashCode.Include
    @ToString.Include
    @Id
    @Column(nullable = false, updatable = false, columnDefinition = "char(36)")
    @JdbcTypeCode(Types.CHAR)
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    private String deleteReason;

    private boolean deleted = false;

}
