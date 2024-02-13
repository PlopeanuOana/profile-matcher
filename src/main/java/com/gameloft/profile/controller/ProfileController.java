package com.gameloft.profile.controller;

import com.gameloft.profile.model.create.PlayerProfileCreateDTO;
import com.gameloft.profile.model.retrieve.PlayerProfileDTO;
import com.gameloft.profile.service.PlayerProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final PlayerProfileService service;

    @GetMapping(value = "/get_client_config/{player_id}")
    public ResponseEntity<PlayerProfileDTO> getClientProfile(@PathVariable("player_id") UUID playerId) {
        log.info("Retrieve player's profile for id {}", playerId);

        final var playerProfile = service.getPlayerProfile(playerId);

        return ResponseEntity.ok(playerProfile);
    }

    @PostMapping(value = "/client")
    public ResponseEntity<PlayerProfileDTO> createProfile(@RequestBody PlayerProfileCreateDTO playerProfileCreateDTO) {
        log.info("Create profile for a new client");

        final var profile = service.createProfile(playerProfileCreateDTO);

        return ResponseEntity.ok(profile);
    }

}
