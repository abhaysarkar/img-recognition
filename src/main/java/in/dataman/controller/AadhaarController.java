package in.dataman.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.dataman.dto.CardDataDTO;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@RestController
@RequestMapping("/api/aadhaar")
public class AadhaarController {
    
    @GetMapping
    public String home() {
        return "Welcome to my application";
    }
    
    @PostMapping("/extract")
    public ResponseEntity<String> extractAadhaar(@ModelAttribute CardDataDTO formData) throws IOException {
        MultipartFile file = formData.getFile();
        String cardType = formData.getCardName();
        ITesseract tesseract = new Tesseract();
        
        
        tesseract.setDatapath(new ClassPathResource("tessdata").getFile().getPath());
        tesseract.setLanguage("eng");

        try {
            // Get the path to the tessdata directory in the resources folder
            ClassPathResource resource = new ClassPathResource("tessdata");
            File tessdataDir = resource.getFile();
            // Set the Tesseract data path
            tesseract.setDatapath(tessdataDir.getAbsolutePath());
            tesseract.setLanguage("eng");
            // Save the uploaded file to a temporary location
            File tempFile = File.createTempFile("aadhaar", ".png");
            file.transferTo(tempFile);
            // Perform OCR on the image
            String result = tesseract.doOCR(tempFile);
            // Extract Aadhaar number using regex
            String cardNumber = "";
            if("Aadhar Card".equals(cardType)) {
                cardNumber = extractAadhaarNumber(result);
            }
            if("Debit Card".equals(cardType)) {
                cardNumber = extractDebitCardNumber(result);
            }
            if("Credit Card".equals(cardType)) {
                cardNumber = extractCreditCardNumber(result);
            }
            if("Aabha Card".equals(cardType)) {
                cardNumber = extractAabhaCardNumber(result);
            }
            if("Driving Licence".equals(cardType)) {
                cardNumber = extractDrivingLicenseNumber(result);
            }
            // Clean up temporary file
            Files.deleteIfExists(tempFile.toPath());
            return ResponseEntity.ok(cardNumber);
        } catch (TesseractException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing image");
        }
    }
    
    private String extractAadhaarNumber(String ocrText) {
        // Aadhaar number regex pattern (12 digits)
        String regex = "\\d{4}\\s\\d{4}\\s\\d{4}";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(ocrText);
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return "Aadhaar number not found";
        }
    }

    private String extractCreditCardNumber(String ocrText) {
        String regex = "\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(ocrText);
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return "Credit card number not found";
        }
    }

    private String extractDebitCardNumber(String ocrText) {
        String regex = "\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(ocrText);
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return "Debit card number not found";
        }
    }

    private String extractDrivingLicenseNumber(String ocrText) {
        String regex = "\\d{4}\\s\\d{4}\\s\\d{4}"; // Adjust regex as needed
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(ocrText);
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return "Driving license number not found";
        }
    }

    private String extractAabhaCardNumber(String ocrText) {
        String regex = "\\d{2}-\\d{4}-\\d{4}-\\d{4}"; // Adjust regex as needed
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(ocrText);
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return "Aabha card number not found";
        }
    }
}
