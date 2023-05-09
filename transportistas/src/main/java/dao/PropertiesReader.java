package dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import controller.Logger;


public class PropertiesReader {
	
	public String reader(String property) {
		String properties = "../Transportistas/Transportistas_lib/Properties/config.properties";
		Properties proper= new Properties();
		Logger log = new Logger();
		try {
			proper.load(new FileReader(properties));
			return proper.getProperty(property);
		} catch (FileNotFoundException e) {
			log.error(" [Properties Reader]: File "+properties+" no existe:" + e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error(" [Properties Reader]: Propiedad "+property+" no de encontro:" + e);
		}
		return null;
	}
}
