package ru.itis.springrabbitmq.consumers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.springrabbitmq.dto.PersonalDataDto;
import ru.itis.springrabbitmq.helpers.Sender;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class HaveDocumentConsumer {
    private Gson gson;
    private Sender sender;

    @RabbitListener(queues = "have_document_queue")
    public void listen(Message message) {
        PersonalDataDto personalDataDto = gson.fromJson(new String(message.getBody()), PersonalDataDto.class);
        System.out.println("Have" + new String(message.getBody()));
        sender.send(personalDataDto.getEmail(), true);
    }
}
