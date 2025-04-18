package com.nihar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nihar.entity.UserTeam;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {}

