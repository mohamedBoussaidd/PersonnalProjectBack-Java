package com.example.Api.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.Api.models.Facture;
import com.example.Api.models.Pdf;
import com.example.Api.models.Produit;
import com.example.Api.models.QuantiteProduit;
import com.example.Api.repository.FactureRepository;
import com.example.Api.repository.ProduitRepository;
import com.example.Api.repository.QuantiteProduitRepository;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.text.Document;
import com.lowagie.text.DocumentException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/pdf")
@WebServlet("/pdf")
public class PdfController extends HttpServlet {

    @Autowired
    private FactureRepository factureRepository;
    @Autowired
    private QuantiteProduitRepository quantiteProduitRepository;
    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping("getPdf")
    public void exportToPdf(HttpServletRequest request, HttpServletResponse response)
            throws DocumentException, IOException {

        // response.setContentType("application/pdf");
        // DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        // String currentDateTime = dateFormatter.format(new Date());

        // String headerKey = "Content-Disposition";
        // String headerValue = "attachement; filename=client_"+currentDateTime+ ".pdf";
        // response.setHeader(headerKey,headerValue);

        // pdf.export(response);
        String masterPath = "/Users/boussaidmohamed/Desktop/javaProject/MaFacture/src/main/resources/static/facturePrincipale.pdf";
        response.setContentType("application/pdf");

        Facture facture = factureRepository.findById(10L).get();
        ArrayList<QuantiteProduit> listeQuantiteProduit = new ArrayList<QuantiteProduit>();
        listeQuantiteProduit = quantiteProduitRepository.findByFactureId(facture.getId());
        ArrayList<Produit> listeProduit = new ArrayList<Produit>();

        for(QuantiteProduit qP: listeQuantiteProduit){
            Produit produit = produitRepository.findById(qP.getProduit().getId()).get();
            produit.setQuantite(qP.getQuantite());
            listeProduit.add(produit);
        }

        try (
                PdfReader reader = new PdfReader(masterPath);
                PdfWriter writer = new PdfWriter(response.getOutputStream());
                PdfDocument document = new PdfDocument(reader, writer))
                 {
            PdfPage page = document.getPage(1);
          
            PdfCanvas canvas = new PdfCanvas(page);

            FontProgram fontProgram = FontProgramFactory.createFont();
            PdfFont font = PdfFontFactory.createFont(fontProgram, "utf-8");
            canvas.setFontAndSize(font, 13);

            canvas.beginText();

            // premiere valleur de gauche a droite et deuxieme en hautteur
            canvas.setTextMatrix(480, 488);
            canvas.showText("" + facture.getNumeroFacture());

            canvas.setTextMatrix(180, 488);
            canvas.showText("" + facture.getClient().getNom().toUpperCase());

            canvas.setTextMatrix(130, 460);
            canvas.showText("" + facture.getClient().getAdresse().toUpperCase());

            int top = 330;
            NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.FRANCE);
            double totalFacture = 0;
            for(Produit produit : listeProduit){

            canvas.setTextMatrix(66, top);
            canvas.showText("" + produit.getDesignation().toUpperCase()); 

            canvas.setTextMatrix(252, top);
            canvas.showText("" + formatter.format(produit.getPrix())); 

            canvas.setTextMatrix(260, top);
            canvas.showText("€"); 

            canvas.setTextMatrix(353, top);
            canvas.showText("" + produit.getQuantite()); 

            double totalLigne= produit.getPrix() * produit.getQuantite();
            totalFacture += totalLigne;
            canvas.setTextMatrix(472, top);
            canvas.showText("" + formatter.format(totalLigne)); 

            top-=28;
            }

            canvas.setTextMatrix(486, 115);
            canvas.showText("" + formatter.format(facture.getPrixTotalHT())); 

            canvas.setTextMatrix(486, 92);
            canvas.showText("" + formatter.format(totalFacture * 0.2)); 

            canvas.setTextMatrix(486, 73);
            canvas.showText("" +formatter.format(facture.getPrixTotalTTC())); 


            canvas.endText();
        }

    }
}
