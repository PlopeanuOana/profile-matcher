package com.gameloft.profile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "CLAN")
@AllArgsConstructor
@NoArgsConstructor
public class Clan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "CLAN_ID")
    private Set<PlayerProfile> playerProfile;
}
