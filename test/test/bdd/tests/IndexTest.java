package test.bdd.tests;

import test.bdd.categories.NegativeTest;
import test.bdd.categories.PositiveTest;
import test.bdd.categories.SmokeTest;
import test.bdd.factory.FactoryDriver;
import test.bdd.pages.IndexPage;
import test.bdd.util.FakerUtil;
import test.bdd.util.TesteUtil;
import test.bdd.shared.Constantes;
import org.junit.*;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.WebDriver;

import static test.bdd.elements.IndexElements.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class IndexTest {

    private static WebDriver driver;
    private static IndexPage indexPage;

    @Before
    public void inicializa() {
        driver = FactoryDriver.createDriver(Constantes.NOME_DO_NAVEGADOR);
        driver.get(Constantes.PAGINA_PARA_TESTE);
        indexPage = new IndexPage(driver);
    }

    @Test
    @Category({PositiveTest.class, SmokeTest.class})
    public void deveCadastrarUsuarioComNomeComLoginComSenha() {

        indexPage.abrirDialogCadastro();

        indexPage.realizarCadastro(FakerUtil.gerarNome(), FakerUtil.gerarLogin(), FakerUtil.gerarSenha());

        boolean resultadoTeste = TesteUtil.encontrouTexto(TEXTO_SUCESSO_CADASTRO_USUARIO, driver);

        assertTrue(resultadoTeste);

    }

    @Test
    @Category({NegativeTest.class, SmokeTest.class})
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
    @Category(NegativeTest.class)
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
    @Category(NegativeTest.class)
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
    @Category(NegativeTest.class)
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
    @Category(NegativeTest.class)
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
    @Category(NegativeTest.class)
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
    @Category({PositiveTest.class, SmokeTest.class})
    public void deveRealizarLoginComLoginComSenha() {

        indexPage.realizarLogin(Constantes.USUARIO_TESTE_LOGIN, Constantes.SENHA_TESTE_LOGIN);

        assertTrue(TesteUtil.encontrouTexto(TEXTO_SUCESSO_LOGIN, driver));

    }

    @Test
    @Category(NegativeTest.class)
    public void naoDeveRealizarLoginSemLoginSemSenha() {

        indexPage.realizarLogin("", "");

        assertFalse(TesteUtil.encontrouTexto(TEXTO_SUCESSO_LOGIN, driver));

    }

    @Test
    @Category(NegativeTest.class)
    public void naoDeveRealizarLoginComLoginSemSenha() {

        indexPage.realizarLogin(Constantes.USUARIO_TESTE_LOGIN, "");

        assertFalse(TesteUtil.encontrouTexto(TEXTO_SUCESSO_LOGIN, driver));

    }

    @Test
    @Category(NegativeTest.class)
    public void naoDeveRealizarLoginSemLoginComSenha() {

        indexPage.realizarLogin("", Constantes.SENHA_TESTE_LOGIN);

        assertFalse(TesteUtil.encontrouTexto(TEXTO_SUCESSO_LOGIN, driver));

    }

    @After
    public void finaliza() {
        TesteUtil.finalizarDriver(driver);
    }

}

