package com.nihar.service;

import java.util.List;
import java.util.Optional;

import com.nihar.entity.Team;

public interface TeamService {
    Team save(Team team);
    List<Team> findAll();
    Optional<Team> findById(Long id);
    Optional<Team> findByUuid(String uuid);
    void delete(Long id);
    Team updateTeam(String uuid, Team updatedTeam);
    long countTeams();

}
