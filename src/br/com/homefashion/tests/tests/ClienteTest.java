package br.com.homefashion.tests.tests;

import br.com.homefashion.tests.factory.FactoryDriver;
import br.com.homefashion.tests.pages.ClientePage;
import br.com.homefashion.tests.pages.IndexPage;
import br.com.homefashion.tests.util.TesteUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static br.com.homefashion.tests.elements.ClienteElements.*;
import static org.junit.Assert.assertTrue;

import static br.com.homefashion.tests.util.Constantes.*;


public class ClienteTest {

    private static WebDriver driver;
    private static IndexPage indexPage;
    private static ClientePage clientePage;

    @Before
    public static void inicializa() {
        driver = FactoryDriver.createDriver(NOME_DO_NAVEGADOR);
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

        assertTrue(resultadoTeste);

    }

    @After
    public static void finaliza() {
        TesteUtil.finalizarDriver(driver);
    }

}


