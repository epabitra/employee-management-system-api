package com.nihar.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nihar.entity.UserTeam;
import com.nihar.repository.UserTeamRepository;
import com.nihar.service.UserTeamService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserTeamServiceImpl implements UserTeamService {
    private final UserTeamRepository repo;

    public UserTeam save(UserTeam obj) { return repo.save(obj); }
    public List<UserTeam> findAll() { return repo.findAll(); }
    public Optional<UserTeam> findById(Long id) { return repo.findById(id); }
    public void delete(Long id) { repo.deleteById(id); }
}

