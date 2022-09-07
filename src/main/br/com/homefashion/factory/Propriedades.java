package br.com.homefashion.factory;

public class Propriedades {

    public static Conexoes Conexao = Conexoes.LOCALHOST;

    public enum Conexoes {
        LOCALHOST,
        PRODUCAO;
    }




}
