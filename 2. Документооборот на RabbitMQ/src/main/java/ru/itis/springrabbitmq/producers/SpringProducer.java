package ru.itis.springrabbitmq.producers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.springrabbitmq.dto.PersonalDataDto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class SpringProducer {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private Gson gson;
    @Value("${routingKey.direct.dontHaveDocument}")
    private String dontHaveDocumentDirectRoutingKey;
    @Value("${routingKey.direct.haveDocument}")
    private String haveDocumentDirectRoutingKey;
    @Value("${exchage.direct}")
    private String direct_exchange;
    @Value("${exchage.fanout}")
    private String fanout_exchange;
    @Value("${exchage.topic}")
    private String topic_exchange;
    @Value("${routingKey.topic.budget}")
    private String budgetTopicRoutingKey;

    public void send() {
        ArrayList<PersonalDataDto> personalData = readInfo();
        for (PersonalDataDto p : personalData
        ) {
            String object = gson.toJson(p);
            //fanout
            amqpTemplate.convertAndSend(fanout_exchange, "", object);
            //direct
            String routingKey = p.isHasAdditionalDocuments() ? haveDocumentDirectRoutingKey : dontHaveDocumentDirectRoutingKey;
            amqpTemplate.convertAndSend(direct_exchange, routingKey, object);
            //topic
            routingKey = p.isBudget() ? budgetTopicRoutingKey : "";
            amqpTemplate.convertAndSend(topic_exchange, routingKey, object);
        }
    }

    private static ArrayList<PersonalDataDto> readInfo() {
        ArrayList<PersonalDataDto> personalData = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("input.txt")));

            while (bufferedReader.ready()) {
                PersonalDataDto p = PersonalDataDto.builder()
                        .surname(bufferedReader.readLine())
                        .name(bufferedReader.readLine())
                        .patronymic(bufferedReader.readLine())
                        .email(bufferedReader.readLine())
                        .passportId(bufferedReader.readLine())
                        .birthDate(bufferedReader.readLine())
                        .itn(bufferedReader.readLine())
                        .pasportScan(bufferedReader.readLine())
                        .isBudget(Boolean.parseBoolean(bufferedReader.readLine()))
                        .phoneNumber(bufferedReader.readLine())
                        .group(bufferedReader.readLine())
                        .course(Integer.parseInt(bufferedReader.readLine()))
                        .hasAdditionalDocuments(Boolean.parseBoolean(bufferedReader.readLine()))
                        .build();
                personalData.add(p);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return personalData;
    }
}
