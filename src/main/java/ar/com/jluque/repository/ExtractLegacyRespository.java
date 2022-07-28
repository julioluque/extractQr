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

@Repository
public class ExtractLegacyRespository {
	private static final Logger log = LoggerFactory.getLogger(ExtractLegacyRespository.class);

	@Autowired
	@Qualifier("jdbcSlave")
	private JdbcTemplate jdbcTemplate;

	public List<ExtractLine> findExtractInterop(String queryExtract) {
		log.info("Buscando extract de interoperable.");
		return jdbcTemplate.query(queryExtract, new BeanPropertyRowMapper<>(ExtractLine.class));
	}
}
