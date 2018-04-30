package com.test;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.main.Asistente;

public class MsjQueDiaFueSeraTest {

	Asistente grace;
	
	@Before
	public void setup() {
		grace = new Asistente("grace");
	}
	
	@Test
	public void diaDentroDe() throws ParseException {
		Assert.assertEquals(
				"será miércoles 2 de mayo de 2018",
				grace.escuchar("qué día será dentro de 2 días?")
			);
		
		Assert.assertEquals(
				"será sábado 30 de junio de 2018",
				grace.escuchar("qué día será dentro de 2 meses?")
			);
		
		Assert.assertEquals(
				"será jueves 30 de abril de 2020",
				grace.escuchar("qué día será dentro de 2 años?")
			);
	}
	
	@Test
	public void diaHace() throws ParseException {
		Assert.assertEquals(
				"Ayer fue domingo 29 de abril de 2018",
				grace.escuchar("qué día fue ayer?")
			);
		
		Assert.assertEquals(
				"fue viernes 27 de abril de 2018",
				grace.escuchar("@grace qué día fue hace 3 días?")
			);
		
		Assert.assertEquals(
				"fue viernes 20 de abril de 2018",
				grace.escuchar("@grace QUÉ DIA fue hace 10 días?")
			);
		
		Assert.assertEquals(
				"fue miércoles 28 de febrero de 2018",
				grace.escuchar("qué día fue hace 2 meses?")
			);
		
		Assert.assertEquals(
				"fue sábado 30 de abril de 2016",
				grace.escuchar("@grace qué día fue hace 2 años?")
			);
		
		Assert.assertEquals(
				"fue jueves 30 de abril de 2015",
				grace.escuchar("@grace QUE DIA FUE HACE 3 años?")
			);
}
}
