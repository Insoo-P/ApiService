package com.insoo.project;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

@RestController
@RequestMapping("/api/email")
public class EmailSendController {

    @PostMapping("/sendEmailCode")
    public Map<String, Object> sendEmail(@RequestBody Map<String, Object> paramMap){

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("emailCode", "");

        String host = "smtp.naver.com";
        int port = 587;
        String user = "insoo1157";
        String password = (String) paramMap.get("smtpPassword"); // 보완때문에 임시로 프론트에서 직적 입력하도록 수정

        String to = (String) paramMap.get("clientEmail");
        String subject = "Test Email";
        String body = generateRandomString(6);

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        // props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.starttls.enable", "true"); // 왜 위에걸로 하면 안되고 이걸로 해야지 되는거냐?
        props.put("mail.smtp.ssl.trust", host); // SSL 인증서 신뢰 설정

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user + "@naver.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully.");
            responseMap.put("emailCode", body);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return responseMap;
    }

    // 랜덤 문자열 생성 함수
    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder stringBuilder = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(index));
        }

        return stringBuilder.toString();
    }
}