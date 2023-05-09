package procesos;

import java.io.File;

public class Limpiar {
	
	public void limpieza(String carpetaok,String carpetadat) {
		File archivoOK = new File (carpetaok);
		File archivoDAT = new File (carpetadat);
		
		String[] archivosOK = archivoOK.list();
		String[] archivosDat = archivoDAT.list();
		
		for(int i =0;i < archivosOK.length;++i) {
			File archivoaBorrar =  new File (carpetaok+archivosOK[i]);
			archivoaBorrar.delete();
		}
		for(int i =0;i < archivosDat.length;++i) {
			File archivoaBorrar =  new File (carpetaok+archivosDat[i]);
			archivoaBorrar.delete();
		}
		
	}
	
}
