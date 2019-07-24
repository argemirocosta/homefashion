package br.com.homefashion.tests.util;

public class Constantes {

    private Constantes() {}

    //Página
    public static final String PAGINA_PARA_TESTE = "http://localhost:8080/home_fashion/";


    //Login - Inputs e Botões
    public static final String INDEX_INPUT_LOGIN_USUARIO = "form:j_idt12";

    public static final String INDEX_INPUT_LOGIN_SENHA = "form:j_idt14";

    public static final String INDEX_BOTAO_LOGIN = "form:j_idt16";


    //Cadasto usuário - Inputs e Botões

    public static final String INDEX_BOTAO_ABRIR_DIALOG_CADASTRO_USUARIO = "formBtnCadastro:j_idt19";

    public static final String INDEX_BOTAO_FECHAR_DIALOG_CADASTRO_USUARIO = "formCadastro:j_idt32";

    public static final String INDEX_INPUT_CADASTRO_USUARIO_NOME = "formCadastro:j_idt24";

    public static final String INDEX_INPUT_CADASTRO_USUARIO_LOGIN = "formCadastro:j_idt26";

    public static final String INDEX_INPUT_CADASTRO_USUARIO_SENHA = "formCadastro:j_idt28";

    public static final String INDEX_BOTAO_CADASTRAR_USUARIO = "formCadastro:j_idt31";


    //Cadastro de cliente - Inputs e Botões

    public static final String CLIENTE_BOTAO_ABRIR_DIALOG_CADASTRO_CLIENTE = "formClientes:j_idt24";

    public static final String CLIENTE_INPUT_NOME = "formCadastroCliente:j_idt49";

    public static final String CLIENTE_INPUT_TELEFONE1 = "formCadastroCliente:j_idt51";

    public static final String CLIENTE_INPUT_TELEFONE2 = "formCadastroCliente:j_idt53";

    public static final String CLIENTE_BOTAO_CADASTRAR_CLIENTE = "formCadastroCliente:j_idt57";


    //Texto sucesso do teste
    public static final String TEXTO_SUCESSO_CADASTRO_CLIENTE = "Cliente cadastrado com sucesso!";

    public static final String TEXTO_SUCESSO_CADASTRO_USUARIO = "Usuário cadastrado com sucesso!";

    public static final String TEXTO_SUCESSO_LOGIN = "Telefone 1";

}
