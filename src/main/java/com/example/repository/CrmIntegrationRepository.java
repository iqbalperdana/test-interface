package com.example.repository;

import com.example.entity.CrmIntegration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmIntegrationRepository extends JpaRepository<CrmIntegration, String> {
}
