# Captain Cybot's Adventure Backend

## Setup:

### Database:
1. Install Docker Desktop (https://www.docker.com/products/docker-desktop/)
2. Run docker container using: docker run  --name captaincybotdb -p 5432:5432 -e POSTGRES_USER=captain -e POSTGRES_PASSWORD=cybot -d postgres

### Backend:
1. Install IntelliJ (https://www.jetbrains.com/idea/)
2. Clone captain-cybot-adventure-backend repository (https://github.com/aelsammak/captain-cybot-adventure-backend)
3. Open repository in IntelliJ
4. Open pom.xml from root directory and ensure Maven dependencies are installed
5. Create a private and public key pair using openssl named private.pem and public.pem respectively
6. Create a new directory in src/main/resources named certs and place private.pem and public.pem inside the new directory

#### OPTIONAL: 
1. If password recovery is wanted, update spring.mail.username and spring.mail.password in src/main/resources/application.properties to a personal account (password de activated for repository)
2. Run src/main/java/captain.cybot.adventure.backend/CaptainCybotAdventureBackendApplication file to start backend service

### Frontend:
1. Ensure Node.js is installed on the system for npm commands (https://nodejs.org/en/download)
2. Install Visual Studio Code (https://code.visualstudio.com/)
3. Clone captain-cybot-adventure-frontend repository (https://github.com/aelsammak/captain-cybot-adventure-frontend)
4. Open Repository in Visual Studio Code
5. Open terminal in IDE and type "npm install"
6. After npm install complete run "npm start" command
