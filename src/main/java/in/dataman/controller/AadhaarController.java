// package in.dataman.controller;

// import java.awt.image.BufferedImage;
// import java.io.ByteArrayInputStream;
// import java.io.File;
// import java.io.IOException;
// import java.io.InputStream;
// import java.nio.file.Files;
// import java.nio.file.StandardCopyOption;

// import javax.imageio.ImageIO;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.core.io.ClassPathResource;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;

// import in.dataman.dto.CardDataDTO;
// import net.sourceforge.tess4j.ITesseract;
// import net.sourceforge.tess4j.Tesseract;
// import net.sourceforge.tess4j.TesseractException;

// @RestController
// @RequestMapping("/api/aadhaar")
// public class AadhaarController {

// 	@GetMapping
// 	public String home() {
// 		return "Welcome to my application";
// 	}

// //    @PostMapping("/extract")
// //    public ResponseEntity<String> extractAadhaar(@ModelAttribute CardDataDTO formData) throws IOException {
// //        MultipartFile file = formData.getFile();
// //        String cardType = formData.getCardName();
// //        ITesseract tesseract = new Tesseract();
// //        
// //        
// //        tesseract.setDatapath(new ClassPathResource("tessdata").getFile().getPath());
// //        tesseract.setLanguage("eng");
// //
// //        try {
// //            // Get the path to the tessdata directory in the resources folder
// //            ClassPathResource resource = new ClassPathResource("tessdata");
// //            File tessdataDir = resource.getFile();
// //            // Set the Tesseract data path
// //            tesseract.setDatapath(tessdataDir.getAbsolutePath());
// //            tesseract.setLanguage("eng");
// //            // Save the uploaded file to a temporary location
// //            File tempFile = File.createTempFile("aadhaar", ".png");
// //            file.transferTo(tempFile);
// //            // Perform OCR on the image
// //            String result = tesseract.doOCR(tempFile);
// //            // Extract Aadhaar number using regex
// //            String cardNumber = "";
// //            if("Aadhar Card".equals(cardType)) {
// //                cardNumber = extractAadhaarNumber(result);
// //            }
// //            if("Debit Card".equals(cardType)) {
// //                cardNumber = extractDebitCardNumber(result);
// //            }
// //            if("Credit Card".equals(cardType)) {
// //                cardNumber = extractCreditCardNumber(result);
// //            }
// //            if("Aabha Card".equals(cardType)) {
// //                cardNumber = extractAabhaCardNumber(result);
// //            }
// //            if("Driving Licence".equals(cardType)) {
// //                cardNumber = extractDrivingLicenseNumber(result);
// //            }
// //            // Clean up temporary file
// //            Files.deleteIfExists(tempFile.toPath());
// //            return ResponseEntity.ok(cardNumber);
// //        } catch (TesseractException | IOException e) {
// //            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing image");
// //        }
// //    }

// //	@PostMapping("/extract")
// //	public ResponseEntity<String> extractAadhaar(@ModelAttribute CardDataDTO formData) {
// //		MultipartFile file = formData.getFile();
// //		String cardType = formData.getCardName();
// //		ITesseract tesseract = new Tesseract();
// //
// //		try {
// //			// Load the tessdata file from the classpath
// //			tesseract.setDatapath(new ClassPathResource("tessdata").getFile().getAbsolutePath());
// //			tesseract.setLanguage("eng");
// //
// //			// Use in-memory storage for the uploaded image
// //			ByteArrayInputStream bis = new ByteArrayInputStream(file.getBytes());
// //			BufferedImage image = ImageIO.read(bis);
// //
// //			// Perform OCR on the in-memory image
// //			String result = tesseract.doOCR(image);
// //
// //			String cardNumber = "";
// //			switch (cardType) {
// //			case "Aadhar Card":
// //				cardNumber = extractAadhaarNumber(result);
// //				break;
// //			case "Debit Card":
// //				cardNumber = extractDebitCardNumber(result);
// //				break;
// //			case "Credit Card":
// //				cardNumber = extractCreditCardNumber(result);
// //				break;
// //			case "Aabha Card":
// //				cardNumber = extractAabhaCardNumber(result);
// //				break;
// //			case "Driving Licence":
// //				cardNumber = extractDrivingLicenseNumber(result);
// //				break;
// //			default:
// //				cardNumber = "Card type not supported";
// //			}
// //
// //			return ResponseEntity.ok(cardNumber);
// //
// //		} catch (TesseractException | IOException e) {
// //			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
// //					.body("Error processing image: " + e.getMessage());
// //		}
// //	}

// //	@PostMapping("/extract")
// //	public ResponseEntity<String> extractAadhaar(@ModelAttribute CardDataDTO formData) {
// //		MultipartFile file = formData.getFile();
// //		String cardType = formData.getCardName();
// //		ITesseract tesseract = new Tesseract();
// //
// //		try {
// //			// Load tessdata from the classpath as a stream
// //			ClassPathResource tessdataResource = new ClassPathResource("tessdata/eng.traineddata");
// //			File tessdataDir = Files.createTempDirectory("tessdata").toFile();
// //
// //			// Copy tessdata files to temporary location because Tesseract requires a file
// //			// path
// //			File engFile = new File(tessdataDir, "eng.traineddata");
// //			try (InputStream is = tessdataResource.getInputStream()) {
// //				Files.copy(is, engFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
// //			}
// //
// //			// Set Tesseract's data path to the temporary tessdata directory
// //			tesseract.setDatapath(tessdataDir.getAbsolutePath());
// //			tesseract.setLanguage("eng");
// //
// //			// Use in-memory storage for the uploaded image
// //			ByteArrayInputStream bis = new ByteArrayInputStream(file.getBytes());
// //			BufferedImage image = ImageIO.read(bis);
// //
// //			// Perform OCR on the in-memory image
// //			String result = tesseract.doOCR(image);
// //
// //			String cardNumber = "";
// //			switch (cardType) {
// //			case "Aadhar Card":
// //				cardNumber = extractAadhaarNumber(result);
// //				break;
// //			case "Debit Card":
// //				cardNumber = extractDebitCardNumber(result);
// //				break;
// //			case "Credit Card":
// //				cardNumber = extractCreditCardNumber(result);
// //				break;
// //			case "Aabha Card":
// //				cardNumber = extractAabhaCardNumber(result);
// //				break;
// //			case "Driving Licence":
// //				cardNumber = extractDrivingLicenseNumber(result);
// //				break;
// //			default:
// //				cardNumber = "Card type not supported";
// //			}
// //
// //			return ResponseEntity.ok(cardNumber);
// //
// //		} catch (TesseractException | IOException e) {
// //			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
// //					.body("Error processing image: " + e.getMessage());
// //		}
// //	}

// //	@PostMapping("/extract")
// //	public ResponseEntity<String> extractAadhaar(@ModelAttribute CardDataDTO formData) {
// //		Logger logger = LoggerFactory.getLogger(getClass()); // Initialize logger
// //		MultipartFile file = formData.getFile();
// //		String cardType = formData.getCardName();
// //		ITesseract tesseract = new Tesseract();
// //
// //		try {
// //			// Load tessdata from the classpath as a stream
// //			logger.info("Loading tessdata for OCR...");
// //			ClassPathResource tessdataResource = new ClassPathResource("tessdata/eng.traineddata");
// //			File tessdataDir = Files.createTempDirectory("tessdata").toFile();
// //
// //			// Copy tessdata files to temporary location because Tesseract requires a file
// //			// path
// //			logger.info("Copying tessdata to temporary directory...");
// //			File engFile = new File(tessdataDir, "eng.traineddata");
// //			try (InputStream is = tessdataResource.getInputStream()) {
// //				Files.copy(is, engFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
// //			}
// //
// //			// Set Tesseract's data path to the temporary tessdata directory
// //			tesseract.setDatapath(tessdataDir.getAbsolutePath());
// //			tesseract.setLanguage("eng");
// //
// //			// Use in-memory storage for the uploaded image
// //			logger.info("Reading uploaded image file...");
// //			ByteArrayInputStream bis = new ByteArrayInputStream(file.getBytes());
// //			BufferedImage image = ImageIO.read(bis);
// //
// //			// Perform OCR on the in-memory image
// //			logger.info("Performing OCR...");
// //			String result = tesseract.doOCR(image);
// //
// //			String cardNumber = "";
// //			switch (cardType) {
// //			case "Aadhar Card":
// //				logger.info("Extracting Aadhar card number...");
// //				cardNumber = extractAadhaarNumber(result);
// //				break;
// //			case "Debit Card":
// //				logger.info("Extracting Debit card number...");
// //				cardNumber = extractDebitCardNumber(result);
// //				break;
// //			case "Credit Card":
// //				logger.info("Extracting Credit card number...");
// //				cardNumber = extractCreditCardNumber(result);
// //				break;
// //			case "Aabha Card":
// //				logger.info("Extracting Aabha card number...");
// //				cardNumber = extractAabhaCardNumber(result);
// //				break;
// //			case "Driving Licence":
// //				logger.info("Extracting Driving Licence number...");
// //				cardNumber = extractDrivingLicenseNumber(result);
// //				break;
// //			default:
// //				logger.warn("Unsupported card type: {}", cardType);
// //				cardNumber = "Card type not supported";
// //			}
// //
// //			return ResponseEntity.ok(cardNumber);
// //
// //		} catch (TesseractException e) {
// //			logger.error("Tesseract OCR error: {}", e.getMessage(), e);
// //			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("OCR error: " + e.getMessage());
// //		} catch (IOException e) {
// //			logger.error("File processing error: {}", e.getMessage(), e);
// //			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
// //					.body("Error processing image: " + e.getMessage());
// //		} catch (Exception e) {
// //			logger.error("Unexpected error: {}", e.getMessage(), e);
// //			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
// //		}
// //	}
	
	
	
	
// 	@PostMapping("/extract")
// 	public ResponseEntity<String> extractAadhaar(@ModelAttribute CardDataDTO formData) {
// 	    Logger logger = LoggerFactory.getLogger(getClass()); // Initialize logger
// 	    MultipartFile file = formData.getFile();
// 	    String cardType = formData.getCardName();
// 	    ITesseract tesseract = new Tesseract();

// 	    try {
// 	        // Set Tesseract's data path to the installed location on Heroku
// 	        logger.info("Setting tessdata path for OCR...");
// 	        tesseract.setDatapath("/app/.apt/usr/share/tesseract-ocr/4.00/tessdata");
// 	        tesseract.setLanguage("eng");

// 	        // Use in-memory storage for the uploaded image
// 	        logger.info("Reading uploaded image file...");
// 	        ByteArrayInputStream bis = new ByteArrayInputStream(file.getBytes());
// 	        BufferedImage image = ImageIO.read(bis);

// 	        // Perform OCR on the in-memory image
// 	        logger.info("Performing OCR...");
// 	        String result = tesseract.doOCR(image);

// 	        String cardNumber = "";
// 	        switch (cardType) {
// 	            case "Aadhar Card":
// 	                logger.info("Extracting Aadhar card number...");
// 	                cardNumber = extractAadhaarNumber(result);
// 	                break;
// 	            case "Debit Card":
// 	                logger.info("Extracting Debit card number...");
// 	                cardNumber = extractDebitCardNumber(result);
// 	                break;
// 	            case "Credit Card":
// 	                logger.info("Extracting Credit card number...");
// 	                cardNumber = extractCreditCardNumber(result);
// 	                break;
// 	            case "Aabha Card":
// 	                logger.info("Extracting Aabha card number...");
// 	                cardNumber = extractAabhaCardNumber(result);
// 	                break;
// 	            case "Driving Licence":
// 	                logger.info("Extracting Driving Licence number...");
// 	                cardNumber = extractDrivingLicenseNumber(result);
// 	                break;
// 	            default:
// 	                logger.warn("Unsupported card type: {}", cardType);
// 	                cardNumber = "Card type not supported";
// 	        }

// 	        return ResponseEntity.ok(cardNumber);

// 	    } catch (TesseractException e) {
// 	        logger.error("Tesseract OCR error: {}", e.getMessage(), e);
// 	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("OCR error: " + e.getMessage());
// 	    } catch (IOException e) {
// 	        logger.error("File processing error: {}", e.getMessage(), e);
// 	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
// 	                .body("Error processing image: " + e.getMessage());
// 	    } catch (Exception e) {
// 	        logger.error("Unexpected error: {}", e.getMessage(), e);
// 	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
// 	    }
// 	}


// 	private String extractAadhaarNumber(String ocrText) {
// 		// Aadhaar number regex pattern (12 digits)
// 		String regex = "\\d{4}\\s\\d{4}\\s\\d{4}";
// 		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
// 		java.util.regex.Matcher matcher = pattern.matcher(ocrText);
// 		if (matcher.find()) {
// 			return matcher.group(0);
// 		} else {
// 			return "Aadhaar number not found";
// 		}
// 	}

// 	private String extractCreditCardNumber(String ocrText) {
// 		String regex = "\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}";
// 		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
// 		java.util.regex.Matcher matcher = pattern.matcher(ocrText);
// 		if (matcher.find()) {
// 			return matcher.group(0);
// 		} else {
// 			return "Credit card number not found";
// 		}
// 	}

// 	private String extractDebitCardNumber(String ocrText) {
// 		String regex = "\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}";
// 		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
// 		java.util.regex.Matcher matcher = pattern.matcher(ocrText);
// 		if (matcher.find()) {
// 			return matcher.group(0);
// 		} else {
// 			return "Debit card number not found";
// 		}
// 	}

// 	private String extractDrivingLicenseNumber(String ocrText) {
// 		String regex = "\\d{4}\\s\\d{4}\\s\\d{4}"; // Adjust regex as needed
// 		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
// 		java.util.regex.Matcher matcher = pattern.matcher(ocrText);
// 		if (matcher.find()) {
// 			return matcher.group(0);
// 		} else {
// 			return "Driving license number not found";
// 		}
// 	}

// 	private String extractAabhaCardNumber(String ocrText) {
// 		String regex = "\\d{2}-\\d{4}-\\d{4}-\\d{4}"; // Adjust regex as needed
// 		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
// 		java.util.regex.Matcher matcher = pattern.matcher(ocrText);
// 		if (matcher.find()) {
// 			return matcher.group(0);
// 		} else {
// 			return "Aabha card number not found";
// 		}
// 	}
// }




package in.dataman.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
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

    // @PostMapping("/extract")
    // public ResponseEntity<String> extractAadhaar(@ModelAttribute CardDataDTO formData) throws IOException {
    //     MultipartFile file = formData.getFile();
    //     String cardType = formData.getCardName();
    //     ITesseract tesseract = new Tesseract();
        
        
    //     tesseract.setDatapath(new ClassPathResource("tessdata").getFile().getPath());
    //     tesseract.setLanguage("eng");

    //     try {
    //         // Get the path to the tessdata directory in the resources folder
    //         ClassPathResource resource = new ClassPathResource("tessdata");
    //         File tessdataDir = resource.getFile();
    //         // Set the Tesseract data path
    //         tesseract.setDatapath(tessdataDir.getAbsolutePath());
    //         tesseract.setLanguage("eng");
    //         // Save the uploaded file to a temporary location
    //         File tempFile = File.createTempFile("aadhaar", ".png");
    //         file.transferTo(tempFile);
    //         // Perform OCR on the image
    //         String result = tesseract.doOCR(tempFile);
    //         // Extract Aadhaar number using regex
    //         String cardNumber = "";
    //         if("Aadhar Card".equals(cardType)) {
    //             cardNumber = extractAadhaarNumber(result);
    //         }
            
    //         if("Aabha Card".equals(cardType)) {
    //             cardNumber = extractAabhaCardNumber(result);
    //         }
            
    //         // Clean up temporary file
    //         Files.deleteIfExists(tempFile.toPath());
    //         return ResponseEntity.ok(cardNumber);
    //     } catch (TesseractException | IOException e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing image");
    //     }
    // }



	// @PostMapping("/extract")
	// public ResponseEntity<String> extractAadhaar(@ModelAttribute CardDataDTO formData) throws IOException {
	// 	MultipartFile file = formData.getFile();
	// 	String cardType = formData.getCardName();
	// 	ITesseract tesseract = new Tesseract();

	// 	// Set Tesseract data path dynamically
	// 	String tessdataPath;
	// 	if (System.getenv("DYNO") != null) {
	// 		// Running on Heroku
	// 		tessdataPath = "/app/.apt/usr/share/tesseract-ocr/4.00/tessdata";
	// 	} else {
	// 		// Running locally
	// 		tessdataPath = new ClassPathResource("tessdata").getFile().getPath();
	// 	}
	// 	tesseract.setDatapath(tessdataPath);
	// 	tesseract.setLanguage("eng");

	// 	try {
	// 		// Save the uploaded file to a temporary location
	// 		File tempFile = File.createTempFile("aadhaar", ".png");
	// 		file.transferTo(tempFile);

	// 		// Perform OCR on the image
	// 		String result = tesseract.doOCR(tempFile);

	// 		// Extract card number based on type
	// 		String cardNumber = "";
	// 		if ("Aadhar Card".equals(cardType)) {
	// 			cardNumber = extractAadhaarNumber(result);
	// 		} else if ("Aabha Card".equals(cardType)) {
	// 			cardNumber = extractAabhaCardNumber(result);
	// 		}

	// 		// Clean up temporary file
	// 		Files.deleteIfExists(tempFile.toPath());

	// 		return ResponseEntity.ok(cardNumber);
	// 	} catch (TesseractException | IOException e) {
	// 		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing image");
	// 	}
	// }



	@PostMapping("/extract")
public ResponseEntity<String> extractAadhaar(@ModelAttribute CardDataDTO formData) throws IOException {
    MultipartFile file = formData.getFile();
    String cardType = formData.getCardName();
    ITesseract tesseract = new Tesseract();

    // Set Tesseract data path dynamically
    String tessdataPath;
    if (System.getenv("DYNO") != null) {
        // Running on Heroku
        tessdataPath = "/app/.apt/usr/share/tesseract-ocr/4.00/tessdata";
    } else {
        // Running locally
        tessdataPath = new ClassPathResource("tessdata").getFile().getPath();
    }
    tesseract.setDatapath(tessdataPath);
    tesseract.setLanguage("eng");

    File tempFile = null;
    try {
        // Save the uploaded file to a temporary location
        tempFile = File.createTempFile("aadhaar", ".png");
        file.transferTo(tempFile);
        
        // Perform OCR on the image
        String result = tesseract.doOCR(tempFile);
        
        // Extract card number based on type
        String cardNumber = "";
        if ("Aadhar Card".equals(cardType)) {
            cardNumber = extractAadhaarNumber(result);
        } else if ("Aabha Card".equals(cardType)) {
            cardNumber = extractAabhaCardNumber(result);
        }

        return ResponseEntity.ok(cardNumber);
    } catch (TesseractException e) {
        // Log stack trace and return detailed error message
        String errorDetails = "Error performing OCR: " + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    } catch (IOException e) {
        // Log stack trace and return detailed error message
        String errorDetails = "Error handling file: " + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    } finally {
        // Clean up temporary file in the finally block to ensure it is deleted
        if (tempFile != null && tempFile.exists()) {
            Files.deleteIfExists(tempFile.toPath());
        }
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

