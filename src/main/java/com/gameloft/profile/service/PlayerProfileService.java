package com.gameloft.profile.service;

import com.gameloft.profile.model.create.PlayerProfileCreateDTO;
import com.gameloft.profile.model.retrieve.PlayerProfileDTO;

import java.util.UUID;

public interface PlayerProfileService {

    PlayerProfileDTO getPlayerProfile(UUID playerId);

    PlayerProfileDTO createProfile(PlayerProfileCreateDTO playerProfileDTO);

}
