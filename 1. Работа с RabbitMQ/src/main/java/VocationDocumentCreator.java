import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeoutException;

public class VocationDocumentCreator {

    private final static String EXCHANGE_NAME = "data";
    private final static String EXCHANGE_TYPE = "fanout";
    public static final String DEST = "results/Отпуск/";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(3);

            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            String queue = channel.queueDeclare().getQueue();

            channel.queueBind(queue, EXCHANGE_NAME, "");

            DeliverCallback deliverCallback = (consumerTag, message) -> {
                ByteArrayInputStream in = new ByteArrayInputStream(message.getBody());
                ObjectInputStream is = new ObjectInputStream(in);

                try {
                    PersonalDataDto personalData = (PersonalDataDto) is.readObject();
                    System.out.println("Получена новая информация " + personalData);
                    String fileDest = DEST + "Заявление на отпуск " + personalData.getSurname() + " " + personalData.getName() + " " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE) + ".pdf";

                    File file = new File(fileDest);
                    file.getParentFile().mkdirs();
                    PdfWriter writer = new PdfWriter(fileDest);
                    PdfDocument pdf = new PdfDocument(writer);

                    Document document = new Document(pdf);

                    PdfFont russian = PdfFontFactory.createFont("times.ttf", "CP1251", true);

                    String text = String.format("ЗАЯВЛЕНИЕ\n Прошу предоставить мне очередной оплачиваемый отпуск с 21.09 на 14 календарных дней\n %s %s",personalData.getSurname(), personalData.getName());
                    document.add(new Paragraph(text).setFont(russian));

                    document.close();

                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                } catch (ClassNotFoundException e) {
                    System.out.println("FAILED");
                    channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
                    throw new IllegalArgumentException(e);
                }
            };

            channel.basicConsume(queue, false, deliverCallback, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
