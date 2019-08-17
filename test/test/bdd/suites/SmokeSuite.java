package test.bdd.suites;

import test.bdd.categories.SmokeTest;
import test.bdd.tests.ClienteTest;
import test.bdd.tests.IndexTest;
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
