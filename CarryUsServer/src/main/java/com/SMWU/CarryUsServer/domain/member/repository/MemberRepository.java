package com.SMWU.CarryUsServer.domain.member.repository;

import com.SMWU.CarryUsServer.domain.auth.exception.AuthException;
import com.SMWU.CarryUsServer.domain.member.entity.Member;
import com.SMWU.CarryUsServer.domain.member.entity.enums.PlatformType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static com.SMWU.CarryUsServer.domain.auth.exception.AuthExceptionType.NOT_FOUND_MEMBER;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByPlatformTypeAndPlatformId(PlatformType platformType, String platformId);
    Member saveAndFlush(Member member);

    Optional<Member> findByMemberId(Long memberId);

    default Member findByMemberIdOrElseThrowException(Long memberId){
        return findByMemberId(memberId).orElseThrow(
                () -> new AuthException(NOT_FOUND_MEMBER));
    }
}
