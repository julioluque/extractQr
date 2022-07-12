package ar.com.jluque.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

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
			BufferedReader brPagos = new BufferedReader(new FileReader(files.getPagos()));
			BufferedReader brInterop = new BufferedReader(new FileReader(files.getInterop()));
			BufferedWriter bwMerge = new BufferedWriter(new FileWriter(files.getMerge()));

			String pagosText = extractRead(brPagos);
			String interopText = extractRead(brInterop);

			mergeWrite(bwMerge, pagosText, interopText);
			bwMerge.flush();

		} catch (IOException e) {
			System.out.println("Error E/S: " + e);
		}

		return "OK";
	}

	public static String extractRead(BufferedReader br) throws IOException {
		String texto = "";
		String bfRead;
		while ((bfRead = br.readLine()) != null) {
			texto = texto + bfRead + "\n";
		}
		return texto;
	}

	public static void mergeWrite(BufferedWriter bwMerge, String brPagos, String brInterop) throws IOException {
		bwMerge.write(brPagos);
		bwMerge.newLine();
		bwMerge.write(brInterop);
	}

}
