package br.com.homefashion.tests.pages;

import br.com.homefashion.tests.util.TesteUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static br.com.homefashion.tests.elements.ClienteElements.*;

public class ClientePage {

    private final WebDriver driver;


    public ClientePage(WebDriver driver) {
        this.driver = driver;
    }

    public void abrirDialogNovoCliente(){
        WebElement botaoCadastro = TesteUtil.buscarComponentePorNome(CLIENTE_BOTAO_ABRIR_DIALOG_CADASTRO_CLIENTE, driver);
        botaoCadastro.click();
    }

    public void fecharDialogCadastro(){
        WebElement botaoFecharDialogCadastro = TesteUtil.buscarComponentePorNome(CLIENTE_BOTAO_ABRIR_DIALOG_CADASTRO_CLIENTE, driver);
        botaoFecharDialogCadastro.click();
    }

    public void realizarCadastroCliente(String nome, Integer telefone1, Integer telefone2){
        WebElement campoNome = TesteUtil.buscarComponentePorNome(CLIENTE_INPUT_NOME, driver);
        WebElement campoTelefone1 = TesteUtil.buscarComponentePorNome(CLIENTE_INPUT_TELEFONE1, driver);
        WebElement campoTelefone2 = TesteUtil.buscarComponentePorNome(CLIENTE_INPUT_TELEFONE2, driver);
        WebElement botaoCadastrar = TesteUtil.buscarComponentePorNome(CLIENTE_BOTAO_CADASTRAR_CLIENTE, driver);

        campoNome.sendKeys(nome);
        campoTelefone1.sendKeys(String.valueOf(telefone1));
        campoTelefone2.sendKeys(String.valueOf(telefone2));

        botaoCadastrar.click();

    }

}
