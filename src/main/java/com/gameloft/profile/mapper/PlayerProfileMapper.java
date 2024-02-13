package com.gameloft.profile.mapper;

import com.gameloft.profile.entity.Clan;
import com.gameloft.profile.entity.Device;
import com.gameloft.profile.entity.PlayerProfile;
import com.gameloft.profile.model.create.ClanCreateDTO;
import com.gameloft.profile.model.create.DeviceCreateDTO;
import com.gameloft.profile.model.create.PlayerProfileCreateDTO;
import com.gameloft.profile.model.retrieve.DeviceDTO;
import com.gameloft.profile.model.retrieve.PlayerProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Mapper for {@link PlayerProfile}
 */

@Mapper(componentModel = "spring")
public interface PlayerProfileMapper {
    PlayerProfileDTO toDTO(PlayerProfile entity);

    DeviceDTO toDTO(Device device);

    @Mapping(target = "created", expression = "java(toTimestamp(dto.created()))")
    PlayerProfile toEntity(PlayerProfileCreateDTO dto);

    Clan toEntity(ClanCreateDTO clanCreateDTO);

    Device toEntity(DeviceCreateDTO dto);

    default Timestamp toTimestamp(String value) {
        if (value == null) {
            return null;
        }
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX");
        LocalDateTime localDateTime = LocalDateTime.parse(value, formatter);
        return Timestamp.from(localDateTime.toInstant(ZoneOffset.UTC));
    }
}
