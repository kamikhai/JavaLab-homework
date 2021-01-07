package ru.itis.springrabbitmq.consumers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.itis.springrabbitmq.dto.PersonalDataDto;
import ru.itis.springrabbitmq.helpers.Sender;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
@AllArgsConstructor
public class MaterialSupportForBudgetCreator {
    private Gson gson;
    public static final String DEST = "results/Заявления/";

    @RabbitListener(queues = "budget_queue")
    public void listen(Message message) {
        PersonalDataDto personalData = gson.fromJson(new String(message.getBody()), PersonalDataDto.class);
        System.out.println("Budget" + new String(message.getBody()));

        createPdf(personalData);

    }

    private static void createPdf(PersonalDataDto personalData) {
        PDDocument pDDocument = null;
        try {
            String fileDest = DEST + "БЮДЖЕТ Заявление на получение материальной помощи " + personalData.getSurname() + " " + personalData.getName() + " " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE) + " " + UUID.randomUUID().toString() + ".pdf";
            pDDocument = PDDocument.load(new File("templates/Template2.pdf"));
            PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm();
            pDAcroForm.getField("course").setValue(personalData.getCourse().toString());
            pDAcroForm.getField("group").setValue(personalData.getGroup());
            pDAcroForm.getField("fio").setValue(personalData.getSurname()+" "+personalData.getName()+" "+personalData.getPatronymic());
            pDAcroForm.getField("passportId").setValue(personalData.getPassportId());
            pDAcroForm.getField("itn").setValue(personalData.getItn());
            pDAcroForm.getField("date").setValue(personalData.getBirthDate());
            pDAcroForm.getField("phone").setValue(personalData.getPhoneNumber());
            pDDocument.save(fileDest);
            pDDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
