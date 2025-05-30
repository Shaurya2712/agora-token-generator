package com.example.agora;

import io.agora.media.RtcTokenBuilder2.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AgoraTokenServiceTest {
    private final String appId = "e7f19b4ccadd45cfa77f472983a8c858";
    private final String appCertificate = "3c8e689c851244d2bc90c10e93d9d619";
    private final AgoraTokenService service = new AgoraTokenService(appId, appCertificate);

    @Test
    public void testGenerateDynamicUidToken() {
        String channelName = "test-channel-123";
        int uid = 0; // Dynamic UID for Flutter app
        int durationMinutes = 30;
        String token = service.generateMeetingToken(channelName, uid, durationMinutes);
        System.out.println("Dynamic UID token (uid=0): " + token);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    public void testGeneratePatientUidToken() {
        String channelName = "test-channel-123";
        int uid = 1001; // Patient UID
        int durationMinutes = 30;
        String token = service.generateMeetingToken(channelName, uid, durationMinutes);
        System.out.println("Patient UID token: " + token);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    public void testGenerateDoctorUidToken() {
        String channelName = "test-channel-123";
        int uid = 2001; // Doctor UID
        int durationMinutes = 30;
        String token = service.generateMeetingToken(channelName, uid, durationMinutes);
        System.out.println("Doctor UID token: " + token);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }
}