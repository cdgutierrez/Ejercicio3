package com.test;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

import org.junit.Assert;

import com.main.Asistente;


public class MsjSinSentidoTest {

	
	Asistente grace;
	
	@Before
	public void setup() {
		grace = new Asistente("@grace");
	}
	
	
	
	@Test
	public void sinsentido() throws ParseException {
		String[] mensajes = {"Este mensaje no tiene sentido @grace","No se que y como preguntarte @grace",
				"no se que idoma hablas @grace"};
		
		for (String mensaje : mensajes) {
			Assert.assertEquals(
					"Disculpa... no entiendo el pedido, ¿podrías repetirlo?",grace.escuchar(mensaje)
					);
		}
	}
	
}
