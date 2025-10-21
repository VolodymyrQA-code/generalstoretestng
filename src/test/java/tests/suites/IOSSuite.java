package tests.suites;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import tests.IOSTests;

@Suite
@SelectClasses({
        IOSTests.class
})
public class IOSSuite {
    // Порожній клас — лише для агрегування тестів
}
