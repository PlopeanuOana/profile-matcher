package com.gameloft.profile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "PLAYER_PROFILE", indexes = {@Index(name = "idx_player_id", columnList = "PLAYER_ID")})
public class PlayerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "PLAYER_ID", unique = true)
    private UUID playerId;

    private String credential;

    @CreationTimestamp
    private Timestamp created;

    @UpdateTimestamp
    private Timestamp modified;

    @Column(name = "LAST_SESSION")
    private Timestamp lastSession;

    @Column(name = "TOTAL_SPENT")
    private double totalSpent;

    @Column(name = "TOTAL_REFUND")
    private double totalRefund;

    @Column(name = "TOTAL_TRANSACTIONS")
    private int totalTransactions;

    @Column(name = "LAST_PURCHASE")
    private Timestamp lastPurchase;

    @OneToMany( cascade = CascadeType.MERGE)
    @JoinColumn(name = "PLAYER_ID")
    private Set<Device> devices = new HashSet<>();

    private int level;

    private int xp;

    @Column(name = "TOTAL_PLAYTIME")
    private int totalPlaytime;

    private String country;

    private String language;

    private Timestamp birthdate;

    private String gender;

    @ElementCollection
    @CollectionTable(name = "PLAYER_INVENTORY", joinColumns = @JoinColumn(name = "PLAYER_ID"))
    @MapKeyColumn(name = "ITEM")
    @Column(name = "QUANTITY")
    private Map<String, Integer> inventory;

    @ManyToOne
    @JoinColumn(name = "CLAN_ID")
    private Clan clan;

    @Column(name = "CUSTOM_FIELD")
    private String customField;

    @ElementCollection
    @CollectionTable(name = "ACTIVE_CAMPAIGNS", joinColumns = @JoinColumn(name = "PLAYER_ID"))
    @Column(name = "CAMPAIGN_NAME")
    private List<String> activeCampaigns;

}

