package suites;

import br.com.homefashion.util.DataUtilTeste;
import br.com.homefashion.util.DocumentosUtilTeste;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DataUtilTeste.class,
        DocumentosUtilTeste.class
})

public class SuitePackageUtil {

    @BeforeClass
    public static void iniciarSuiteDeTeste(){
        System.out.println("Iniciando classe ");
    }

    @AfterClass
    public static void finalizarSuiteDeTeste(){
        System.out.println("Finalizando classe");
    }

}
