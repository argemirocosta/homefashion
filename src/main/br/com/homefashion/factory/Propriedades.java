package br.com.homefashion.factory;

public class Propriedades {

    public static Conexoes Conexao = Conexoes.PRODUCAO;

    public enum Conexoes {
        LOCALHOST,
        PRODUCAO;
    }




}
