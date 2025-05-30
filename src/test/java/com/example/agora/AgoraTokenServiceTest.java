package com.example.agora;
import io.agora.media.RtcTokenBuilder2.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AgoraTokenServiceTest {
    // Use your actual Agora credentials here
    private final String appId = "e7f19b4ccadd45cfa77f472983a8c858";
    private final String appCertificate = "3c8e689c851244d2bc90c10e93d9d619";
    private final AgoraTokenService tokenService = new AgoraTokenService(appId, appCertificate);

    @Test
    public void testGenerateTokenWithUid() {
        // Test parameters
        String channelName = "test-channel";
        int uid = 12345;
        Role role = Role.ROLE_PUBLISHER;
        int expireSeconds = 3600; // 1 hour

        // Generate token
        String token = tokenService.generateRtcToken(channelName, uid, role, expireSeconds);

        // Verify token
        assertNotNull(token, "Token should not be null");
        assertFalse(token.isEmpty(), "Token should not be empty");
        System.out.println("Generated token with UID: " + token);
    }

    @Test
    public void testGenerateTokenWithUserAccount() {
        // Test parameters
        String channelName = "test-channel";
        String userAccount = "test-user-123";
        Role role = Role.ROLE_PUBLISHER;
        int expireSeconds = 3600; // 1 hour

        // Generate token
        String token = tokenService.generateRtcTokenWithUserAccount(channelName, userAccount, role, expireSeconds);

        // Verify token
        assertNotNull(token, "Token should not be null");
        assertFalse(token.isEmpty(), "Token should not be empty");
        System.out.println("Generated token with user account: " + token);
    }

    @Test
    public void testGenerateTokenWithSubscriberRole() {
        // Test parameters
        String channelName = "test-channel";
        int uid = 12345;
        Role role = Role.ROLE_SUBSCRIBER;
        int expireSeconds = 3600; // 1 hour

        // Generate token
        String token = tokenService.generateRtcToken(channelName, uid, role, expireSeconds);

        // Verify token
        assertNotNull(token, "Token should not be null");
        assertFalse(token.isEmpty(), "Token should not be empty");
        System.out.println("Generated token with subscriber role: " + token);
    }

    @Test
    public void testGenerateTokenWithDifferentExpiration() {
        // Test parameters
        String channelName = "test-channel";
        int uid = 0;
        Role role = Role.ROLE_PUBLISHER;
        int expireSeconds = 7200; // 2 hours

        // Generate token
        String token = tokenService.generateRtcToken(channelName, uid, role, expireSeconds);

        // Verify token
        assertNotNull(token, "Token should not be null");
        assertFalse(token.isEmpty(), "Token should not be empty");
        System.out.println("Generated token with 2-hour expiration: " + token);
    }
}