package com.example.agora;

import io.agora.media.RtcTokenBuilder2;
import io.agora.media.RtcTokenBuilder2.Role;

/**
 * Service class for generating Agora RTC tokens using the Agora RtcTokenBuilder2.
 * Designed for universal use in Spring Boot projects to generate tokens for meetings.
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
     * @param channelName   The name of the channel to join (e.g., test-channel-123).
     * @param uid           The user ID (a 32-bit unsigned integer).
     * @param role          The role of the user (e.g., ROLE_PUBLISHER or ROLE_SUBSCRIBER).
     * @param expireSeconds The token expiration time in seconds.
     * @return The generated RTC token.
     */
    public String generateRtcToken(String channelName, int uid, Role role, int expireSeconds) {
        RtcTokenBuilder2 tokenBuilder = new RtcTokenBuilder2();
        String token = tokenBuilder.buildTokenWithUid(appId, appCertificate, channelName, uid, role, expireSeconds, expireSeconds);
        System.out.println("Generated UID-based token for UID: " + uid + ", Channel: " + channelName + ", Expires in: " + expireSeconds + "s");
        return token;
    }

    /**
     * Generates an RTC token for a user identified by a user account string.
     *
     * @param channelName   The name of the channel to join (e.g., test-channel-123).
     * @param userAccount   The user account string (max 255 bytes).
     * @param role          The role of the user (e.g., ROLE_PUBLISHER or ROLE_SUBSCRIBER).
     * @param expireSeconds The token expiration time in seconds.
     * @return The generated RTC token.
     */
    public String generateRtcTokenWithUserAccount(String channelName, String userAccount, Role role, int expireSeconds) {
        RtcTokenBuilder2 tokenBuilder = new RtcTokenBuilder2();
        String token = tokenBuilder.buildTokenWithUserAccount(appId, appCertificate, channelName, userAccount, role, expireSeconds, expireSeconds);
        System.out.println("Generated UserAccount-based token for User: " + userAccount + ", Channel: " + channelName + ", Expires in: " + expireSeconds + "s");
        return token;
    }

    /**
     * Generates an RTC token for a meeting with a specific duration.
     *
     * @param channelName    The name of the channel to join (e.g., test-channel-123).
     * @param uid            The user ID (a 32-bit unsigned integer).
     * @param role           The role of the user (e.g., ROLE_PUBLISHER or ROLE_SUBSCRIBER).
     * @param durationMinutes The meeting duration in minutes.
     * @return The generated RTC token.
     */
    public String generateMeetingToken(String channelName, int uid, Role role, int durationMinutes) {
        int expireSeconds = durationMinutes * 60;
        return generateRtcToken(channelName, uid, role, expireSeconds);
    }

    /**
     * Generates an RTC token for a meeting with a specific duration using a user account.
     *
     * @param channelName    The name of the channel to join (e.g., test-channel-123).
     * @param userAccount    The user account string (max 255 bytes).
     * @param role           The role of the user (e.g., ROLE_PUBLISHER or ROLE_SUBSCRIBER).
     * @param durationMinutes The meeting duration in minutes.
     * @return The generated RTC token.
     */
    public String generateMeetingTokenWithUserAccount(String channelName, String userAccount, Role role, int durationMinutes) {
        int expireSeconds = durationMinutes * 60;
        return generateRtcTokenWithUserAccount(channelName, userAccount, role, expireSeconds);
    }
}
