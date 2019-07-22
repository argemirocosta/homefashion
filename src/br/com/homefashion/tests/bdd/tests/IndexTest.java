package br.com.homefashion.tests.bdd.tests;

import br.com.homefashion.tests.bdd.pages.IndexPage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertTrue;

public class IndexTest {

    private static WebDriver driver;
    private static IndexPage indexPage;

    @BeforeClass
    public static void inicializa() {
        driver = new FirefoxDriver();
        driver.get("http://localhost:8080/homefashion/");
        indexPage = new IndexPage(driver);
    }

    @Test
    public void deveCadastrarUsuario() {

        indexPage.abrirDialogCadastro();

        indexPage.realizarCadastro("João", "joao", "2");

        Boolean resultadoTeste = indexPage.encontrouTexto("Usuário cadastrado com sucesso!");

        if (!resultadoTeste) {
            indexPage.fecharDialogCadastro();
        }

        assertTrue(resultadoTeste);

    }

    @Test
    public void deveRealizarLogin() {

        indexPage.realizarLogin("argemiro", "1");

        assertTrue(indexPage.encontrouTexto("Telefone 1"));

    }

    @AfterClass
    public static void finaliza() {
        driver.close();
    }

}


