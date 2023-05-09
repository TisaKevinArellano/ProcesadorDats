package procesos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import controller.Logger;
import dao.PropertiesReader;
import db.ConexionDB;

public class Copiar {
	
	public boolean copy(String tipo, String fecha, String corredor) {
		
		/****************************************************
		 * Inicializa Logmanager							*
		 * Se crean variables necesarias					*
		 * Se Inicializa el validador de los archivos		*
		 * Recibe nombre de archivos 						*
		 * Comienza el copiado de los archivos 				*
		 ****************************************************/
		
		Logger log = new Logger();
		List<String> listaOK = new ArrayList<String>();
		List<String> listaDat = new ArrayList<String>();
		ValidarFiles validFiles = new ValidarFiles();
		PropertiesReader read = new PropertiesReader();
		String archivo;
		String rutaOk = read.reader("RutaOK");
		String rutaDat = read.reader("RutaDat");
		String rutaOk206 = read.reader("RutaOK206");
		String rutaDat206 = read.reader("RutaDat206");
		File archivoOK;
		File archivoDat;
		File archivoOK206;
		File archivoDat206;
		
		listaOK = validFiles.ValidFilesOK(fecha, tipo, corredor);
		listaDat = validFiles.ValidFilesDat(fecha, tipo, listaOK);
		
		Iterator<String> itrtListaOK = listaOK.iterator();
		Iterator<String> itrtListaDat = listaDat.iterator();
		log.info(" [Copia]: Limpiando directorios");
		Limpiar limpiar = new Limpiar();
		limpiar.limpieza(rutaOk206,rutaDat206);
		
		log.info(" [Copia]: Copiando archivos...");
		while(itrtListaOK.hasNext()) {
			try {
				archivo = itrtListaOK.next();
				archivoOK = new File (rutaOk+archivo);
				archivoOK206 = new File (rutaOk206+archivo);
				log.info(" [Copia]Tomando archivo: "+rutaOk206+archivo);
				Files.copy(archivoOK.toPath(),archivoOK206.toPath(),StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				log.error(" [Copia]: Error al intentar copiar los archivos "+e);
			}
		}
		archivo = null;
		while(itrtListaDat.hasNext()) {
			try {
				archivo = itrtListaDat.next();
				archivoDat = new File (rutaDat+archivo);
				archivoDat206 = new File (rutaDat206+archivo);
				log.info(" [Copia]: Tomando archivo: "+rutaDat206+archivo);
				Files.copy(archivoDat.toPath(),archivoDat206.toPath(),StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				log.error(" [Copia]: Error al intentar copiar los archivos "+e);
				return false;
			}
		}
		return true;
	}
}
