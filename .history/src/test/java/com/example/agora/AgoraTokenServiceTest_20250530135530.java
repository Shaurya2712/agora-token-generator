package com.example.agora;

import io.agora.media.RtcTokenBuilder2.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AgoraTokenServiceTest {
    @Test
    public void testGenerateRtcToken() {
        String appId = "e7f19b4ccadd45cfa77f472983a8c858";
        String appCertificate = "3c8e689c851244d2bc90c10e93d9d619";
        String channelName = "test-channel-123";
        int uid = 12345;
        int expireSeconds = 3600;
        AgoraTokenService service = new AgoraTokenService(appId, appCertificate);
        String token = service.generateRtcToken(channelName, uid, Role.ROLE_PUBLISHER, expireSeconds);
        assertNotNull(token);
        assertFalse(token.isEmpty());
        System.out.println("__testGenerateRtcToken : " + token);
    }

    @Test
    public void testGenerateRtcTokenWithUserAccount() {
        String appId = "e7f19b4ccadd45cfa77f472983a8c858";
        String appCertificate = "3c8e689c851244d2bc90c10e93d9d619";
        String channelName = "test-channel-123";
        String userAccount = "test-user-123";
        int expireSeconds = 3600;
        AgoraTokenService service = new AgoraTokenService(appId, appCertificate);
        String token = service.generateRtcTokenWithUserAccount(channelName, userAccount, Role.ROLE_PUBLISHER, expireSeconds);
        assertNotNull(token);
        assertFalse(token.isEmpty());
        System.out.println("__testGenerateRtcTokenWithUserAccount : " + token);
    }
}
