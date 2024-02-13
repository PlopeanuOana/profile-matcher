package com.gameloft.profile.model.retrieve;

import java.util.Map;
import java.util.Set;
import java.util.UUID;


public record PlayerProfileDTO(UUID playerId, String credential, String created, String modified, String lastSession,
                               double totalSpent, double totalRefund, int totalTransactions, String lastPurchase,
                               Set<String> activeCampaigns, Set<DeviceDTO> devices, int level, int xp,
                               int totalPlaytime, String country, String language, String birthdate, String gender,
                               Map<String, Integer> inventory, ClanDTO clan, String customField) {

}
