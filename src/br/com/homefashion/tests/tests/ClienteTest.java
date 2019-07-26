package br.com.homefashion.tests.tests;

import br.com.homefashion.tests.pages.ClientePage;
import br.com.homefashion.tests.pages.IndexPage;
import br.com.homefashion.tests.util.TesteUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertTrue;

import static br.com.homefashion.tests.util.Constantes.*;


public class ClienteTest {

    private static WebDriver driver;
    private static IndexPage indexPage;
    private static ClientePage clientePage;

    @BeforeClass
    public static void inicializa() {
        driver = new FirefoxDriver();
        driver.get(PAGINA_PARA_TESTE);
        indexPage = new IndexPage(driver);
        clientePage = new ClientePage(driver);
    }

    @Test
    public void deveCadastrarCliente() {

        indexPage.realizarLogin("argemiro", "1");

        clientePage.abrirDialogNovoCliente();

        clientePage.realizarCadastroCliente("Jose", 1, 2);

        boolean resultadoTeste = TesteUtil.encontrouTexto(TEXTO_SUCESSO_CADASTRO_CLIENTE, driver);

        if (!resultadoTeste) {
            clientePage.fecharDialogCadastro();
        }

        assertTrue(resultadoTeste);

    }

    @AfterClass
    public static void finaliza() {
        TesteUtil.finalizarDriver(driver);
    }

}


