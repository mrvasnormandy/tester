package com.verticalbytes.utilities.email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SSLEmailer implements Emailer {

	private final String from;
	private final String server;
	private final Authenticator authenticator;

	private SSLEmailer(Builder builder) {
		from = builder.from;
		server = builder.server;
		authenticator = builder.authenticator;
	}

	public void send(Email email) throws MessagingException, AddressException {
		boolean debug = false;
		Session session = getSession();

		session.setDebug(debug);

		try (Transport bus = session.getTransport("smtp")) {
			bus.connect();
			Message message = new MimeMessage(session);
			message.addHeader("X-Priority", "1");
			message.setFrom(new InternetAddress(from));
			String[] to = email.getTo();
			InternetAddress[] addressTo = new InternetAddress[to.length];
			for (int i = 0; i < to.length; i++)
				addressTo[i] = new InternetAddress(to[i]);
			message.setRecipients(Message.RecipientType.TO, addressTo);
			message.setSubject(email.getSubject());
			BodyPart body = new MimeBodyPart();
			body.setContent(email.getBody(), "text/html");
			MimeMultipart multipart = new MimeMultipart();
			multipart.addBodyPart(body);
			message.setContent(multipart);
			Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	private Session getSession() {
		Properties props = getProperties();
		Session session = Session.getDefaultInstance(props, authenticator);
		return session;
	}

	private Properties getProperties() {
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.EnableSSL.enable", "true");
		props.put("mail.smtp.auth", "true");

		props.put("mail.smtp.host", server);
		props.put("mail.debug", "true");

		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		return props;
	}

	public static class Builder {
		private String from;
		private String server;
		private Authenticator authenticator;

		public SSLEmailer build() {
			return new SSLEmailer(this);
		}

		public Builder from(String from) {
			this.from = from;
			return this;
		}

		public Builder server(String server) {
			this.server = server;
			return this;
		}

		public Builder authenticator(Authenticator authenticator) {
			this.authenticator = authenticator;
			return this;
		}
	}
}
