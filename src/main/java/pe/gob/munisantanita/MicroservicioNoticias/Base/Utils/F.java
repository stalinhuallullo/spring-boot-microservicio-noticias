package pe.gob.munisantanita.MicroservicioNoticias.Base.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class F {

	public static double getRandom(double min, double max){
	    double x = (Math.random()*((max-min)+1))+min;
	    return x;
	}
	
	public static String getTimeStamp() {
	    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()).toString();
	}
	
	
}
