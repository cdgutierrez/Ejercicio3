package com.test;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.main.Asistente;

public class MsjDarHoraFechaDiaTest {

	Asistente grace;
	
	@Before
	public void setup() {
		grace = new Asistente("grace");
	}
	
	@Test
	public void hora() throws ParseException {
		String[] mensajes = {"�QU� HORA ES, @grace?", "@grace, la hora por favor","me dec�s la hora @grace?"
		};
		
		for (String mensaje : mensajes) {
			Assert.assertEquals(
					"Son las 13:56hs",
					grace.escuchar(mensaje)
			);
		}
	}
	
	@Test
	public void fecha() throws ParseException {
		String[] mensajes = {
				"�qu� d�a es, @grace?",
				"@grace, la fecha por favor",
				"me dec�s la fecha @grace?"
		};
		for (String mensaje : mensajes) {
			Assert.assertEquals(
					"Hoy es lunes 30 de abril de 2018",
					grace.escuchar(mensaje)
			);
		}
	}
	
	@Test
	public void diaDeLaSemana() throws ParseException {
		String[] mensajes = {"�qu� d�a de la semana es hoy, @grace?"
		};
		for (String mensaje : mensajes) {
			Assert.assertEquals(
					"Hoy es lunes",
					grace.escuchar(mensaje)
			);
		}
}
}
