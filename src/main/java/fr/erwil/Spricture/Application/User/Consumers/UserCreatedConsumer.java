package fr.erwil.Spricture.Application.User.Consumers;

import fr.erwil.Spricture.Application.User.User;
import fr.erwil.Spricture.Configuration.AdminProperties;
import fr.erwil.Spricture.Tools.Mail.MailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserCreatedConsumer {
    private static final Logger log = LogManager.getLogger(UserCreatedConsumer.class);
    private final MailService mailService;
    private final AdminProperties adminProperties;

    public UserCreatedConsumer(MailService mailService, AdminProperties adminProperties) {
        this.mailService = mailService;
        this.adminProperties = adminProperties;
    }

    @KafkaListener(topics = "user-created", groupId = "user")
    public void onUserCreated(String mail){
        mailService.sendSimpleMessage(adminProperties.getMail(), "New account created in Photone.", "Here is the mail of the new account : "+ mail);
    }
}
