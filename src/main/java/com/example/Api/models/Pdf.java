package com.example.Api.models;

import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.stereotype.Component;

import com.lowagie.text.Font;
import com.lowagie.text.*;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException; 
import java.util.ArrayList;
import java.util.List;

@Component
public class Pdf {
    
    private void writeTapleHeader(PdfPTable table){
        
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.blue);
        cell.setPadding(5);
        
        Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase( "etudiant ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase(  "Nom", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase(  "Prenom", font));
        table.addCell(cell);

        

        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table){
        List<Client> listClient = new ArrayList<Client>();

        Client client1 = new Client("ghh","ghjkk","fhcgvj");
        Client client2 = new Client("ghh","ghjkk","fhcgvj");
        Client client3 = new Client("ghh","ghjkk","fhcgvj");
        listClient.add(client1);
        listClient.add(client2);
        listClient.add(client3);

        for(Client clientt: listClient){
            table.addCell(String.valueOf(clientt.getNom()));
            table.addCell(String.valueOf(clientt.getEmail()));
            table.addCell(String.valueOf(clientt.getAdresse()));
        }
        
    }

    public void export(HttpServletResponse response)throws DocumentException,IOException{
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.pink);

        Paragraph p = new Paragraph("liste des Clients", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f,3.5f,3.0f});
        table.setSpacingBefore(10);

        writeTapleHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();



    }
}
