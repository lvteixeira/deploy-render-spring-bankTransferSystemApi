package com.acme.bankTransferSystemApi.repository;

import com.acme.bankTransferSystemApi.model.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepository extends JpaRepository<TransferEntity, Long> {
    List<TransferEntity> findByUserId(String userId);
}