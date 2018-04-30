package com.test;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.main.Asistente;

public class MsjSaludoTest {
	Asistente grace;
	
	@Before
	public void setup() {
		grace = new Asistente("@grace");
	}
	
	
	
	@Test
	public void sinsentido() throws ParseException {
		String[] mensajes = {"buenas","HOLA","que tal","tardes","BUEN DÍA","noches","buen dia","hey"};
		
		for (String mensaje : mensajes) {
			
			Assert.assertEquals("Hola!",grace.escuchar(mensaje));
		}
	}
	
}
