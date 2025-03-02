package com.gucardev.springbootstartertemplate.domain.transaction.repository;

import com.gucardev.springbootstartertemplate.domain.common.repository.BaseRepository;
import com.gucardev.springbootstartertemplate.domain.transaction.entity.Transaction;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction, UUID> {

}
