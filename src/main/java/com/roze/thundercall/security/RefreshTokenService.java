package com.roze.thundercall.security;

import com.roze.thundercall.entity.RefreshToken;
import com.roze.thundercall.repository.RefreshTokenRepository;
import com.roze.thundercall.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${app.jwt.refresh-expiration}")
    private Long refreshTokenDuration;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshToken createRefreshToken(Long userId) {
        Optional<RefreshToken> existingRefreshToken = refreshTokenRepository.findByUserId(userId);
        if (existingRefreshToken.isPresent()) {
            RefreshToken refreshToken = existingRefreshToken.get();
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDuration));
            return refreshTokenRepository.save(refreshToken);
        }
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDuration));
        refreshToken.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(refreshToken);
    }

}
