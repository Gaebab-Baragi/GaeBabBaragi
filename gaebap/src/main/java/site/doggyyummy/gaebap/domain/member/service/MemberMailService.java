package site.doggyyummy.gaebap.domain.member.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import site.doggyyummy.gaebap.domain.member.exception.custom.InvalidEmailException;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberMailService {
    private final JavaMailSender emailSender;
    private String authCode;
    private Integer codeLength = 8;
    protected String sendingEmail = "doggy.yummy.site@gmail.com";

    private void generateAuthCode(){
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

    public MimeMessage createEmailForm(String email) throws MessagingException {

        generateAuthCode();
        String fromEmail = sendingEmail;
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

    public MimeMessage createEmailForm(String email, String password) throws MessagingException {

        String fromEmail = sendingEmail;
        String toEmail = email; //받는 사람
        String title = "[개밥바라기] 새 비밀번호 발급"; //제목

        log.info("from {} to {}", fromEmail, toEmail);
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail);
        message.setSubject(title);
        message.setFrom(fromEmail);
        message.setText(password);
        return message;
    }

    public String sendEmail(String toEmail) throws InvalidEmailException{
        try {
            MimeMessage emailForm = createEmailForm(toEmail);
            //emailSender.send(emailForm);
            log.info("email : {}", toEmail);
            log.info("email-verification-code : {}", authCode);
        }
        catch (Exception e){
            throw new InvalidEmailException();
        }

        return authCode; //인증 코드 반환
    }

    public String sendEmail(String toEmail, String message) throws InvalidEmailException{
        try {
            MimeMessage emailForm = createEmailForm(toEmail);
            //emailSender.send(emailForm);
            log.info("email : {}", toEmail);
            log.info("new-password : {}", message);
        }
        catch (Exception e){
            throw new InvalidEmailException();
        }
        return message; //인증 코드 반환
    }
}
