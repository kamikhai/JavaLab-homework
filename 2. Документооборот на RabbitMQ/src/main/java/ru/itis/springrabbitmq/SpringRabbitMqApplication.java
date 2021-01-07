package ru.itis.springrabbitmq;


import com.google.gson.Gson;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import ru.itis.springrabbitmq.producers.SpringProducer;

import java.util.Locale;
import java.util.Properties;

@SpringBootApplication
@EnableRabbit
public class SpringRabbitMqApplication {

    @Value("${queue.direct.haveDocumentQueue}")
    private String haveDocumentQueue;
    @Value("${queue.direct.dontHaveDocumentQueue}")
    private String dontHaveDocumentQueue;
    @Value("${queue.topic.budgetQueue}")
    private String budgetQueue;
    @Value("${queue.fanout.saveScanQueue}")
    private String saveScanQueue;
    @Value("${queue.fanout.materialSupportDocumentsQueue}")
    private String materialSupportDocumentsQueue;
    @Value("${routingKey.direct.haveDocument}")
    private String haveDocumentDirectRoutingKey;
    @Value("${routingKey.direct.dontHaveDocument}")
    private String dontHaveDocumentDirectRoutingKey;
    @Value("${routingKey.topic.budget}")
    private String budgetTopicRoutingKey;
    @Value("${exchage.direct}")
    private String direct_exchange;
    @Value("${exchage.fanout}")
    private String fanout_exchange;
    @Value("${exchage.topic}")
    private String topic_exchange;
    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringRabbitMqApplication.class, args);
//        SpringProducer producer = context.getBean(SpringProducer.class);
//        producer.send();
    }

    @Bean
    public Queue haveDocumentQueue() {
        return new Queue(haveDocumentQueue);
    }

    @Bean
    public Queue dontHaveDocumentQueue() {
        return new Queue(dontHaveDocumentQueue);
    }

    @Bean
    public Queue budgetQueue() {
        return new Queue(budgetQueue);
    }

    @Bean
    public Queue saveScanQueue() {
        return new Queue(saveScanQueue);
    }

    @Bean
    public Queue materialSupportDocumentsQueue() {
        return new Queue(materialSupportDocumentsQueue);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(fanout_exchange);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(direct_exchange);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(topic_exchange);
    }

    @Bean
    public Binding haveDocumentDirectBinding() {
        return BindingBuilder.bind(haveDocumentQueue()).to(directExchange()).with(haveDocumentDirectRoutingKey);
    }

    @Bean
    public Binding budgetTopicBinding() {
        return BindingBuilder.bind(budgetQueue()).to(topicExchange()).with(budgetTopicRoutingKey);
    }

    @Bean
    public Binding saveScanFanoutBinding() {
        return BindingBuilder.bind(saveScanQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding materialSupportDocumentsQueueFanoutBinding() {
        return BindingBuilder.bind(materialSupportDocumentsQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding dontHaveDocumentDirectBinding() {
        return BindingBuilder.bind(dontHaveDocumentQueue()).to(directExchange()).with(dontHaveDocumentDirectRoutingKey);
    }

    @Bean
    public AmqpTemplate amqpTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public FreeMarkerConfigurer getConf() {
        final FreeMarkerConfigurer result = new FreeMarkerConfigurer();
        result.setTemplateLoaderPath("/templates/");
        result.setDefaultEncoding("UTF-8");
        return result;
    }

    @Bean
    public freemarker.template.Configuration configuration() {
        freemarker.template.Configuration configuration = getConf().getConfiguration();
        configuration.setEncoding(new Locale("ru"), "utf-8");
        return configuration;
    }

    @Bean
    public FreeMarkerViewResolver freemarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setPrefix("");
        resolver.setContentType("text/html; charset=utf-8");
        resolver.setSuffix(".ftl");
        return resolver;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(environment.getProperty("mail.email"));
        mailSender.setPassword(environment.getProperty("mail.password"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }
}
