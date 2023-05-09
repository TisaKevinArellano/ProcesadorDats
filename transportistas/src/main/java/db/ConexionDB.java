package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import controller.Logger;
import dao.PropertiesReader;


public class ConexionDB {
	public Connection DBConnection () {
		/************************************************************
		 * Inicializa Logmanager									*
		 * Inicializa PropertiesReader								*
		 * Obtiene las propiedades de conexion a la base de datos	*
		 * Realiza la coneccion con la base							*
		 ************************************************************/
		Logger log = new Logger();
		PropertiesReader prop = new PropertiesReader();
		Connection conn;
		String db;
		String user;
		String pswd;
		String port;
		String srvName;
		try {
			 db= prop.reader("DataBase");
			 user= prop.reader("User");
			 pswd= prop.reader("Password");
			 port= prop.reader("Port");
			 srvName= prop.reader("ServerName");
			 log.info("[Conexion a BD]: Conectando con la base de datos ...");
			conn = DriverManager.getConnection(
					"jdbc:sqlserver://"+srvName+":"+port+";database="+db+";user="+user+";password="+pswd+";" + "encrypt=true;"
	                        + "trustServerCertificate=true;"
	                        + "loginTimeout=30;");
			log.info(conn.toString());
			return conn;
			
		} catch (SQLException e) {
			log.error(" [Conexion a BD]: Error al intentar conectar a la DB "+e);
			return null;
		}
		
	}
	

}
