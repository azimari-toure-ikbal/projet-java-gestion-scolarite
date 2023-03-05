package com.gesschoolapp.docmaker;

import com.gesschoolapp.Exceptions.PDFException;
import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.matieres.Module;
import com.gesschoolapp.models.matieres.Note;
import com.gesschoolapp.models.paiement.Paiement;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.utils.Toolbox;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PDFGenerator {

    public static void cerficatScolariteGenerator(Apprenant apprenant) throws PDFException {

        // Verifier que l'apprenant soit inscrit
        if (apprenant.getEtatPaiement() == 0) {
            throw new PDFException("L'apprenant " + apprenant.getFullName() + " n'est pas inscrit !");
        }

        // Initialisation du document PDF
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        String file = apprenant.getFullName().replace(" ", "_") + "_" + LocalDate.now();
        String path = "storage/certificats/pdfs/" + file + ".pdf";
        String filename = "storage/certificats/imgs/" + file + ".png";


        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
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

            // Ajouter le titre encadré en haut de page
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Certificat de scolarité", font);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingBefore(50f);
            title.setSpacingAfter(50f);

            document.add(title);

            // Ajouter le contenu du certificat de scolarité
            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Paragraph content = new Paragraph();
            content.add(new Phrase("Je soussigné Monsieur Al Abdourahamane Abdoulaye AZOUMARI, Directeur de l'établissement, atteste que Mme, Mlle, M. : " + apprenant.getFullName() + " né(e) le " + apprenant.getDateNaissance().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " est régulièrement inscrit(e) à ladite école en " + apprenant.getClasse() + ", année scolaire " + (LocalDate.now().getYear() - 1) + "-" + (LocalDate.now().getYear()) + ".", contentFont));
            content.add(new Phrase("\nEn foi de quoi, la présente attestation est délivrée pour servir et valoir ce que de droit."));
            content.setAlignment(Element.ALIGN_JUSTIFIED);
            content.setSpacingAfter(30f);

            document.add(content);

            // Ajouter la signature du directeur de l'établissement
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100f);
            table.getDefaultCell().setBorder(0);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new Paragraph("Fait à Dakar, le " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), contentFont));
            table.addCell(new Paragraph("Le Directeur,", contentFont));
            table.addCell(new Paragraph("Al Abdourahamane Abdoulaye AZOUMARI", contentFont));
            table.setSpacingBefore(50f);

            document.add(table);

            document.close();

            Toolbox.pdfToImage(path, filename);

        } catch (Exception e) {
            throw new PDFException("Erreur lors de la génération du PDF : " + e.getMessage());
        }
    }

    public static void recuGenerator(Paiement paiement) throws PDFException {
        // Initialisation du document PDF
        Document document = new Document();
        String path = "storage/reçus/pdfs/" + paiement.getNumeroRecu() + "_ " + paiement.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).replace("-", "_") + ".pdf";
        String filename = "storage/reçus/imgs/" + paiement.getNumeroRecu() + "_ " + paiement.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).replace("-", "_") + ".png";

        try {
            // Création du fichier PDF
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));

            // Ouverture du document
            document.open();

            // Ajout du logo de l'établissement à gauche
            Image logo = Image.getInstance("src/com/gesschoolapp/resources/images/schoolup_logo.png");
            logo.scaleAbsolute(80, 80);
            document.add(logo);

            // Ajout du titre
            Paragraph title = new Paragraph("RECU DE PAIEMENT", new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLACK));
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(30f);
            document.add(title);

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

            Toolbox.pdfToImage(path, filename);
            System.out.println("PDF généré avec succès !");
        } catch (Exception e) {
            throw new PDFException("Erreur lors de la génération du PDF : " + e.getMessage());
        }
    }

    public static void bulletinGenerator(Classe classe, List<Module> modules, int semestre) throws PDFException {
        // Recuperer la liste des eleves
        List<Apprenant> apprenants = classe.getApprenants();

        // Supprimer les modules qui ne sont pas du bon semestre
        modules.removeIf(module -> module.getSemestre() != semestre);


        // Verifier si la liste des eleves est vide
        if (apprenants.isEmpty()) {
            throw new PDFException("La classe " + classe.getIntitule() + " ne contient aucun élève !");
        }

        // Supprimer les etudiants qui ont un etat de paiement == 0
        apprenants.removeIf(apprenant -> apprenant.getEtatPaiement() == 0);

        // Recuperer la liste des notes
        List<Note> notes = new ArrayList<>();
        for (Module module : modules) {
            notes.addAll(module.getNotes());
        }

        for (Apprenant apprenant : apprenants) {

            // Recuperer les notes de l'eleve
            List<Note> apprenantNotes = new ArrayList<>();
            for (Note note : notes) {
                if (note.getApprenant().getMatricule() == apprenant.getMatricule()) {
                    apprenantNotes.add(note);
                }
            }

            Document document = new Document();
            String file = apprenant.getFullName().replace(" ", "_") + "_bulletin_semestre_" + semestre;
            String path = "storage/bulletins/pdfs/" + file + ".pdf";
            String filename = "storage/bulletins/imgs/" + file + ".png";

            try {
                PdfWriter.getInstance(document, new FileOutputStream(path));
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
                title.add(new Phrase("\n\n\n\nBulletin de notes du Semestre " + semestre, new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD)));
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                // Ajouter les informations de l'étudiant
                Paragraph studentInfo = new Paragraph();
                studentInfo.add(new Phrase("\n\nNom de l'étudiant: " + apprenant.getFullName()));
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

                // Ajouter les données des notes de l'étudiant
                tableLineGenerator(table, modules, apprenantNotes);

                // Ajouter le tableau au document
                document.add(table);

                // Ajouter les moyennes de l'étudiant et de la classe
                Paragraph averages = new Paragraph();
                averages.add(new Phrase("\n\nMoyenne de l'étudiant: " + String.format("%.2f", calcMoyenneEtudiant(apprenantNotes))));
                averages.add(new Phrase("\nMoyenne de la classe: " + String.format("%.2f", calcMoyenneClasse(classe, apprenantNotes))));
                document.add(averages);

                // Ajouter une ligne de séparation
                Paragraph separator = new Paragraph();
                document.add(separator);

                // Appreciation du semestre
                Paragraph appreciation = new Paragraph();
                if (calcMoyenneEtudiant(apprenantNotes) >= 10) {
                    Chunk chunk = new Chunk("Appréciation: " +apprenant.getFullName() + " a réussi le semestre.", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.GREEN));
                    appreciation.add(chunk);
                } else {
                    Chunk chunk = new Chunk("Appréciation: " +apprenant.getFullName() + " n'a pas réussi le semestre.", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.RED));
                    appreciation.add(chunk);
                }
                document.add(appreciation);

                document.close();

                Toolbox.pdfToImage(path, filename);
                System.out.println("Bulletin généré avec succès");
            } catch (DocumentException | IOException e) {
                throw new PDFException("Erreur lors de la génération du PDF : " + e.getMessage());
            }
        }
    }

    private static void tableLineGenerator(PdfPTable table, List<Module> modules, List<Note> notes) {
        for (Module module : modules) {
            table.addCell(module.getIntitule());
            for (Note note : notes) {
                if (note.getModule().equals(module.getIntitule())) {
                    table.addCell(String.valueOf(note.getNote()));
                }
            }
        }
    }

    private static float calcMoyenneEtudiant(List<Note> notes) {
        float sum = 0;
        for (Note note : notes) {
            sum += note.getNote();
        }
        try {
            return sum / notes.size();
        } catch (ArithmeticException e) {
            return 0;
        }
    }

    private static float calcMoyenneClasse(Classe classe, List<Note> notes) {
        float sum = 0;
        for (Apprenant apprenant : classe.getApprenants()) {
            sum += calcMoyenneEtudiant(notes);
        }
        return sum / classe.getApprenants().size();
    }
}
