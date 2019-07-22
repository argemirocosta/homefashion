package br.com.homefashion.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IndexPage {

    private final WebDriver driver;


    public IndexPage(WebDriver driver) {
        this.driver = driver;
    }

    public void realizarLogin(String login, String senha) {

        WebElement campoLogin = driver.findElement(By.name("form:j_idt12"));
        WebElement campoSenha = driver.findElement(By.name("form:j_idt14"));
        WebElement botaoLogin = driver.findElement(By.name("form:j_idt16"));

        campoLogin.sendKeys(login);
        campoSenha.sendKeys(senha);

        botaoLogin.click();

    }

    public boolean encontrouTexto(String item) {
        return driver.getPageSource().contains(item);
    }


    public void abrirDialogCadastro(){
        WebElement botaoCadastro = driver.findElement(By.name("formBtnCadastro:j_idt19"));
        botaoCadastro.click();
    }

    public void fecharDialogCadastro(){
        WebElement botaoFecharDialogCadastro = driver.findElement(By.name("formCadastro:j_idt32"));
        botaoFecharDialogCadastro.click();
    }

    public void realizarCadastro(String nome, String login, String senha){
        WebElement campoNome = driver.findElement(By.name("formCadastro:j_idt24"));
        WebElement campoLogin = driver.findElement(By.name("formCadastro:j_idt26"));
        WebElement campoSenha = driver.findElement(By.name("formCadastro:j_idt28"));
        WebElement botaoCadastrar = driver.findElement(By.name("formCadastro:j_idt31"));

        campoNome.sendKeys(nome);
        campoLogin.sendKeys(login);
        campoSenha.sendKeys(senha);

        botaoCadastrar.click();

    }

}
