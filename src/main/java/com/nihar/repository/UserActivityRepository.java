package com.nihar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nihar.entity.UserActivity;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity,Long>{

}
