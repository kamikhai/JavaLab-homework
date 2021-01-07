import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class DataProducer {
    private final static String EXCHANGE_NAME = "data";
    private final static String EXCHANGE_TYPE = "fanout";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);

            while (true) {
                PersonalDataDto personalData = readInfo();

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(personalData);
                oos.flush();
                byte [] data = bos.toByteArray();
                channel.basicPublish(EXCHANGE_NAME, "", null, data);
            }
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static PersonalDataDto readInfo() {
        PersonalDataDto personalData = new PersonalDataDto();
        Scanner scanner = new Scanner(System.in);
        System.out.println("------------ДОБАВЛЕНИЕ НОВОГО ПОЛЬЗОВАТЕЛЯ------------");
        System.out.println("Введите фамилию");
        personalData.setSurname(scanner.nextLine());
        System.out.println("Введите имя");
        personalData.setName(scanner.nextLine());
        System.out.println("Введите номер паспорта");
        personalData.setPassportId(scanner.nextLine());
        System.out.println("Введите дату выдачи паспорта в формате ДД.ММ.ГГГГ");
        personalData.setDate(scanner.nextLine());
        System.out.println("Введите возраст");
        personalData.setAge(scanner.nextInt());
        return personalData;
    }

}
