Agora Token Generator
A Java library for generating Agora RTC tokens to authenticate users in real-time communication sessions. This library supports fine-grained privilege control for Agora channels, used in projects like agora-token-server for video call applications.
Features

Generate tokens for Agora RTC sessions with UID or user account.
Support for publisher and subscriber roles.
Configurable privilege expiration times.

Prerequisites

Java 8
Maven 3.6+
GitHub account with access to Shaurya2712/agora-token-generator
Personal Access Token (PAT) with read:packages scope (each user must generate their own)

Adding the Dependency
The library is hosted on GitHub Packages. Add it to your Maven project’s pom.xml:
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/Shaurya2712/agora-token-generator</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>com.example</groupId>
        <artifactId>agora-token-generator</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>

Authenticate with GitHub Packages
Each user must authenticate with their own GitHub account and PAT:

Generate a PAT:
Go to GitHub > Settings > Developer settings > Personal access tokens > Generate new token (classic).
Select read:packages scope.
Copy the token (e.g., ghp_...).


Update ~/.m2/settings.xml:<settings>
    <servers>
        <server>
            <id>github</id>
            <username>YOUR_GITHUB_USERNAME</username>
            <password>YOUR_PAT</password>
        </server>
    </servers>
</settings>


Repository Access (for private repository):
Ensure you’re a collaborator on https://github.com/Shaurya2712/agora-token-generator.
Contact the repository owner (Shaurya) to be added if you lack access.



Usage Example
Generate a token for an Agora RTC channel:
import io.agora.media.RtcTokenBuilder2;
import io.agora.media.RtcTokenBuilder2.Role;

public class TokenExample {
    public static void main(String[] args) {
        String appId = "e7f19b4ccadd45cfa77f472983a8c858";
        String appCertificate = "your-app-certificate";
        String channelName = "test-channel-123";
        int uid = 0; // Dynamic UID
        int tokenExpire = 600; // 10 minutes
        int privilegeExpire = 600;

        RtcTokenBuilder2 builder = new RtcTokenBuilder2();
        String token = builder.buildTokenWithUid(
            appId, appCertificate, channelName, uid, Role.ROLE_PUBLISHER, tokenExpire, privilegeExpire
        );

        System.out.println("Generated Token: " + token);
    }
}

Replace your-app-certificate with your Agora App Certificate.
Building the Project (Optional)
To modify the library:

Clone the repository:git clone https://github.com/Shaurya2712/agora-token-generator.git
cd agora-token-generator


Build and install locally:mvn clean install



Testing
Test token generation with agora-token-server:
curl "http://localhost:8080/token?channelName=test-channel-123&uid=0"

Contributing

Fork the repository.
Create a branch: git checkout -b feature/your-feature.
Commit changes: git commit -m "Add your feature".
Push: git push origin feature/your-feature.
Open a pull request.

License
MIT License (see LICENSE).
Contact
For issues, contact Shaurya or open a GitHub issue.
