package ar.com.jluque.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ar.com.jluque.dto.ExtractLine;

@Repository
public class ExtractRepository {

//	@Autowired
//	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("jdbcMaster")
	private JdbcTemplate jdbcTemplate;

	public List<ExtractLine> findExtractPagos(String queryExtract) {
		return jdbcTemplate.query(queryExtract, new BeanPropertyRowMapper<>(ExtractLine.class));
	}

}
