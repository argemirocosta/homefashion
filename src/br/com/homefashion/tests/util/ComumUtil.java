package br.com.homefashion.tests.util;

import org.openqa.selenium.WebDriver;

public final class ComumUtil {

    public static boolean encontrouTexto(String item, WebDriver driver) {
        return driver.getPageSource().contains(item);
    }

    public static void finalizarDriver(WebDriver driver){
        driver.close();
    }

}
