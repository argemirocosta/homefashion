package br.com.homefashion.tests.suites;

import br.com.homefashion.tests.categories.PositiveTest;
import br.com.homefashion.tests.tests.ClienteTest;
import br.com.homefashion.tests.tests.IndexTest;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Categories.class)
@Suite.SuiteClasses({
        IndexTest.class,
        ClienteTest.class
})
@Categories.IncludeCategory({PositiveTest.class})

public class PositiveSuite {
}