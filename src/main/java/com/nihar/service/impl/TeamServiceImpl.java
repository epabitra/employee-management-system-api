package com.nihar.service.impl;

import com.nihar.entity.Team;
import com.nihar.repository.TeamRepository;
import com.nihar.service.TeamService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public Team save(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public Optional<Team> findById(Long id) {
        return teamRepository.findById(id);
    }

    @Override
    public Optional<Team> findByUuid(String uuid) {
        return teamRepository.findByUuid(uuid);
    }

    @Override
    public void delete(Long id) {
        teamRepository.deleteById(id);
    }
    
    @Override
    public long countTeams() {
        return teamRepository.count();
    }


    @Override
    public Team updateTeam(String uuid, Team updated) {
        return teamRepository.findByUuid(uuid)
                .map(existing -> {
                    existing.setTeamName(updated.getTeamName());
                    existing.setDescription(updated.getDescription());
                    existing.setUpdatedBy(updated.getUpdatedBy());
                    existing.setUpdatedDate(LocalDateTime.now());
                    return teamRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Team not found with UUID: " + uuid));
    }

}
