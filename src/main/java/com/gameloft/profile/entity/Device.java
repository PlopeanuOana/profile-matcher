package com.gameloft.profile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "DEVICE")
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String model;
    private String carrier;

    private String firmware;

    @Column(name = "PLAYER_ID")
    @JoinColumn(name = "PLAYER_ID", table = "PLAYER_PROFILE", referencedColumnName = "ID")
    private UUID playerId;
}
