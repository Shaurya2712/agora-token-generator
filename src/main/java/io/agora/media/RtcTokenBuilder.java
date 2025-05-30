package io.agora.media;

/**
 * Builds RTC tokens for Agora RTC sessions using either a user ID (UID) or a user account.
 * This class generates tokens required for authentication in Agora's real-time communication services.
 */
public class RtcTokenBuilder {
    public enum Role {
        /**
         * DEPRECATED. Role_Attendee has the same privileges as Role_Publisher.
         */
        Role_Attendee(0),
        /**
         * RECOMMENDED. Use this role for a voice/video call or a live broadcast, if your scenario does not require authentication for co-hosts.
         */
        Role_Publisher(1),
        /**
         * Only use this role if your scenario requires authentication for co-hosts.
         * 
         * @see <a href="https://docs.agora.io/en/video-calling/get-started/authentication-workflow?#co-host-token-authentication">Agora Co-host Token Authentication</a>
         */
        Role_Subscriber(2),
        /**
         * DEPRECATED. Role_Admin has the same privileges as Role_Publisher.
         */
        Role_Admin(101);

        public int initValue;

        Role(int initValue) {
            this.initValue = initValue;
        }
    }

    /**
     * Builds an RTC token using an integer UID.
     *
     * @param appId The App ID issued by Agora.
     * @param appCertificate Certificate of the application registered in the Agora Dashboard.
     * @param channelName The unique channel name for the Agora RTC session in string format. The string length must be less than 64 bytes. Supported characters include:
     * <ul>
     *    <li>The 26 lowercase English letters: a to z.</li>
     *    <li>The 26 uppercase English letters: A to Z.</li>
     *    <li>The 10 digits: 0 to 9.</li>
     *    <li>The space.</li>
     *    <li>!, #, $, %, &amp;, (, ), +, -, :, ;, &lt;, =, ., &gt;, ?, @, [, ], ^, _, {, }, |, ~, ,</li>
     * </ul>
     * @param uid User ID. A 32-bit unsigned integer ranging from 1 to (2^32-1). Use 0 for dynamic UID assignment.
     * @param role The user role.
     * <ul>
     *     <li>Role_Publisher = 1: Recommended for voice/video calls or live broadcasts.</li>
     *     <li>Role_Subscriber = 2: Use for live broadcasts requiring co-host authentication. Contact Agora support to enable this feature.</li>
     * </ul>
     * @param privilegeTs The privilege expiration timestamp, in seconds since 1/1/1970. For example, to allow access for 10 minutes, set this to the current timestamp plus 600 seconds.
     * @return The generated RTC token, or an empty string if an error occurs.
     */
    public String buildTokenWithUid(String appId, String appCertificate,
            String channelName, int uid, Role role, int privilegeTs) {
        String account = uid == 0 ? "" : String.valueOf(uid);
        return buildTokenWithUserAccount(appId, appCertificate, channelName,
                account, role, privilegeTs);
    }

    /**
     * Builds an RTC token using a string user account.
     *
     * @param appId The App ID issued by Agora.
     * @param appCertificate Certificate of the application registered in the Agora Dashboard.
     * @param channelName The unique channel name for the Agora RTC session in string format. The string length must be less than 64 bytes. Supported characters include:
     * <ul>
     *    <li>The 26 lowercase English letters: a to z.</li>
     *    <li>The 26 uppercase English letters: A to Z.</li>
     *    <li>The 10 digits: 0 to 9.</li>
     *    <li>The space.</li>
     *    <li>!, #, $, %, &amp;, (, ), +, -, :, ;, &lt;, =, ., &gt;, ?, @, [, ], ^, _, {, }, |, ~, ,</li>
     * </ul>
     * @param account The user account.
     * @param role The user role.
     * <ul>
     *     <li>Role_Publisher = 1: Recommended for voice/video calls or live broadcasts.</li>
     *     <li>Role_Subscriber = 2: Use for live broadcasts requiring co-host authentication. Contact Agora support to enable this feature.</li>
     * </ul>
     * @param privilegeTs The privilege expiration timestamp, in seconds since 1/1/1970. For example, to allow access for 10 minutes, set this to the current timestamp plus 600 seconds.
     * @return The generated RTC token, or an empty string if an error occurs.
     */
    public String buildTokenWithUserAccount(String appId, String appCertificate,
            String channelName, String account, Role role, int privilegeTs) {

        // Assign appropriate access privileges to each role.
        AccessToken builder = new AccessToken(appId, appCertificate, channelName, account);
        builder.addPrivilege(AccessToken.Privileges.kJoinChannel, privilegeTs);
        if (role == Role.Role_Publisher || role == Role.Role_Subscriber || role == Role.Role_Admin) {
            builder.addPrivilege(AccessToken.Privileges.kPublishAudioStream, privilegeTs);
            builder.addPrivilege(AccessToken.Privileges.kPublishVideoStream, privilegeTs);
            builder.addPrivilege(AccessToken.Privileges.kPublishDataStream, privilegeTs);
        }

        try {
            return builder.build();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}