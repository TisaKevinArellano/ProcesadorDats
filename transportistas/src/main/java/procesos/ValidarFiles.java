package procesos;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import controller.Logger;
import ftp.Conexion;

public class ValidarFiles {
	public List<String> ValidFilesOK(String fecha,String tipo,String Corredores) {
		
		/************************************************************************
		 * Inicializa Logmanager.												*
		 * Inicializa conexion con servidor de archivos.						*
		 * Inicializa el formato de la fecha.									*
		 * Revisa el contenido de la carpeta retornada.							*
		 * Verfica que los archivos se crearan en la fecha recibida.			*
		 * Almacena los nombres que contengan las validaciones especificadas.	*
		 * Retorna la lista del nombre de los archivos.							*
		 ************************************************************************/
		
		Logger log = new Logger();
		Conexion con = new Conexion();
		DateFormat dformat = new SimpleDateFormat("yyyyMMdd");
		
		File carpetaOK = con.connect("RutaOK","");
		String[] archivosok = carpetaOK.list();
		List<String> listaOK = null;
		if(archivosok == null || archivosok.length ==0) {
			log.warn(" [ValidFilesOK]: No hay archivos o no pudo hacer la conexion");
		}else {
			listaOK = new ArrayList<String>();
			for(int ok = 0 ;ok<archivosok.length;ok++) {
				if(archivosok[ok].substring(0,1).equals(tipo)) {
					File okfile =con.connect("RutaOK",archivosok[ok]);
					Date d = new Date(okfile.lastModified());
					if(dformat.format(d).toString().equals(fecha)) {
						listaOK.add(archivosok[ok]);
					}
				}
			}
		}
		
		return listaOK;
	}
	
	public List<String> ValidFilesDat(String fecha,String tipo,List<String> listaOK) {
		
		/************************************************************************
		 * Inicializa Logmanager.												*
		 * Inicializa conexion con servidor de archivos.						*
		 * Inicializa el formato de la fecha.									*
		 * Revisa el contenido de la carpeta retornada.							*
		 * Verfica que los archivos coincidan con los OK.						*
		 * Almacena los nombres que contengan las validaciones especificadas.	*
		 * Retorna la lista del nombre de los archivos.							*
		 ************************************************************************/
		
		Logger log = new Logger();
		Conexion con = new Conexion();
		File carpetaDAT = con.connect("RutaDat","");
		String[] archivosDat = carpetaDAT.list();
		List<String> listaDat = new ArrayList<String>();
		String nombre_Archivo;
		
		if(archivosDat == null || archivosDat.length ==0) {
			log.warn(" [ValidFilesDat]: No hay archivos o no pudo hacer la conexion");
		}else {
			for(int dat = 0 ;dat<archivosDat.length;dat++) {
				Iterator<String> itertrListaOK = listaOK.iterator();
				while (itertrListaOK.hasNext()) {
					nombre_Archivo= itertrListaOK.next();
					if(archivosDat[dat].contains(nombre_Archivo.substring(0, nombre_Archivo.length()-14))&& archivosDat[dat].substring(0,1).equals(tipo)) {
						listaDat.add(archivosDat[dat]);
					}
				}
			}
		}
		return listaDat;
	}
}
