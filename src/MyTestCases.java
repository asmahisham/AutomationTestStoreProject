import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MyTestCases {

	WebDriver driver = new ChromeDriver();

	String myWebsite = "https://automationteststore.com/";

	String[] firstNames = { "mohammad", "ola", "khalid", "yasmine", "ayat", "alaa", "waleed", "Rama" };
	String[] lastNames = { "yaser", "mustafa", "Mohammad", "abdullah", "sami", "omar" };

	Random rand = new Random();

	String globalUserFirstName = "";

	String globalUsernameForTheLogin = "";

	String Password = "ilovemyMom1233!@#";

	@BeforeTest
	public void SetUp() {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		driver.get(myWebsite);

	}

	@Test(priority = 1, enabled = false)
	public void SignUp() throws InterruptedException {

		int RandomIndexForFirstName = rand.nextInt(firstNames.length);
		int RandomIndexForLastName = rand.nextInt(lastNames.length);

		String userFirstName = firstNames[RandomIndexForFirstName];
		String userLastName = lastNames[RandomIndexForLastName];

		int RandomNumForEmail = rand.nextInt(657432);

		String domainName = "@gmail.com";

		String email = userFirstName + userLastName + RandomNumForEmail + domainName;

		driver.findElement(By.linkText("Login or register")).click();

		WebElement SignUpButton = driver.findElement(By.xpath("//button[@title='Continue']"));

		SignUpButton.click();

		Thread.sleep(2000);

		WebElement firstName = driver.findElement(By.id("AccountFrm_firstname"));
		firstName.sendKeys(userFirstName);

		globalUserFirstName = userFirstName;

		WebElement lastName = driver.findElement(By.id("AccountFrm_lastname"));
		lastName.sendKeys(userLastName);

		WebElement emailInput = driver.findElement(By.id("AccountFrm_email"));
		emailInput.sendKeys(email);

		WebElement AdressInput = driver.findElement(By.id("AccountFrm_address_1"));
		AdressInput.sendKeys("amman city - tlaa al ali");
		WebElement CityInput = driver.findElement(By.id("AccountFrm_city"));
		CityInput.sendKeys("capital city");

		WebElement CountryInput = driver.findElement(By.id("AccountFrm_country_id"));

		Select selectorCountry = new Select(CountryInput);

		int randomCountry = rand.nextInt(1, 240);

		selectorCountry.selectByIndex(randomCountry);

		Thread.sleep(3000);

		WebElement ZoneIdInput = driver.findElement(By.id("AccountFrm_zone_id"));
		Select selectorZone = new Select(ZoneIdInput);
		int randomState = rand.nextInt(1, 6);

		selectorZone.selectByIndex(randomState);

		WebElement PostalCodeInput = driver.findElement(By.id("AccountFrm_postcode"));
		PostalCodeInput.sendKeys("13310");
		WebElement LoginNameInput = driver.findElement(By.id("AccountFrm_loginname"));

		String LocalUsername = userFirstName + userLastName + RandomNumForEmail;
		globalUsernameForTheLogin = LocalUsername;

		LoginNameInput.sendKeys(LocalUsername);

		WebElement password = driver.findElement(By.id("AccountFrm_password"));
		password.sendKeys(Password);

		WebElement confirmPassword = driver.findElement(By.id("AccountFrm_confirm"));
		confirmPassword.sendKeys(Password);

		WebElement AgreeCheckBox = driver.findElement(By.id("AccountFrm_agree"));
		AgreeCheckBox.click();

		WebElement createAnAccountButton = driver.findElement(By.xpath("//button[@title='Continue']"));
		Thread.sleep(3000);

		createAnAccountButton.click();
	}

	@Test(priority = 2, enabled = false)
	public void LogOut() throws InterruptedException {

		Thread.sleep(2000);
		WebElement userNav = driver.findElement(By.id("customernav"));
		Actions actions = new Actions(driver);
		actions.moveToElement(userNav).perform();

		Thread.sleep(2000);

		driver.findElement(By.linkText("Not " + globalUserFirstName + "? Logoff")).click();

		// actions.click().build().perform();

	}

	@Test(priority = 3, enabled = false)
	public void LogIn() {

		driver.findElement(By.linkText("Login or register")).click();

		WebElement userLogin = driver.findElement(By.id("loginFrm_loginname"));
		userLogin.sendKeys(globalUsernameForTheLogin);

		WebElement userPass = driver.findElement(By.id("loginFrm_password"));
		userPass.sendKeys(Password);

		WebElement LoginButton = driver.findElement(By.xpath("//button[@title='Login']"));
		LoginButton.click();

	}

	@Test(priority = 4)
	public void AddItemToTheCart() throws InterruptedException {

		String[] websitesForTheItems = { "https://automationteststore.com/index.php?rt=product/category&path=68",
				"https://automationteststore.com/index.php?rt=product/category&path=36",
				"https://automationteststore.com/index.php?rt=product/category&path=43",
				"https://automationteststore.com/index.php?rt=product/category&path=49",
				"https://automationteststore.com/index.php?rt=product/category&path=58",
				"https://automationteststore.com/index.php?rt=product/category&path=52",
				"https://automationteststore.com/index.php?rt=product/category&path=65", };

		int randomIndexForItem = rand.nextInt(websitesForTheItems.length);

		driver.get(websitesForTheItems[randomIndexForItem]);

		WebElement ListOfItems = driver.findElement(By.cssSelector(".thumbnails.row"));
		int totalNumOfItems = ListOfItems.findElements(By.tagName("li")).size();
		int RandomIndexForItem = rand.nextInt(totalNumOfItems);

		Thread.sleep(3000);

		ListOfItems.findElements(By.tagName("li")).get(RandomIndexForItem).click();

		WebElement Container = driver.findElement(By.cssSelector(".thumbnails.grid.row.list-inline"));
		int numberOfProducts = Container.findElements(By.cssSelector(".col-md-3.col-sm-6.col-xs-12")).size();
		int RandomIndexForProducts = rand.nextInt(numberOfProducts);

		Thread.sleep(3000);

		Container.findElements(By.cssSelector(".col-md-3.col-sm-6.col-xs-12")).get(RandomIndexForProducts).click();

		WebElement ULList = driver.findElement(By.className("productpagecart"));

		int LiItem = ULList.findElements(By.tagName("li")).get(0).findElements(By.tagName("span")).size();

		if (LiItem > 0) {

			driver.get(myWebsite);

			System.out.println("Sorry, The Item is Out Of Stock ");
			String ExpectedResult = "https://automationteststore.com/";
			String ActualResult = driver.getCurrentUrl();
			Assert.assertEquals(ActualResult, ExpectedResult, "expected != actual");

		} else {

			driver.findElement(By.className("cart")).click();

			Thread.sleep(2000);
			String ActualResult = driver.findElement(By.className("heading1")).getText();
			String ExpectedResult = "Shopping Cart";

			Assert.assertEquals(ActualResult, ExpectedResult.toUpperCase());
			boolean ExpectedValueForCheckOut = true;
			boolean ActualValueForCheckOut = driver.findElement(By.id("cart_checkout1")).isDisplayed();
			Assert.assertEquals(ActualValueForCheckOut, ExpectedValueForCheckOut, "expected != actual");

		}

	}

}
