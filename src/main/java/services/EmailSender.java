package services;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class EmailSender {
    public static void sendValidationCode(String email) throws IOException, MessagingException {
        final Properties properties = new Properties();
        properties.load(EmailSender.class.getClassLoader().getResourceAsStream("mail.properties"));

        Session mailSession = Session.getDefaultInstance(properties);
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress("finysis@mail.ru"));
        message.addRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(email)});
        message.setSubject("Validation Code");
        message.setText("Hello, dear friend ! \n" +
                "It's your validation code : " + generateValidationCode() + "\n" +
                "Please, enter him into check-field in your FinYsis app. \n" +
                "\n" +
                "Thank you for using FinYsis!");

        Transport tr = mailSession.getTransport();
        tr.connect(null,"BjFgXeuSeftCaU17k8Yr");
        tr.sendMessage(message, message.getAllRecipients());
        tr.close();
    }

    private static String generateValidationCode(){
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append((char) ((int) (Math.random() * 10) + 70));
        }
        return code.toString();
    }
}
