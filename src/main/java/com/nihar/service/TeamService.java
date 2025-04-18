package com.nihar.service;

import java.util.List;
import java.util.Optional;

import com.nihar.entity.Team;

public interface TeamService {
    Team save(Team obj);
    List<Team> findAll();
    Optional<Team> findById(Long id);
    void delete(Long id);
}

