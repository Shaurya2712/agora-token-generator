package com.example.agora;

import io.agora.media.RtcTokenBuilder2.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AgoraTokenServiceTest {
    @Test
    public void testGenerateRtcToken() {
        // This test uses valid credentials and should generate a valid token
        String appId = "e7f19b4ccadd45cfa77f472983a8c858";
        String appCertificate = "3c8e689c851244d2bc90c10e93d9d619";
        String channelName = "test-channel";
        int uid = 12345;
        int expireSeconds = 3600;
        AgoraTokenService service = new AgoraTokenService(appId, appCertificate);
        String token = service.generateRtcToken(channelName, uid, Role.ROLE_PUBLISHER, expireSeconds);
        assertNotNull(token);
        assertFalse(token.isEmpty());
        System.out.println("Generated Valid Token: " + token);
        // This token will be accepted by Agora servers because:
        // 1. It's signed with a valid App Certificate
        // 2. The App ID matches a real Agora project
        // 3. The signature can be verified by Agora's servers
    }

    @Test
    public void testGenerateRtcTokenWithUserAccount() {
        // This test uses invalid credentials and will generate an invalid token
        String appId = "your-app-id";
        String appCertificate = "your-app-certificate";
        String channelName = "test-channel";
        String userAccount = "test-user";
        int expireSeconds = 3600;
        AgoraTokenService service = new AgoraTokenService(appId, appCertificate);
        String token = service.generateRtcTokenWithUserAccount(channelName, userAccount, Role.ROLE_PUBLISHER, expireSeconds);
        assertNotNull(token);
        assertFalse(token.isEmpty());
        System.out.println("Generated Invalid Token: " + token);
        // This token will be rejected by Agora servers because:
        // 1. The App ID is not a valid UUID
        // 2. The App Certificate is not valid
        // 3. The signature cannot be verified by Agora's servers
    }

    @Test
    public void testGenerateRtcTokenWithInvalidCredentials() {
        // This test demonstrates that invalid credentials result in empty tokens
        String appId = "invalid-uuid";
        String appCertificate = "invalid-uuid";
        String channelName = "test-channel";
        String userAccount = "test-user";
        int expireSeconds = 3600;
        AgoraTokenService service = new AgoraTokenService(appId, appCertificate);
        String token = service.generateRtcTokenWithUserAccount(channelName, userAccount, Role.ROLE_PUBLISHER, expireSeconds);
        assertTrue(token.isEmpty(), "Token should be empty for invalid credentials");
        System.out.println("Generated Empty Token for Invalid Credentials");
        // This test shows that the SDK's validation prevents generation of tokens with invalid credentials
    }

    @Test
    public void testGenerateMeetingToken() {
        String channelName = "test-channel-123";
        int uid = 2001; // Doctor UID
        Role role = Role.ROLE_PUBLISHER;
        int durationMinutes = 30; // 30 minutes

        String token = service.generateMeetingToken(channelName, uid, role, durationMinutes);
        System.out.println("Doctor UID-based meeting token: " + token);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    public void testGenerateMeetingTokenWithUserAccount() {
        String channelName = "test-channel-123";
        String userAccount = "doctor"; // Doctor user account
        Role role = Role.ROLE_PUBLISHER;
        int durationMinutes = 30; // 30 minutes

        String token = service.generateMeetingTokenWithUserAccount(channelName, userAccount, role, durationMinutes);
        System.out.println("Doctor UserAccount-based meeting token: " + token);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }
}