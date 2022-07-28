package ar.com.jluque.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ar.com.jluque.dto.ExtractLine;

@Repository
public class ExtractLegacyRespository {

	@Autowired
	@Qualifier("jdbcSlave")
	private JdbcTemplate jdbcTemplate;

	public List<ExtractLine> findExtractInterop(String queryExtract) {
		System.out.println("Buscando extract de interoperable.");
		return jdbcTemplate.query(queryExtract, new BeanPropertyRowMapper<>(ExtractLine.class));
	}
}
