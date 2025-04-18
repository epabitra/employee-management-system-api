package com.nihar.service;

import java.util.List;
import java.util.Optional;

import com.nihar.entity.HolidaysList;

public interface HolidaysListService {
	 HolidaysList save(HolidaysList obj);
	    List<HolidaysList> findAll();
	    Optional<HolidaysList> findById(Long id);
	    void delete(Long id);

}
