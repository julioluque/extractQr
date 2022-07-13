package ar.com.jluque.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExtractDto {

	private String extract;
	private int rows;

}
