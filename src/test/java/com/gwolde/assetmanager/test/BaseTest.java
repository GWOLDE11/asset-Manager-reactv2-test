package com.gwolde.assetmanager.test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import com.gwolde.assetmanager.page.AssetManager;
import com.gwolde.assetmanager.utils.AssetManagerUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * Created by gwolde11 on 4/23/17.
 */
public abstract class BaseTest {

    static {

        System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");
    }


    private String screenShotPath;
    private int failedTest = 0;
    private Long timeout = 15000L;
    private WebDriver driver;
    private AssetManagerUtil assetManagerUtil;
    protected AssetManager assetManager;

    /**
     * Executed before any tests declared inside a TestNG suite are run
     * check user able to login to dev environment if not change the test environment to demo
     *
     * @param context which contains all the information for a given test run
     * @throws java.io.FileNotFoundException
     */
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context) throws IOException {
        Map<String, String> parameters = context.getSuite().getXmlSuite().getParameters();

        try {
            screenShotPath = new File(context.getOutputDirectory() +
                    parameters.get("screenShotPath")).getAbsolutePath();
            if (new File(screenShotPath).exists())
                FileUtils.deleteDirectory(new File(screenShotPath));
            String browserType = System.getProperty("browserType");

            if (browserType != null) {
                driverFor(BrowserType.getBrowserType(browserType));

            } else {
                driverFor(BrowserType.FIREFOX);
            }
            assetManagerUtil = AssetManagerUtil.getInstance(driver);
            if (System.getProperty("testEnvironment") != null)
                assetManager = launchAssetManager(System.getProperty("testEnvironment"));
            else
                assetManager = launchAssetManager("localhost");
        } catch (Exception e) {

            ATUReports.add("Server is not available", LogAs.FAILED, new CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
            System.out.println(e.getMessage());
            driver.quit();
            System.exit(-100);
        }
    }

    /**
     * Get the WebDriver depending on browser type
     *
     * @param browserName browser type (firefox, chrome, safari or IE
     * @return
     * @throws java.lang.Exception
     */
    private void driverFor(BrowserType browserName) throws WebDriverException {

        switch (browserName) {
            case FIREFOX:
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("dom.webnotifications.enabled", false);
                driver = new FirefoxDriver(profile);
                break;
            case SAFARI:
                SafariOptions safariOpts = new SafariOptions();
                DesiredCapabilities cap = DesiredCapabilities.safari();
                cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "dismiss");
                cap.setCapability(SafariOptions.CAPABILITY, safariOpts);
                cap.setBrowserName("safari");
                cap.setPlatform(Platform.MAC);
                driver = new SafariDriver(cap);
                break;
            case GOOGLECHROME:

            case MAC:
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-notifications");
                //if (browserName.equals(BrowserType.MAC)) ;
                // options.setBinary(macBinary);
                driver = new ChromeDriver(options);
                break;
            case IEXPLORE:
                driver = new InternetExplorerDriver();
                break;

            default:
                throw new UnsupportedOperationException("Browser " + browserName.getType() + " not supported.");

        }
    }

    /**
     * Executed after any tests declared inside a TestNG suite are run
     *
     * @throws java.lang.Exception
     */
    @AfterSuite(alwaysRun = true)
    public void afterSuite() throws WebDriverException {
        if (driver != null) {
            driver.quit();
            if (driver != null)
                driver = null;
        }
    }

    /**
     * Run after executing each test method.
     *
     * @param result interface represents the result of a test
     * @throws java.lang.Exception
     */

    @AfterMethod(alwaysRun = true)
    public void afterTestMethod(ITestResult result) throws FileNotFoundException {
        if (!result.isSuccess()) {
            try {
                File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String failureImageFileName = result.getMethod().getMethodName() + getFormattedDate() + ".png";
                String destFile = screenShotPath + "/" + failureImageFileName;
                FileUtils.copyFile(imageFile, new File(destFile));
                ATUReports.add(result.getMethod().getMethodName(), LogAs.FAILED, new CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get the today's date
     *
     * @return the formated date string
     */
    private String getFormattedDate() {
        return new SimpleDateFormat("MM-dd-yyyy_HH-ss").format(new GregorianCalendar().getTime());
    }

    /**
     * Launch Cortex web Client depending on the test environment
     *
     * @param host web client host de, demo or localhost
     * @return returns the Login page
     */
    public AssetManager launchAssetManager(String host) throws Exception {

        if (host.indexOf("dev") >= 0 || host.indexOf("demo") >= 0) {
            if (!host.startsWith("https"))
                driver.get("https://" + host + ".com");
            else
                driver.get(host + "com");
        } else {
            if (!host.startsWith("http"))
                driver.get("http://" + host + ":3000");
            else
                driver.navigate().to(host);
        }
        return new AssetManager();
    }


    enum BrowserType {

        FIREFOX("firefox"),
        GOOGLECHROME("googlechrome"),
        SAFARI("safari"),
        IEXPLORE("iexplore"),
        MAC("mac"),
        ANDROID("android"),
        HTMLUNIT("htmlunit"),
        IPHONE("iPhone"),
        IPAD("iPad");

        private String type;

        BrowserType(String type) {

            this.type = type;
        }

        public String getType() {
            return type;
        }


        public static BrowserType getBrowserType(String type) {

            for (BrowserType t : BrowserType.values()) {
                if (t.getType().equals(type))
                    return t;
            }
            return null;
        }
    }


}
