package com.gesschoolapp.docmaker;

import com.gesschoolapp.Exceptions.PDFException;
import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.paiement.Paiement;
import com.gesschoolapp.models.student.Apprenant;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PDFGenerator {

    public static void recuGenerator(Paiement paiement) throws PDFException {
        // Initialisation du document PDF
        Document document = new Document();
        try {
            // Création du fichier PDF
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("storage/reçus/" + paiement.getNumeroRecu() + "_ " + paiement.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).replace("-", "_") + ".pdf"));

            // Ouverture du document
            document.open();

            // Ajout du titre
            Paragraph title = new Paragraph("RECU DE PAIEMENT", new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLACK));
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(30f);
            document.add(title);

            // Ajout du logo de l'établissement à gauche
            Image logo = Image.getInstance("src/com/gesschoolapp/resources/images/schoolup_logo.png");
            logo.scaleAbsolute(80, 80);
            document.add(logo);

            // Ajout de la date et du numéro de recu à droite
            Paragraph dateNum = new Paragraph("Date de paiement : " + paiement.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\nNuméro de reçu : " + paiement.getNumeroRecu(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK));
            dateNum.setAlignment(Element.ALIGN_RIGHT);
            document.add(dateNum);

            // Ajout des informations de paiement
            Paragraph nom = new Paragraph("Reçu de : " + paiement.getApprenant().getFullName(), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK));
            nom.setSpacingBefore(10f);
            document.add(nom);
            Paragraph classe = new Paragraph("En classe de : " + paiement.getApprenant().getClasse(), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK));
            document.add(classe);
            Paragraph montant = new Paragraph("Montant : " + paiement.getMontant() + " CFA", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK));
            document.add(montant);
            Paragraph rubrique = new Paragraph("Rubrique : " + paiement.getRubrique(), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK));
            rubrique.setSpacingAfter(30f);
            document.add(rubrique);

            // Ajout de l'image du tampon de l'école
            Image tampon = Image.getInstance("src/com/gesschoolapp/resources/images/compta.png");
            tampon.scaleAbsolute(80, 80);
            tampon.setAbsolutePosition(250, 400);
            document.add(tampon);

            // Ajout d'une bordure au tampon
            PdfContentByte canvas = writer.getDirectContent();
            canvas.rectangle(250, 400, tampon.getScaledWidth(), tampon.getScaledHeight());
            canvas.stroke();

            // Réduction de la taille des marges
            document.setMargins(30, 30, 30, 30);

            // Réduction de la taille de la page
            document.setPageSize(PageSize.A8);

            // Fermeture du document
            document.close();
        } catch (Exception e) {
            throw new PDFException("Erreur lors de la génération du PDF : " + e.getMessage());
        }
    }

    public static void bulletinGeneratorSemester1(Apprenant apprenant, Classe classe) throws PDFException {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("storage/bulletins/" + apprenant.getNom() + "_" + apprenant.getPrenom() + "_bulletin_semestre_1.pdf"));
            document.open();

            // Ajouter une photo de l'école
            Image schoolImage = Image.getInstance("src/com/gesschoolapp/resources/images/schoolup_logo.png");
            schoolImage.scaleToFit(100, 100);
            schoolImage.setAbsolutePosition(450f, 700f);
            document.add(schoolImage);

            // Ajouter les informations de l'école
            Paragraph schoolInfo = new Paragraph();
            schoolInfo.add(new Phrase("SchoolUp", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD)));
            schoolInfo.add(new Phrase("\nAdresse: 123 Johnny Street"));
            schoolInfo.add(new Phrase("\nDakar, Sénégal 11200"));
            schoolInfo.add(new Phrase("\nTél: (221) 77-777-77-77"));
            schoolInfo.setAlignment(Element.ALIGN_CENTER);
            document.add(schoolInfo);

            // Ajouter le titre "Bulletin de notes"
            Paragraph title = new Paragraph();
            title.add(new Phrase("\n\n\n\nBulletin de notes du Semestre 1", new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD)));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Ajouter les informations de l'étudiant
            Paragraph studentInfo = new Paragraph();
            studentInfo.add(new Phrase("\n\nNom de l'étudiant: " + apprenant.getNom() + " " + apprenant.getPrenom()));
            studentInfo.add(new Phrase("\nClasse: " + apprenant.getClasse()));
            studentInfo.add(new Phrase("\nMatricule: " + apprenant.getMatricule()));
            studentInfo.add(new Phrase("\nNationalité: " + apprenant.getNationalite()));
            studentInfo.add(new Phrase("\nDate de naissance: " + apprenant.getDateNaissance().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));

            document.add(studentInfo);

            // Ajouter le tableau des notes
            PdfPTable table = new PdfPTable(2);

            // Ajouter un margin top de 20px
            table.setSpacingBefore(20f);

            // Ajouter les titres des colonnes
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
            throw new PDFException("Erreur lors de la génération du PDF : " + e.getMessage());
        }
    }
}
