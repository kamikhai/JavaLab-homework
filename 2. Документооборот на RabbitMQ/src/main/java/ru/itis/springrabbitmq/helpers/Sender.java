package ru.itis.springrabbitmq.helpers;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

@Component
public class Sender {

    private String username;
    @Value("${mail.password}")
    private String password;
    private Properties props;
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private Configuration configuration;

    public void send(String email, boolean haveDocuments) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String text = makeTemplate(haveDocuments);
            helper.setText(text, true);
            helper.addTo(email);
            helper.setSubject("Attention");
        } catch (MessagingException e) {
            throw new IllegalStateException();
        }
        emailSender.send(message);
    }

    private String makeTemplate(boolean haveDocuments) {
        try {
            Template template = haveDocuments ? configuration.getTemplate("accept_mail_template.ftl") : configuration.getTemplate("decline_mail_template.ftl");
            return template.toString();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }


}