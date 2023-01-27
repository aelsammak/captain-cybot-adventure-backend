package captain.cybot.adventure.backend.service;


import captain.cybot.adventure.backend.model.user.EmailDetails;

public interface EmailService {
    boolean sendSimpleMail(EmailDetails details);

    boolean sendMailWithAttachment(EmailDetails details);
}