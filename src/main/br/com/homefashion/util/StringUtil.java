package br.com.homefashion.util;


public final class StringUtil {

    public static String retirarCaracteresEspeciais(String textoParaSerTratado) {

        return textoParaSerTratado.replaceAll("[^0-9]", "");
    }

}
