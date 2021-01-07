package ru.itis.springrabbitmq.producers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import ru.itis.springrabbitmq.dto.PersonalDataDto;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class Producer {
    //fanout
    private final static String DATA_EXCHANGE = "data";
    private final static String EXCHANGE_TYPE_1 = "fanout";

    //direct
    private final static String HAVE_DOCUMENTS_QUEUE = "have_document_queue";
    private final static String DONT_HAVE_DOCUMENTS_QUEUE = "dont_have_document_queue";
    private final static String ROUTING_KEY_1 = "have";
    private final static String ROUTING_KEY_2 = "dontHave";
    private final static String DOCUMENTS_EXCHANGE = "documents_direct_exchange";
    private final static String EXCHANGE_TYPE_2 = "direct";

    //topic
    private final static String IS_BUDGET_ROUTING_KEY = "students.isBudget";
    private final static String IS_NOT_BUDGET_ROUTING_KEY = "students.isNotBudget";
    private final static String BUDGET_EXCHANGE = "budget_topic_exchange";
    private final static String EXCHANGE_TYPE_3 = "topic";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            //fanout
            Channel fanoutChannel = connection.createChannel();
            fanoutChannel.exchangeDeclare(DATA_EXCHANGE, EXCHANGE_TYPE_1);
            //direct
            Channel directChannel = connection.createChannel();
            directChannel.exchangeDeclare(DOCUMENTS_EXCHANGE, EXCHANGE_TYPE_2);
            directChannel.queueBind(HAVE_DOCUMENTS_QUEUE, DOCUMENTS_EXCHANGE, ROUTING_KEY_1);
            directChannel.queueBind(DONT_HAVE_DOCUMENTS_QUEUE, DOCUMENTS_EXCHANGE, ROUTING_KEY_2);
            //topic
            Channel topicChannel = connection.createChannel();
            topicChannel.exchangeDeclare(BUDGET_EXCHANGE, EXCHANGE_TYPE_3);

            ArrayList<PersonalDataDto> personalData = readInfo();

            for (PersonalDataDto p : personalData) {
                //for fanout
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(p);
                objectOutputStream.flush();
                byte[] data = byteArrayOutputStream.toByteArray();
                fanoutChannel.basicPublish(DATA_EXCHANGE, "", null, data);

                //for direct
                String currentRouting = p.isHasAdditionalDocuments() ? ROUTING_KEY_1 : ROUTING_KEY_2;
                directChannel.basicPublish(DOCUMENTS_EXCHANGE, currentRouting, null, p.getEmail().getBytes());

                //for topic
                currentRouting = p.isBudget() ? IS_BUDGET_ROUTING_KEY : IS_NOT_BUDGET_ROUTING_KEY;
                topicChannel.basicPublish(BUDGET_EXCHANGE, currentRouting, null, data);
            }


        } catch (IOException |
                TimeoutException e) {
            throw new IllegalArgumentException(e);
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
        System.out.println(personalData);
        return personalData;
    }

}
