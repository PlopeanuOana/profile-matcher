package com.gameloft.profile.campaign.model;

import java.time.LocalDateTime;

public record Campaign(String game, String name, double priority, Matchers matchers, LocalDateTime startDate,
                       LocalDateTime endDate) {
}
