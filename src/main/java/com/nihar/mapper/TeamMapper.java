package com.nihar.mapper;

import com.nihar.dto.TeamDto;
import com.nihar.entity.Team;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {

    public TeamDto toDto(Team team) {
        if (team == null) return null;

        return TeamDto.builder()
                .id(team.getId())
                .uuid(team.getUuid())
                .name(team.getTeamName())
                .description(team.getDescription())
                .build();
    }

    public Team toEntity(TeamDto dto) {
        if (dto == null) return null;

        return Team.builder()
                .id(dto.getId())
                .uuid(dto.getUuid())
                .teamName(dto.getName())
                .description(dto.getDescription())
                .build();
    }
}
