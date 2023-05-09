package procesos;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import controller.Logger;
import dao.PropertiesReader;
import dao.Select;
import ftp.Conexion;

public class Ordenar {
	
	public void order(Connection conn, String corredoresargs) {
		
		/************************************************************************
		 * Se inicializa la coneccion con las carpetas							*
		 * Se guarda la lista de los archivos a mover 							*
		 * Procede a realizar los movientos a sus respectivas carpetas			*
		 ************************************************************************/
		Logger log = new Logger();
		Conexion con = new Conexion();
		File carpetaOK = con.connect("RutaOK206","");
		File carpetaDAT = con.connect("RutaDat206","");
		Select select = new Select();
		String[] archivosok = carpetaOK.list();
		String[] archivosdat = carpetaDAT.list();
		List<String> corredores = new ArrayList<String>();
		PropertiesReader read = new PropertiesReader();
		String rutaCorredor = read.reader("rutaCorredor");
		
		if(corredoresargs.length()>2) {
			String corredoresArray[] = corredoresargs.split(",");
			for(int i = 0;i<corredoresArray.length;i++) {
				corredores.add(corredoresArray[i]);
			}
		}else {
			corredores = select.SelectCorredores(conn);
		}

		
		for(int ok = 0;ok < archivosok.length;ok++) {
			Iterator<String> itrtcorredores = corredores.iterator();
			while(itrtcorredores.hasNext()) {
				String corredor = itrtcorredores.next();
				if(archivosok[ok].contains(corredor+"_01TISA")) {
					File rutaAmover = new File(rutaCorredor+corredor+"\\"+"OK"+"\\");
					File movefile = new File(read.reader("RutaOK206")+archivosok[ok]);
					if(!rutaAmover.exists()) {
						if (rutaAmover.mkdirs()) {
							log.info(" [OrdenarArchivos]: Se Creo directorio correctamente");
						}
					}
					log.info(" [OrdenarArchivos]: Moviendo Archivos OK");
					movefile.renameTo(new File(rutaCorredor+corredor+"\\"+"OK"+"\\"+archivosok[ok]));
				}
			}
			
		}
		
		for(int dat = 0;dat < archivosdat.length;dat++) {
			Iterator<String> itrtcorredores = corredores.iterator();
			while(itrtcorredores.hasNext()) {
				String corredor = itrtcorredores.next();
				if(archivosdat[dat].contains(corredor+".DAT")) {
					File movefile = new File(read.reader("RutaDat206")+archivosdat[dat]);
					File rutaAmover = new File(rutaCorredor+corredor+"\\"+"DAT"+"\\");
					if(!rutaAmover.exists()) {
						if (rutaAmover.mkdirs()) {
							log.info(" [OrdenarArchivos]: Se Creo directorio correctamente");
						}
					}
					log.info(" [OrdenarArchivos]: Moviendo Archivos DAT");
					movefile.renameTo(new File(rutaCorredor+corredor+"\\"+"DAT"+"\\"+archivosdat[dat]));
				}
			}
		}	
	}
}
