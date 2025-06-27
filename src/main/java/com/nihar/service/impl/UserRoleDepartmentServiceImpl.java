package com.nihar.service.impl;

import com.nihar.entity.UserRoleDepartment;
import com.nihar.repository.UserRoleDepartmentRepository;
import com.nihar.service.UserRoleDepartmentService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleDepartmentServiceImpl implements UserRoleDepartmentService {
    
 @Autowired   
 UserRoleDepartmentRepository  userRoleDepartmentRepository ;

	@Override
	public UserRoleDepartment save(UserRoleDepartment obj) {
		// TODO Auto-generated method stub
		return userRoleDepartmentRepository.save(obj) ;
	}

	@Override
	public List<UserRoleDepartment> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<UserRoleDepartment> findById(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}
}

