package br.com.homefashion.builders;

import br.com.homefashion.model.Cliente;

public class ClienteBuilderTest {

    private Cliente cliente;

    private ClienteBuilderTest(){

    }

    public static ClienteBuilderTest umClienteTeste1(){
        ClienteBuilderTest builderTest = new ClienteBuilderTest();
        builderTest.cliente = new Cliente();
        builderTest.cliente.setNome("Usuario teste 1");
        return builderTest;
    }

    public static ClienteBuilderTest umClienteTeste2(){
        ClienteBuilderTest builderTest = new ClienteBuilderTest();
        builderTest.cliente = new Cliente();
        builderTest.cliente.setNome("Usuario teste 2");
        return builderTest;
    }

    public Cliente agora(){
        return cliente;
    }

}
