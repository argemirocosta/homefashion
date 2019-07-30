package br.com.homefashion.tests.tests;

import br.com.homefashion.tests.pages.IndexPage;
import br.com.homefashion.tests.util.TesteUtil;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static br.com.homefashion.tests.util.Constantes.*;


public class IndexTest {

    private static WebDriver driver;
    private static IndexPage indexPage;

    @Before
    public void inicializa() {
        driver = new FirefoxDriver();
        driver.get(PAGINA_PARA_TESTE);
        indexPage = new IndexPage(driver);
    }

    @Test
    public void deveCadastrarUsuarioComNomeComLoginComSenha() {

        indexPage.abrirDialogCadastro();

        indexPage.realizarCadastro("João", "joao", "2");

        boolean resultadoTeste = TesteUtil.encontrouTexto(TEXTO_SUCESSO_CADASTRO_USUARIO, driver);

        if (!resultadoTeste) {
            indexPage.fecharDialogCadastro();
        }

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

        indexPage.realizarCadastro("João", "joao", "");

        boolean resultadoTeste = TesteUtil.encontrouTexto(TEXTO_SUCESSO_CADASTRO_USUARIO, driver);

        if (!resultadoTeste) {
            indexPage.fecharDialogCadastro();
        }

        assertFalse(resultadoTeste);

    }

    @Test
    public void naoDeveCadastrarUsuarioComNomeSemLoginSemSenha() {

        indexPage.abrirDialogCadastro();

        indexPage.realizarCadastro("João", "", "");

        boolean resultadoTeste = TesteUtil.encontrouTexto(TEXTO_SUCESSO_CADASTRO_USUARIO, driver);

        if (!resultadoTeste) {
            indexPage.fecharDialogCadastro();
        }

        assertFalse(resultadoTeste);

    }

    @Test
    public void naoDeveCadastrarUsuarioSemNomeComLoginComSenha() {

        indexPage.abrirDialogCadastro();

        indexPage.realizarCadastro("", "joao", "2");

        boolean resultadoTeste = TesteUtil.encontrouTexto(TEXTO_SUCESSO_CADASTRO_USUARIO, driver);

        if (!resultadoTeste) {
            indexPage.fecharDialogCadastro();
        }

        assertFalse(resultadoTeste);

    }

    @Test
    public void naoDeveCadastrarUsuarioSemNomeComLoginSemSenha() {

        indexPage.abrirDialogCadastro();

        indexPage.realizarCadastro("", "joao", "");

        boolean resultadoTeste = TesteUtil.encontrouTexto(TEXTO_SUCESSO_CADASTRO_USUARIO, driver);

        if (!resultadoTeste) {
            indexPage.fecharDialogCadastro();
        }

        assertFalse(resultadoTeste);

    }

    @Test
    public void naoDeveCadastrarUsuarioSemNomeSemLoginComSenha() {

        indexPage.abrirDialogCadastro();

        indexPage.realizarCadastro("", "", "1");

        boolean resultadoTeste = TesteUtil.encontrouTexto(TEXTO_SUCESSO_CADASTRO_USUARIO, driver);

        if (!resultadoTeste) {
            indexPage.fecharDialogCadastro();
        }

        assertFalse(resultadoTeste);

    }

    @Test
    public void deveRealizarLoginComLoginComSenha() {

        indexPage.realizarLogin("joao", "2");

        assertTrue(TesteUtil.encontrouTexto(TEXTO_SUCESSO_LOGIN, driver));

    }

    @Test
    public void naoDeveRealizarLoginSemLoginSemSenha() {

        indexPage.realizarLogin("", "");

        assertFalse(TesteUtil.encontrouTexto(TEXTO_SUCESSO_LOGIN, driver));

    }

    @Test
    public void naoDeveRealizarLoginComLoginSemSenha() {

        indexPage.realizarLogin("joao", "");

        assertFalse(TesteUtil.encontrouTexto(TEXTO_SUCESSO_LOGIN, driver));

    }

    @Test
    public void naoDeveRealizarLoginSemLoginComSenha() {

        indexPage.realizarLogin("", "2");

        assertFalse(TesteUtil.encontrouTexto(TEXTO_SUCESSO_LOGIN, driver));

    }

    @After
    public void finaliza() {
        TesteUtil.finalizarDriver(driver);
    }

}


