package ru.itis.springrabbitmq.consumers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.itis.springrabbitmq.dto.PersonalDataDto;
import ru.itis.springrabbitmq.helpers.Sender;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ScanDownloader {
    private Gson gson;
    public static final String DEST = "results/Сканы/";

    @RabbitListener(queues = "save_scan_queue")
    public void listen(Message message) {
        PersonalDataDto personalData = gson.fromJson(new String(message.getBody()), PersonalDataDto.class);
        System.out.println("Scan" + new String(message.getBody()));
        URL url = null;
        try {
            url = new URL(personalData.getPasportScan());
            String fileName = url.getFile();
            BufferedImage image = ImageIO.read(url);
            File output = new File(DEST + personalData.getSurname() + "_" + personalData.getName() + "_" + UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf(".")));
            ImageIO.write(image, "jpg", output);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
