package com.gdsc.jmt.domain.user.dto;

public record LogoutRequest(String accessToken, String refreshToken) {
}
