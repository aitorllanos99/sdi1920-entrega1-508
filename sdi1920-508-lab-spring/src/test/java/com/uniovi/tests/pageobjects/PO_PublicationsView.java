package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.util.SeleniumUtils;

public class PO_PublicationsView {
	
	static public void fillAddPublication(WebDriver driver,String titlep, String contentp) {
		// Esperamos 5 segundo a que carge el DOM porque en algunos equipos falla
		SeleniumUtils.esperarSegundos(driver, 5);
		// Rellenemos el campo de publicacion
		WebElement title = driver.findElement(By.name("title"));
		title.clear();
		title.sendKeys(titlep);
		WebElement content = driver.findElement(By.name("content"));
		content.clear();
		content.sendKeys(contentp);
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}
}
