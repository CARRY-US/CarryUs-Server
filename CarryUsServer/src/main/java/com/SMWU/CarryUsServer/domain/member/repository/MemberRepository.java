package com.SMWU.CarryUsServer.domain.member.repository;

import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.member.entity.enums.PlatformType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Long, Member> {
    Optional<Member> findByPlatformTypeAndPlatformId(PlatformType platformType, String PlatformId);
}
