package ga.dgtt.autoecole.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * Service pour la génération de QR codes
 * 
 * Ce service génère des QR codes pour les documents et entités
 * du système R-DGTT.
 */
@Service
public class QRCodeService {
    
    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;
    
    /**
     * Génère un QR code à partir d'un texte
     */
    public String genererQRCode(String texte) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(texte, BarcodeFormat.QR_CODE, WIDTH, HEIGHT);
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            
            byte[] imageBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
            
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Erreur lors de la génération du QR code", e);
        }
    }
    
    /**
     * Génère un QR code pour une auto-école
     */
    public String genererQRCodeAutoEcole(String numeroDemande, String nom, String statut) {
        String donnees = String.format("AUTO_ECOLE|%s|%s|%s|%s", 
                                      numeroDemande, nom, statut, System.currentTimeMillis());
        return genererQRCode(donnees);
    }
    
    /**
     * Génère un QR code pour un candidat
     */
    public String genererQRCodeCandidat(String numeroLicence, String nom, String prenom, String categorie) {
        String donnees = String.format("CANDIDAT|%s|%s|%s|%s|%s", 
                                      numeroLicence, nom, prenom, categorie, System.currentTimeMillis());
        return genererQRCode(donnees);
    }
    
    /**
     * Génère un QR code pour un document
     */
    public String genererQRCodeDocument(String typeDocument, String nomFichier, String hash) {
        String donnees = String.format("DOCUMENT|%s|%s|%s|%s", 
                                      typeDocument, nomFichier, hash, System.currentTimeMillis());
        return genererQRCode(donnees);
    }
    
    /**
     * Génère un QR code pour une évaluation
     */
    public String genererQRCodeEvaluation(String numeroEvaluation, String typeEvaluation, String resultat) {
        String donnees = String.format("EVALUATION|%s|%s|%s|%s", 
                                      numeroEvaluation, typeEvaluation, resultat, System.currentTimeMillis());
        return genererQRCode(donnees);
    }
    
    /**
     * Génère un QR code pour une autorisation
     */
    public String genererQRCodeAutorisation(String numeroAutorisation, String nomAutoEcole, String dateExpiration) {
        String donnees = String.format("AUTORISATION|%s|%s|%s|%s", 
                                      numeroAutorisation, nomAutoEcole, dateExpiration, System.currentTimeMillis());
        return genererQRCode(donnees);
    }
    
    /**
     * Valide un QR code
     */
    public boolean validerQRCode(String qrCode, String typeAttendu) {
        try {
            String donnees = new String(Base64.getDecoder().decode(qrCode));
            return donnees.startsWith(typeAttendu + "|");
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Extrait les données d'un QR code
     */
    public String[] extraireDonneesQRCode(String qrCode) {
        try {
            String donnees = new String(Base64.getDecoder().decode(qrCode));
            return donnees.split("\\|");
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'extraction des données du QR code", e);
        }
    }
}
