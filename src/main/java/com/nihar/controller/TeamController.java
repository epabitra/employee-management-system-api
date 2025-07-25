package com.nihar.controller;

import com.nihar.entity.Team;
import com.nihar.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    // Get all teams
    @GetMapping("/list")
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(teamService.findAll());
    }

    // Get team by UUID
    @GetMapping("/{uuid}")
    public ResponseEntity<Team> getTeamByUuid(@PathVariable String uuid) {
        Optional<Team> team = teamService.findByUuid(uuid);
        return team.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Create new team
    @PostMapping("/create")
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        // Set required fields manually (you can automate with DTO + Mapper later)
        team.setUuid(UUID.randomUUID().toString());
        team.setCreatedDate(LocalDateTime.now());
        team.setCreatedBy("system"); // replace with logged-in user later
        team.setActive(true);

        Team savedTeam = teamService.save(team);
        return ResponseEntity.ok(savedTeam);
    }
    
 // Count total teams
    @GetMapping("/count")
    public ResponseEntity<Long> countTeams() {
        return ResponseEntity.ok(teamService.countTeams());
    }


    // Update team by UUID
    @PutMapping("/{uuid}")
    public ResponseEntity<Team> updateTeam(@PathVariable String uuid, @RequestBody Team updatedTeam) {
        Optional<Team> existingTeamOpt = teamService.findByUuid(uuid);

        if (existingTeamOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Team existingTeam = existingTeamOpt.get();
        existingTeam.setTeamName(updatedTeam.getTeamName());
        existingTeam.setDescription(updatedTeam.getDescription());
        existingTeam.setUpdatedBy("system"); // replace with current user
        existingTeam.setUpdatedDate(LocalDateTime.now());

        Team savedTeam = teamService.save(existingTeam);
        return ResponseEntity.ok(savedTeam);
    }

    // Delete team by UUID
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteTeam(@PathVariable String uuid) {
        Optional<Team> existingTeam = teamService.findByUuid(uuid);
        if (existingTeam.isPresent()) {
            teamService.delete(existingTeam.get().getId());
            return ResponseEntity.noContent().build(); // Corrected return type to ResponseEntity<Void>
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
