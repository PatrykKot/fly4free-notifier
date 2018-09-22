package com.kotlarz.fly4freenotifier.service;

import com.kotlarz.fly4freenotifier.domain.event.SiteEvent;
import com.kotlarz.fly4freenotifier.domain.notified.NotifiedUser;
import com.kotlarz.fly4freenotifier.domain.phrase.Phrase;
import com.kotlarz.fly4freenotifier.domain.phrase.SiteByPhrase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class EmailNotifier {
    private static final String CONTENT_TEMPLATE_PATH = "mail/template.html";

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SiteTypeTranslationResolver translationResolver;

    @Transactional
    public void notify(SiteByPhrase siteByPhrase) {
        NotifiedUser owner = siteByPhrase.getPhrase().getOwner();
        String email = owner.getEmail();

        try {
            MimeMessage message = createEmailMessage(siteByPhrase, email);
            log.info("Sending email to " + email);

            javaMailSender.send(message);
            siteByPhrase.setEmailSent(true);
        } catch (Exception ex) {
            log.error("Cannot send email to {}", email);
            log.error(ex.getMessage());

            siteByPhrase.setEmailSent(false);
        }
    }

    private MimeMessage createEmailMessage(SiteByPhrase siteByPhrase, String email) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject(createSubject(siteByPhrase));
        message.setContent(createContent(siteByPhrase), "text/html; charset=utf-8");
        return message;
    }

    private String createContent(SiteByPhrase siteByPhrase) throws IOException {
        Phrase phrase = siteByPhrase.getPhrase();
        SiteEvent event = siteByPhrase.getSiteEvent();

        String template = readTemplate();
        Document document = Jsoup.parse(template);
        document.getElementById("phrase").text(phrase.getText());
        document.getElementById("site-type").text(translationResolver.translate(phrase.getSiteType()));
        document.getElementById("content").text(event.getContent());
        document.getElementById("link").text(event.getLink());
        return document.toString();
    }

    private String readTemplate() throws IOException {
        InputStream templateStream = ClassLoader.getSystemResourceAsStream(CONTENT_TEMPLATE_PATH);
        return IOUtils.toString(templateStream, StandardCharsets.UTF_8);
    }

    private String createSubject(SiteByPhrase siteByPhrase) {
        return "Nowa oferta lotów pasująca do '" + siteByPhrase.getPhrase().getText().toUpperCase().trim() + "'";
    }
}
