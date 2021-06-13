package models;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.Properties;

@Root(name = "AppConfig")
public class Configuracion {


    @Element
    public static String DatabaseUser;

    @Element
    public static String DatabasePass;

    @Element
    public static String URL;

    @Element
    public static String MailConfigFrom;

    @Element(name = "EmailEmergencia")
    public static String MailConfigTo;

    @Element
    public static String MailConfigUser;

    @Element
    public static String MailConfigPass;

    public static Properties MailConfigProps;

    static {
        MailConfigProps = new Properties();
        MailConfigProps.put("mail.smtp.auth", true);
        MailConfigProps.put("mail.smtp.starttls.enable", "true");
        MailConfigProps.put("mail.smtp.host", "smtp.mailtrap.io");
        MailConfigProps.put("mail.smtp.port", "2525");
        MailConfigProps.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
        MailConfigProps.put("mail.mime.charset","UTF-8");
    }

    public static void iniciar() throws Exception {
        Serializer serializer = new Persister();
        File source = new File("src/main/resources/App.config.xml");

        Configuracion configuracion = serializer.read(Configuracion.class, source);
    }
}
