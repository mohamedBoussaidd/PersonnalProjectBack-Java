package com.example.Api.services;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import com.example.Api.models.Entreprise;
import com.example.Api.models.Facture;
import com.example.Api.models.Produit;
import com.example.Api.models.QuantiteProduit;
import com.example.Api.repository.EntrepriseRepository;
import com.example.Api.repository.FactureRepository;
import com.example.Api.repository.ProduitRepository;
import com.example.Api.repository.QuantiteProduitRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.lowagie.text.DocumentException;
import java.io.IOException;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PdfService {

        @Autowired
        private QuantiteProduitRepository quantiteProduitRepository;
        @Autowired
        private ProduitRepository produitRepository;
        public void creePdf(Facture facture, Entreprise entreprise)
                        throws DocumentException {
                // je recuperer la quantite de produit selon l'id de la facture 
                ArrayList<QuantiteProduit> listeQuantiteProduit = new ArrayList<QuantiteProduit>();
                listeQuantiteProduit = quantiteProduitRepository.findByFactureId(facture.getId());
                // dans l'objet quantitéProduit il ya l'id d'une facture l'id du produit et la quantite de ce produit
                ArrayList<Produit> listeProduit = new ArrayList<Produit>();
                //pour chaque quantite de produit je recupere le produit en question par son id et je lui ajoute sa quantite
                for (QuantiteProduit qP : listeQuantiteProduit) {
                        Produit produit = produitRepository.findById(qP.getProduit().getId()).get();
                        produit.setQuantite(qP.getQuantite());
                        listeProduit.add(produit);
                }
                // je definie l'url a la quel je cree le pdf et le nom du fichier 
                String path = "//Users/boussaidmohamed/Desktop/javaProject/MaFacture/src/main/resources/telechargement/"
                                + facture.getClient().getNom().replaceAll("\\s+", "") + facture.getNumeroFacture();
                try (PdfWriter pdfWriter = new PdfWriter(path)) {
                        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
                        Document document = new Document(pdfDocument);
                        pdfDocument.setDefaultPageSize(PageSize.A4);
                        // header de la facture
                        float col = 280f;
                        float columnWidth[] = { col, col };
                        Table table = new Table(columnWidth);
                        table.setBackgroundColor(new DeviceRgb(240, 238, 238)).setFontColor(new DeviceRgb(55, 53, 53))
                                        .setBorder(Border.NO_BORDER);
                        table.addCell(new Cell().add(new Paragraph("FACTURE"))
                                        .setTextAlignment(TextAlignment.CENTER)
                                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                        .setMarginTop(30f)
                                        .setMarginBottom(30f)
                                        .setFontSize(30f)
                                        .setBorder(Border.NO_BORDER)).setHeight(150f);
                        table.addCell(new Cell().add(new Paragraph(entreprise.getName()+"\n"+entreprise.getDescription()+"\n"+entreprise.getNumber()))
                                        .setTextAlignment(TextAlignment.RIGHT)
                                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                        .setMarginTop(30f)
                                        .setMarginBottom(30f)
                                        .setBorder(Border.NO_BORDER)
                                        .setFontSize(10f)
                                        .setMarginRight(20f));
                        // information de l expediteur
                        float colWidht[] = {
                                        80, 300, 100, 80
                        };
                        Table infoFacture = new Table(colWidht);
                        infoFacture.addCell(new Cell(0, 4).add(new Paragraph("Envoyer par : "))
                                        .setBold()
                                        .setBorder(Border.NO_BORDER));
                        infoFacture.addCell(new Cell().add(new Paragraph("Nom : ")).setBorder(Border.NO_BORDER));
                        infoFacture.addCell(new Cell().add(new Paragraph(entreprise.getName()))
                                        .setBorder(Border.NO_BORDER));
                        infoFacture.addCell(
                                        new Cell().add(new Paragraph("No. Facture : ")).setBorder(Border.NO_BORDER));
                        infoFacture.addCell(
                                        new Cell().add(new Paragraph("" + facture.getNumeroFacture()))
                                                        .setBorder(Border.NO_BORDER));
                        infoFacture.addCell(new Cell().add(new Paragraph("No. Siret : ")).setBorder(Border.NO_BORDER));
                        infoFacture.addCell(
                                        new Cell().add(new Paragraph("" + entreprise.getNumeroSiret()))
                                                        .setBorder(Border.NO_BORDER));
                        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.FRANCE);
                        infoFacture.addCell(new Cell().add(new Paragraph("Date : ")).setBorder(Border.NO_BORDER));
                        infoFacture
                                        .addCell(new Cell()
                                                        .add(new Paragraph(""
                                                                        + dateFormat.format(facture.getDateCreation())))
                                                        .setBorder(Border.NO_BORDER));
                        infoFacture.addCell(new Cell().add(new Paragraph("E-mail : ")).setBorder(Border.NO_BORDER));
                        infoFacture.addCell(new Cell().add(new Paragraph(entreprise.getEmail()))
                                        .setBorder(Border.NO_BORDER));
                        infoFacture.addCell(new Cell().add(new Paragraph("No. Tel : ")).setBorder(Border.NO_BORDER));
                        infoFacture.addCell(new Cell().add(new Paragraph("" + entreprise.getNumber()))
                                        .setBorder(Border.NO_BORDER));
                        // corp de la facture
                        float corpWidth[] = { 140, 140, 140, 140 };
                        Table infoProduit = new Table(corpWidth).setMarginTop(25f);
                        infoProduit.addCell(new Cell().add(new Paragraph("SERVICE")).setBorderRight(Border.NO_BORDER)
                                        .setTextAlignment(TextAlignment.CENTER));
                        infoProduit.addCell(new Cell().add(new Paragraph("QTÉ")).setBorderRight(Border.NO_BORDER)
                                        .setBorderLeft(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
                        infoProduit.addCell(new Cell().add(new Paragraph("PRIX")).setBorderRight(Border.NO_BORDER)
                                        .setBorderLeft(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
                        infoProduit.addCell(new Cell().add(new Paragraph("TOTAL")).setBorderLeft(Border.NO_BORDER)
                                        .setTextAlignment(TextAlignment.CENTER));
                        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.FRANCE);
                        double totalFacture = 0;
                        for (Produit produit : listeProduit) {
                                infoProduit.addCell(new Cell().add(new Paragraph(produit.getDesignation()))
                                                .setBorder(Border.NO_BORDER)
                                                .setTextAlignment(TextAlignment.CENTER)
                                                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                                .setBackgroundColor(new DeviceRgb(219, 215, 214)).setHeight(40f));
                                infoProduit
                                                .addCell(new Cell().add(new Paragraph("" + produit.getQuantite()))
                                                                .setBorder(Border.NO_BORDER)
                                                                .setTextAlignment(TextAlignment.CENTER)
                                                                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                                                .setBackgroundColor(new DeviceRgb(219, 215, 214)));
                                infoProduit.addCell(
                                                new Cell().add(new Paragraph("" + formatter.format(produit.getPrix())))
                                                                .setBorder(Border.NO_BORDER)
                                                                .setTextAlignment(TextAlignment.CENTER)
                                                                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                                                .setBackgroundColor(new DeviceRgb(219, 215, 214)));
                                double totalLigne = produit.getPrix() * produit.getQuantite();
                                totalFacture += totalLigne;
                                infoProduit.addCell(
                                                new Cell().add(new Paragraph("" + formatter.format(totalLigne)))
                                                                .setBorder(Border.NO_BORDER)
                                                                .setTextAlignment(TextAlignment.CENTER)
                                                                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                                                .setBackgroundColor(new DeviceRgb(219, 215, 214)));
                        }
                        /*
                         * information total
                         */
                        float largeurInfoTotal[] = {
                                        560
                        };
                        Table infoTotal = new Table(largeurInfoTotal);
                        Integer total = 1500;
                        infoTotal.addCell(
                                        new Cell().add(new Paragraph("TOTAL HT: " + formatter.format(totalFacture)))
                                                        .setBorder(Border.NO_BORDER)
                                                        .setTextAlignment(TextAlignment.RIGHT));
                        infoTotal.addCell(new Cell().add(new Paragraph("TVA: " + formatter.format(totalFacture * 0.2)))
                                        .setBorder(Border.NO_BORDER)
                                        .setTextAlignment(TextAlignment.RIGHT));
                        infoTotal.addCell(
                                        new Cell().add(new Paragraph(
                                                        "TOTAL TTC: " + formatter.format(facture.getPrixTotalTTC())))
                                                        .setBorder(Border.NO_BORDER)
                                                        .setTextAlignment(TextAlignment.RIGHT));
                        /*
                         * information destinataire
                         */
                        float largeurInfoDestinataire[] = {
                                        560
                        };
                        Table infoDestinataire = new Table(largeurInfoDestinataire);
                        infoDestinataire.addCell(new Cell(0, 4).add(new Paragraph("Facturer à : "))
                                        .setBold()
                                        .setBorder(Border.NO_BORDER));
                        infoDestinataire.addCell(
                                        new Cell(0, 4).add(new Paragraph(facture.getClient().getNom()))
                                                        .setBorder(Border.NO_BORDER));
                        infoDestinataire.addCell(
                                        new Cell(0, 4).add(new Paragraph(facture.getClient().getAdresse()))
                                                        .setBorder(Border.NO_BORDER));
                        infoDestinataire.addCell(
                                        new Cell(0, 4).add(new Paragraph(facture.getClient().getComplementAdresse()))
                                                        .setBorder(Border.NO_BORDER));
                        infoDestinataire.addCell(
                                        new Cell(0, 4).add(new Paragraph(facture.getClient().getEmail()))
                                                        .setBorder(Border.NO_BORDER));
                        infoDestinataire.addCell(
                                        new Cell(0, 4).add(new Paragraph(facture.getClient().getNumero()))
                                                        .setBorder(Border.NO_BORDER));
                        // ajout au pdf
                        document.add(table);
                        document.add(new Paragraph("\n"));
                        document.add(infoFacture);
                        document.add(infoProduit);
                        document.add(infoTotal);
                        document.add(infoDestinataire);
                        document.close();
                } catch (IOException e) {
                        System.out.println(e.getCause());
                        ;
                }

        }
}
