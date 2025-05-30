package com.example.agora;

import io.agora.media.RtcTokenBuilder2;
import io.agora.media.RtcTokenBuilder2.Role;

public class AgoraTokenService {
    private final String appId;
    private final String appCertificate;

    public AgoraTokenService(String appId, String appCertificate) {
        this.appId = appId;
        this.appCertificate = appCertificate;
    }

    public String generateRtcToken(String channelName, int uid, Role role, int expireSeconds) {
        RtcTokenBuilder2 tokenBuilder = new RtcTokenBuilder2();
        String token = tokenBuilder.buildTokenWithUid(appId, appCertificate, channelName, uid, role, expireSeconds, expireSeconds);
        System.out.println("UID-based token: UID=" + uid + ", Channel=" + channelName + ", Expires=" + expireSeconds + "s");
        return token;
    }

    public String generateRtcTokenWithUserAccount(String channelName, String userAccount, Role role, int expireSeconds) {
        RtcTokenBuilder2 tokenBuilder = new RtcTokenBuilder2();
        String token = tokenBuilder.buildTokenWithUserAccount(appId, appCertificate, channelName, userAccount, role, expireSeconds, expireSeconds);
        System.out.println("UserAccount-based token: User=" + userAccount + ", Channel=" + channelName + ", Expires=" + expireSeconds + "s");
        return token;
    }

    public String generateMeetingToken(String channelName, int uid, int durationMinutes) {
        int expireSeconds = durationMinutes * 60;
        return generateRtcToken(channelName, uid, Role.ROLE_PUBLISHER, expireSeconds);
    }

    public String generateMeetingTokenWithUserAccount(String channelName, String userAccount, int durationMinutes) {
        int expireSeconds = durationMinutes * 60;
        return generateRtcTokenWithUserAccount(channelName, userAccount, Role.ROLE_PUBLISHER, expireSeconds);
    }
}