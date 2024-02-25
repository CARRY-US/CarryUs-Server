package com.SMWU.CarryUsServer.external.oauth.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoMemberVO(
        Long id,
        @JsonProperty("kakao_account") KakaoAccount kakaoAccount
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record KakaoAccount(Profile profile) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Profile(String nickname, @JsonProperty("thumbnail_image_url") String thumbnailImageUrl) {}
}

