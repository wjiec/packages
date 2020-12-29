package com.wjiec.springaio.email;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.Thymeleaf;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Configuration
public class Application {

    public static void main(String[] args) throws MessagingException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Application.class);
        context.refresh();

        JavaMailSenderImpl mailSender = context.getBean(JavaMailSenderImpl.class);
        System.out.println(mailSender);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("noreply@wjiec.com");
        simpleMailMessage.setTo("customer@wjiec.com");
        simpleMailMessage.setSubject("email form spring");
        simpleMailMessage.setText("welcome to spring.");
        mailSender.send(simpleMailMessage);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("noreply@wjiec.com");
        mimeMessageHelper.setTo("customer@wjiec.com");
        mimeMessageHelper.setSubject("email form spring");
        mimeMessageHelper.setText("welcome to spring.");
        mimeMessageHelper.addAttachment("bill.txt", new ClassPathResource("/bill.txt"));
        mailSender.send(mimeMessage);

        MimeMessage htmlMessage = mailSender.createMimeMessage();
        MimeMessageHelper htmlMessageHelper = new MimeMessageHelper(htmlMessage);
        htmlMessageHelper.setText("<html><body><p>Html Message</p></body></html>", true);

        Context ctx = new Context();
        ctx.setVariable("name", "jayson");
        ctx.setVariable("age", 18);

        SpringTemplateEngine templateEngine = context.getBean(SpringTemplateEngine.class);
        String mailText = templateEngine.process("welcome.html", ctx);
        System.out.println(mailText);
    }

    @Bean
    public MailSender mailSender(Environment env) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(env.getProperty("email.host"));
        mailSender.setUsername(env.getProperty("email.username"));
        mailSender.setPassword(env.getProperty("email.password"));

        return mailSender;
    }

    @Bean
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine;
    }

    @Bean
    public ClassLoaderTemplateResolver classLoaderTemplateResolver() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("mail/");
        resolver.setTemplateMode("HTML");
        resolver.setCharacterEncoding(StandardCharsets.UTF_8.name());

        return resolver;
    }

}
