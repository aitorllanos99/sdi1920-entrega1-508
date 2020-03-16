package com.uniovi.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_PublicationsView;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.util.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MySocialNetworkTests {
	// En Windows (Debe ser la versión 65.0.1 y desactivar las actualizacioens
	// automáticas)):
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "D:\\1Escritorio\\Informatica\\Uni\\Tercer curso\\Segundo Cuatrimestre\\Sistemas Distribuidos e Internet\\PL-SDI-Sesión5-material\\PL-SDI-Sesión5-material\\geckodriver024win64.exe";

	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8090";

	// Antes de cada prueba se navega al URL home de la aplicaciónn
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Prueba del formulario de registro. registro con datos correctos
	@Test
	public void PR1() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "prueba@email.com", "Prueba", "1", "77777", "77777");
	}

	// Prueba del formulario de registro. Todo vacio menos password
	// Error.empty.email
	@Test
	public void PR2() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "", "aa", "aa", "77777", "77777");
		PO_View.getP();
		// COmprobamos el error de email vacio.
		PO_RegisterView.checkKey(driver, "Error.empty.email", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "prueba2@email.com", "", "Perez", "77777", "77777");
		// COmprobamos el error de Nombre vacio .
		PO_RegisterView.checkKey(driver, "Error.empty.name", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "prueba3@email.com", "Josefo", "", "77777", "77777");
		// COmprobamos el error de Apellido vacio .
		PO_RegisterView.checkKey(driver, "Error.empty.lastName", PO_Properties.getSPANISH());

	}

	// Prueba del formulario de registro. Contraseñas no iguales
	// Error.signup.passwordConfirm.coincidence
	@Test
	public void PR3() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "pruebae1p3@email.com", "aa", "aa", "12", "1");
		PO_View.getP();
		// COmprobamos el error de la contraseña no coincide.
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
	}

	// Prueba del formulario de registro. Email existente
	// Error.signup.email.duplicate
	@Test
	public void PR4() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "macmiller@gmail.com", "aa", "aa", "12", "12");
		PO_View.getP();
		// COmprobamos el error de email duplicado.
		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());
	}

	// Loguearse con exito desde el ROl de Administrador, admin@email.com, 123456
	@Test
	public void PR5() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@email.com", "123456");
		// Comprobamos que podemos ir al menu de usuarios
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de usuarios.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
		// Ahora nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	// Loguearse con exito desde el ROl de Usuario, macmiller@gmail.com, 123456
	@Test
	public void PR6() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "macmiller@gmail.com", "123456");
		// Comprobamos que podemos ir al menu de usuarios
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de usuarios.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
		// Ahora nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	// Loguearse con datos vacios
	@Test
	public void PR7() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "", "");
		// Comprobamos que no podemos ir al menu de usuarios
		try {
			List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
			elementos.get(0).click();
			// Pinchamos en la opción de lista de usuarios.
			elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
			elementos.get(0).click();
		} catch (Exception e) {
		}
	}

	// Loguearse como usuario pero contraseña incorrecta
	@Test
	public void PR8() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "macmiller@gmail.com", "12345");
		// Comprobamos que no podemos ir al menu de usuarios
		try {
			List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
			elementos.get(0).click();
			// Pinchamos en la opción de lista de usuarios.
			elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
			elementos.get(0).click();
		} catch (Exception e) {
		}
	}

	// Identificación válida y desconexión con usuario de ROL usuario
	@Test
	public void PR9() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "macmiller@gmail.com", "123456");
		// Vamos a desconectarnos.
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	// Intentamos desconectarnos sin estar autenticados
	@Test
	public void PR10() {
		// Vamos a desconectarnos pero no encuentra el boton al estar oculto.
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "logout", 2);
	}

	// Loguearse, comprobar que se visualizan 3 filas de usuarios y desconectarse
	// usando el rol de usuario estandar.
	@Test
	public void PR11() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "macmiller@gmail.com", "123456");
		// Pinchamos en la opción de menu de Usuarios:
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de usuarios.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
		// Esperamos a que se muestren los enlaces de paginacion la lista de notas
		PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		// Contamos el número de filas de usuarios
		assertTrue(elementos.size() == 3);
		// Ahora nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	// Loguearse, Hacer una búsqueda con el campo vacío y comprobar que se muestra
	// la página que
	// corresponde con el listado usuarios existentes en el sistema
	// usando el rol de usuario estandar.
	@Test
	public void PR12() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "macmiller@gmail.com", "123456");
		// Pinchamos en la opción de menu de Usuarios:
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de usuarios.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
		// Esperamos a que se muestren los enlaces de paginacion la lista de notas
		PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		// Contamos el número de filas de usuarios que es el mismo porque no hay texto
		PO_PrivateView.searchByText(driver, "");
		assertTrue(elementos.size() == 3);
		// Ahora nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	// Hacer una búsqueda escribiendo en el campo un texto que no exista y comprobar
	// que se
	// muestra la página que corresponde, con la lista de usuarios vacía.
	// usando el rol de usuario estandar.
	@Test
	public void PR13() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "macmiller@gmail.com", "123456");
		// Pinchamos en la opción de menu de Usuarios:
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de usuarios.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
		// Esperamos a que se muestren los enlaces de paginacion la lista de notas
		PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		// Contamos el número de filas de usuarios que no se puede porque tarda
		// demasiado en cargar
		PO_PrivateView.searchByText(driver, "algoquenocorrresponde");
		try {
			PO_View.checkElement(driver, "free", "//td[contains(text(), 'algoquenocorresponde')]");
		} catch (TimeoutException te) {
		}
		// Ahora nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	// Hacer una búsqueda escribiendo en el campo un texto que no exista y comprobar
	// que se
	// muestra la página que corresponde, con la lista de usuarios vacía.
	// usando el rol de usuario estandar.
	@Test
	public void PR14() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "macmiller@gmail.com", "123456");
		// Pinchamos en la opción de menu de Usuarios:
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de usuarios.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
		// Esperamos a que se muestren los enlaces de paginacion la lista de notas
		PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		// Contamos el número de filas de usuarios y solo es la que buscamos
		PO_PrivateView.searchByText(driver, "travis");
		elementos = PO_View.checkElement(driver, "free", "//td[contains(text(), 'travis')]");
		assertTrue(elementos.size() == 1);
		// Ahora nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	// Desde el listado de usuarios de la aplicación, enviar una invitación de
	// amistad a un usuario.
	// Comprobar que la solicitud de amistad aparece en el listado de invitaciones
	// usando el rol de usuario estandar.
	@Test
	public void PR15() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "macmiller@gmail.com", "123456");
		// Pinchamos en la opción de menu de Usuarios:
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de usuarios.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
		// Esperamos a que se muestren los enlaces de paginacion la lista de notas
		PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		// Buscamos un usuario cualquiera y le enviamos una solicitud
		By enlace = By.xpath("//td[contains(text(), 'Ayax')]/following-sibling::*[1]");
		driver.findElement(enlace).click();
		SeleniumUtils.esperarSegundos(driver, 1);
		// Ahora nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		// Vamos al formulario de logueo con el usuario al que se lo enviamos.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "ayax@gmail.com", "123456");
		// Pinchamos en la opción de menu de Usuarios:
		elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de peticiones.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, '/user/listPetitions')]");
		elementos.get(0).click();
		// Compromamos que hay una peticion
		assertTrue(elementos.size() == 1);
		// Ahora nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");

	}

	// Desde el listado de usuarios de la aplicación, enviar una invitación de
	// amistad a un usuario al
	// que ya le habíamos enviado la invitación previamente. No debería dejarnos
	// enviar la invitación, se podría
	// ocultar el botón de enviar invitación o notificar que ya había sido enviada
	// previamente.
	@Test
	public void PR16() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "macmiller@gmail.com", "123456");
		// Pinchamos en la opción de menu de Usuarios:
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de usuarios.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
		// Esperamos a que se muestren los enlaces de paginacion la lista de notas
		PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		// Buscamos un usuario cualquiera y le enviamos una solicitud
		By enlace = By.xpath("//td[contains(text(), 'Ayax')]/following-sibling::*[1]");
		driver.findElement(enlace).click();
		SeleniumUtils.esperarSegundos(driver, 1);

		// Buscamos un usuario cualquiera y le enviamos una solicitud de nuevo y no se
		// puede porque el boton se oculta
		try {
			enlace = By.xpath("//td[contains(text(), 'Ayax')]/following-sibling::*[1]");
			driver.findElement(enlace).click();
			SeleniumUtils.esperarSegundos(driver, 1);
		} catch (TimeoutException te) {
		}
		// Ahora nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	// Desde el listado de usuarios de la aplicación, enviar una invitación de
	// amistad a un usuario al
	// que ya le habíamos enviado la invitación previamente. No debería dejarnos
	// enviar la invitación, se podría
	// ocultar el botón de enviar invitación o notificar que ya había sido enviada
	// previamente.
	@Test
	public void PR17() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "macmiller@gmail.com", "123456");
		// Pinchamos en la opción de menu de Usuarios:
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de usuarios.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/listPetitions')]");
		elementos.get(0).click();
		// Esperamos a que se muestren los enlaces de paginacion la lista de notas
		PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		// Compromamos que hay una peticion, la de por defecto, Blake-> MacMiller
		elementos = PO_View.checkElement(driver, "free", "//td[contains(text(), 'blake')]");
		assertTrue(elementos.size() == 1);
		// Ahora nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	// Sobre el listado de invitaciones recibidas. Hacer click en el botón/enlace de
	// una de ellas y
	// comprobar que dicha solicitud desaparece del listado de invitaciones
	@Test
	public void PR18() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "macmiller@gmail.com", "123456");
		// Pinchamos en la opción de menu de Usuarios:
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de usuarios.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
		// Esperamos a que se muestren los enlaces de paginacion la lista de notas
		PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		// Compromamos que hay una peticion, la de por defecto, Blake-> MacMiller y la
		// aceptamos
		By enlace = By.xpath("//td[contains(text(), 'Blake')]/following-sibling::*[2]");
		driver.findElement(enlace).click();
		SeleniumUtils.esperarSegundos(driver, 1);
		// Comprobamos que cuando la acepta ya no existe
		try {
			elementos = PO_View.checkElement(driver, "free", "//td[contains(text(), 'blake')]");
		} catch (Exception e) {
		}
		// Ahora nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	// Mostrar el listado de amigos de un usuario. Comprobar que el listado contiene
	// los amigos que deben ser.
	@Test
	public void PR19() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "macmiller@gmail.com", "123456");
		// Pinchamos en la opción de menu de Usuarios:
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de usuarios.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/listFriends')]");
		elementos.get(0).click();
		// Esperamos a que se muestren los enlaces de paginacion la lista de notas
		PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		// Comprobamos que existe el amigo por defecto del usuario macmiller -> travis
		// scott
		elementos = PO_View.checkElement(driver, "free", "//td[contains(text(), 'travis')]");
		assertTrue(elementos.size() == 1);
		// Ahora nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	// Visualizar al menos cuatro páginas en Español/Inglés/Español (comprobando que
	// algunas de las etiquetas cambian al idioma correspondiente)..
	@Test
	public void PR20() {

		// Primera pagina: Pagina Principal

		// --------Español------
		PO_RegisterView.checkKey(driver, "welcome.message", PO_Properties.getSPANISH());
		// Pinchamos en la opción de menu de Idiomas:
		WebElement btnLanguages = driver.findElement(By.id("btnLanguage"));
		btnLanguages.click();
		// Cambiamos a ingles
		WebElement btnenglish = driver.findElement(By.id("btnEnglish"));
		btnenglish.click();
		// --------Ingles-------
		PO_RegisterView.checkKey(driver, "welcome.message", PO_Properties.getENGLISH());
		// Pinchamos en la opción de menu de Idiomas:
		WebElement btnLanguages2 = driver.findElement(By.id("btnLanguage"));
		btnLanguages2.click();
		// Cambiamos a español
		WebElement btnspanish = driver.findElement(By.id("btnSpanish"));
		btnspanish.click();

		// --------Español------
		PO_RegisterView.checkKey(driver, "welcome.message", PO_Properties.getSPANISH());

		// Segunda Pagina: Registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");

		// --------Español------
		PO_RegisterView.checkKey(driver, "signup.email", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "signup.title", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "signup.name", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "signup.lastName", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "signup.send", PO_Properties.getSPANISH());
		// Pinchamos en la opción de menu de Idiomas:
		WebElement btnLanguages3 = driver.findElement(By.id("btnLanguage"));
		btnLanguages3.click();
		// Cambiamos a ingles
		WebElement btnenglish2 = driver.findElement(By.id("btnEnglish"));
		btnenglish2.click();
		// --------Ingles-------
		PO_RegisterView.checkKey(driver, "signup.email", PO_Properties.getENGLISH());
		PO_RegisterView.checkKey(driver, "signup.title", PO_Properties.getENGLISH());
		PO_RegisterView.checkKey(driver, "signup.name", PO_Properties.getENGLISH());
		PO_RegisterView.checkKey(driver, "signup.lastName", PO_Properties.getENGLISH());
		PO_RegisterView.checkKey(driver, "signup.send", PO_Properties.getENGLISH());
		// Pinchamos en la opción de menu de Idiomas:
		WebElement btnLanguages4 = driver.findElement(By.id("btnLanguage"));
		btnLanguages4.click();
		// Cambiamos a español
		WebElement btnspanish2 = driver.findElement(By.id("btnSpanish"));
		btnspanish2.click();
		// --------Español------
		PO_RegisterView.checkKey(driver, "signup.email", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "signup.title", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "signup.name", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "signup.lastName", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "signup.send", PO_Properties.getSPANISH());

		// Tercera Pagina: Logearse
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");

		// --------Español------
		PO_RegisterView.checkKey(driver, "login.title", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "login.email", PO_Properties.getSPANISH());
		// Pinchamos en la opción de menu de Idiomas:
		WebElement btnLanguages5 = driver.findElement(By.id("btnLanguage"));
		btnLanguages5.click();
		// Cambiamos a ingles
		WebElement btnenglish3 = driver.findElement(By.id("btnEnglish"));
		btnenglish3.click();
		// --------Ingles-------
		PO_RegisterView.checkKey(driver, "login.title", PO_Properties.getENGLISH());
		PO_RegisterView.checkKey(driver, "login.email", PO_Properties.getENGLISH());
		// Pinchamos en la opción de menu de Idiomas:
		WebElement btnLanguages6 = driver.findElement(By.id("btnLanguage"));
		btnLanguages6.click();
		// Cambiamos a español
		WebElement btnspanish3 = driver.findElement(By.id("btnSpanish"));
		btnspanish3.click();
		// --------Español------
		PO_RegisterView.checkKey(driver, "login.title", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "login.email", PO_Properties.getSPANISH());

		// Cuarta Pagina: Ver Usuarios
		// Primero nos registramos porque sino no podemos acceder al listado
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "macmiller@gmail.com", "123456");
		// Pinchamos en la opción de menu de Usuarios:
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de usuarios.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
		// --------Español------
		PO_RegisterView.checkKey(driver, "user.list.search", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "user.list.title", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "user.list.p", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "user.list.email", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "user.list.name", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "user.list.lastName", PO_Properties.getSPANISH());
		// Pinchamos en la opción de menu de Idiomas:
		WebElement btnLanguages7 = driver.findElement(By.id("btnLanguage"));
		btnLanguages7.click();
		// Cambiamos a ingles
		WebElement btnenglish4 = driver.findElement(By.id("btnEnglish"));
		btnenglish4.click();
		// --------Ingles-------
		PO_RegisterView.checkKey(driver, "user.list.search", PO_Properties.getENGLISH());
		PO_RegisterView.checkKey(driver, "user.list.title", PO_Properties.getENGLISH());
		PO_RegisterView.checkKey(driver, "user.list.p", PO_Properties.getENGLISH());
		PO_RegisterView.checkKey(driver, "user.list.email", PO_Properties.getENGLISH());
		PO_RegisterView.checkKey(driver, "user.list.name", PO_Properties.getENGLISH());
		PO_RegisterView.checkKey(driver, "user.list.lastName", PO_Properties.getENGLISH());
		// Pinchamos en la opción de menu de Idiomas:
		WebElement btnLanguages8 = driver.findElement(By.id("btnLanguage"));
		btnLanguages8.click();
		// Cambiamos a español
		WebElement btnspanish4 = driver.findElement(By.id("btnSpanish"));
		btnspanish4.click();
		// --------Español------
		PO_RegisterView.checkKey(driver, "user.list.search", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "user.list.title", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "user.list.p", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "user.list.email", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "user.list.name", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "user.list.lastName", PO_Properties.getSPANISH());
		// Ahora nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	// Intentar acceder sin estar autenticado a la opción de listado de usuarios.
	@Test
	public void PR21() {
		// Intentamos pinchar en el menu de usuarios pero no lo encontrara
		try {
			// Pinchamos en la opción de menu de Usuarios:
			List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
			elementos.get(0).click();
			// Pinchamos en la opción de lista de usuarios.
			elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
			elementos.get(0).click();
		} catch (TimeoutException te) {
		}
	}

	// Intentar acceder sin estar autenticado a la opción de listado de
	// publicaciones.
	@Test
	public void PR22() {
		// Intentamos pinchar en el menu de publicaciones pero no lo encontrara
		try {
			// Pinchamos en la opción de menu de publicaciones:
			List<WebElement> elementos = PO_View.checkElement(driver, "free",
					"//li[contains(@id, 'publications-menu')]/a");
			elementos.get(0).click();
			// Pinchamos en la opción de lista de publicaciones.
			elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'publications/list')]");
			elementos.get(0).click();
		} catch (TimeoutException te) {
		}
	}

	// Estando autenticado como usuario estándar intentar acceder a una opción
	// disponible solo para usuarios administradores
	@Test
	public void PR23() {
		// Primero nos registramos
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "macmiller@gmail.com", "123456");
		try {
			// Pinchamos en la opción de menu de administradores pero como no se le muestra
			// al usuario estandar no la encuentra:
			List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'admin-menu')]/a");
			elementos.get(0).click();
		} catch (TimeoutException te) {
		}
		// Ahora nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	// Estando autenticado como usuario estándar intentar acceder a una opción
	// disponible solo para usuarios administradores
	@Test
	public void PR24() {
		// Primero nos registramos
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "macmiller@gmail.com", "123456");
		// Pinchamos en la opción de menu de publicaciones:
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'publications-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de publicaciones.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'publication/add')]");
		elementos.get(0).click();
		PO_PublicationsView.fillAddPublication(driver, "Prueba 24", "Prueba24");
		// Comprobamos que sale la publicacion creada
		elementos = PO_View.checkElement(driver, "free", "//td[contains(text(), 'Prueba24')]");
		assertTrue(elementos.size() == 1);
		// Ahora nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	// Estando autenticado como usuario estándar intentar acceder a una opción
	// disponible solo para usuarios administradores
	@Test
	public void PR25() {
		// Primero nos registramos
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "macmiller@gmail.com", "123456");
		// Pinchamos en la opción de menu de publicaciones:
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'publications-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de publicaciones.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'publication/add')]");
		elementos.get(0).click();
		PO_PublicationsView.fillAddPublication(driver, "", "");
		// COmprobamos el error de los campos coincide.
		PO_RegisterView.checkKey(driver, "Error.empty.title", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "Error.empty.content", PO_Properties.getSPANISH());
		// Ahora nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	// Mostrar el listado de publicaciones de un usuario y comprobar que se muestran
	// todas las que existen para dicho usuario.
	@Test
	public void PR26() {
		// Primero nos registramos
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "macmiller@gmail.com", "123456");
		// Pinchamos en la opción de menu de publicaciones:
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'publications-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de publicaciones.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'publication/list')]");
		elementos.get(0).click();
		// Comprobamos que existen las dos publicaciones por defecto: Blue Slide Park y
		// Swimming
		elementos = PO_View.checkElement(driver, "free", "//td[contains(text(), 'Blue Slide Park')]");
		assertTrue(elementos.size() == 1);
		elementos = PO_View.checkElement(driver, "free", "//td[contains(text(), 'Swimming')]");
		assertTrue(elementos.size() == 1);
		// Ahora nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	// Mostrar el listado de publicaciones de un usuario amigo y comprobar que se
	// muestran todas las que existen para dicho usuario.
	@Test
	public void PR27() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "macmiller@gmail.com", "123456");
		// Pinchamos en la opción de menu de Usuarios:
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de usuarios.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/listFriends')]");
		elementos.get(0).click();
		// Esperamos a que se muestren los enlaces de paginacion la lista de notas
		PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		// Buscamos al usuario amigo por defecto y le damos a ver publicaciones
		PO_View.checkElement(driver, "free", "//td[contains(text(), 'Travis')]");
		// Vamos a sus publicaciones
		driver.navigate().to(URL + "/publication/list/travisscott@gmail.com");
		// Comprobamos que existen la publicaciones por defecto: Astroworld
		elementos = PO_View.checkElement(driver, "free", "//td[contains(text(), 'Astroworld')]");
		assertTrue(elementos.size() == 1);
		// Ahora nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	// Utilizando un acceso vía URL u otra alternativa, tratar de listar las
	// publicaciones de un usuario que no sea amigo del usuario identificado en
	// sesión. Comprobar que el sistema da un error de autorización.
	@Test
	public void PR28() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "macmiller@gmail.com", "123456");
		// Probamos a ver las publicaciones de Post Malone el cual no es amigo de Mac
		// Miller y vemos que no se puede
		try {
			driver.navigate().to(URL + "/publication/list/postmalone@gmail.com");
		} catch (Exception e) {
		}
		// Ahora nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	// Mostrar el listado de usuarios y comprobar que se muestran todos los que
	// existen en el sistema.
	@Test
	public void PR31() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@email.com", "123456");
		// Pinchamos en la opción de menu de Usuarios:
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de usuarios.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
		// Esperamos a que se muestren los enlaces de paginacion la lista de notas
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		// Comprobamos que existe el otro administrador ya que es el unico que lo ve
		elementos = PO_View.checkElement(driver, "free", "//td[contains(text(), 'admin2')]");
		assertTrue(elementos.size() == 1);
		// Ahora nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");

	}

}