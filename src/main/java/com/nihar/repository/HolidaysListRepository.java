package com.nihar.repository;

import com.nihar.entity.HolidaysList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidaysListRepository extends JpaRepository<HolidaysList, Long> {
}

