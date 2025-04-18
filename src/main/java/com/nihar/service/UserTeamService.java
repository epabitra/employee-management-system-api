package com.nihar.service;

import java.util.List;
import java.util.Optional;

import com.nihar.entity.UserTeam;

public interface UserTeamService {
    UserTeam save(UserTeam obj);
    List<UserTeam> findAll();
    Optional<UserTeam> findById(Long id);
    void delete(Long id);
}

