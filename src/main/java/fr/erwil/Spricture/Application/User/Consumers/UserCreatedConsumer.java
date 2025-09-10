package fr.erwil.Spricture.Application.User.Consumers;

import fr.erwil.Spricture.Application.User.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserCreatedConsumer {
    private static final Logger log = LogManager.getLogger(UserCreatedConsumer.class);

    @KafkaListener(topics = "user-created", groupId = "user")
    public void onUserCreated(String message){
        log.info("UserCreated event reçu : {}", message);
    }
}
