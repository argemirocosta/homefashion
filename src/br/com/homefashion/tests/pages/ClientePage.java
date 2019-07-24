package br.com.homefashion.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static br.com.homefashion.tests.util.Constantes.*;

public class ClientePage {

    private final WebDriver driver;


    public ClientePage(WebDriver driver) {
        this.driver = driver;
    }

    public void abrirDialogNovoCliente(){
        WebElement botaoCadastro = driver.findElement(By.name(CLIENTE_BOTAO_ABRIR_DIALOG_CADASTRO_CLIENTE));
        botaoCadastro.click();
    }

    public void fecharDialogCadastro(){
        WebElement botaoFecharDialogCadastro = driver.findElement(By.name(CLIENTE_BOTAO_ABRIR_DIALOG_CADASTRO_CLIENTE));
        botaoFecharDialogCadastro.click();
    }

    public void realizarCadastroCliente(String nome, Integer telefone1, Integer telefone2){
        WebElement campoNome = driver.findElement(By.name(CLIENTE_INPUT_NOME));
        WebElement campoTelefone1 = driver.findElement(By.name(CLIENTE_INPUT_TELEFONE1));
        WebElement campoTelefone2 = driver.findElement(By.name(CLIENTE_INPUT_TELEFONE2));
        WebElement botaoCadastrar = driver.findElement(By.name(CLIENTE_BOTAO_CADASTRAR_CLIENTE));

        campoNome.sendKeys(nome);
        campoTelefone1.sendKeys(String.valueOf(telefone1));
        campoTelefone2.sendKeys(String.valueOf(telefone2));

        botaoCadastrar.click();

    }

}
