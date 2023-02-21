package com.gesschoolapp.docmaker;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class PDFBulletin {
    private static final String FILE_NAME = "Bulletin_de_notes.pdf";

    public static void main(String[] args) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(FILE_NAME));
            document.open();

            // Ajouter une photo de l'école
            Image schoolImage = Image.getInstance("src/com/gesschoolapp/resources/images/schoolup_logo.png");
            schoolImage.scaleToFit(100, 100);
            schoolImage.setAbsolutePosition(450f, 700f);
            document.add(schoolImage);

            // Ajouter les informations de l'école
            Paragraph schoolInfo = new Paragraph();
            schoolInfo.add(new Phrase("École Example", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD)));
            schoolInfo.add(new Phrase("\nAdresse: 123 Main Street"));
            schoolInfo.add(new Phrase("\nVille, État ZIP Code"));
            schoolInfo.add(new Phrase("\nTél: (123) 456-7890"));
            schoolInfo.setAlignment(Element.ALIGN_CENTER);
            document.add(schoolInfo);

            // Ajouter le titre "Bulletin de notes"
            Paragraph title = new Paragraph();
            title.add(new Phrase("\n\n\n\nBulletin de notes", new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD)));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Ajouter les informations de l'étudiant
            Paragraph studentInfo = new Paragraph();
            studentInfo.add(new Phrase("\n\nNom complet: John Doe"));
            studentInfo.add(new Phrase("\nClasse: 12th Grade"));
            studentInfo.add(new Phrase("\nMatricule: 123456"));
            studentInfo.add(new Phrase("\nNationalité: Américain"));
            studentInfo.add(new Phrase("\nDate de naissance: 01/01/2000"));
            studentInfo.add(new Phrase("\nLieu de naissance: New York, NY"));
            document.add(studentInfo);

            // Ajouter le tableau des notes
            PdfPTable table = new PdfPTable(4);

            // Ajouter un margin top de 20px
            table.setSpacingBefore(20f);

            table.addCell(new Phrase("Module", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            table.addCell(new Phrase("Note", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));

            // Ajouter les données des notes de l'étudiant (utilisation de valeurs d'exemple)
            table.addCell("Mathématiques");
            table.addCell("18.0");
            table.addCell("Anglais");
            table.addCell("14.0");
            table.addCell("Français");
            table.addCell("16.0");

            // Ajouter le tableau au document
            document.add(table);

            // Ajouter les moyennes de l'étudiant et de la classe
            Paragraph averages = new Paragraph();
            averages.add(new Phrase("\n\nMoyenne de l'étudiant: 16.0"));
            averages.add(new Phrase("\nMoyenne de la classe: 14.0"));
            averages.add(new Phrase("\nRang de l'étudiant: 1/100"));
            document.add(averages);

            document.close();

            System.out.println("Le bulletin de notes a été créé avec succès.");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}

