package variousConcept;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CrmTest {
	

	String browser= null;
	String url;
	WebDriver driver;
	
	//ELEMENT LIST
	By USERNAME_LOCATOR = By.xpath("//input[@id='username']");
	By PASSWORD_LOCATOR = By.xpath("//*[@id=\"password\"]");
	By SIGNIN_BUTTON_LOCATOR = By.xpath("/html/body/div/div/div/form/div[3]/button");
	By DASHBOARD_HEADER_LOCATOR = By.xpath("//h2[contains(text(), 'Dashboard')]");
//	By ORDER_MENU_LOCATOR= By.xpath("//*[@id=\"side-menu\"]li[7]/ul/li[2]/a");
//	By ADD_ORDER_MENU_LOCATOR = By.xpath("//*[@id=\"side-menu\"]/li[7]/ul/li[2]/a");
	By CUSTOMER_MENU_LOCATOR= By.xpath("//*[@id=\"side-menu\"]/li[3]/a/span[1]");
	By ADD_CUSTOMER_MENU_LOCATOR = By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a");
//  By PRODUCT_SERVICE_LOCATOR = By.xpath("//select[@id=\"pid\"]");
	By ADD_CONTACT_HEADER_LOCATOR = By.xpath("//*[@id=\"page-wrapper\"]/div[3]/div[1]/div/div/div/div[1]/h5");
	By FULL_NAME_LOCATOR = By.xpath("//*[@id=\"account\"]");
	By COMPANY_DROPDOWN_LOCATOR = By.xpath("//select[@id=\"cid\"]");
	By EMAIL_LOCATOR = By.xpath("//*[@id=\"email\"]");
	By COUNTRY_LOCATOR = By.xpath("//*[@id=\"rform\"]/div[1]/div[1]/div[9]");
	
	
	@BeforeTest
	public void  readConfig() {
		//fileReader//Scanner//InputStream//BufferedReader
		
		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used = " + browser);
		    url = prop.getProperty("url");
			
		}catch(IOException e) {
			e.printStackTrace();
		
		}
	}

	@BeforeMethod
	public void init() {
		if (browser.equalsIgnoreCase("chrome")) {
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
		driver = new ChromeDriver();
		
	} else if (browser.equalsIgnoreCase("firefox")) {
		System.setProperty("webdriver.gecko.driver", "driver/geckodriver.exe");
		driver = new FirefoxDriver();
		}
		
        driver.manage().deleteAllCookies();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	//@Test(priority = 1)
	public void loginTest() {

		driver.findElement(USERNAME_LOCATOR).sendKeys("demo@techfios.com");
		driver.findElement(PASSWORD_LOCATOR).sendKeys("abc123");
		driver.findElement(SIGNIN_BUTTON_LOCATOR).click();

		String dashboardHeaderText = driver.findElement(DASHBOARD_HEADER_LOCATOR).getText();
		
		System.out.println(dashboardHeaderText);
		Assert.assertEquals(dashboardHeaderText, "Dashboard", "Wrong Page!!");
		
	}
	@Test(priority = 2)
	public void addCustomerTest() {
		loginTest();
		driver.findElement(CUSTOMER_MENU_LOCATOR).click();
		driver.findElement(ADD_CUSTOMER_MENU_LOCATOR).click();
		
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ADD_CONTACT_HEADER_LOCATOR));
		
		Assert.assertEquals(driver.findElement(ADD_CONTACT_HEADER_LOCATOR).getText(),"Add Contact", "Wrong page!!");
		
		driver.findElement(FULL_NAME_LOCATOR).sendKeys("Selenium October" + generatedRandom(9999));
		
		selectFromDropdown(COMPANY_DROPDOWN_LOCATOR, "Techfios");
		
		driver.findElement(EMAIL_LOCATOR).sendKeys( generatedRandom(9999)+ "demo@techfios.com");
		
		selectFromDropdown(COUNTRY_LOCATOR, "Nepal");
		
	}
	

	public void selectFromDropdown(By locator, String visibleText) {
		Select sel = new Select(driver.findElement(locator));
		sel.selectByVisibleText("Techfios");
	}

	public int generatedRandom(int bounderyNum) {
		Random rnd = new Random();
		int generatedNum = rnd.nextInt(bounderyNum);
		return generatedNum;
	
	}

	//@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}
