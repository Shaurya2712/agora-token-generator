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
        return tokenBuilder.buildTokenWithUid(appId, appCertificate, channelName, uid, role, expireSeconds, expireSeconds);
    }

    public String generateRtcTokenWithUserAccount(String channelName, String userAccount, Role role, int expireSeconds) {
        RtcTokenBuilder2 tokenBuilder = new RtcTokenBuilder2();
        return tokenBuilder.buildTokenWithUserAccount(appId, appCertificate, channelName, userAccount, role, expireSeconds, expireSeconds);
    }
}
