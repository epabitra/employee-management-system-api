package com.nihar.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nihar.entity.UserActivity;
import com.nihar.repository.UserActivityRepository;
import com.nihar.service.UserActivityService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserActivityServiceImpl implements UserActivityService {
    private final UserActivityRepository repo;
    public UserActivity save(UserActivity obj) { return repo.save(obj); }
    public List<UserActivity> findAll() { return repo.findAll(); }
    public Optional<UserActivity> findById(Long id) { return repo.findById(id); }
    public void delete(Long id) { repo.deleteById(id); }
}
