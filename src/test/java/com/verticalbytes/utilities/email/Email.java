package com.verticalbytes.utilities.email;

import java.net.Authenticator;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class Email {

	private final String[] to;
	private final String subject;
	private final String body;

	private Email(Builder builder) {
		to = builder.to;
		subject = builder.subject;
		body = builder.body;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String[] to;
		private String subject;
		private String body;

		public Email build() {
			return new Email(this);
		}

		public Builder to(String[] to) {
			this.to = to;
			return this;
		}

		public Builder subject(String subject) {
			this.subject = subject;
			return this;
		}

		public Builder body(String body) {
			this.body = body;
			return this;
		}
	}

	public String[] getTo() {
		return to;
	}

	public String getSubject() {
		return subject;
	}

	public String getBody() {
		return body;
	}
}
