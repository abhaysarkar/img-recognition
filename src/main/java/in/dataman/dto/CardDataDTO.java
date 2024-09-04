package in.dataman.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDataDTO {
	
	private MultipartFile file;
	private String cardName;

}