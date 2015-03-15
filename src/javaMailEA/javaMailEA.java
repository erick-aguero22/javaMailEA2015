package javaMailEA;

/**
 * 
 * @author Erick Aguero <erickaguero.aries@gmail.com>
 */

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class javaMailEA {

    public String EnviarCorreo(String TipoCorreo, final String emisor, final String pass, String receptor, String asunto, String mensaje, Boolean tipoMensaje) {
        String resultado = "";
        Transport tra = null;
        try {
            //propiedades para JAVAMAIL
            Properties props = new Properties();
            //verificar el tipo de correo en este momento solo esta el uso de gmail o outlook
            Boolean TypeCorreo = TipoCorreo.toLowerCase().equals("gmail");
            
            /*
                para ver mas de los host de los distintos tipos de servicios de correo vea la siguiente documentacion
                http://www.oracle.com/technetwork/java/javamail/faq/index.html
            */
            
            String host = TypeCorreo?"smtp.gmail.com":"smtp-mail.outlook.com";
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.user", emisor);
            props.put("mail.smtp.auth", "true");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emisor, pass);
                }
            });

            MimeMessage msj = new MimeMessage(session);
            msj.setFrom(new InternetAddress(emisor));

            msj.addRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
            msj.setSubject(asunto);
            //dependiendo de la seleccion le permitira mostrar si se enviar con un foramto html o solo texto
            if (tipoMensaje == true) {
                msj.setText(mensaje, "ISO-8859-1", "html");
            } else {
                msj.setText(mensaje);
            }

            // Metodos para el envio del correo
            tra = session.getTransport("smtp");
            if (TypeCorreo) {
                tra.connect(emisor, pass);
            } else {
                tra.connect("smtp-mail.outlook.com", emisor, pass);
            }
            tra.sendMessage(msj, msj.getAllRecipients());
            
            // Cierre de la clase Transport
            tra.close();
            resultado = "Se envio correctamente el mensaje!!!";
        } catch (Exception e) {
            resultado = e.getMessage();
        } finally {
            try {
                tra.close();
            } catch (MessagingException e) {
            }
            return resultado;
        }
    }
}
