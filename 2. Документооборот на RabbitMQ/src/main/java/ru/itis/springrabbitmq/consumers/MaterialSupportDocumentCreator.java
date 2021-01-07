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
public class MaterialSupportDocumentCreator {
    private Gson gson;
    public static final String DEST = "results/Заявления/";

    @RabbitListener(queues = "material_support_document_queue")
    public void listen(Message message) {
        PersonalDataDto personalDataDto = gson.fromJson(new String(message.getBody()), PersonalDataDto.class);
        System.out.println("Document" + new String(message.getBody()));

        createPdf(personalDataDto);
    }

    private static void createPdf(PersonalDataDto personalData) {
        PDDocument pDDocument = null;
        try {
            String fileDest = DEST + "Заявление на получение материальной помощи " + personalData.getSurname() + " " + personalData.getName() + " " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE) + " " + UUID.randomUUID().toString() + ".pdf";
            pDDocument = PDDocument.load(new File("templates/Template.pdf"));
            PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm();
            pDAcroForm.getField("group").setValue(personalData.getGroup());
            pDAcroForm.getField("surname").setValue(personalData.getSurname());
            pDAcroForm.getField("name").setValue(personalData.getName());
            pDAcroForm.getField("patronymic").setValue(personalData.getPatronymic());
            pDAcroForm.getField("budget").setValue(personalData.isBudget() ? "бюджет" : "контракт");
            pDAcroForm.getField("pasportId").setValue(personalData.getPassportId());
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
