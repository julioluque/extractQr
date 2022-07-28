package ar.com.jluque.domain;

public class Constant {

	public static final String QUERY_INTEROP = "-- INTEROP\r\n"
			+ "SELECT\r\n"
			+ "(CASE WHEN OPERACION_1.CBU_EMISOR IS NULL THEN RPAD(' ', 22, ' ') ELSE RPAD(TO_CHAR(OPERACION_1.CBU_EMISOR), 22, ' ') END)\r\n"
			+ "|| (CASE WHEN OPERACION_1.EMISOR IS NULL THEN RPAD(' ', 4, ' ') ELSE RPAD(TO_CHAR(OPERACION_1.EMISOR), 4, ' ') END)\r\n"
			+ "|| (CASE WHEN OPERACION_1.CUIT_TITULAR IS NULL THEN RPAD(' ', 19, ' ') ELSE RPAD(TO_CHAR(OPERACION_1.CUIT_TITULAR), 19, ' ') END) \r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.TERMINAL_POS IS NULL THEN RPAD('0', 50, ' ') ELSE RPAD(TO_CHAR(QR_FORMATEADO.TERMINAL_POS), 50, ' ') END)\r\n"
			+ "|| RPAD(' ', 22, ' ') \r\n"
			+ "|| RPAD(' ', 4, ' ')\r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.CUIT IS NULL THEN RPAD(' ', 13, ' ') ELSE RPAD(TO_CHAR(QR_FORMATEADO.CUIT), 13, ' ') END)\r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.MCC IS NULL THEN RPAD('0', 4, ' ') ELSE RPAD(TO_CHAR(QR_FORMATEADO.MCC), 4, ' ') END)\r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.NOMBRE_FANTASIA IS NULL THEN RPAD(' ', 30, ' ') ELSE RPAD(TO_CHAR(QR_FORMATEADO.NOMBRE_FANTASIA), 30, ' ') END)\r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.CIUDAD IS NULL THEN RPAD(' ', 20, ' ') ELSE RPAD(TO_CHAR(QR_FORMATEADO.CIUDAD), 20, ' ') END)\r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.CODIGO_POSTAL IS NULL THEN RPAD(' ', 10, ' ') ELSE RPAD(TO_CHAR(QR_FORMATEADO.CODIGO_POSTAL), 10, ' ') END)\r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.CVU IS NULL THEN RPAD(' ', 22, ' ') ELSE RPAD(TO_CHAR(QR_FORMATEADO.CVU), 22, ' ') END)\r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.MONEDA IS NOT NULL AND QR_FORMATEADO.MONEDA  = 'ARS' THEN  RPAD('032', 3, ' ') ELSE RPAD(' ', 3, ' ') END)\r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.MONTO IS NULL THEN RPAD(' ', 10, ' ') ELSE	RPAD(\r\n"
			+ "		(SUBSTR(QR_FORMATEADO.MONTO, 0, INSTR(QR_FORMATEADO.MONTO, '.')) \r\n"
			+ "			|| RPAD(SUBSTR(QR_FORMATEADO.MONTO, INSTR(QR_FORMATEADO.MONTO, '.')+1, LENGTH(QR_FORMATEADO.MONTO)), 2, '0')), 10, ' ')\r\n"
			+ "		END)\r\n"
			+ "|| TO_CHAR(CAST(OPERACION_1.FECHA_ULT_ESTADO AS DATE),'YYYYMMDD')\r\n"
			+ "|| TO_CHAR(CAST(OPERACION_1.FECHA_ULT_ESTADO AS DATE),'HH24MISS')\r\n"
			+ "|| (CASE WHEN CANAL.CODIGO IS NULL THEN RPAD(' ', 50, ' ') ELSE RPAD(TO_CHAR(CANAL.CODIGO), 50, ' ') END)\r\n"
			+ "|| (CASE WHEN ESTADO.DESCRIPCION IS NULL THEN RPAD(' ', 20, ' ') ELSE RPAD(TO_CHAR(ESTADO.DESCRIPCION), 20, ' ') END)\r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.OPERACION_ID IS NULL THEN RPAD(' ', 10, ' ') ELSE RPAD(TO_CHAR(QR_FORMATEADO.OPERACION_ID), 10, ' ') END)\r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.REF_FIRST_DATA_ID IS NULL THEN RPAD(' ', 100, ' ') ELSE RPAD(TO_CHAR(QR_FORMATEADO.REF_FIRST_DATA_ID), 100, ' ') END)\r\n"
			+ "|| (CASE WHEN OPERACION_DETALLE_1.REF_NUM IS NULL THEN RPAD(' ', 40, ' ') ELSE RPAD(TO_CHAR(OPERACION_DETALLE_1.REF_NUM), 40, ' ') END)\r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.DOMINIO_REVERSO IS NULL THEN RPAD(' ', 99, ' ') ELSE RPAD(TO_CHAR(QR_FORMATEADO.DOMINIO_REVERSO), 99, ' ') END)\r\n"
			+ "|| RPAD(' ', 10, ' ')\r\n"
			+ "|| (CASE \r\n"
			+ "		WHEN OPERACION_DETALLE_1.REF_NUM IS NULL THEN RPAD(' ', 40, ' ')\r\n"
			+ "		WHEN (TRIM(QR_FORMATEADO.DOMINIO_REVERSO) = 'com.mercadolibre' AND OPERACION_DETALLE_1.PASO_ID = 4) THEN RPAD('0', 40, '0')\r\n"
			+ "		WHEN (TRIM(QR_FORMATEADO.DOMINIO_REVERSO) = 'com.fiserv' AND OPERACION_DETALLE_1.PASO_ID = 3) THEN RPAD(TO_CHAR(OPERACION_DETALLE_1.REF_NUM), 40, ' ')\r\n"
			+ "		ELSE RPAD(' ', 40, ' ')\r\n"
			+ "	END)\r\n"
			+ "|| RPAD(' ', 51, ' ') AS Linea\r\n"
			+ "FROM \r\n"
			+ "(\r\n"
			+ "	(\r\n"
			+ "		(\r\n"
			+ "			(\r\n"
			+ "				SELECT\r\n"
			+ "								OPERACION.OPERACION_ID,\r\n"
			+ "								OPERACION.DOCUMENTO,\r\n"
			+ "								OPERACION.CUIT_TITULAR ,\r\n"
			+ "								OPERACION.CANAL_ID, \r\n"
			+ "								OPERACION.EMISOR, \r\n"
			+ "								OPERACION.CBU_EMISOR, \r\n"
			+ "								OPERACION.TARJETA, \r\n"
			+ "								OPERACION.FECHA_ALTA, \r\n"
			+ "								OPERACION.FECHA_ULT_ESTADO, \r\n"
			+ "								OPERACION.ESTADO_ID\r\n"
			+ "				FROM\r\n"
			+ "								USR_QR_INTEROPERABLE.OPERACION OPERACION\r\n"
			+ "				WHERE\r\n"
			+ "						(	\r\n"
			+ "							OPERACION.EMISOR = '0014'\r\n"
			+ "							AND OPERACION.CANAL_ID IN (1,5,10,11, 12, 13, 17, 18, 26, 27, 28, 29, 31, 32, 20, 21, 24, 14, 15, 16, 19, 25, 22, 23, 33)\r\n"
			+ "							AND TO_CHAR(OPERACION.FECHA_ALTA, 'DD-MM-YYYY') = TO_CHAR(SYSDATE -1, 'DD-MM-YYYY')\r\n"
			+ "--						AND TO_CHAR(OPERACION.FECHA_ALTA, 'DD-MM-YYYY') = '08-07-2022'\r\n"
			+ "\r\n"
			+ "				)\r\n"
			+ "				ORDER BY OPERACION.FECHA_ALTA DESC\r\n"
			+ "\r\n"
			+ "			) \r\n"
			+ "			OPERACION_1\r\n"
			+ "			LEFT OUTER JOIN USR_QR_INTEROPERABLE.QR_FORMATEADO QR_FORMATEADO ON\r\n"
			+ "				OPERACION_1.OPERACION_ID = QR_FORMATEADO.OPERACION_ID\r\n"
			+ "		)\r\n"
			+ "		LEFT OUTER JOIN USR_QR_INTEROPERABLE.ESTADO ESTADO ON\r\n"
			+ "			OPERACION_1.ESTADO_ID = ESTADO.ESTADO_ID\r\n"
			+ "	)\r\n"
			+ "	LEFT OUTER JOIN USR_QR_INTEROPERABLE.CANAL CANAL ON\r\n"
			+ "		OPERACION_1.CANAL_ID = CANAL.CANAL_ID AND CANAL.TERMINAL_TIPO = 'C1'\r\n"
			+ ")\r\n"
			+ "LEFT OUTER JOIN (\r\n"
			+ "SELECT\r\n"
			+ "			OPERACION_DETALLE.OPERACION_ID,\r\n"
			+ "			OPERACION_DETALLE.PASO_ID,\r\n"
			+ "			OPERACION_DETALLE.REF_NUM,\r\n"
			+ "			OPERACION_DETALLE.ESTADO_ID\r\n"
			+ "FROM\r\n"
			+ "			USR_QR_INTEROPERABLE.OPERACION_DETALLE OPERACION_DETALLE\r\n"
			+ "WHERE\r\n"
			+ "	(\r\n"
			+ "		OPERACION_DETALLE.PASO_ID IN (3, 4, 5)\r\n"
			+ "		AND OPERACION_DETALLE.ESTADO_ID = 7\r\n"
			+ "	)\r\n"
			+ ") \r\n"
			+ "	OPERACION_DETALLE_1 ON\r\n"
			+ "	OPERACION_1.OPERACION_ID = OPERACION_DETALLE_1.OPERACION_ID\r\n"
			+ "WHERE\r\n"
			+ "	(1 = 1)";
	
	
	public static final String QUERY_PAGOS = "-- PAGOS\r\n"
			+ "SELECT \r\n"
			+ "(CASE WHEN OPERACION_1.CBU_EMISOR IS NULL THEN RPAD(' ', 22, ' ') ELSE RPAD(TO_CHAR(OPERACION_1.CBU_EMISOR), 22, ' ') END)\r\n"
			+ "|| (CASE WHEN OPERACION_1.EMISOR IS NULL THEN RPAD(' ', 4, ' ') ELSE RPAD(TO_CHAR(OPERACION_1.EMISOR), 4, ' ') END)\r\n"
			+ "|| (CASE WHEN OPERACION_1.DOCUMENTO IS NULL THEN RPAD(' ', 19, ' ') ELSE RPAD(TO_CHAR(OPERACION_1.DOCUMENTO), 19, ' ') END) \r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.TERMINAL_POS IS NULL THEN RPAD('0', 50, ' ') ELSE RPAD(TO_CHAR(QR_FORMATEADO.TERMINAL_POS), 50, ' ') END)\r\n"
			+ "|| RPAD(' ', 22, ' ') \r\n"
			+ "|| RPAD(' ', 4, ' ')\r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.CUIT IS NULL THEN RPAD(' ', 13, ' ') ELSE RPAD(TO_CHAR(QR_FORMATEADO.CUIT), 13, ' ') END)\r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.MCC IS NULL THEN RPAD('0', 4, ' ') ELSE RPAD(TO_CHAR(QR_FORMATEADO.MCC), 4, ' ') END)\r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.NOMBRE_FANTASIA IS NULL THEN RPAD(' ', 30, ' ') ELSE RPAD(TO_CHAR(QR_FORMATEADO.NOMBRE_FANTASIA), 30, ' ') END)\r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.CIUDAD IS NULL THEN RPAD(' ', 20, ' ') ELSE RPAD(TO_CHAR(QR_FORMATEADO.CIUDAD), 20, ' ') END)\r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.CODIGO_POSTAL IS NULL THEN RPAD(' ', 10, ' ') ELSE RPAD(TO_CHAR(QR_FORMATEADO.CODIGO_POSTAL), 10, ' ') END)\r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.CVU IS NULL THEN RPAD(' ', 22, ' ') ELSE RPAD(TO_CHAR(QR_FORMATEADO.CVU), 22, ' ') END)\r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.MONEDA IS NULL THEN RPAD(' ', 3, ' ') ELSE RPAD(TO_CHAR(QR_FORMATEADO.MONEDA), 3, ' ') END)\r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.MONTO IS NULL THEN RPAD(' ', 10, ' ') ELSE	RPAD(\r\n"
			+ "		(SUBSTR(QR_FORMATEADO.MONTO, 0, INSTR(QR_FORMATEADO.MONTO, '.')) \r\n"
			+ "			|| RPAD(SUBSTR(QR_FORMATEADO.MONTO, INSTR(QR_FORMATEADO.MONTO, '.')+1, LENGTH(QR_FORMATEADO.MONTO)), 2, '0')), 10, ' ')\r\n"
			+ "		END)\r\n"
			+ "|| TO_CHAR(CAST(OPERACION_1.FECHA_ULT_ESTADO AS DATE),'YYYYMMDD')\r\n"
			+ "|| TO_CHAR(CAST(OPERACION_1.FECHA_ULT_ESTADO AS DATE),'HH24MISS')\r\n"
			+ "|| (CASE WHEN CANAL.CODIGO IS NULL THEN RPAD(' ', 50, ' ') ELSE RPAD(TO_CHAR(CANAL.CODIGO), 50, ' ') END)\r\n"
			+ "|| (CASE WHEN ESTADO.DESCRIPCION IS NULL THEN RPAD(' ', 20, ' ') ELSE RPAD(TO_CHAR(ESTADO.DESCRIPCION), 20, ' ') END)\r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.OPERACION_ID IS NULL THEN RPAD(' ', 10, ' ') ELSE RPAD(TO_CHAR(QR_FORMATEADO.OPERACION_ID), 10, ' ') END)\r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.REF_FIRST_DATA_ID IS NULL THEN RPAD(' ', 100, ' ') ELSE RPAD(TO_CHAR(QR_FORMATEADO.REF_FIRST_DATA_ID), 100, ' ') END)\r\n"
			+ "|| (CASE WHEN OPERACION_DETALLE_1.REF_NUM IS NULL THEN RPAD(' ', 40, ' ') ELSE RPAD(TO_CHAR(OPERACION_DETALLE_1.REF_NUM), 40, ' ') END)\r\n"
			+ "|| (CASE WHEN QR_FORMATEADO.DOMINIO_REVERSO IS NULL THEN RPAD(' ', 99, ' ') ELSE RPAD(TO_CHAR(QR_FORMATEADO.DOMINIO_REVERSO), 99, ' ') END)\r\n"
			+ "|| RPAD(' ', 10, ' ')\r\n"
			+ "|| (CASE \r\n"
			+ "		WHEN OPERACION_DETALLE_1.REF_NUM IS NULL THEN RPAD(' ', 40, ' ')\r\n"
			+ "		WHEN (TRIM(QR_FORMATEADO.DOMINIO_REVERSO) = 'com.mercadolibre' AND OPERACION_DETALLE_1.PASO_ID = 4) THEN RPAD('0', 40, '0')\r\n"
			+ "		WHEN (TRIM(QR_FORMATEADO.DOMINIO_REVERSO) = 'com.fiserv' AND OPERACION_DETALLE_1.PASO_ID = 3) THEN RPAD(TO_CHAR(OPERACION_DETALLE_1.REF_NUM), 40, ' ')\r\n"
			+ "		WHEN (TRIM(QR_FORMATEADO.DOMINIO_REVERSO) = 'com.yacare' AND OPERACION_DETALLE_1.PASO_ID = 4) THEN RPAD(TO_CHAR(OPERACION_DETALLE_1.REF_NUM), 40, ' ')\r\n"
			+ "		ELSE RPAD(' ', 40, ' ')\r\n"
			+ "	END)\r\n"
			+ "|| RPAD(' ', 51, ' ') AS Linea\r\n"
			+ "FROM \r\n"
			+ "	(\r\n"
			+ "		(\r\n"
			+ "			(\r\n"
			+ "				(\r\n"
			+ "					SELECT\r\n"
			+ "						OPERACION.OPERACION_ID,	\r\n"
			+ "						OPERACION.DOCUMENTO,\r\n"
			+ "						OPERACION.CANAL_ID, \r\n"
			+ "						OPERACION.EMISOR, \r\n"
			+ "						OPERACION.CBU_EMISOR, \r\n"
			+ "						OPERACION.TARJETA, \r\n"
			+ "						OPERACION.FECHA_ALTA, \r\n"
			+ "						OPERACION.FECHA_ULT_ESTADO, \r\n"
			+ "						OPERACION.ESTADO_ID\r\n"
			+ "					FROM\r\n"
			+ "						USR_QR_INTEROPERABLE.OPERACION OPERACION\r\n"
			+ "					WHERE\r\n"
			+ "					(	\r\n"
			+ "							OPERACION.EMISOR = '0014'\r\n"
			+ "							AND OPERACION.CANAL_ID IN (1, 5, 10, 11) \r\n"
			+ "							AND TO_CHAR(OPERACION.FECHA_ALTA, 'DD-MM-YYYY') = TO_CHAR(SYSDATE -1, 'DD-MM-YYYY')\r\n"
			+ "--						AND TO_CHAR(OPERACION.FECHA_ALTA, 'DD-MM-YYYY') = '08-07-2022'\r\n"
			+ "					)\r\n"
			+ "					ORDER BY OPERACION.FECHA_ALTA DESC\r\n"
			+ "				) \r\n"
			+ "				OPERACION_1\r\n"
			+ "LEFT OUTER JOIN USR_QR_INTEROPERABLE.QR_FORMATEADO QR_FORMATEADO ON\r\n"
			+ "	OPERACION_1.OPERACION_ID = QR_FORMATEADO.OPERACION_ID\r\n"
			+ "			)\r\n"
			+ "LEFT OUTER JOIN USR_QR_INTEROPERABLE.ESTADO ESTADO ON\r\n"
			+ "	OPERACION_1.ESTADO_ID = ESTADO.ESTADO_ID\r\n"
			+ "		)\r\n"
			+ "LEFT OUTER JOIN USR_QR_INTEROPERABLE.CANAL CANAL ON\r\n"
			+ "	OPERACION_1.CANAL_ID = CANAL.CANAL_ID\r\n"
			+ "	)\r\n"
			+ "LEFT OUTER JOIN (\r\n"
			+ "	SELECT\r\n"
			+ "			OPERACION_DETALLE.OPERACION_ID,\r\n"
			+ "			OPERACION_DETALLE.PASO_ID,\r\n"
			+ "			OPERACION_DETALLE.REF_NUM,\r\n"
			+ "			OPERACION_DETALLE.ESTADO_ID\r\n"
			+ "	FROM\r\n"
			+ "			USR_QR_INTEROPERABLE.OPERACION_DETALLE OPERACION_DETALLE\r\n"
			+ "	WHERE\r\n"
			+ "		(\r\n"
			+ "			OPERACION_DETALLE.PASO_ID IN (3, 4, 5)\r\n"
			+ "			AND OPERACION_DETALLE.ESTADO_ID = 7\r\n"
			+ "		)\r\n"
			+ "	) \r\n"
			+ "	OPERACION_DETALLE_1 ON\r\n"
			+ "	OPERACION_1.OPERACION_ID = OPERACION_DETALLE_1.OPERACION_ID\r\n"
			+ "WHERE\r\n"
			+ "	(1 = 1)";
}
