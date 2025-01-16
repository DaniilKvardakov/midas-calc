package midas.components;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class Reporter {

    /**
     * Специальный пароль от почты клиента.
     */
    @Value("${midas.bag.report.secret.key}")
    private String reportKey;
    /**
     * Адрес отправителя(приложения).
     */
    @Value("${midas.bag.report.from}")
    private String reportFrom;
    /**
     * Адрес получателя(разработчик).
     */
    @Value("${midas.bag.report.to}")
    private String reportTo;

    /**
     * Метод отправки баг репорта на почту.
     *
     * @param reportData данные при которых возникла ошибка.
     * @throws MessagingException
     * @see MessagingException
     */
    public void reportABug(String reportData) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.mail.ru");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        Session session = Session.getInstance(props);
        session.setDebug(true);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(reportFrom));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(reportTo));
        msg.setSubject("Problem Midas!");
        msg.setText(reportData);
        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.mail.ru", reportFrom, reportKey); // Явное подключение
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
    }

}
