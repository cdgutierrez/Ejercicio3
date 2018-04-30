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
				"ser� mi�rcoles 2 de mayo de 2018",
				grace.escuchar("qu� d�a ser� dentro de 2 d�as?")
			);
		
		Assert.assertEquals(
				"ser� s�bado 30 de junio de 2018",
				grace.escuchar("qu� d�a ser� dentro de 2 meses?")
			);
		
		Assert.assertEquals(
				"ser� jueves 30 de abril de 2020",
				grace.escuchar("qu� d�a ser� dentro de 2 a�os?")
			);
	}
	
	@Test
	public void diaHace() throws ParseException {
		Assert.assertEquals(
				"Ayer fue domingo 29 de abril de 2018",
				grace.escuchar("qu� d�a fue ayer?")
			);
		
		Assert.assertEquals(
				"fue viernes 27 de abril de 2018",
				grace.escuchar("@grace qu� d�a fue hace 3 d�as?")
			);
		
		Assert.assertEquals(
				"fue viernes 20 de abril de 2018",
				grace.escuchar("@grace QU� DIA fue hace 10 d�as?")
			);
		
		Assert.assertEquals(
				"fue mi�rcoles 28 de febrero de 2018",
				grace.escuchar("qu� d�a fue hace 2 meses?")
			);
		
		Assert.assertEquals(
				"fue s�bado 30 de abril de 2016",
				grace.escuchar("@grace qu� d�a fue hace 2 a�os?")
			);
		
		Assert.assertEquals(
				"fue jueves 30 de abril de 2015",
				grace.escuchar("@grace QUE DIA FUE HACE 3 a�os?")
			);
}
}
