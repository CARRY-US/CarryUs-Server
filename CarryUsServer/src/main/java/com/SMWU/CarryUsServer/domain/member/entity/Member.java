package com.SMWU.CarryUsServer.domain.member.entity;

import com.SMWU.CarryUsServer.domain.member.entity.enums.PlatformType;
import com.SMWU.CarryUsServer.domain.member.entity.enums.Role;
import com.SMWU.CarryUsServer.global.entity.AuditingTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MEMBERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends AuditingTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String platformId;

    private String name;

    @Enumerated(EnumType.STRING)
    private PlatformType platformType;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String phoneNumber;

    private String profileImg;

    @Builder
    public Member(Long memberId, String platformId, String name, PlatformType platformType, Role role, String phoneNumber, String profileImg) {
        this.memberId = memberId;
        this.platformId = platformId;
        this.name = name;
        this.platformType = platformType;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.profileImg = profileImg;
    }
}
