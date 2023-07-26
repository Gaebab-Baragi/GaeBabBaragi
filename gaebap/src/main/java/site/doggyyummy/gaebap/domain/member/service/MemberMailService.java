package site.doggyyummy.gaebap.domain.member.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberMailService {
    private final JavaMailSender emailSender;
    private String authCode;
    private Integer codeLength = 8;

    public void generateAuthCode(){
            String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

            SecureRandom rand = new SecureRandom();
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < codeLength; i++)
            {
                int randomIndex = rand.nextInt(chars.length());
                sb.append(chars.charAt(randomIndex));
            }
            authCode = sb.toString();
    }

    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {

        generateAuthCode();
        String fromEmail = "pj0642@gmail.com"; //보낸 사람
        String toEmail = email; //받는 사람
        String title = "[개밥바라기] 인증 이메일"; //제목

        log.info("from {} to {}", fromEmail, toEmail);
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail);
        message.setSubject(title);
        message.setFrom(fromEmail);
        message.setText(authCode);
        return message;
    }
    public String sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {
        log.info("만든다?");
        MimeMessage emailForm = createEmailForm(toEmail);
        log.info("만들었다");
        log.info("authCode {}", authCode);
        try {
            emailSender.send(emailForm);
        }
        catch (Exception e){
            log.info(e.getMessage());
        }
        log.info("보냈다");

        return authCode; //인증 코드 반환
    }

}
