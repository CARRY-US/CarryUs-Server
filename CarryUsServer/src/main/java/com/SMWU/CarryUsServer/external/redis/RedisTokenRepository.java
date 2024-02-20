package com.SMWU.CarryUsServer.external.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RedisTokenRepository extends CrudRepository<RedisTokenVO, String> {
    Optional<RedisTokenVO> findByMemberId(String memberId);
}
