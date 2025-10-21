package tests.suites;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import tests.HomeTest;
import tests.SplashTest;

import org.junit.jupiter.api.Test;


@Suite
@SelectClasses({
    SplashTest.class,
    HomeTest.class
})
public class AllTestsSuite {
}
