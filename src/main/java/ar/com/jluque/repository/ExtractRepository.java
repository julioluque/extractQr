package ar.com.jluque.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ar.com.jluque.dto.ExtractLine;
import ar.com.jluque.service.ExtractService;

@Repository
public class ExtractRepository {

	private static final Logger log = LoggerFactory.getLogger(ExtractRepository.class);

	@Autowired
	@Qualifier("jdbcMaster")
	private JdbcTemplate jdbcTemplate;

	public List<ExtractLine> findExtractPagos(String queryExtract) {
		log.info("Buscando extract de pagos.");
		return jdbcTemplate.query(queryExtract, new BeanPropertyRowMapper<>(ExtractLine.class));
	}

}
