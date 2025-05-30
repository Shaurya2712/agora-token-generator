
package com.example.agora;

import io.agora.media.RtcTokenBuilder2.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AgoraTokenServiceTest {
    private final String appId = "e7f19b4ccadd45cfa77f472983a8c858";
    private final String appCertificate = "3c8e689c851244d2bc90c10e93d9d619";
    private final AgoraTokenService service = new AgoraTokenService(appId, appCertificate);

    @Test
    public void testGenerateRtcToken() {
        String channelName = "test-channel-123";
        int uid = 1001; // Patient UID
        Role role = Role.ROLE_PUBLISHER;
        int expireSeconds = 1800; // 30 minutes

        String token = service.generateRtcToken(channelName, uid, role, expireSeconds);
        System.out.println("Patient UID-based token: " + token);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    public void testGenerateRtcTokenWithUserAccount() {
        String channelName = "test-channel-123";
        String userAccount = "patient"; // Patient user account
        Role role = Role.ROLE_PUBLISHER;
        int expireSeconds = 1800; // 30 minutes

        String token = service.generateRtcTokenWithUserAccount(channelName, userAccount, role, expireSeconds);
        System.out.println("Patient UserAccount-based token: " + token);
        assertNotNull(token);
        assertFalse(token.isEmpty());
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