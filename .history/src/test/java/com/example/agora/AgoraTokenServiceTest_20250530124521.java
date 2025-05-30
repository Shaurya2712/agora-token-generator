package com.example.agora;

import io.agora.media.RtcTokenBuilder2.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AgoraTokenServiceTest {
    @Test
    public void testGenerateRtcToken() {
        String appId = "your-app-id";
        String appCertificate = "your-app-certificate";
        String channelName = "test-channel";
        int uid = 12345;
        int expireSeconds = 3600;
        AgoraTokenService service = new AgoraTokenService(appId, appCertificate);
        String token = service.generateRtcToken(channelName, uid, Role.PUBLISHER, expireSeconds);
        assertNotNull(token);
        assertFalse(token.isEmpty());
        System.out.println("Generated Token: " + token);
    }

    @Test
    public void testGenerateRtcTokenWithUserAccount() {
        String appId = "your-app-id";
        String appCertificate = "your-app-certificate";
        String channelName = "test-channel";
        String userAccount = "test-user";
        int expireSeconds = 3600;
        AgoraTokenService service = new AgoraTokenService(appId, appCertificate);
        String token = service.generateRtcTokenWithUserAccount(channelName, userAccount, Role.PUBLISHER, expireSeconds);
        assertNotNull(token);
        assertFalse(token.isEmpty());
        System.out.println("Generated Token: " + token);
    }
}
