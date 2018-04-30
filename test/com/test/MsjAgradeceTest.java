package com.test;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.main.Asistente;

public class MsjAgradeceTest {
	Asistente grace;
	
	@Before
	public void setup() {
		grace = new Asistente("@grace");
	}
	
	
	
	@Test
	public void sinsentido() throws ParseException {
		String[] mensajes = {"¡Muchas gracias, @grace!","@grace gracias","gracias @grace"};
		
		for (String mensaje : mensajes) {
			
			Assert.assertEquals("no es nada!",grace.escuchar(mensaje));
		}
	}
	
}
