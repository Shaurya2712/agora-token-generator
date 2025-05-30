package com.example.agora;

import io.agora.media.RtcTokenBuilder2;
import io.agora.media.RtcTokenBuilder2.Role;

/**
 * Service class for generating Agora RTC tokens using the Agora RtcTokenBuilder2.
 */
public class AgoraTokenService {
    private final String appId;
    private final String appCertificate;

    /**
     * Constructs an AgoraTokenService with the specified App ID and App Certificate.
     *
     * @param appId          The Agora App ID.
     * @param appCertificate The Agora App Certificate.
     */
    public AgoraTokenService(String appId, String appCertificate) {
        this.appId = appId;
        this.appCertificate = appCertificate;
    }

    /**
     * Generates an RTC token for a user identified by a numeric UID.
     *
     * @param channelName   The name of the channel to join.
     * @param uid           The user ID (a 32-bit unsigned integer).
     * @param role          The role of the user (e.g., ROLE_PUBLISHER or ROLE_SUBSCRIBER).
     * @param expireSeconds The token expiration time in seconds.
     * @return The generated RTC token, or an empty string if generation fails.
     */
    public String generateRtcToken(String channelName, int uid, Role role, int expireSeconds) {
        RtcTokenBuilder2 tokenBuilder = new RtcTokenBuilder2();
        return tokenBuilder.buildTokenWithUid(appId, appCertificate, channelName, uid, role, expireSeconds, expireSeconds);
    }

    /**
     * Generates an RTC token for a user identified by a user account string.
     *
     * @param channelName   The name of the channel to join.
     * @param userAccount   The user account string (max 255 bytes).
     * @param role          The role of the user (e.g., ROLE_PUBLISHER or ROLE_SUBSCRIBER).
     * @param expireSeconds The token expiration time in seconds.
     * @return The generated RTC token, or an empty string if generation fails.
     */
    public String generateRtcTokenWithUserAccount(String channelName, String userAccount, Role role, int expireSeconds) {
        RtcTokenBuilder2 tokenBuilder = new RtcTokenBuilder2();
        return tokenBuilder.buildTokenWithUserAccount(appId, appCertificate, channelName, userAccount, role, expireSeconds, expireSeconds);
    }
}