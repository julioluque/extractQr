package ar.com.jluque.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ar.com.jluque.dto.ExtractFiles;
import ar.com.jluque.config.Environments;

public class ExtractMapper {

	public ExtractMapper() {
	}

	public static void getPaths(ExtractFiles files, Environments env) {
		if (files.getPagos() == null || files.getPagos().equals(""))
			files.setPagos(env.getPagosPath());
		else
			files.setPagos(files.getPagos().replace("/", "\\"));

		if (files.getInterop() == null || files.getInterop().equals(""))
			files.setInterop(env.getInteropPath());
		else
			files.setInterop(files.getInterop().replace("/", "\\"));

		if (files.getMerge() == null || files.getMerge().equals(""))
			files.setMerge(env.getMergePath());
		else
			files.setMerge(files.getMerge().replace("/", "\\"));
	}
	
	public static String getHeader() {
		String header = "HEADER";
		String codigoEntidad = "QR0014";
		String frecuencia = "D_";
		String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
		String separador = ".txt";
		String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		String version = "03";
		String filler = " ".repeat(631);
		header = header + codigoEntidad + frecuencia + date + separador + dateTime + version + filler;
		System.out.println(header);
		return header;
	}

	public static String getFooter(int pagosRows, int interopRows) {
		String footer = "FOOTER";
		String totalRows = String.valueOf(pagosRows + interopRows);
		String rows = String.format("%-10s", totalRows);
		String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		footer = footer + rows + dateTime;
		System.out.println(footer);
		return footer;
	}
}
