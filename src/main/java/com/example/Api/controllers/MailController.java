package com.example.Api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.example.Api.models.User;


@Component
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/mail")
@ConfigurationProperties(prefix = "mail")
public class MailController {

	String host;
	String port;
	String username;
	String password;

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return this.port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Autowired
	private JavaMailSender javaMailSender;

	public String sendEmail(User user) {
		try {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(user.getEmail());

		msg.setSubject("Activation de votre compte");
		msg.setText( "http://localhost:4200/activation?id="+user.getId()+"&idActivation="+user.getIdActivation());
		
			System.out.println(msg);
			
			javaMailSender.send(msg);
			
			return "Votre message à été envoyé avec succes";
		} catch (Exception e) {
			System.out.println("Votre envoi à echoué : "+e.getMessage());
			return  "Votre envoi à echoué : "+e.getMessage();
		}
	}

	@PostMapping("/email")
	public void envoiEmailPJ(){
		Properties props = new Properties();
		props.put("mail.smtp.auth","true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host",getHost());
		props.put("mail.smtp.port",getPort());

		Session session = Session.getInstance(props, new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(getUsername(),getPassword());
			}
		});
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress("araahendek@gmail.com"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("mohamedboussaid69700@hotmail.fr"));
			msg.setSubject("Sujet test");
			msg.setText(" Body test");
			Transport.send(msg);
			System.out.println("envoye reussi");
		} catch (Exception e) {
			//TODO: handle exception
			e.printStackTrace();
		}
	}
}
