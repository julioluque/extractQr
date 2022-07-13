package ar.com.jluque.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
public class Environments {

	@Value("${file.path.pagos}")
	private String pagosPath;

	@Value("${file.path.interop}")
	private String interopPath;

	@Value("${file.path.merge}")
	private String mergePath;
}