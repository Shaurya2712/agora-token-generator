package io.agora.media;

/**
 * Builds advanced RTC and RTM tokens for Agora sessions with fine-grained privilege control.
 * This class generates tokens for authentication in Agora's real-time communication and messaging services,
 * supporting multiple privileges with individual expiration timestamps.
 */
public class RtcTokenBuilder2 {
    public enum Role {
        /**
         * RECOMMENDED. Use this role for a voice/video call or a live broadcast, if
         * your scenario does not require authentication for co-hosts.
         */
        ROLE_PUBLISHER(1),
        /**
         * Only use this role if your scenario requires authentication for co-hosts.
         * Contact Agora support to enable authentication for co-hosting, otherwise
         * Role_Subscriber has the same privileges as Role_Publisher.
         *
         * @see <a href="https://docs.agora.io/en/video-calling/get-started/authentication-workflow?#co-host-token-authentication">Agora Co-host Token Authentication</a>
         */
        ROLE_SUBSCRIBER(2),;

        public int initValue;

        Role(int initValue) {
            this.initValue = initValue;
        }
    }

    /**
     * Builds an RTC token using an integer UID.
     *
     * @param appId The App ID issued by Agora. Apply for a new App ID from the Agora Dashboard if missing.
     * @param appCertificate Certificate of the application registered in the Agora Dashboard.
     * @param channelName Unique channel name for the Agora RTC session in string format. The string length must be less than 64 bytes. Supported characters include:
     *                    <ul>
     *                    <li>All lowercase English letters: a to z.</li>
     *                    <li>All uppercase English letters: A to Z.</li>
     *                    <li>All numeric characters: 0 to 9.</li>
     *                    <li>The space character.</li>
     *                    <li>!, #, $, %, &amp;, (, ), +, -, :, ;, &lt;, =, ., &gt;, ?, @, [, ], ^, _, {, }, |, ~, ,</li>
     *                    </ul>
     * @param uid User ID. A 32-bit unsigned integer ranging from 1 to (2^32-1). Must be unique. Set to 0 for dynamic UID assignment.
     * @param role ROLE_PUBLISHER: A broadcaster/host in a live-broadcast profile.
     *             ROLE_SUBSCRIBER: An audience (default) in a live-broadcast profile.
     * @param tokenExpire The token expiration time, in seconds since now (e.g., 600 for 10 minutes).
     * @param privilegeExpire The privilege expiration time, in seconds since now (e.g., 600 for 10 minutes).
     * @return The RTC token, or an empty string if an error occurs.
     */
    public String buildTokenWithUid(String appId, String appCertificate, String channelName, int uid, Role role, int tokenExpire, int privilegeExpire) {
        return buildTokenWithUserAccount(appId, appCertificate, channelName, AccessToken2.getUidStr(uid), role, tokenExpire, privilegeExpire);
    }

    /**
     * Builds an RTC token using a user account.
     *
     * @param appId The App ID issued by Agora. Apply for a new App ID from the Agora Dashboard if missing.
     * @param appCertificate Certificate of the application registered in the Agora Dashboard.
     * @param channelName Unique channel name for the Agora RTC session in string format. The string length must be less than 64 bytes. Supported characters include:
     *                    <ul>
     *                    <li>All lowercase English letters: a to z.</li>
     *                    <li>All uppercase English letters: A to Z.</li>
     *                    <li>All numeric characters: 0 to 9.</li>
     *                    <li>The space character.</li>
     *                    <li>!, #, $, %, &amp;, (, ), +, -, :, ;, &lt;, =, ., &gt;, ?, @, [, ], ^, _, {, }, |, ~, ,</li>
     *                    </ul>
     * @param account The user account, max length 255 bytes.
     * @param role ROLE_PUBLISHER: A broadcaster/host in a live-broadcast profile.
     *             ROLE_SUBSCRIBER: An audience (default) in a live-broadcast profile.
     * @param tokenExpire The token expiration time, in seconds since now (e.g., 600 for 10 minutes).
     * @param privilegeExpire The privilege expiration time, in seconds since now (e.g., 600 for 10 minutes).
     * @return The RTC token, or an empty string if an error occurs.
     */
    public String buildTokenWithUserAccount(String appId, String appCertificate, String channelName, String account, Role role, int tokenExpire,
            int privilegeExpire) {
        AccessToken2 accessToken = new AccessToken2(appId, appCertificate, tokenExpire);
        AccessToken2.Service serviceRtc = new AccessToken2.ServiceRtc(channelName, account);

        serviceRtc.addPrivilegeRtc(AccessToken2.PrivilegeRtc.PRIVILEGE_JOIN_CHANNEL, privilegeExpire);
        if (role == Role.ROLE_PUBLISHER) {
            serviceRtc.addPrivilegeRtc(AccessToken2.PrivilegeRtc.PRIVILEGE_PUBLISH_AUDIO_STREAM, privilegeExpire);
            serviceRtc.addPrivilegeRtc(AccessToken2.PrivilegeRtc.PRIVILEGE_PUBLISH_VIDEO_STREAM, privilegeExpire);
            serviceRtc.addPrivilegeRtc(AccessToken2.PrivilegeRtc.PRIVILEGE_PUBLISH_DATA_STREAM, privilegeExpire);
        }
        accessToken.addService(serviceRtc);

        try {
            return accessToken.build();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Generates an RTC token with specified privileges.
     * <p>
     * This method supports generating a token with the following privileges:
     * - Joining an RTC channel.
     * - Publishing audio in an RTC channel.
     * - Publishing video in an RTC channel.
     * - Publishing data streams in an RTC channel.
     * <p>
     * The privileges for publishing audio, video, and data streams apply only if co-host authentication is enabled.
     * A user can have multiple privileges, each valid for up to 24 hours. The SDK triggers onTokenPrivilegeWillExpire
     * and onRequestToken callbacks when the token is about to expire or has expired. Maintain privilege timestamps
     * in your app logic, as callbacks do not specify the affected privilege. Generate a new token and call renewToken
     * or joinChannel to continue.
     * <p>
     * Ensure privilege timestamps are set reasonably. For example, if the join channel privilege expires before
     * the publish audio privilege, the user is kicked off the channel and cannot publish audio, even if the audio
     * privilege is still valid.
     *
     * @param appId The App ID of your Agora project.
     * @param appCertificate The App Certificate of your Agora project.
     * @param channelName The unique channel name for the Agora RTC session in string format. The string length must be less than 64 bytes. Supported characters include:
     *                    <ul>
     *                    <li>All lowercase English letters: a to z.</li>
     *                    <li>All uppercase English letters: A to Z.</li>
     *                    <li>All numeric characters: 0 to 9.</li>
     *                    <li>The space character.</li>
     *                    <li>!, #, $, %, &amp;, (, ), +, -, :, ;, &lt;, =, ., &gt;, ?, @, [, ], ^, _, {, }, |, ~, ,</li>
     *                    </ul>
     * @param uid The user ID. A 32-bit unsigned integer ranging from 1 to (2^32-1). Must be unique. Set to 0 to skip UID authentication.
     * @param tokenExpire The token expiration time, in seconds since now (e.g., 600 for 10 minutes).
     * @param joinChannelPrivilegeExpire The join channel privilege expiration time, in seconds since now (e.g., 600 for 10 minutes).
     * @param pubAudioPrivilegeExpire The publish audio privilege expiration time, in seconds since now (e.g., 600 for 10 minutes).
     * @param pubVideoPrivilegeExpire The publish video privilege expiration time, in seconds since now (e.g., 600 for 10 minutes).
     * @param pubDataStreamPrivilegeExpire The publish data stream privilege expiration time, in seconds since now (e.g., 600 for 10 minutes).
     * @return The RTC token, or an empty string if an error occurs.
     */
    public String buildTokenWithUid(String appId, String appCertificate, String channelName, int uid, int tokenExpire, int joinChannelPrivilegeExpire,
            int pubAudioPrivilegeExpire, int pubVideoPrivilegeExpire, int pubDataStreamPrivilegeExpire) {
        return buildTokenWithUserAccount(appId, appCertificate, channelName, AccessToken2.getUidStr(uid), tokenExpire, joinChannelPrivilegeExpire,
                pubAudioPrivilegeExpire, pubVideoPrivilegeExpire, pubDataStreamPrivilegeExpire);
    }

    /**
     * Generates an RTC token with specified privileges.
     * <p>
     * This method supports generating a token with the following privileges:
     * - Joining an RTC channel.
     * - Publishing audio in an RTC channel.
     * - Publishing video in an RTC channel.
     * - Publishing data streams in an RTC channel.
     * <p>
     * The privileges for publishing audio, video, and data streams apply only if co-host authentication is enabled.
     * A user can have multiple privileges, each valid for up to 24 hours. The SDK triggers onTokenPrivilegeWillExpire
     * and onRequestToken callbacks when the token is about to expire or has expired. Maintain privilege timestamps
     * in your app logic, as callbacks do not specify the affected privilege. Generate a new token and call renewToken
     * or joinChannel to continue.
     * <p>
     * Ensure privilege timestamps are set reasonably. For example, if the join channel privilege expires before
     * the publish audio privilege, the user is kicked off the channel and cannot publish audio, even if the audio
     * privilege is still valid.
     *
     * @param appId The App ID of your Agora project.
     * @param appCertificate The App Certificate of your Agora project.
     * @param channelName The unique channel name for the Agora RTC session in string format. The string length must be less than 64 bytes. Supported characters include:
     *                    <ul>
     *                    <li>All lowercase English letters: a to z.</li>
     *                    <li>All uppercase English letters: A to Z.</li>
     *                    <li>All numeric characters: 0 to 9.</li>
     *                    <li>The space character.</li>
     *                    <li>!, #, $, %, &amp;, (, ), +, -, :, ;, &lt;, =, ., &gt;, ?, @, [, ], ^, _, {, }, |, ~, ,</li>
     *                    </ul>
     * @param account The user account, max length 255 bytes.
     * @param tokenExpire The token expiration time, in seconds since now (e.g., 600 for 10 minutes).
     * @param joinChannelPrivilegeExpire The join channel privilege expiration time, in seconds since now (e.g., 600 for 10 minutes).
     * @param pubAudioPrivilegeExpire The publish audio privilege expiration time, in seconds since now (e.g., 600 for 10 minutes).
     * @param pubVideoPrivilegeExpire The publish video privilege expiration time, in seconds since now (e.g., 600 for 10 minutes).
     * @param pubDataStreamPrivilegeExpire The publish data stream privilege expiration time, in seconds since now (e.g., 600 for 10 minutes).
     * @return The RTC token, or an empty string if an error occurs.
     */
    public String buildTokenWithUserAccount(String appId, String appCertificate, String channelName, String account, int tokenExpire,
            int joinChannelPrivilegeExpire, int pubAudioPrivilegeExpire, int pubVideoPrivilegeExpire, int pubDataStreamPrivilegeExpire) {
        AccessToken2 accessToken = new AccessToken2(appId, appCertificate, tokenExpire);
        AccessToken2.Service serviceRtc = new AccessToken2.ServiceRtc(channelName, account);

        serviceRtc.addPrivilegeRtc(AccessToken2.PrivilegeRtc.PRIVILEGE_JOIN_CHANNEL, joinChannelPrivilegeExpire);
        serviceRtc.addPrivilegeRtc(AccessToken2.PrivilegeRtc.PRIVILEGE_PUBLISH_AUDIO_STREAM, pubAudioPrivilegeExpire);
        serviceRtc.addPrivilegeRtc(AccessToken2.PrivilegeRtc.PRIVILEGE_PUBLISH_VIDEO_STREAM, pubVideoPrivilegeExpire);
        serviceRtc.addPrivilegeRtc(AccessToken2.PrivilegeRtc.PRIVILEGE_PUBLISH_DATA_STREAM, pubDataStreamPrivilegeExpire);
        accessToken.addService(serviceRtc);

        try {
            return accessToken.build();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Builds an RTC and RTM token using a user account.
     *
     * @param appId The App ID issued by Agora. Apply for a new App ID from the Agora Dashboard if missing.
     * @param appCertificate Certificate of the application registered in the Agora Dashboard.
     * @param channelName Unique channel name for the Agora RTC session in string format.
     * @param account The user account, max length 255 bytes.
     * @param role ROLE_PUBLISHER: A broadcaster/host in a live-broadcast profile.
     *             ROLE_SUBSCRIBER: An audience (default) in a live-broadcast profile.
     * @param tokenExpire The token expiration time, in seconds since now (e.g., 600 for 10 minutes).
     * @param privilegeExpire The privilege expiration time, in seconds since now (e.g., 600 for 10 minutes).
     * @return The RTC and RTM token, or an empty string if an error occurs.
     */
    public String buildTokenWithRtm(String appId, String appCertificate, String channelName, String account, Role role, int tokenExpire, int privilegeExpire) {
        AccessToken2 accessToken = new AccessToken2(appId, appCertificate, tokenExpire);
        AccessToken2.Service serviceRtc = new AccessToken2.ServiceRtc(channelName, account);

        serviceRtc.addPrivilegeRtc(AccessToken2.PrivilegeRtc.PRIVILEGE_JOIN_CHANNEL, privilegeExpire);
        if (role == Role.ROLE_PUBLISHER) {
            serviceRtc.addPrivilegeRtc(AccessToken2.PrivilegeRtc.PRIVILEGE_PUBLISH_AUDIO_STREAM, privilegeExpire);
            serviceRtc.addPrivilegeRtc(AccessToken2.PrivilegeRtc.PRIVILEGE_PUBLISH_VIDEO_STREAM, privilegeExpire);
            serviceRtc.addPrivilegeRtc(AccessToken2.PrivilegeRtc.PRIVILEGE_PUBLISH_DATA_STREAM, privilegeExpire);
        }
        accessToken.addService(serviceRtc);

        AccessToken2.Service serviceRtm = new AccessToken2.ServiceRtm(account);

        serviceRtm.addPrivilegeRtm(AccessToken2.PrivilegeRtm.PRIVILEGE_LOGIN, tokenExpire);
        accessToken.addService(serviceRtm);

        try {
            return accessToken.build();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Builds an RTC and RTM token with separate accounts and privileges.
     *
     * @param appId The App ID issued by Agora. Apply for a new App ID from the Agora Dashboard if missing.
     * @param appCertificate Certificate of the application registered in the Agora Dashboard.
     * @param channelName Unique channel name for the Agora RTC session in string format.
     * @param rtcAccount The RTC user account, max length 255 bytes.
     * @param rtcRole ROLE_PUBLISHER: A broadcaster/host in a live-broadcast profile.
     *                ROLE_SUBSCRIBER: An audience (default) in a live-broadcast profile.
     * @param rtcTokenExpire The RTC token expiration time, in seconds since now (e.g., 600 for 10 minutes).
     * @param joinChannelPrivilegeExpire The join channel privilege expiration time, in seconds since now (e.g., 600 for 10 minutes).
     * @param pubAudioPrivilegeExpire The publish audio privilege expiration time, in seconds since now (e.g., 600 for 10 minutes).
     * @param pubVideoPrivilegeExpire The publish video privilege expiration time, in seconds since now (e.g., 600 for 10 minutes).
     * @param pubDataStreamPrivilegeExpire The publish data stream privilege expiration time, in seconds since now (e.g., 600 for 10 minutes).
     * @param rtmUserId The RTM user account, max length 64 bytes.
     * @param rtmTokenExpire The RTM token expiration time, in seconds since now (e.g., 600 for 10 minutes).
     * @return The RTC and RTM token, or an empty string if an error occurs.
     */
    public String buildTokenWithRtm2(String appId, String appCertificate, String channelName, String rtcAccount, Role rtcRole, int rtcTokenExpire,
            int joinChannelPrivilegeExpire, int pubAudioPrivilegeExpire, int pubVideoPrivilegeExpire, int pubDataStreamPrivilegeExpire, String rtmUserId,
            int rtmTokenExpire) {
        AccessToken2 accessToken = new AccessToken2(appId, appCertificate, rtcTokenExpire);
        AccessToken2.Service serviceRtc = new AccessToken2.ServiceRtc(channelName, rtcAccount);

        serviceRtc.addPrivilegeRtc(AccessToken2.PrivilegeRtc.PRIVILEGE_JOIN_CHANNEL, joinChannelPrivilegeExpire);
        if (rtcRole == Role.ROLE_PUBLISHER) {
            serviceRtc.addPrivilegeRtc(AccessToken2.PrivilegeRtc.PRIVILEGE_PUBLISH_AUDIO_STREAM, pubAudioPrivilegeExpire);
            serviceRtc.addPrivilegeRtc(AccessToken2.PrivilegeRtc.PRIVILEGE_PUBLISH_VIDEO_STREAM, pubVideoPrivilegeExpire);
            serviceRtc.addPrivilegeRtc(AccessToken2.PrivilegeRtc.PRIVILEGE_PUBLISH_DATA_STREAM, pubDataStreamPrivilegeExpire);
        }
        accessToken.addService(serviceRtc);

        AccessToken2.Service serviceRtm = new AccessToken2.ServiceRtm(rtmUserId);

        serviceRtm.addPrivilegeRtm(AccessToken2.PrivilegeRtm.PRIVILEGE_LOGIN, rtmTokenExpire);
        accessToken.addService(serviceRtm);

        try {
            return accessToken.build();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}