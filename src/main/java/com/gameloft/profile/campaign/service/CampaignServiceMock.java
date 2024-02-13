package com.gameloft.profile.campaign.service;

import com.gameloft.profile.campaign.model.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
public class CampaignServiceMock implements CampaignClient {
    @Override
    public List<Campaign> getCurrentRunningCampaigns() {
        final var matchers = new Matchers(
                new Level(1, 3),
                new Has(Map.of("country", List.of("US", "RO", "CA"), "items", List.of("item_1"))),
                new DoesNotHave(Map.of("items", List.of("item_4"))));

        final var campaign1 = new Campaign("mygame", "mycampaign1", 10.5, matchers,
                LocalDateTime.parse("2022-01-25T00:00:00"), LocalDateTime.parse("2022-02-25T00:00:00"));

        return List.of(campaign1);
    }
}
