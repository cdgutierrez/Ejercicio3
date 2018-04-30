package com.main;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Asistente {
	
	
	private String nombre;
	
	
	
	public Asistente(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	
	
	
	/* escucha recibe un string, ese string pasado al metodo nroMensaje que devuelve el id de los mensajes
	 * por cada case ejecutara el metodo del mensaje/comando recibido
	 * 
	 * 
	 */

	public String escuchar(String strIn) throws ParseException {
		
		String stdStrIn = stdStr(strIn);
		int tipoMensaje = nroMensaje(stdStrIn);

		String strOut="";
		
		switch(tipoMensaje) {
		case 0: strOut=saludar();break;
		case 1: strOut=agradecer();break;
		case 2: strOut=darHora();break;
		case 3: strOut=darFecha();break;
		case 4: strOut=darDiaSemana();break;
		case 5: strOut=darDiaFueSera(stdStrIn);break;
		

		
		
		default: strOut=msjNoDetectado();
		}
		
		return strOut;
		
	}
	
	
	
	private static String darDiaSemana() {
		Calendar cad = new GregorianCalendar();
		Locale locale = Locale.getDefault();
		String dia = cad.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale);

		return ("Hoy es "+ dia);
		
	}
	
	private static String darFecha() {
		Calendar cad = new GregorianCalendar();
		Locale locale = Locale.getDefault();
		String dia = cad.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale);
		String mes = cad.getDisplayName(Calendar.MONTH, Calendar.LONG, locale);
		
		return ("Hoy es "+ dia +" "+ cad.get(Calendar.DATE) + " de " + mes +" de " + cad.get(Calendar.YEAR));
		
	}
	
	
	private static String darHora() {
		
		Calendar cal = new GregorianCalendar();
		int h,m;
		h=cal.get(Calendar.HOUR_OF_DAY);
		m=cal.get(Calendar.MINUTE);
		return ("Son las "+h+":"+m+"hs");
		
	}
	
	private static String agradecer() {
		return "no es nada!";
	}
	
	private static String saludar() {
		return "Hola!";		
	}
	
	
	private static String msjNoDetectado() {
		return "Disculpa... no entiendo el pedido, ¿podrías repetirlo?";
	}

	
	
	
	
	//   limpiar de tildes a la strIn
	private static String stdStr(String strIn) {
		
        String limpio = Normalizer.normalize(strIn.trim().toLowerCase(), Normalizer.Form.NFD);

        // eliminar char que no son ascii salvo ¿?!¡ñ
        limpio = limpio.replaceAll("[^\\p{ASCII}(N\u0303)(n\u0303)(\u00A1)(\u00BF)(\u00B0)(U\u0308)(u\u0308)]", "");
        limpio = Normalizer.normalize(limpio, Normalizer.Form.NFC);
        
        return limpio;
	}
	
	
	
	private static int nroMensaje(String strIn) {
			
			
			/*
			 * 0 - saludos
			 * 1 - agradecimientos
			 * 2 - hora
			 * 3 - fecha
			 * 4 - diaDeLaSemana
			 * 5 - queDia Fue/Sera ...
			 * 6 - tiempoDesde ...
			 * 
			 */
			
		
			
	        // Matriz de mensajes, la fila corresponde al tipo de mensaje que luego usamos para un case
	        // la referencia es tomada desde los puntos de los test
	        // en caso de no encontrar un mensaje valido, devolver -1 y pedir repetir la pregunta
			// estos strings de mensajes siempre tienen que estar en minusculas
	        
			String[][] mensajes = {
					{"buenas","hola","que tal","tardes","noches","buen dia","hey"},
					{"muchas gracias","gracias","muchisimas gracias"},
					{"que hora es","la hora","me decis la hora"},
					{"que dia es","la fecha","me decis la fecha"},
					{"que dia de la semana es hoy"},
					{"que dia sera","que dia fue"},
					{"cuantos dias pasaron desde", "cuantos meses pasaron desde", "cuantos años pasaron desde"}
	
			};
			
		
	
			boolean f=false; // flag para cortar el for en la primer ocurrencia, quizas haya otra mejora
			int nMsj=-1;
			
			for(int i=0;i<mensajes.length;i++ ){
				
				for(int j=0;j<mensajes[i].length;j++) {
					
					
					
					// expresion regular segun lo que esta en el test, quizas haya que mejorarlo
				
					Pattern regex = Pattern.compile("\\b" + Pattern.quote(mensajes[i][j]) + "\\b");
					Matcher match = regex.matcher(strIn.toLowerCase().trim());
	
					if(match.find()) {
						
	//				    System.out.println("Encontrado: '" + match.group() 
	//                    + "' dentro de '" + strIn.toLowerCase().trim() 
	//                    + "' en la posición " + match.start());
						
						nMsj = i;
					    f=true;
					    break;
					} 
				}
				
				if(f)
					break;
			}
			
			return nMsj;
		}
		
	
	

	private static String darDiaFueSera(String strIn) throws ParseException {

		
		int x = extraerNro(strIn);
		
		Calendar fechaPedida = new GregorianCalendar();
		Locale locale = Locale.getDefault();
		String dia ="",mes="",tiempo="";
		int desplazamiento=0;
		
		if(x>0) {

			
			Pattern regex = Pattern.compile(".*que dia sera dentro de \\d* (dias|dia|mes|meses|año|años)\\?$");
			Matcher match = regex.matcher(strIn);

			if(match.matches()) {
				desplazamiento = 1;
				tiempo = "será ";
			}
				

			regex = Pattern.compile(".*que dia fue hace \\d* (dias|dia|mes|meses|año|años)\\?$");
			match = regex.matcher(strIn);
			
			if(match.matches()) {
				desplazamiento = -1;
				tiempo = "fue ";
			}
			
			
			
			if(desplazamiento !=0) {
		
				regex = Pattern.compile(".*\\d* (dias|dia)\\?$");
				match = regex.matcher(strIn);
	
				if(match.matches()) {
					// calcula los dias
				
					fechaPedida.add(Calendar.DATE, x*desplazamiento);
					
					dia = fechaPedida.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale);
					mes = fechaPedida.getDisplayName(Calendar.MONTH, Calendar.LONG, locale);
					return (tiempo + dia +" "+ fechaPedida.get(Calendar.DATE) + " de " + mes +" de " + fechaPedida.get(Calendar.YEAR));
					
				}
	
	
				regex = Pattern.compile(".*\\d* (meses|mes)\\?$");
				match = regex.matcher(strIn);
				
				if(match.matches()) {
					// calcula los meses
					
					fechaPedida.add(Calendar.MONTH, x*desplazamiento);
					
					dia = fechaPedida.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale);
					mes = fechaPedida.getDisplayName(Calendar.MONTH, Calendar.LONG, locale);
					return (tiempo + dia +" "+ fechaPedida.get(Calendar.DATE) + " de " + mes +" de " + fechaPedida.get(Calendar.YEAR));
					
				}
	
				
				
				regex = Pattern.compile(".*\\d* (años|año)\\?$");
				match = regex.matcher(strIn);
				
				if(match.matches()) {
					// calcula los años
					fechaPedida.add(Calendar.YEAR, x*desplazamiento);
					
					dia = fechaPedida.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale);
					mes = fechaPedida.getDisplayName(Calendar.MONTH, Calendar.LONG, locale);
	
					return (tiempo + dia +" "+ fechaPedida.get(Calendar.DATE) + " de " + mes +" de " + fechaPedida.get(Calendar.YEAR));
				}
			
			}


		}
		
		Pattern regex = Pattern.compile(".*ayer\\?$");
		Matcher match = regex.matcher(strIn);
		
		if(match.matches()) {
			// calcula ayer
		
			fechaPedida.add(Calendar.DATE, -1);
			
			dia = fechaPedida.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale);
			mes = fechaPedida.getDisplayName(Calendar.MONTH, Calendar.LONG, locale);
			
			return ("Ayer fue "+ dia +" "+ fechaPedida.get(Calendar.DATE) + " de " + mes +" de " + fechaPedida.get(Calendar.YEAR));

			
		}
		return ("disculpa, no entiendo tu consulta");
	}
	
	
	
	private static int extraerNro(String cadena) {
		
		StringBuilder str = new StringBuilder();
		char c;
		for (int i = 0; i < cadena.length(); i++) {
			c = cadena.charAt(i);
			if(Character.isDigit(c))
				str.append(c);
		}
		if(str.length()>0)
			return Integer.parseInt(str.toString());
		else 
			return -1;
			

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/* 
	 * estos 3 metodos siguientes funcionan, los comento porque no tengo test 
	 * 
	 
	
	
	static public String tiempoDiasDesdeFecha(String strIn) throws ParseException {
		
		SimpleDateFormat formatIn = new SimpleDateFormat("'cuántos días pasaron desde el' dd 'de' MMMMM 'de' yyyy");
		SimpleDateFormat formatOut = new SimpleDateFormat("dd 'de' MMMMM 'de' yyyy");

		//String strIn = "cuántos días pasaron desde el 1 de abril de 2018";

		Date fechaStrIn = formatIn.parse(strIn);
		
		Calendar desde = new GregorianCalendar();
		Calendar hasta = new GregorianCalendar();
		
		desde.setTime(fechaStrIn);
		hasta.setTime(new Date());
		
		long dias = (hasta.getTimeInMillis() - desde.getTimeInMillis())/ (24 * 60 * 60 * 1000);
		
		return ("Entre el "+ formatOut.format(fechaStrIn) + " y hoy pasaron "  + dias + " dias");
		
	}
	
	static public String tiempoMesesDesdeFecha(String strIn) throws ParseException {
		
		SimpleDateFormat formatIn = new SimpleDateFormat("'cuántos meses pasaron desde el' dd 'de' MMMMM 'de' yyyy");
		SimpleDateFormat formatOut = new SimpleDateFormat("dd 'de' MMMMM 'de' yyyy");

		//String strIn = "cuántos días pasaron desde el 1 de abril de 2018";

		Date fechaStrIn = formatIn.parse(strIn);
		
		Calendar desde = new GregorianCalendar();
		Calendar hasta = new GregorianCalendar();
		
		desde.setTime(fechaStrIn);
		hasta.setTime(new Date());
		
		int anios = hasta.get(Calendar.YEAR) - desde.get(Calendar.YEAR);
		int meses = hasta.get(Calendar.MONTH) - desde.get(Calendar.MONTH);
		int totalMeses = anios*12 + meses;
		 
		return ("Entre el "+ formatOut.format(fechaStrIn) + " y hoy pasaron "  + totalMeses + " meses");
		
	}
	
	
	static public String tiempoAniosDesdeFecha(String strIn) throws ParseException {
		
		SimpleDateFormat formatIn = new SimpleDateFormat("'cuántos años pasaron desde el' dd 'de' MMMMM 'de' yyyy");
		SimpleDateFormat formatOut = new SimpleDateFormat("dd 'de' MMMMM 'de' yyyy");

		//String strIn = "cuántos días pasaron desde el 1 de abril de 2018";

		Date fechaStrIn = formatIn.parse(strIn);
		
		Calendar desde = new GregorianCalendar();
		Calendar hasta = new GregorianCalendar();
		
		desde.setTime(fechaStrIn);
		hasta.setTime(new Date());
		
		int anios = hasta.get(Calendar.YEAR) - desde.get(Calendar.YEAR);
		 
		return ("Entre el "+ formatOut.format(fechaStrIn) + " y hoy pasaron "  + anios + " años");
		
	}
	
	
	*/
	
	
	
}
