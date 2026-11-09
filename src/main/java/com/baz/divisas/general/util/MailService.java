package com.baz.divisas.general.util;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

import com.baz.divisas.general.config.PropApp;
import com.baz.divisas.general.model.EntCorreo;

/**
 * Servicio Mail
 * @author B813196
 *
 */
@Service
public class MailService {
		
	private static final String FORMATO_TEXTO = "text/html; charset=UTF-8";
	
	private static String host = PropApp.getIpMail();
	private static String mailServer = PropApp.getHostMail();
	private static String remitente = PropApp.getRemitenteMail();

	public MailService() {
		super();
	}

	public void enviaMail(EntCorreo correo) {
		Properties properties;
		Session session;
		MimeMessage message;
		BodyPart messageBodyPart = null;
		Multipart multipart = null;

		try {
			
			if (null != correo && null != mailServer && null != host) {
				
				properties = new Properties(System.getProperties());
				properties.setProperty(mailServer, host);
				session = Session.getDefaultInstance(properties);
				message = new MimeMessage(session);
				message.setFrom(new InternetAddress(remitente));
				setDestinatarios(message, correo.getDestinatarios());
				message.setSubject(correo.getAsunto());
				messageBodyPart = new MimeBodyPart();
				multipart = new MimeMultipart("related");
				messageBodyPart.setContent("<br><p><h4>" + getSaltoDeLinea(correo.getMensaje()) + "</h4></p><br><br>",
						FORMATO_TEXTO);

				multipart.addBodyPart(messageBodyPart);

				message.setContent(multipart);
				Transport.send(message);

			} 
			else {
				LogApp.mensaje("Ocurrio un error al intentar enviar el correo, faltan datos del correo");
			}
		} 
		catch(MessagingException|RuntimeException e) {
			LogApp.excepcion(e, "Ocurrio un error al intentar enviar el correo");
		}
	}

	private static void setDestinatarios(MimeMessage message, String destinatarios)
			throws MessagingException {

		String[] des;

		if (destinatarios != null) {
			des = limpiaDestinatarios(destinatarios.split(";")).split(";");
			InternetAddress[] iadestinatarios = new InternetAddress[(des.length)];
			for (int i = 0; i < (des.length); i++) {
				if (des[i].trim().length() > 0) {
					iadestinatarios[i] = new InternetAddress((des[i]));
				}
			}
			message.setRecipients(Message.RecipientType.TO, iadestinatarios);

		} 
		else {
			LogApp.mensaje("Ocurrio un error al intentar enviar el correo, los destinatarios no pueden ser nulos");
		}

	}

	private static String limpiaDestinatarios(String[] des) {
		StringBuilder sb = new StringBuilder();
		if (des != null && des.length > 0) {
			for (String s : des) {
				if (s.trim().length() > 0) {
					sb.append(s + ";");}
			}
		}
		return sb.toString();
	}

	private static String getSaltoDeLinea(String texto) {
		if (texto != null) {
			texto = texto.replace("\\r\\n", "<br>");
			texto = texto.replace("\r\n", "<br>");
			texto = texto.replace("\\r", "<br>");
			texto = texto.replace("\\n", "<br>");
			texto = texto.replace("\r", "<br>");
			texto = texto.replace("\n", "<br>");

		} 
		else {
			texto = "";
		}

		return texto;
	}

}
