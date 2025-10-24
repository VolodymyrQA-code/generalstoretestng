package tests.suites;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import tests.ApiPostTests;

@Suite
@SelectClasses({
        ApiPostTests.class
})
public class ApiSuite {
    // Порожній клас, просто агрегація тестів
}
