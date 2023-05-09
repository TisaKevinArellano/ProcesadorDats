package procesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import controller.Logger;
import dao.PropertiesReader;

public class Insertar {
	
	public void insert(List<String> datos,String corredor,Connection conn) {
		
		/********************************************************
		 * Inicializa el logManager								*
		 * Se establece coneccion con la base de datos			*
		 * Se hace la insercion de los datos por transaccion	*
		 ********************************************************/
		Logger log = new Logger();
		int RESULT;
		String insrt = null;
		PropertiesReader read = new PropertiesReader();
		
		Iterator<String> dtInsIte = datos.iterator();
		log.info(" [Insertar]: Insertando datos del corredor "+corredor);
		
		while(dtInsIte.hasNext()){
			try {
				insrt = "INSERT INTO ["+read.reader("DataBase")+"].[debito].[D_"+corredor+"] VALUES "+dtInsIte.next();
				PreparedStatement PRPSTM =conn.prepareStatement(insrt);
				RESULT = PRPSTM.executeUpdate();
				
			} catch (SQLException e) {
				log.error(" [Insertar]: No se pudo insertar "+e);
			}
		}
	}
}
