Gitter - GitHub Repo Cloning Utility

Created by Linebeck
© 2016 - 2023. All Rights Reserved.

Gitter is a user-friendly utility designed to simplify the process of cloning an unlimited amount of both public and private GitHub repositories onto a papermc server. This configuration guide will walk you through setting up Gitter for seamless repository cloning into your designated plugin directory on the server.
Configuration Steps

    SSH Keys Setup:

    Gitter supports SSH key-based authentication for cloning private repositories. To set up SSH keys:
        SSH-Key-One:
            Name: Give your SSH key a descriptive name.
            SSH-Key-Path: Specify the file path to your SSH private key. RSA is recommended.
            SSH-Key-Password: If your key is password-protected, provide the password here.

    Session Configuration:

    Sessions represent individual repository cloning configurations. Each session is linked to a specific GitHub repository. To set up a session:
        Session-One:
            Name: Assign a name to your session for reference.
            Repository: Provide the URL of the GitHub repository you want to clone.
            Directory-Location: Specify the plugin directory where you want the repository to be cloned. You may use either "/" or "\".
            Repository-Type: Choose either "DENIZEN" or "CONFIGURATION" to indicate the type of repository.
            SSH-Key-Name: If using SSH authentication, specify the name of the SSH key from the SSH Keys Setup section.

Usage Instructions

    SSH Key Setup:

    Before configuring sessions, ensure your SSH key(s) are ready. If you're using a password-protected key, make sure to provide the correct password.

    Session Configuration:
        Fill in the details for each session you want to create. For example, if you want to clone a private repository, provide the repository URL, plugin directory location, select "private" as the repository type, and specify the relevant SSH key name.

    Cloning Repositories onto the Server:

    Once your sessions are configured, you're ready to start cloning repositories onto the server:
        Run the command: /gitter <repo>
        If setup correctly, Gitter will automatically handle the rest.
        The repository will be cloned into the designated plugin directory on the server.

    Versatile Applications:

    Gitter is not limited to a single use case. Clone repositories that contain configuration files, Skript scripts, Denizen scripts, or any other content your heart desires. Make the most of Gitter's flexibility to streamline your workflow and collaboration.

Feedback and Support

Gitter is designed to enhance your GitHub repository cloning experience on a papermc server environment. If you encounter any issues during setup or usage, or if you have suggestions for improvements, please feel free to contact Linebeck at me@linebeck.com.
Legal Notice

Gitter is released under the terms of the Open Source License. Feel free to use, distribute, and modify this utility in accordance with the terms of the license. Your contributions to the project are highly appreciated and welcomed.