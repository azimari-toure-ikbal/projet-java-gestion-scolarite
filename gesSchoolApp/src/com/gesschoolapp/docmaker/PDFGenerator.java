package com.gesschoolapp.docmaker;

import com.gesschoolapp.Exceptions.PDFException;
import com.gesschoolapp.models.paiement.Paiement;
import com.gesschoolapp.models.student.Apprenant;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PDFGenerator {

    public static void recuGenerator(Paiement paiement) throws PDFException {
        // Initialisation du document PDF
        Document document = new Document();
        try {
            // Création du fichier PDF
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("storage/reçus/" + paiement.getNumeroRecu() + ".pdf"));

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
            Paragraph dateNum = new Paragraph(new SimpleDateFormat("dd/MM/yyyy").format(paiement.getDate()) + "\nNuméro de reçu :" + paiement.getNumeroRecu(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK));
            dateNum.setAlignment(Element.ALIGN_RIGHT);
            document.add(dateNum);

            // Ajout des informations de paiement
            Paragraph nom = new Paragraph("Reçu de : " + paiement.getApprenant().getPrenom() + " " + paiement.getApprenant().getNom(), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK));
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

    public static void bulletinGenerator(Apprenant apprenant) throws PDFException {}
}
