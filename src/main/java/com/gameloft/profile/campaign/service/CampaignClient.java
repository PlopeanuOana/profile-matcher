package com.gameloft.profile.campaign.service;

import com.gameloft.profile.campaign.model.Campaign;

import java.util.List;

public interface CampaignClient {
    List<Campaign> getCurrentRunningCampaigns();
}
