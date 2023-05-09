package procesos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import controller.Logger;
import dao.PropertiesReader;
import dao.Select;
import ftp.Conexion;

public class LecturaArchivos {
	
	public void leer(String fecha,String corredoresargs,Connection conn) {
		
		/************************************************************************
		 * Inicializa Logmanager												*
		 * Conecta ruta de archivos												*
		 * Verifica si los corredores vienen de argumentos o se toman de la DB	*
		 * Revisa si el corrredor cuenta con archivo							*
		 * Comienza la extraccion de los datos									*
		 * una vez terminado se manda a insertar 								*
		 * Repite proceso hasta terminar con los corredores						*
		 * Al terminar llama a acomodar los archivos en sus respectivas carpetas*
		 ************************************************************************/
		Logger log = new Logger();
		Conexion con = new Conexion();
		File carpetaDAT = con.connect("RutaDat206","");
		Select select = new Select();
		PropertiesReader read = new PropertiesReader();
		String[] archivosdat = carpetaDAT.list();
		Insertar inserta = new Insertar();
		List<String> corredores = new ArrayList<String>();
		
		if(corredoresargs.length()>2) {
			String corredoresArray[] = corredoresargs.split(",");
			for(int i = 0;i<corredoresArray.length;i++) {
				corredores.add(corredoresArray[i]);
			}
		}else {
			corredores = select.SelectCorredores(conn);
		}
		Borrar borrado = new Borrar();
		borrado.borrar(conn,fecha, corredores);
		for(int dat = 0;dat < archivosdat.length;dat++) {
			Iterator<String> itrtcorredores = corredores.iterator();
			while(itrtcorredores.hasNext()) {
				String corredor = itrtcorredores.next();
				if(archivosdat[dat].contains(corredor+".DAT")) {
					
					try {
						FileReader fr = new FileReader(read.reader("RutaDat206")+archivosdat[dat]);
						BufferedReader bf = new BufferedReader(fr);
						String linea = bf.readLine();
						String[] C = linea.split("<");
						List<String> C1 = new ArrayList<String>();
						List<String> C2 = new ArrayList<String>();
						List<String> C3 = new ArrayList<String>();
						List<String> C4 = new ArrayList<String>();
						List<Double> C5 = new ArrayList<Double>();
						List<String> C8 = new ArrayList<String>();
						List<String> C9 = new ArrayList<String>();
						List<String> C14 = new ArrayList<String>();
						List<String> C17 = new ArrayList<String>();
						for(int i = 0;i<C.length;i++) {
							if(C[i].contains("/C1>") || C[i].contains("/C2>") || C[i].contains("/C3>")  || C[i].contains("/C4>")  || C[i].contains("/C5>") || C[i].contains("/C8>") || C[i].contains("/C9>") || C[i].contains("/C14>")   || C[i].contains("/C17>") ) {
							}
							else if(C[i].contains("C1>") ) {
								C1.add(C[i].substring(3,C[i].length()));
							}
							else if(C[i].contains("C2>") ) {
								C2.add(C[i].substring(C[i].length()-14,C[i].length()-10)+"-"+C[i].substring(C[i].length()-10,C[i].length()-8)+"-"+C[i].substring(C[i].length()-8,C[i].length()-6));
							}
							else if(C[i].contains("C3>") ) {
								C3.add(C[i].substring(3,C[i].length()));
							}
							else if(C[i].contains("C4>") ) {
								C4.add(C[i].substring(3,C[i].length()));
							}
							else if(C[i].contains("C5>")) {
								C5.add(Double.parseDouble(C[i].substring(3,7)));
							}
							else if(C[i].contains("C8>") ) {
								C8.add(C[i].substring(3,C[i].length()));
							}
							else if(C[i].contains("C9>") ) {
								C9.add(C[i].substring(3,C[i].length()));
							}
							else if(C[i].contains("C14>") ) {
								C14.add(C[i].substring(4,C[i].length()));
							}
							else if(C[i].contains("C17>")) {
								C17.add(C[i].substring(4,C[i].length()));
							}
						}//for C
						Iterator<String> C1i =C1.iterator();
						Iterator<String> C2i =C2.iterator();
						Iterator<String> C3i =C3.iterator();
						Iterator<String> C4i =C4.iterator();
						Iterator<Double> C5i = C5.iterator();
						Iterator<String> C8i =C8.iterator();
						Iterator<String> C9i =C9.iterator();
						Iterator<String> C14i =C14.iterator();
						Iterator<String> C17i =C17.iterator();
						List<String> toInsert = new ArrayList<String>();
						//System.out.println(archivosdat[dat].substring(archivosdat[dat].lastIndexOf("_202")+1,archivosdat[dat].lastIndexOf("_202")+9));
						while(C5i.hasNext() && C17i.hasNext() && C4i.hasNext()) {
							toInsert.add("('"+corredor
								+"','"+C1i.next()
								+"','"+C2i.next()
								+"','"+C3i.next()
								+"','"+C4i.next()
								+"','"+C5i.next()
								+"','"+C8i.next()
								+"','"+C9i.next()
								+"','"+C14i.next()
								+"','"+C17i.next()
								+"','"+fecha.substring(fecha.length()-8,fecha.length()-4)
								+"-"+fecha.substring(fecha.length()-4,fecha.length()-2)
								+"-"+fecha.substring(fecha.length()-2,fecha.length())
								+"','"+archivosdat[dat].substring(archivosdat[dat].lastIndexOf("_202")+1,archivosdat[dat].lastIndexOf("_202")+5)
								+"-"+archivosdat[dat].substring(archivosdat[dat].lastIndexOf("_202")+5,archivosdat[dat].lastIndexOf("_202")+7)
								+"-"+archivosdat[dat].substring(archivosdat[dat].lastIndexOf("_202")+7,archivosdat[dat].lastIndexOf("_202")+9)
								+"','"+archivosdat[dat]
								+"')");
						}
						inserta.insert(toInsert, corredor,conn);
					} catch (FileNotFoundException e) {
						log.error(" [LeyendoArchivos]: No existe el archivo "+e);
					} catch (IOException e) {
						log.error(" [LeyendoArchivos]: No se pudo leer el archivo "+e);
					}
				}
			}
			}
		Ordenar ord = new Ordenar();
		ord.order(conn,corredoresargs);
		}
	}
