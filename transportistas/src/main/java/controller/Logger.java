package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import dao.PropertiesReader;

public class Logger{
	
	public void error(String message) {
		Logger.this.write(message,"log.error");
	}
	
	public void info(String message) {
		Logger.this.write(message,"log.info");
	}
	
	public void warn(String message) {
		Logger.this.write(message,"log.warning");
	}
	
	private void write(String message,String file) {
		PropertiesReader read = new PropertiesReader();
		DateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dateLog = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fecha = dformat.format(Calendar.getInstance().getTime());
		String dateLogS = dateLog.format(Calendar.getInstance().getTime());
		String rutaLog = read.reader("RutaLog");
		File logDir= new File(rutaLog);
		if(!logDir.exists()) {
			if (logDir.mkdirs()) {
				System.out.println("Se creo directorio sin problema");
			}
		}
		File logFile= new File(rutaLog+"/"+fecha+"_"+file);
		try {
		if(!logFile.exists()) {
				if (logFile.createNewFile()) {
					FileWriter fw = new FileWriter(logFile.getAbsoluteFile(),true);
		            BufferedWriter bw = new BufferedWriter(fw);
		            bw.write("LOG del dia "+fecha);
		            bw.close();
					System.out.println("Se creo archivo sin problema");
				}
		}
			FileWriter fw = new FileWriter(logFile.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("\n"+dateLogS+message);
            bw.close();
		} catch (IOException e) {
			System.out.println("No se pudo crear archivo "+ e);
		}
	}
	
}
