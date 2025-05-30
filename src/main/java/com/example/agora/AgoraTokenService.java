package com.example.agora;
import io.agora.media.RtcTokenBuilder2;
import io.agora.media.RtcTokenBuilder2.Role;

/**
 * Service class for generating Agora RTC tokens.
 * This class provides methods to generate tokens for both UID-based and user account-based authentication.
 */
public class AgoraTokenService {
    private final String appId;
    private final String appCertificate;

    /**
     * Creates a new AgoraTokenService with the specified credentials.
     *
     * @param appId          Your Agora App ID
     * @param appCertificate Your Agora App Certificate
     */
    public AgoraTokenService(String appId, String appCertificate) {
        this.appId = appId;
        this.appCertificate = appCertificate;
    }

    /**
     * Generates an RTC token for joining a channel with a numeric UID.
     *
     * @param channelName    The name of the channel to join
     * @param uid           The user ID (use 0 for auto-assigned UID)
     * @param role          The user role (ROLE_PUBLISHER or ROLE_SUBSCRIBER)
     * @param expireSeconds Token expiration time in seconds (e.g., 3600 for 1 hour)
     * @return The generated token
     */
    public String generateRtcToken(String channelName, int uid, Role role, int expireSeconds) {
        RtcTokenBuilder2 tokenBuilder = new RtcTokenBuilder2();
        String token = tokenBuilder.buildTokenWithUid(appId, appCertificate, channelName, uid, role, expireSeconds, expireSeconds);
        System.out.println("UID-based token: UID=" + uid + ", Channel=" + channelName + ", Expires=" + expireSeconds + "s");
        return token;
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
    public String generateRtcTokenWithUserAccount(String channelName, String userAccount, Role role, int expireSeconds) {
        RtcTokenBuilder2 tokenBuilder = new RtcTokenBuilder2();
        String token = tokenBuilder.buildTokenWithUserAccount(appId, appCertificate, channelName, userAccount, role, expireSeconds, expireSeconds);
        System.out.println("UserAccount-based token: User=" + userAccount + ", Channel=" + channelName + ", Expires=" + expireSeconds + "s");
        return token;
    }

    /**
     * Generates a meeting token with publisher role for a numeric UID.
     * This is a convenience method that sets the role to ROLE_PUBLISHER.
     *
     * @param channelName     The name of the channel to join
     * @param uid            The user ID
     * @param durationMinutes Token duration in minutes
     * @return The generated token
     */
    public String generateMeetingToken(String channelName, int uid, int durationMinutes) {
        int expireSeconds = durationMinutes * 60;
        return generateRtcToken(channelName, uid, Role.ROLE_PUBLISHER, expireSeconds);
    }

    /**
     * Generates a meeting token with publisher role for a user account.
     * This is a convenience method that sets the role to ROLE_PUBLISHER.
     *
     * @param channelName     The name of the channel to join
     * @param userAccount    The user account
     * @param durationMinutes Token duration in minutes
     * @return The generated token
     */
    public String generateMeetingTokenWithUserAccount(String channelName, String userAccount, int durationMinutes) {
        int expireSeconds = durationMinutes * 60;
        return generateRtcTokenWithUserAccount(channelName, userAccount, Role.ROLE_PUBLISHER, expireSeconds);
    }
}