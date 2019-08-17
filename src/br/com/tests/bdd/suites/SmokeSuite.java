package br.com.tests.bdd.suites;

import br.com.tests.bdd.categories.SmokeTest;
import br.com.tests.bdd.tests.ClienteTest;
import br.com.tests.bdd.tests.IndexTest;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Categories.class)
@Suite.SuiteClasses({
        IndexTest.class,
        ClienteTest.class
})
@Categories.IncludeCategory({SmokeTest.class})

public class SmokeSuite {
}
