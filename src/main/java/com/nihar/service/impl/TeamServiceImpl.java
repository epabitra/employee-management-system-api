package com.nihar.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nihar.entity.Team;
import com.nihar.repository.TeamRepository;
import com.nihar.service.TeamService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository repo;

    public Team save(Team obj) { return repo.save(obj); }
    public List<Team> findAll() { return repo.findAll(); }
    public Optional<Team> findById(Long id) { return repo.findById(id); }
    public void delete(Long id) { repo.deleteById(id); }
}

