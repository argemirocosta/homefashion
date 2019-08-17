package br.com.tests.bdd.tests;

import br.com.tests.bdd.categories.PositiveTest;
import br.com.tests.bdd.categories.SmokeTest;
import br.com.tests.bdd.factory.FactoryDriver;
import br.com.tests.bdd.pages.ClientePage;
import br.com.tests.bdd.pages.IndexPage;
import br.com.tests.bdd.util.FakerUtil;
import br.com.tests.bdd.util.TesteUtil;
import org.junit.experimental.categories.Category;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static br.com.tests.bdd.elements.ClienteElements.*;
import static org.junit.Assert.assertTrue;

import static br.com.tests.bdd.shared.Constantes.*;


public class ClienteTest {

    private static WebDriver driver;
    private static IndexPage indexPage;

    public ClienteTest() {
    }

    @Before
    public void inicializa() {
        driver = FactoryDriver.createDriver(NOME_DO_NAVEGADOR);
        driver.get(PAGINA_PARA_TESTE);
        indexPage = new IndexPage(driver);
    }

    @Test
    @Category({PositiveTest.class, SmokeTest.class})
    public void deveCadastrarCliente() {
        ClientePage page = PageFactory.initElements(driver, ClientePage.class);

        indexPage.realizarLogin(USUARIO_TESTE_LOGIN, SENHA_TESTE_LOGIN);

        page.abrirDialogNovoCliente();

        page.realizarCadastroCliente(FakerUtil.gerarNome(), FakerUtil.gerarTelefone(), FakerUtil.gerarTelefone());

        boolean resultadoTeste = TesteUtil.encontrouTexto(TEXTO_SUCESSO_CADASTRO_CLIENTE, driver);

        assertTrue(resultadoTeste);

    }

    @After
    public void finaliza() {
        TesteUtil.finalizarDriver(driver);
    }

}


