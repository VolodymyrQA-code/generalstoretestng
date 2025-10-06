package tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.jupiter.api.Test;


@Suite
@SelectClasses({
    SplashTest.class,
    HomeTest.class
})
public class AllTestsSuite {
}
