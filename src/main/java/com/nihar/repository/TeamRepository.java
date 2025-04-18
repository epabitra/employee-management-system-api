package com.nihar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nihar.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {}

