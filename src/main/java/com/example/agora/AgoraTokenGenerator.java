package com.example.agora;

import io.agora.media.RtcTokenBuilder2;
import io.agora.media.RtcTokenBuilder2.Role;

/**
 * A simple utility class for generating Agora RTC tokens.
 */
public class AgoraTokenGenerator {
    private final String appId;
    private final String appCertificate;

    /**
     * Creates a new AgoraTokenGenerator with the specified credentials.
     *
     * @param appId          Your Agora App ID
     * @param appCertificate Your Agora App Certificate
     */
    public AgoraTokenGenerator(String appId, String appCertificate) {
        this.appId = appId;
        this.appCertificate = appCertificate;
    }

    /**
     * Generates an RTC token for joining a channel.
     *
     * @param channelName    The name of the channel to join
     * @param uid           The user ID (use 0 for auto-assigned UID)
     * @param role          The user role (ROLE_PUBLISHER or ROLE_SUBSCRIBER)
     * @param expireSeconds Token expiration time in seconds (e.g., 3600 for 1 hour)
     * @return The generated token
     */
    public String generateToken(String channelName, int uid, Role role, int expireSeconds) {
        RtcTokenBuilder2 tokenBuilder = new RtcTokenBuilder2();
        return tokenBuilder.buildTokenWithUid(appId, appCertificate, channelName, uid, role, expireSeconds, expireSeconds);
    }

    /**
     * Generates an RTC token for joining a channel with a user account.
     *
     * @param channelName    The name of the channel to join
     * @param userAccount    The user account (max 255 bytes)
     * @param role          The user role (ROLE_PUBLISHER or ROLE_SUBSCRIBER)
     * @param expireSeconds Token expiration time in seconds (e.g., 3600 for 1 hour)
     * @return The generated token
     */
    public String generateTokenWithUserAccount(String channelName, String userAccount, Role role, int expireSeconds) {
        RtcTokenBuilder2 tokenBuilder = new RtcTokenBuilder2();
        return tokenBuilder.buildTokenWithUserAccount(appId, appCertificate, channelName, userAccount, role, expireSeconds, expireSeconds);
    }
} 