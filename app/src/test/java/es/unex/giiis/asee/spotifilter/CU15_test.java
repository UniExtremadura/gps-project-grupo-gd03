package es.unex.giiis.asee.spotifilter;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class CU15_test {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    System.setProperty("webdriver.chrome.driver", "C:\\Users\\manur\\chromedriver\\chromedriver.exe");
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void cU16test() {
    driver.get("http://localhost:8000/");
    driver.manage().window().setSize(new Dimension(945, 1012));
    driver.findElement(By.linkText("Contacta con nosotros")).click();
    driver.findElement(By.linkText("^")).click();
    {
      List<WebElement> elements = driver.findElements(By.id("logo"));
      assert(elements.size() > 0);
    }
    assertThat(driver.findElement(By.cssSelector("#mailUs > h2")).getText(), is("Contacta con nosotros"));
    assertThat(driver.findElement(By.cssSelector("h4:nth-child(3)")).getText(), is("O bien contáctanos en nuestras redes sociales:"));
    {
      List<WebElement> elements = driver.findElements(By.id("correo"));
      assert(elements.size() > 0);
    }
    assertThat(driver.findElement(By.id("correo")).getText(), is("Envia un correo al equipo de desarrollo"));
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".redes:nth-child(4) > img"));
      assert(elements.size() > 0);
    }
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".redes:nth-child(5) > img"));
      assert(elements.size() > 0);
    }
  }
}