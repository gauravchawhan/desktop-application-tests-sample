import org.openqa.selenium.WebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TimeChanger
{
    RemoteWebDriver driver = null;
    public static String status = "passed";
    public static String username = System.getenv("LT_USERNAME");
    public static String access_key = System.getenv("LT_ACCESS_KEY");

    String testURL = "https://dayspedia.com/time/online/";

    @BeforeMethod
    @Parameters(value={"browser","version","platform", "resolution"})
    public void testSetUp(String browser, String version, String platform, String resolution) throws Exception
    {
        String platformName = System.getenv("HYPEREXECUTE_PLATFORM") != null ? System.getenv("HYPEREXECUTE_PLATFORM") : platform;
        
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("build", "Sample");
        capabilities.setCapability("platform", System.getenv("HYPEREXECUTE_PLATFORM"));
        capabilities.setCapability("browserName", browser);
        capabilities.setCapability("version", version);
        capabilities.setCapability("network",true);
        capabilities.setCapability("console",true);
        capabilities.setCapability("visual",true);
        capabilities.setCapability("video",false);

        try
        {
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + access_key + "@hub.lambdatest.com/wd/hub"), capabilities);
        }
        catch (MalformedURLException e)
        {
            System.out.println("Invalid grid URL");
        }
        System.out.println("Started session");
    }

    @Test(description="To Do App on React App")
    public void test1_element_addition_1() throws Exception
    {

        Thread.sleep(5000);
        driver.get(testURL);
        Thread.sleep(5000);
        updateTimeToIST();
        Thread.sleep(5000);
        driver.get(testURL);
        Thread.sleep(5000);
        /* Once you are outside this code, the list would be empty */
    }

    public void updateTimeToIST() throws Exception {
    try {
            // Command to update system time to IST
            String command = "cmd /c update_system_time.bat";

            // Execute the batch script
            Process process = Runtime.getRuntime().exec(command);

            // Get the output of the command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("System time updated to IST successfully.");
            } else {
                System.err.println("Failed to update system time to IST.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void tearDown()
    {
        if (driver != null)
        {
            ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
            driver.quit();
        }
    }
}
