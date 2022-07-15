package com.poc.user.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender mailSender;
	//@Value("${mail:username}")
	//private String sender;

	public boolean sendMail(String to, String subject, String body) {

		boolean isSent = false;

		try {
			MimeMessage mimeMsg = mailSender.createMimeMessage();
			MimeMessageHelper msgHelper = new MimeMessageHelper(mimeMsg);

			//msgHelper.setFrom(sender);
			msgHelper.setTo(to);
			msgHelper.setSubject(subject);
			msgHelper.setText(body, true);

			// sending mail
			mailSender.send(mimeMsg);

			isSent = true;
		} catch (Exception e) {

			e.printStackTrace();
		}

		return isSent;

	}

}
