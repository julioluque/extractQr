package ar.com.jluque.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.jluque.domain.Constant;
import ar.com.jluque.dto.ExtractFiles;
import ar.com.jluque.dto.ExtractLine;
import ar.com.jluque.mapper.ExtractMapper;
import ar.com.jluque.repository.ExtractLegacyRespository;
import ar.com.jluque.repository.ExtractRepository;

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
			String footer = ExtractMapper.getFooter(ex.size());
			exctractWriteStreamAsync(files.getMerge(), listStrg, header, footer);

		} catch (Exception e) {
			throw new Exception("Error E/S: " + e.getMessage());
		}
		System.out.println("Fin proceso: " + LocalDateTime.now().toString());
		return "OK";
	}

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

}
