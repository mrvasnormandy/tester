package com.verticalbytes.utilities.email;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface Emailer {
	public void send(Email email) throws MessagingException, AddressException;

	public static SSLEmailer.Builder newSSLEmailerBuilder() {
		return new SSLEmailer.Builder();
	}
}
