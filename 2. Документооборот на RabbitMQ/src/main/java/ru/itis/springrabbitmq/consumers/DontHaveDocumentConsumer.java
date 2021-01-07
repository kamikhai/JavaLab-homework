package ru.itis.springrabbitmq.consumers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.springrabbitmq.dto.PersonalDataDto;
import ru.itis.springrabbitmq.helpers.Sender;

@Component
public class DontHaveDocumentConsumer {
    @Autowired
    private Gson gson;
    @Autowired
    private Sender sender;

    @RabbitListener(queues = "dont_have_document_queue")
    public void listen(Message message) {
        PersonalDataDto personalDataDto = gson.fromJson(new String(message.getBody()), PersonalDataDto.class);
        System.out.println("Dont have" + new String(message.getBody()));
        sender.send(personalDataDto.getEmail(), false);
    }
}
