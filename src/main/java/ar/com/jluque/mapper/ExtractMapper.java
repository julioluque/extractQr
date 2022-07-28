package ar.com.jluque.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ar.com.jluque.dto.ExtractFiles;
import ar.com.jluque.config.Environments;

public class ExtractMapper {

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
		String header = "HEADER";
		String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		String version = "03";
		String filler = " ".repeat(631);
		return header + fileName + dateTime + version + filler;
	}

	public static String getFooter(int pagosRows, int interopRows) {
		String footer = "FOOTER";
		String totalRows = String.valueOf(pagosRows + interopRows);
		String rows = String.format("%-10s", totalRows);
		String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		return footer + rows + dateTime;
	}
}
