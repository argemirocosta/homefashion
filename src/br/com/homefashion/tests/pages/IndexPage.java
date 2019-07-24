package br.com.homefashion.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static br.com.homefashion.tests.util.Constantes.*;

public class IndexPage {

    private final WebDriver driver;


    public IndexPage(WebDriver driver) {
        this.driver = driver;
    }

    public void realizarLogin(String login, String senha) {

        WebElement campoLogin = driver.findElement(By.name(INDEX_INPUT_LOGIN_USUARIO));
        WebElement campoSenha = driver.findElement(By.name(INDEX_INPUT_LOGIN_SENHA));
        WebElement botaoLogin = driver.findElement(By.name(INDEX_BOTAO_LOGIN));

        campoLogin.sendKeys(login);
        campoSenha.sendKeys(senha);

        botaoLogin.click();

    }

    public void abrirDialogCadastro(){
        WebElement botaoCadastro = driver.findElement(By.name(INDEX_BOTAO_ABRIR_DIALOG_CADASTRO_USUARIO));
        botaoCadastro.click();
    }

    public void fecharDialogCadastro(){
        WebElement botaoFecharDialogCadastro = driver.findElement(By.name(INDEX_BOTAO_FECHAR_DIALOG_CADASTRO_USUARIO));
        botaoFecharDialogCadastro.click();
    }

    public void realizarCadastro(String nome, String login, String senha){
        WebElement campoNome = driver.findElement(By.name(INDEX_INPUT_CADASTRO_USUARIO_NOME));
        WebElement campoLogin = driver.findElement(By.name(INDEX_INPUT_CADASTRO_USUARIO_LOGIN));
        WebElement campoSenha = driver.findElement(By.name(INDEX_INPUT_CADASTRO_USUARIO_SENHA));
        WebElement botaoCadastrar = driver.findElement(By.name(INDEX_BOTAO_CADASTRAR_USUARIO));

        campoNome.sendKeys(nome);
        campoLogin.sendKeys(login);
        campoSenha.sendKeys(senha);

        botaoCadastrar.click();

    }

}
