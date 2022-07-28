package ar.com.jluque.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.jluque.domain.Constant;
import ar.com.jluque.dto.ExtractDto;
import ar.com.jluque.dto.ExtractFiles;
import ar.com.jluque.dto.ExtractLine;
import ar.com.jluque.mapper.ExtractMapper;
import ar.com.jluque.repository.ExtractLegacyRespository;
import ar.com.jluque.repository.ExtractRepository;
import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

@Service
public class ExtractService {

	@Autowired
	private ExtractRepository repository;

	@Autowired
	private ExtractLegacyRespository legacyRepository;

	public String getExtract(ExtractFiles files) throws Exception {
		System.out.println("Inicio proceso: " + LocalDateTime.now().toString());
		try {
			String header = ExtractMapper.getHeader(ExtractMapper.getFileName());

			CompletableFuture<List<ExtractLine>> pagosCompletableFuture = CompletableFuture.supplyAsync(() -> {
				return repository.findExtractPagos(Constant.QUERY_PAGOS);
			});

			CompletableFuture<List<ExtractLine>> interopFuture = pagosCompletableFuture.thenApply(pagos -> {
				List<ExtractLine> inter = legacyRepository.findExtractInterop(Constant.QUERY_INTEROP);
				System.out.println(pagos.size() + " + " + inter.size());
				return Stream.of(pagos, inter).flatMap(x -> x.stream()).collect(Collectors.toList());
			});
			
			List<ExtractLine> ex = interopFuture.get();
			
			String listStrg = ex.stream().map(ExtractLine::getLinea).collect(Collectors.joining("\n"));
			String footer = ExtractMapper.getFooterAsync(ex.size());
			exctractWriteStreamAsync(files.getMerge(), listStrg, header, footer);
			
			
			
//			List<ExtractLine> pagosDbList = repository.findExtractPagos(Constant.QUERY_PAGOS);
//			String pagosList = pagosDbList.stream().map(ExtractLine::getLinea).collect(Collectors.joining("\n"));
//
//			List<ExtractLine> interopDbList = legacyRepository.findExtractInterop(Constant.QUERY_INTEROP);
//			String interopList = interopDbList.stream().map(ExtractLine::getLinea).collect(Collectors.joining("\n"));
//
//			String footer = ExtractMapper.getFooter(pagosDbList.size(), interopDbList.size());
//			exctractWriteStreamFinal(files.getMerge(), pagosList, interopList, header, footer);
		} catch (Exception e) {
			throw new Exception("Error E/S: " + e.getMessage());
		}
		System.out.println("Fin proceso: " + LocalDateTime.now().toString());
		return "OK";
	}

//	public String mergeExtract(ExtractFiles files) throws Exception {
//		try {
//			String header = ExtractMapper.getHeader(ExtractMapper.getFileName());
//			ExtractDto pagosDto = exctractReadStream(files.getPagos());
//			ExtractDto interopDto = exctractReadStream(files.getInterop());
//			String footer = ExtractMapper.getFooter(pagosDto.getRows(), interopDto.getRows());
//			exctractWriteStream(files.getMerge(), pagosDto.getExtract(), interopDto.getExtract(), header, footer);
//		} catch (Exception e) {
//			throw new Exception("Error E/S: " + e.getMessage());
//		}
//		return "OK";
//	}

//	private static ExtractDto exctractReadStream(String path) throws Exception {
//		FileInputStream inputStream = null;
//		Scanner sc = null;
//		String line = "";
//		int row = 1;
//
//		try {
//			inputStream = new FileInputStream(path);
//			sc = new Scanner(inputStream, "UTF-8");
//
//			while (sc.hasNextLine()) {
//				line = line + sc.nextLine() + "\n";
//				row++;
//			}
//
//			if (sc.ioException() != null)
//				throw sc.ioException();
//
//		} finally {
//			if (inputStream != null)
//				inputStream.close();
//
//			if (sc != null)
//				sc.close();
//		}
//		return ExtractDto.builder().extract(line).rows(row - 1).build();
//	}

	private void exctractWriteStreamAsync(String path, String extract, String header, String footer)
			throws IOException {
		System.out.println("Mergeando Archivos.");
		byte[] line = "\n".getBytes();
		FileOutputStream outputStream = new FileOutputStream(path);
		outputStream.write(header.getBytes());
		outputStream.write(line);
		outputStream.write(extract.getBytes());
		outputStream.write(line);
		outputStream.write(footer.getBytes());
		outputStream.close();
	}
	
	
//	private void exctractWriteStreamFinal(String path, String pagos, String interop, String header, String footer)
//			throws IOException {
//		System.out.println("Mergeando Archivos.");
//		byte[] line = "\n".getBytes();
//		FileOutputStream outputStream = new FileOutputStream(path);
//		outputStream.write(header.getBytes());
//		outputStream.write(line);
//		outputStream.write(pagos.getBytes());
//		outputStream.write(line);
//		outputStream.write(interop.getBytes());
//		outputStream.write(line);
//		outputStream.write(footer.getBytes());
//		outputStream.close();
//	}

//	private void exctractWriteStream(String path, String pagos, String interop, String header, String footer)
//			throws IOException {
//		FileOutputStream outputStream = new FileOutputStream(path);
//		outputStream.write(header.getBytes());
//		outputStream.write(pagos.getBytes());
//		outputStream.write(interop.getBytes());
//		outputStream.write(footer.getBytes());
//		outputStream.close();
//	}

}
