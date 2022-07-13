package ar.com.jluque.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import ar.com.jluque.dto.ExtractDto;
import ar.com.jluque.dto.ExtractFiles;

@Service
public class ExtractService {

	public String mergeExtract(ExtractFiles files) {

		if (files.getPagos() == null || files.getPagos().equals(""))
			files.setPagos("C:\\Users\\Julio\\Documents\\RLNK\\pagos.txt");
		if (files.getInterop() == null || files.getInterop().equals(""))
			files.setInterop("C:\\Users\\Julio\\Documents\\RLNK\\interop.txt");
		if (files.getMerge() == null || files.getMerge().equals(""))
			files.setMerge("C:\\Users\\Julio\\Documents\\RLNK\\merge.txt");

		try {
			BufferedWriter bwMerge = new BufferedWriter(new FileWriter(files.getMerge()));
			String header = getHeader();
			ExtractDto pagosDto = exctractReadStream(files.getPagos());
			ExtractDto interopDto = exctractReadStream(files.getInterop());
			mergeWrite(bwMerge, pagosDto, interopDto, header);
		} catch (Exception e) {
			System.out.println("Error E/S: " + e.getMessage());
		}

		return "OK";
	}

	public static ExtractDto extractRead(BufferedReader br) throws IOException {
		System.out.println("reading extract  ...");
		String texto = "";
		int row = 1;
		String bfRead;
		while ((bfRead = br.readLine()) != null) {
			row++;
			texto = texto + bfRead + "\n";
		}
		return ExtractDto.builder().extract(texto).rows(row - 1).build();
	}

	private static ExtractDto exctractReadStream(String path) throws Exception {
		FileInputStream inputStream = null;
		Scanner sc = null;
		String line = "";
		int row = 1;

		try {
			inputStream = new FileInputStream(path);
			sc = new Scanner(inputStream, "UTF-8");

			while (sc.hasNextLine()) {
				line = line + sc.nextLine() + "\n";
				row++;
			}
			if (sc.ioException() != null) {
				throw sc.ioException();
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (sc != null) {
				sc.close();
			}
		}
		return ExtractDto.builder().extract(line).rows(row - 1).build();
	}

	public static void mergeWrite(BufferedWriter bwMerge, ExtractDto pagos, ExtractDto interop, String header)
			throws IOException {
		System.out.println("writing both extracts...");
		bwMerge.write(header);
		bwMerge.newLine();
		bwMerge.write(pagos.getExtract());
		bwMerge.write(interop.getExtract());
		bwMerge.write(getFooter(pagos.getRows(), interop.getRows()));
		bwMerge.flush();
	}

	private static String getHeader() {

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

	private static String getFooter(int pagosRows, int interopRows) {
		String footer = "FOOTER";
		String totalRows = String.valueOf(pagosRows + interopRows);
		String rows = String.format("%-10s", totalRows);
		String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		footer = footer + rows + dateTime;
		System.out.println(footer);
		return footer;
	}

}
