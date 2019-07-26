package br.com.homefashion.tests.tests;

import br.com.homefashion.tests.pages.IndexPage;
import br.com.homefashion.tests.util.TesteUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertTrue;

import static br.com.homefashion.tests.util.Constantes.*;


public class IndexTest {

    private static WebDriver driver;
    private static IndexPage indexPage;

    @BeforeClass
    public static void inicializa() {
        driver = new FirefoxDriver();
        driver.get(PAGINA_PARA_TESTE);
        indexPage = new IndexPage(driver);
    }

    @Test
    public void deveCadastrarUsuario() {

        indexPage.abrirDialogCadastro();

        indexPage.realizarCadastro("João", "joao", "2");

        boolean resultadoTeste = TesteUtil.encontrouTexto(TEXTO_SUCESSO_CADASTRO_USUARIO, driver);

        if (!resultadoTeste) {
            indexPage.fecharDialogCadastro();
        }

        assertTrue(resultadoTeste);

    }

    @Test
    public void deveRealizarLogin() {

        indexPage.realizarLogin("joao", "2");

        assertTrue(TesteUtil.encontrouTexto(TEXTO_SUCESSO_LOGIN, driver));

    }

    @AfterClass
    public static void finaliza() {
        TesteUtil.finalizarDriver(driver);
    }

}


