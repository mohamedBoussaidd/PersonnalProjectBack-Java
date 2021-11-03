package com.example.Api.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Api.models.User;


@Component
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/mail")
public class MailController {

	@Autowired
	private JavaMailSender javaMailSender;

	public String sendEmail(User user) {
		try {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(user.getEmail());

		msg.setSubject("Activation de votre compte");
		msg.setText("http://localhost:4200/activation?id="+user.getId()+"&idActivation="+user.getIdActivation());
		
			System.out.println(msg);
			
			javaMailSender.send(msg);
			
			return "Votre message à été envoyé avec succes";
		} catch (Exception e) {
			System.out.println("Votre envoi à echoué : "+e.getMessage());
			return  "Votre envoi à echoué : "+e.getMessage();
		}

	}
}
