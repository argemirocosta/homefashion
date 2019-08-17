package br.com.tests.bdd.util;

import static br.com.tests.bdd.shared.Constantes.*;

import com.github.javafaker.Faker;

import java.util.Locale;

public final class FakerUtil {

    private static Faker faker = new Faker(new Locale(IDIOMA_FAKER));

    public static String gerarNome() {
        return faker.name().fullName();
    }

    public static String gerarLogin() {
        return faker.name().username().toLowerCase();
    }

    public static String gerarSenha() {
        return faker.name().firstName().toLowerCase();
    }

    public static Integer gerarTelefone() {
        return faker.number().numberBetween(1, 1000000);
    }

}
