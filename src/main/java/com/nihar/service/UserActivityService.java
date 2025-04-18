package com.nihar.service;

import java.util.List;
import java.util.Optional;

import com.nihar.entity.UserActivity;

public interface UserActivityService {
	 UserActivity save(UserActivity obj);
	    List<UserActivity> findAll();
	    Optional<UserActivity> findById(Long id);
	    void delete(Long id);

}
