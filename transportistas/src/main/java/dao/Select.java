package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import controller.Logger;
import db.ConexionDB;

public class Select {
	
	public List<String> SelectCorredores(Connection conn) {
		
		/*************************************************
		 * Iniciliza el log								*
		 * Se establece coneccion de bases de datos		*
		 * Se corre Select para obtener corredores		*
		 ************************************************/
		Logger log = new Logger();
		ConexionDB connect = new ConexionDB();
		final String SELECT_CORREDORES = "SELECT corredor FROM transportistas2023.catalogo.corredor_eur";
		PreparedStatement PRPSTM;
		ResultSet RESULT;
		List<String> corredores = new ArrayList<String>();
		
		try {
			PRPSTM = conn.prepareStatement(SELECT_CORREDORES);
			RESULT = PRPSTM.executeQuery();
			while(RESULT.next()) {
				corredores.add(RESULT.getString(1));
			}
			return corredores;
		} catch (SQLException e) {
			log.error(" [Select]: No se pudo hacer Select");
			return corredores;
		}	
	}
}
