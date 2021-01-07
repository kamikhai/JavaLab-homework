package ru.itis;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class HtmlCreator {

    public static void createHtml(String templateName, Map<String, Object> elements, Path out) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(out.toFile()));
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
            cfg.setClassForTemplateLoading(HtmlCreator.class, "/templates");
            Template temp = cfg.getTemplate(templateName);
            temp.process(elements, writer);
        } catch (IOException | TemplateException e) {
            throw new IllegalArgumentException(e);
        }

    }
}
