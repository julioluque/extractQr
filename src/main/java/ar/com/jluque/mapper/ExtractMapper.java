package ar.com.jluque.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.jluque.dto.ExtractFiles;
import ar.com.jluque.service.ExtractService;
import ar.com.jluque.config.Environments;

public class ExtractMapper {
    private static final Logger log = LoggerFactory.getLogger(ExtractMapper.class);

	public ExtractMapper() {
	}

	public static void getPaths(ExtractFiles files, Environments env) {
		files.setPagos(files.getPagos() == null || files.getPagos().equals("") ? env.getPagosPath()
				: files.getPagos().replace("/", "\\"));

		files.setInterop(files.getInterop() == null || files.getInterop().equals("") ? env.getInteropPath()
				: files.getInterop().replace("/", "\\"));

		files.setMerge(files.getMerge() == null || files.getMerge().equals("") ? env.getMergePath() + getFileName()
				: files.getMerge().replace("/", "\\") + getFileName());
	}

	public static String getFileName() {
		String codigoEntidad = "QR0014";
		String frecuencia = "D_";
		String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
		String extension = ".txt";
		return codigoEntidad + frecuencia + date + extension;
	}

	public static String getHeader(String fileName) {
		log.info("Creando header.");
		String header = "HEADER";
		String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		String version = "03";
		String filler = " ".repeat(631);
		return header + fileName + dateTime + version + filler;
	}

	public static String getFooter(int extractRows) {
		log.info("Creando Footer.");
		String footer = "FOOTER";
		String rows = String.format("%-10s", String.valueOf(extractRows + 1));
		String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		return footer + rows + dateTime;
	}

}
