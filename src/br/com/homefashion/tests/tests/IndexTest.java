package br.com.homefashion.tests.tests;

import br.com.homefashion.tests.factory.FactoryDriver;
import br.com.homefashion.tests.pages.IndexPage;
import br.com.homefashion.tests.util.FakerUtil;
import br.com.homefashion.tests.util.TesteUtil;
import org.junit.*;
import org.openqa.selenium.WebDriver;

import static br.com.homefashion.tests.elements.IndexElements.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static br.com.homefashion.tests.shared.Constantes.*;


public class IndexTest {

    private static WebDriver driver;
    private static IndexPage indexPage;

    @Before
    public void inicializa() {
        driver = FactoryDriver.createDriver(NOME_DO_NAVEGADOR);
        driver.get(PAGINA_PARA_TESTE);
        indexPage = new IndexPage(driver);
    }

    @Test
    public void deveCadastrarUsuarioComNomeComLoginComSenha() {

        indexPage.abrirDialogCadastro();

        indexPage.realizarCadastro(FakerUtil.gerarNome(), FakerUtil.gerarLogin(), FakerUtil.gerarSenha());

        boolean resultadoTeste = TesteUtil.encontrouTexto(TEXTO_SUCESSO_CADASTRO_USUARIO, driver);

        assertTrue(resultadoTeste);

    }

    @Test
    public void naoDeveCadastrarUsuarioSemNomeSemLoginSemSenha() {

        indexPage.abrirDialogCadastro();

        indexPage.realizarCadastro("", "", "");

        boolean resultadoTeste = TesteUtil.encontrouTexto(TEXTO_SUCESSO_CADASTRO_USUARIO, driver);

        if (!resultadoTeste) {
            indexPage.fecharDialogCadastro();
        }

        assertFalse(resultadoTeste);

    }

    @Test
    public void naoDeveCadastrarUsuarioComNomeComLoginSemSenha() {

        indexPage.abrirDialogCadastro();

        indexPage.realizarCadastro(FakerUtil.gerarNome(), FakerUtil.gerarLogin(), "");

        boolean resultadoTeste = TesteUtil.encontrouTexto(TEXTO_SUCESSO_CADASTRO_USUARIO, driver);

        if (!resultadoTeste) {
            indexPage.fecharDialogCadastro();
        }

        assertFalse(resultadoTeste);

    }

    @Test
    public void naoDeveCadastrarUsuarioComNomeSemLoginSemSenha() {

        indexPage.abrirDialogCadastro();

        indexPage.realizarCadastro(FakerUtil.gerarNome(), "", "");

        boolean resultadoTeste = TesteUtil.encontrouTexto(TEXTO_SUCESSO_CADASTRO_USUARIO, driver);

        if (!resultadoTeste) {
            indexPage.fecharDialogCadastro();
        }

        assertFalse(resultadoTeste);

    }

    @Test
    public void naoDeveCadastrarUsuarioSemNomeComLoginComSenha() {

        indexPage.abrirDialogCadastro();

        indexPage.realizarCadastro("", FakerUtil.gerarLogin(), FakerUtil.gerarSenha());

        boolean resultadoTeste = TesteUtil.encontrouTexto(TEXTO_SUCESSO_CADASTRO_USUARIO, driver);

        if (!resultadoTeste) {
            indexPage.fecharDialogCadastro();
        }

        assertFalse(resultadoTeste);

    }

    @Test
    public void naoDeveCadastrarUsuarioSemNomeComLoginSemSenha() {

        indexPage.abrirDialogCadastro();

        indexPage.realizarCadastro("", FakerUtil.gerarLogin(), "");

        boolean resultadoTeste = TesteUtil.encontrouTexto(TEXTO_SUCESSO_CADASTRO_USUARIO, driver);

        if (!resultadoTeste) {
            indexPage.fecharDialogCadastro();
        }

        assertFalse(resultadoTeste);

    }

    @Test
    public void naoDeveCadastrarUsuarioSemNomeSemLoginComSenha() {

        indexPage.abrirDialogCadastro();

        indexPage.realizarCadastro("", "", FakerUtil.gerarSenha());

        boolean resultadoTeste = TesteUtil.encontrouTexto(TEXTO_SUCESSO_CADASTRO_USUARIO, driver);

        if (!resultadoTeste) {
            indexPage.fecharDialogCadastro();
        }

        assertFalse(resultadoTeste);

    }

    @Test
    public void deveRealizarLoginComLoginComSenha() {

        indexPage.realizarLogin(USUARIO_TESTE_LOGIN, SENHA_TESTE_LOGIN);

        assertTrue(TesteUtil.encontrouTexto(TEXTO_SUCESSO_LOGIN, driver));

    }

    @Test
    public void naoDeveRealizarLoginSemLoginSemSenha() {

        indexPage.realizarLogin("", "");

        assertFalse(TesteUtil.encontrouTexto(TEXTO_SUCESSO_LOGIN, driver));

    }

    @Test
    public void naoDeveRealizarLoginComLoginSemSenha() {

        indexPage.realizarLogin(USUARIO_TESTE_LOGIN, "");

        assertFalse(TesteUtil.encontrouTexto(TEXTO_SUCESSO_LOGIN, driver));

    }

    @Test
    public void naoDeveRealizarLoginSemLoginComSenha() {

        indexPage.realizarLogin("", SENHA_TESTE_LOGIN);

        assertFalse(TesteUtil.encontrouTexto(TEXTO_SUCESSO_LOGIN, driver));

    }

    @After
    public void finaliza() {
        TesteUtil.finalizarDriver(driver);
    }

}


