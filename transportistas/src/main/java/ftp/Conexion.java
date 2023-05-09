package ftp;

import java.io.File;

import controller.Logger;
import dao.PropertiesReader;

public class Conexion {
	
	public File connect(String ruta,String archivo) {
		
		/********************************************************************
		 * Inicializa Logmanager											*
		 * Inicializa el lector de propiedades								*
		 * Obtine la ruta de los archivos									*
		 * Obtiene nombres de archivos en carpeta o contenido de archivo 	*
		 * Retorna lo obtenido												*
		 * ******************************************************************/
		
		Logger log = new Logger();
		PropertiesReader read = new PropertiesReader();
		String rutaProperties = read.reader(ruta);
		File carpeta = new File(rutaProperties+archivo);
		if(carpeta == null) {
			log.error(" [FTPConnection]: No existe la ruta, archivo o falta conectar con el servidor");
		}
		return carpeta;
	}
	
	
}
