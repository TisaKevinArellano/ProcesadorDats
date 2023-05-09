package procesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import controller.Logger;
import dao.PropertiesReader;

public class Borrar {
	
	public void borrar(Connection conn, String fecha,List<String> corredores) {
		
		/************************************************************
		 * Inicializa el logManager									*
		 * Se establece coneccion con la base de datos				*
		 * Se hace el borrado de los datos por tabla de corredor	*
		 ************************************************************/
		Logger log = new Logger();
		int RESULT;
		String borrar = null;
		PropertiesReader read = new PropertiesReader();
		
		Iterator<String> corredoresIte = corredores.iterator();
		log.info(" [Borrado]: Borrando datos del dia: "+fecha.substring(fecha.length()-8,fecha.length()-4)
		+"-"+fecha.substring(fecha.length()-4,fecha.length()-2)
		+"-"+fecha.substring(fecha.length()-2,fecha.length()));
		while(corredoresIte.hasNext()){
			try {
				borrar = "DELETE FROM ["+read.reader("DataBase")+"].[debito].[D_"+corredoresIte.next()+"] WHERE [fecha_c1_ok_fimpe] = '"+
						fecha.substring(fecha.length()-8,fecha.length()-4)
						+"-"+fecha.substring(fecha.length()-4,fecha.length()-2)
						+"-"+fecha.substring(fecha.length()-2,fecha.length())
						+"'";
				PreparedStatement PRPSTM =conn.prepareStatement(borrar);
				RESULT = PRPSTM.executeUpdate();
				
			} catch (SQLException e) {
				log.error(" [Borrado]: No se pudo borrar "+e);
			}
		}
	}
	
}
