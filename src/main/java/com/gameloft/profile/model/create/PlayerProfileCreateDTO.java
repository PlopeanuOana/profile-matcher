package com.gameloft.profile.model.create;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public record PlayerProfileCreateDTO(
        @JsonProperty("player_id") UUID playerId,
        String credential,
        String created,
        String modified,
        @JsonProperty("last_session") String lastSession,
        @JsonProperty("total_spent") int totalSpent,
        @JsonProperty("total_refund") int totalRefund,
        @JsonProperty("total_transactions") int totalTransactions,
        @JsonProperty("last_purchase") String lastPurchase,
        @JsonProperty("active_campaigns") List<String> activeCampaigns,
        List<DeviceCreateDTO> devices,
        int level,
        int xp,
        @JsonProperty("total_playtime") int totalPlaytime,
        String country,
        String language,
        String birthdate,
        String gender,
        Map<String, Integer> inventory,
        ClanCreateDTO clan,
        @JsonProperty("_customfield") String customField
) {
}


