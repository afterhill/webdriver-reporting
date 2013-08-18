package webdriver.reporting.tests;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

import webdriver.reporting.LoggingTestWatchman;
import webdriver.reporting.LoggingWebDriverEventListener;
import webdriver.reporting.TestCaseReportWriter;

public class SampleGoogleSearchReportTest {
    
    static private final TestCaseReportWriter LOG_FILE_WRITER = new TestCaseReportWriter();
    
    private EventFiringWebDriver driverWithReporting;
    
    @Rule
    public TestRule logRule = new LoggingTestWatchman(LOG_FILE_WRITER);
    
    @Before
    public void setUp() {
        WebDriver driver = new InternetExplorerDriver();
        
        WebDriverEventListener loggingListener = new LoggingWebDriverEventListener(
                LOG_FILE_WRITER);
        driverWithReporting = new EventFiringWebDriver(driver);
        driverWithReporting.register(loggingListener);
    }
    
    @After
    public void tearDown() {
        driverWithReporting.getWrappedDriver().quit();
    }
    
    @Test
    public void testGoogleSearch() {
        driverWithReporting.get("http://www.google.com");
        WebElement element = driverWithReporting.findElement(By.name("q"));
        element.sendKeys("Mifos");
        element.submit();
        fail("Expected Fails!");
    }
    
    @Test
    public void testBaidu() {
        driverWithReporting.get("http://www.baidu.com");
        WebElement element = driverWithReporting.findElement(By.name("wd"));
        element.sendKeys("Mifos");
        element.submit();
    }
    
}
