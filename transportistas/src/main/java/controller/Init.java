package controller;

import java.sql.Connection;
import java.sql.SQLException;

import db.ConexionDB;
import procesos.Copiar;
import procesos.LecturaArchivos;

public class Init {

	public static void main(String args[]) {
		/****************************************************************************
		 * Comienzo del sistema														*
		 * 																			*
		 * Inicializa Logmanager													*
		 * Instancia la clase copiar												*
		 * Verifica si se recibieron los argumentos									*
		 * Llama la clase Copy registrando retorno en bandera 						*
		 * Si regresa verdadero Instancia la clase para leer archivos				*
		 * 																			*
		 ****************************************************************************/
		
		Logger log = new Logger();
		Copiar cpy = new Copiar();
		boolean bandera;
		
		log.info(" [Main]: Comenzando proceso ...");
		
		switch(args[0]) {
		case "D":
			ConexionDB connect = new ConexionDB();
			Connection conn = connect.DBConnection();
			if(args.length == 2) {
				String fechasArray[] = args[1].split(",");
				for(int i = 0;i<fechasArray.length;i++){
					bandera = cpy.copy(args[0],fechasArray[i],"");
					if(bandera) {
						LecturaArchivos leer = new LecturaArchivos();
						log.info(" [Main]: Leyendo Archivos");
						leer.leer(fechasArray[i],"",conn);
					}
				}
			}else if(args.length == 3) {
				String fechasArray[] = args[1].split(",");
				for(int i = 0;i<fechasArray.length;i++) {
					bandera = cpy.copy(args[0],fechasArray[i],args[2]);
					if(bandera) {
						LecturaArchivos leer = new LecturaArchivos();
						log.info(" [Main]: Leyendo Archivos");
						leer.leer(fechasArray[i],args[2],conn);
					}
				}
			}
			log.info(" [Main]: Se completo el Proceso");
			try {
				conn.close();
			} catch (SQLException e) {
				log.error(" [Main]: No se pudo cerrar las sesion de la BD"+ e);
			}
		break;
		case "E":
			
		break;
		case "C":
			
		break;
		case "F":
			
		break;
		default:
			log.error(" [MAIN]: Los parametros enviados no son correctos \n "
					+"Favor de correr con tipo de archivo(D,E,C,F) - fecha(s) 20210101 20210101,20210102 - corredor(opcional) C103 C103,C102 \nejemplos: \n D 20210101 C102 \n D 20210101,20210102 C102,C103 ");
		break;
		}
	}
}
