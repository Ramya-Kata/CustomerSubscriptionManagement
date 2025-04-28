package com.ramyakata.microservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramyakata.microservice.entity.TvPlan;

/**
 * Description: Repository interface for managing TvPlan entities. Provides CRUD
 * operations and query capabilities for the TvPlan table.
 */
public interface TvPlanRepo extends JpaRepository<TvPlan, Long> {

}
