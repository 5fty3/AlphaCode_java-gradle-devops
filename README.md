# Java Application using Gradle

A Java app built with Gradle, deployed via a GitHub Actions CI/CD pipeline to AWS EC2, with monitoring and logging.

## Tech Stack

Java 17 · Gradle · GitHub Actions · AWS (EC2, S3, SSM, IAM, CloudWatch, SNS) · Nginx · Certbot

## Project Structure

```text
AlphaCode_java-gradle-devops/
├── .github/workflows/ci.yml
├── src/main/java/com/example/app/App.java
├── build.gradle
├── settings.gradle
├── gradlew
└── README.md
```

## Application

Simple HTTP app on port 8080. Returns:

Hello from Java Gradle DevOps!


## Build & Test

```bash
./gradlew build   # jar output in build/libs/
./gradlew test
```

## CI/CD Pipeline

`.github/workflows/ci.yml` handles:

Checkout → Setup Java → Gradle Build → Test → Upload JAR to S3 → Deploy via SSM → Restart via systemd → Health check → Rollback on failure

## AWS Deployment

- **S3** stores the built JAR.
- **SSM** runs deployment commands on EC2 with no SSH needed.
- **systemd** manages the app as a service:
```bash
  sudo systemctl restart java-gradle-app
  sudo systemctl status java-gradle-app
```

## Nginx + HTTPS

Nginx reverse-proxies port 443 → `localhost:8080`. HTTPS via Let's Encrypt/Certbot.

Live at: `https://app.5fty3.name.ng`

## Health Check & Rollback

```bash
curl -f http://localhost:8080
```

Before each deploy, the current JAR is backed up. If the health check fails after deploy, the backup is restored and the service restarted.

## Logging & Monitoring

- App logs: `/home/ubuntu/app/app.log` → shipped to CloudWatch log group `/java-gradle-devops/application`
- CloudWatch alarm: `CPUUtilization > 80%` for 5 min → SNS email alert

## Run Locally

```bash
git clone https://github.com/5fty3/AlphaCode_java-gradle-devops.git
cd AlphaCode_java-gradle-devops
./gradlew build
java -jar build/libs/java-gradle-devops-1.0.0.jar
curl http://localhost:8080
```

## DevOps Concepts Demonstrated

CI/CD · automated builds & tests · artifact management · IAM-based deployment · health checks & rollback · centralized logging · monitoring & alerting
