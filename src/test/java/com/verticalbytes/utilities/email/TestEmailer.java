package com.verticalbytes.utilities.email;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.AddressException;

import com.verticalbytes.core.TestCore;

public class TestEmailer implements Emailer {

	@Override
	public void send(Email email) throws MessagingException, AddressException {
		Emailer.newSSLEmailerBuilder()//
				.from(TestCore.config.getProperty("email.from.username"))//
				.server(TestCore.config.getProperty("email.server"))//
				.authenticator(new Auth())//
				.build()//
				.send(email);
	}

	private class Auth extends Authenticator {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(TestCore.config.getProperty("email.from.username"),
					TestCore.config.getProperty("email.from.password"));
		}
	}
}
