package ga.dgtt.permis.service;

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
     * Génère un QR code pour un examen
     */
    public String genererQRCodeExamen(String numeroExamen, String candidatNom, String resultat) {
        String donnees = String.format("EXAMEN|%s|%s|%s|%s", 
                                      numeroExamen, candidatNom, resultat, System.currentTimeMillis());
        return genererQRCode(donnees);
    }
}
