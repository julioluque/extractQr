package ar.com.jluque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.jluque.config.Environments;
import ar.com.jluque.dto.ExtractFiles;
import ar.com.jluque.mapper.ExtractMapper;
import ar.com.jluque.service.ExtractService;

@RestController
@RequestMapping("/extract")
public class ExtractController {

	@Autowired
	private ExtractService service;

	@Autowired
	private Environments env;

	@PostMapping("/")
	public ResponseEntity<String> extractMerge(@RequestBody ExtractFiles files) throws Exception {
		ExtractMapper.getPaths(files, env);
		return new ResponseEntity<>(service.mergeExtract(files), HttpStatus.OK);
	}
}
