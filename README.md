# captain-cybot-adventure-backend
Backend for Captain Cybot's Adventure web-browser game

Setup:

Database:
Install Docker Desktop (https://www.docker.com/products/docker-desktop/)
Run docker container using: docker run  --name captaincybotdb -p 5432:5432 -e POSTGRES_USER=captain -e POSTGRES_PASSWORD=cybot -d postgres

Backend:
Install IntelliJ (https://www.jetbrains.com/idea/)
Clone captain-cybot-adventure-backend repository (https://github.com/aelsammak/captain-cybot-adventure-backend)
Open repository in IntelliJ
Open pom.xml from root directory and ensure Maven dependencies are installed
Create a private and public key pair using openssl named private.pem and public.pem respectively
Create a new directory in src/main/resources named certs and place private.pem and public.pem inside the new directory
OPTIONAL: If password recovery is wanted, update spring.mail.username and spring.mail.password in src/main/resources/application.properties to a personal account (password de-activated for repository)
Run src/main/java/captain.cybot.adventure.backend/CaptainCybotAdventureBackendApplication file to start backend service

Frontend:
Ensure Node.js is installed on the system for npm commands (https://nodejs.org/en/download)
Install Visual Studio Code (https://code.visualstudio.com/)
Clone captain-cybot-adventure-frontend repository (https://github.com/aelsammak/captain-cybot-adventure-frontend)
Open Repository in Visual Studio Code
Open terminal in IDE and type "npm install"
After npm install complete run "npm start" command
