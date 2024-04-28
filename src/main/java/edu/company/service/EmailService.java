package edu.company.service;

import edu.company.dto.MailBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public void sendSimpleMessage(MailBody mailBody) throws Exception {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailBody.to());
            message.setFrom("");
            message.setSubject(mailBody.subject());
            message.setText(mailBody.text());

            javaMailSender.send(message);


        }
        catch (Exception s) {
            log.error(s.getMessage(), s);
            throw new Exception("Error sending mail") ;
        }
    }
}
