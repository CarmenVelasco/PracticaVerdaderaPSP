package models;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;


/**
 * Emailsender es una clase preparada para lanzar correos electrónicos desde el correo PracticaFinalPSPJoseProfe@gmail.com"
 */
public class EmailSender {

    // Las credenciales de mailtrap
    //static final String username= "690c1d3bd8c81b";
    //static final String password= "d63d724f750d00";

    // Los emails. Como es Mailtrap, se pueden poner los que queremos, el nombre no importa
    //static final  String from = "2301566cva@gmail.com";

    // Generamos la sesión con su auntenticación
    private static final Session session;

    static {
        session = Session.getInstance(Configuracion.MailConfigProps, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Configuracion.MailConfigUser, Configuracion.MailConfigPass);
            }
        });
    }

    /**
     * Este método nos permite mandar un correo electrónico a un destinatario que le pasamos por parámetros (emailTo)
     * informando que un producto (que le pasamos por parámetros) se ha quedado sin stock o no habiendo stock
     * se le ha reservado a un cliente y por tanto se requiren más unidades.
     *
     * @param cuerpo
     * @param asunto
     */
    public static void sendEmail(String asunto, String cuerpo) throws MessagingException {


        // Seteamos todas las propiedades necesarias para lanzar el correo
        // Estas propiedas son para la configuración de Mailtrap
        //Properties prop = new Properties();
        //prop.put("mail.smtp.auth", true);
        //prop.put("mail.smtp.starttls.enable", "true");
        //prop.put("mail.smtp.host", "smtp.mailtrap.io");
        //prop.put("mail.smtp.port", "2525");
        //prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
        //prop.put("mail.mime.charset", "UTF-8");

        //Generamos el mensaje
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(Configuracion.MailConfigFrom));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(Configuracion.MailConfigTo));
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();
        message.setSubject(asunto);
        mimeBodyPart.setContent(cuerpo, "text/plain; charset=UTF-8");
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);

        //Y lo enviamos
        Transport.send(message);

    }

}