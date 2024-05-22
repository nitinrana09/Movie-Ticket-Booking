package com.nitinrana.movieticketbooking.service;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.nitinrana.movieticketbooking.exception.MtbException;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	UserService userService;

	public void sendMail(String subject, String body) {

		Properties properties = null;
		Session session = null;
		UserDetails userDetails = null;
		MimeMessage message = null;
		try {

			/* set smtp properties */
			properties = new Properties();
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.auth", true);
			properties.put("mail.smtp.starttls.enable", true);

			/* configure session */
			String userName = "xxxxxxx";
			String password = "xxxxxxx";
			session = Session.getInstance(properties, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			});

			/* get user details */
			userDetails = userService.getUserDetails();

			/* set to and from */
			String to = userDetails.getUsername();
			String from = "xxxxx@xxx.xxx";

			/* create and send mail */
			message = new MimeMessage(session);
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setFrom(new InternetAddress(from));
			message.setSubject(subject);
			message.setText(body);

			Transport.send(message);

		} catch (Exception e) {
			e.printStackTrace();
			throw new MtbException(
					String.format(MtbException.MAIL_NOT_SENT, userService.getUserDetails().getUsername()));

		} finally {
			properties = null;
			session = null;
			userDetails = null;
			message = null;
		}

	}
}
