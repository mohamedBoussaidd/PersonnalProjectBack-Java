package com.example.Api;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.text.StyleConstants.FontConstants;

import com.example.Api.services.FilesStorageService;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs.Rgb;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.lowagie.text.pdf.RGBColor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication

public class MaFactureApplication  implements CommandLineRunner  {

	@Resource
	FilesStorageService storageService;
	public static void main(String[] args) {
		SpringApplication.run(MaFactureApplication.class, args);
		

		// final String username = "araahendek@gmail.com";
		// final String password = "W12345wild/1";

		// Properties props = new Properties();
		// props.put("mail.smtp.auth", "true");
		// props.put("mail.smtp.starttls.enable", "true");
		// props.put("mail.smtp.host", "smtp.gmail.com");
		// props.put("mail.smtp.port", "587");
		// Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		// protected PasswordAuthentication getPasswordAuthentication() {
		// return new PasswordAuthentication(username, password);
		// }
		// });
		// MimeMessage msg = new MimeMessage(session);
		// try {
		// msg.setFrom(new InternetAddress("araahendek@gmail.com"));
		// msg.addRecipient(Message.RecipientType.TO, new
		// InternetAddress("mohamedboussaid69700@hotmail.fr"));
		// msg.setSubject("Sujet test");

		// Multipart contenuEmail = new MimeMultipart();
		// MimeBodyPart textPart = new MimeBodyPart();
		// textPart.setText(" Body test");
		// MimeBodyPart pieceJointe = new MimeBodyPart();
		// pieceJointe.attachFile(
		// "/Users/boussaidmohamed/Desktop/javaProject/MaFacture/src/main/resources/static/pdfFacture2.pdf");

		// contenuEmail.addBodyPart(textPart);
		// contenuEmail.addBodyPart(pieceJointe);

		// msg.setContent(contenuEmail);

		// Transport.send(msg);
		// System.out.println("envoye reussi");
		// } catch (Exception e) {

		// e.printStackTrace();
		// }
		// File fi = new File("/Users/boussaidmohamed/Desktop/dossiertest/");

		
	}
	@Override
		public void run(String... arg) throws Exception {
		//   storageService.deleteAll();
		  storageService.init();
		}
}
