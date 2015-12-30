import org.junit.Assert;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

import static org.junit.Assert.fail;

public class WebDrive
{
    private static Logger log = Logger.getLogger(WebDrive.class);
    private WebDriver driver;
    private static final String FACEBOOK_URL = "https://www.facebook.com/";
    private static final String N11_URL = "http://www.n11.com/";
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception
    {
        driver = new FirefoxDriver();
    }

    @Test
    public void testLogin() throws Exception
    {
        // Selenium Web Driver For Login
        driver.get(FACEBOOK_URL);
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("donmez.elif.c@gmail.com");
        driver.findElement(By.id("pass")).clear();
        driver.findElement(By.id("pass")).sendKeys("berker26bilgi");
        driver.findElement(By.id("u_0_v")).click();
        driver.get(N11_URL);
        driver.findElement(By.linkText("Giriş Yap")).click();
        driver.findElement(By.xpath("//form[@id='loginForm']/div[4]")).click();
        log.debug("Login Success");
    }

    @Test
    public void testAuthors() throws Exception
    {
        // Finding all authors and looping
        driver.get(N11_URL);
        driver.findElement(By.partialLinkText("Kitap")).click();
        driver.findElement(By.linkText("Kitap")).click();
        driver.findElement(By.partialLinkText("Tüm Liste")).click();
        // Gets all alphebets
        List<WebElement> allAlphabets = driver.findElements(By.xpath("//*[@id='brandsPaging']/div/span"));

        for (WebElement alphabets : allAlphabets)
        {
            String alphabet = alphabets.getText();
            if (!alphabet.equals("0-9"))
            {
                List<WebElement> allAuthors = driver.findElements(By.xpath("//div[@id='books']/ul/li/" + alphabet));
                for (WebElement author : allAuthors)
                {
                    if (author.getText().startsWith(alphabet))
                    {
                        Assert.assertTrue(true);
                        log.debug("Authors name starts with" + alphabet);
                    }
                    else
                    {
                        Assert.assertTrue(false);
                        log.debug("Authors name dont starts with" + alphabet);
                    }
                }
            }
        }
    }

    @After
    public void tearDown() throws Exception
    {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString))
        {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by)
    {
        try
        {
            driver.findElement(by);
            return true;
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    private boolean isAlertPresent()
    {
        try
        {
            driver.switchTo().alert();
            return true;
        }
        catch (NoAlertPresentException e)
        {
            return false;
        }
    }

    private String closeAlertAndGetItsText()
    {
        try
        {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert)
            {
                alert.accept();
            }
            else
            {
                alert.dismiss();
            }
            return alertText;
        }
        finally
        {
            acceptNextAlert = true;
        }
    }
}
