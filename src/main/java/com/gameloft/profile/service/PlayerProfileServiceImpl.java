package com.gameloft.profile.service;

import com.gameloft.profile.advisor.exception.InvalidProfileException;
import com.gameloft.profile.advisor.exception.MatchException;
import com.gameloft.profile.advisor.exception.NotFoundException;
import com.gameloft.profile.campaign.model.Matchers;
import com.gameloft.profile.campaign.service.CampaignClient;
import com.gameloft.profile.entity.Clan;
import com.gameloft.profile.entity.PlayerProfile;
import com.gameloft.profile.mapper.PlayerProfileMapper;
import com.gameloft.profile.model.create.PlayerProfileCreateDTO;
import com.gameloft.profile.model.retrieve.PlayerProfileDTO;
import com.gameloft.profile.repository.ClanRepository;
import com.gameloft.profile.repository.PlayerProfileRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class PlayerProfileServiceImpl implements PlayerProfileService {

    private final PlayerProfileRepository repository;
    private final ClanRepository clanRepository;
    private final CampaignClient campaignClient;
    private final PlayerProfileMapper mapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PlayerProfileDTO getPlayerProfile(UUID playerId) {
        final var entityOptional = repository.findByPlayerId(playerId);
        if (entityOptional.isPresent()) {
            final var profileUpdated = getProfileUpdated(entityOptional.get());
            return mapper.toDTO(profileUpdated);
        }
        throw new NotFoundException(String.format("Player with id %s is not found", playerId));
    }

    @Override
    @Transactional
    public PlayerProfileDTO createProfile(PlayerProfileCreateDTO dto) {
        final var entity = mapper.toEntity(dto);

        checkClan(entity);

        entityManager.persist(entity);
        final var saved = repository.save(entity);

        return mapper.toDTO(saved);
    }

    private PlayerProfile getProfileUpdated(final PlayerProfile entity) {
        final var campaigns = campaignClient.getCurrentRunningCampaigns();
        if (campaigns.isEmpty()) {
            throw new MatchException("There are no active campaigns");
        }

        AtomicReference<PlayerProfile> updated = new AtomicReference<>(new PlayerProfile());
        campaigns.forEach(campaign -> {
            try {
                if (this.matchesCampaign(entity, campaign.matchers())) {
                    List<String> activeCampaigns = entity.getActiveCampaigns();
                    activeCampaigns.add(campaign.name());
                    entity.setActiveCampaigns(activeCampaigns);

                    PlayerProfile saved = repository.save(entity);
                    updated.set(saved);
                } else {
                    throw new MatchException("The user did not match any active campaigns");
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new InvalidProfileException("Provided profile has invalid fields");
            }
        });

        return updated.get();
    }

    private boolean matchesCampaign(final PlayerProfile profile, Matchers matchers) throws NoSuchFieldException, IllegalAccessException {
        final var level = matchers.level();
        boolean matchesLevel = profile.getLevel() <= level.max() && profile.getLevel() >= level.min();
        final var hasMatches = matchesConditions(profile, matchers.has().data(), true);
        final var doesNotHaveMatches = matchesConditions(profile, matchers.doesNotHave().data(), false);

        return hasMatches && matchesLevel && doesNotHaveMatches;
    }

    private boolean matchesConditions(PlayerProfile profile, Map<String, List<String>> matchersList, boolean hasCondition) throws NoSuchFieldException, IllegalAccessException {
        boolean matchesConditions = true;

        for (Map.Entry<String, List<String>> entry : matchersList.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();

            if (key.equalsIgnoreCase("items")) {
                final var inventory = profile.getInventory();
                if ((hasCondition && !inventory.keySet().containsAll(values)) || (!hasCondition && inventory.keySet().stream().anyMatch(values::contains))) {
                    matchesConditions = false;
                    break;
                }
            } else {
                final var profileAttribute = profile.getClass().getDeclaredField(key);
                profileAttribute.setAccessible(true);
                final String profileAttributeValue = (String) profileAttribute.get(profile);

                if ((hasCondition && !values.contains(profileAttributeValue) || (!hasCondition && values.contains(profileAttributeValue)))) {
                    matchesConditions = false;
                    break;
                }
            }
        }

        return matchesConditions;
    }

    private void checkClan(final PlayerProfile entity) {
        Clan clan = entity.getClan();
        if (clan != null) {
            final var optionalClan = clanRepository.findById(clan.getId());
            if (optionalClan.isEmpty()) {
                clanRepository.save(clan);
            }
        }
    }
}
