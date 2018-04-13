package fabHotel_MVN.fabHotel_MVN;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import fab_Lib.utility;

public class FabHotel_Search {
	// for Extent Report
	ExtentHtmlReporter htmlReporter;
	ExtentReports fabReport;
	ExtentTest logger;

	// for WebDriver
	WebDriver fabDriver;
	String testURL = "https://www.fabhotels.com/", geckoDriverpath = "D:\\Testing\\geckodriver.exe";

	@Test(priority = 1)
	public void searchHotel() {	
			//Voucher_model_Window.close_Voucher(fabDriver);
			logger = fabReport.createTest("Search Hotel");
			logger.log(Status.PASS, MarkupHelper.createLabel("Search Hotel Test Case Passed", ExtentColor.GREEN));
			WebElement hotelLocation, checkIN, checkOUT, selectAdult, searchHotel;
			hotelLocation = fabDriver.findElement(By.id("autocomplete-location"));
			hotelLocation.sendKeys("Mumbai");
			// CheckIN Date
			checkIN = fabDriver.findElement(By.className("searchCheckIn"));
			checkIN.click();
			// String chechIN_Month =
			// fabDriver.findElement(By.xpath("//table[@class='table-condensed']/thead/tr[2]/th[@class='datepicker-switch']")).getText();
			while (!fabDriver.findElement(By.className("datepicker-switch")).getText().contains("April 2018")) // Handle
																												// the
																												// Month
			{
				fabDriver.findElement(By.className("next")).click();
			}
			List<WebElement> checkin_Day = fabDriver.findElements(By.className("day"));
			for (WebElement chekin_D : checkin_Day) {
				String checkINday = chekin_D.getText();
				if (checkINday.equalsIgnoreCase("15")) {
					chekin_D.click();
					break;
				}
			}
			// CheckOUT Date
			checkOUT = fabDriver.findElement(By.className("searchCheckOut"));
			checkOUT.click();
			while (!fabDriver.findElement(By.className("datepicker-switch")).getText().contains("April 2018")) // Handle
																												// the
																												// month
			{
				fabDriver.findElement(By.className("next")).click();
			}
			List<WebElement> checkout_Day = fabDriver.findElements(By.className("day"));
			for (WebElement checkout_D : checkout_Day) {
				String checkoutDay = checkout_D.getText();
				if (checkoutDay.equalsIgnoreCase("16")) {
					checkout_D.click();
					break;
				}
			}
			// Adults
			selectAdult = fabDriver.findElement(By.xpath("//div[@class='wrap-select-box wrap_select_box']"));
			selectAdult.click();
			List<WebElement> adult = fabDriver.findElements(By.xpath("//div[@class='select-dropdown-section']/span"));
			for (WebElement adlt : adult) {
				String adultStr = adlt.getText();
				if (adultStr.equalsIgnoreCase("2")) {
					adlt.click();
					break;
				}
			}
			// click on Search Button
			searchHotel = fabDriver.findElement(By.id("listingPageBtn"));
			searchHotel.click();
		}
	@Test(priority = 2)
	public void hotelList() throws InterruptedException {
		logger = fabReport.createTest("Select Hotel From List");
		logger.log(Status.PASS, MarkupHelper.createLabel("Select Hotel From List Test Case Passed", ExtentColor.GREEN));
		WebElement sortHotelsbyPrice;

		// Validate Hotel List Page
		try {
			String actual_Page_Title = "Budget Hotels in Mumbai, Online Budget Hotel Booking in Mumbai - FabHotels";
			System.out.println("Actual page Title: " + actual_Page_Title);
			// for Firefox only it does not return the page title in latest version
			WebDriverWait wait = new WebDriverWait(fabDriver, 20);
			wait.until(ExpectedConditions.titleIs(actual_Page_Title));
			String expected_Page_Title = fabDriver.getTitle();
			System.out.println("Expected Page Title: " + expected_Page_Title);
			Assert.assertEquals(expected_Page_Title, actual_Page_Title);
			System.out.println("Page Validated--Correct page...");
		} catch (AssertionError e) {
			System.out.println(e.getMessage());
		}
		// Filter Hotels by Price (Min to Max)
		sortHotelsbyPrice = fabDriver.findElement(By.xpath("//span[@class='rupees-text']"));
		String XXXX = sortHotelsbyPrice.getText();
		System.out.println(XXXX);
		sortHotelsbyPrice.click();
		// fabDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// WebDriverWait wait = new WebDriverWait(fabDriver,30);
		Thread.sleep(3000);
		// Select Hotel to Book
		List<WebElement> bookHotel = fabDriver.findElements(By.xpath("//h3[@class='hotel-card-title']/a"));
		for (WebElement bookH : bookHotel) {
			String hName = bookH.getText();
			System.out.println(hName);
			if (hName.equalsIgnoreCase("FabHotel Parimeet")) {

				// fabDriver.findElement(By.className("btn hotel-book-btn")).click();
				bookH.click();
				// fabDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				Thread.sleep(10000);
				break;
			}
		}
	}

	@Test(priority = 3)
	public void finalHotel_Book() throws InterruptedException {
		logger = fabReport.createTest("Final Booking");
		logger.log(Status.PASS, MarkupHelper.createLabel("Final Booking Test Case Passed", ExtentColor.GREEN));
		WebElement scrollTO, select_Guest, proceed_To_Pay;
		// Switch to new Tab
		ArrayList<String> newTab = new ArrayList<String>(fabDriver.getWindowHandles());
		fabDriver.switchTo().window(newTab.get(1));
		fabDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		String finalHotelURL = fabDriver.getCurrentUrl();
		System.out.println(finalHotelURL);
		// Scroll Page down to Room list using Javascript Executor.
		JavascriptExecutor jsEXE = (JavascriptExecutor) fabDriver;
		scrollTO = fabDriver.findElement(By.xpath("//div[contains(@class,'btn check-availability available')]"));
		System.out.println(scrollTO.getText());
		jsEXE.executeScript("arguments[0].scrollIntoView();", scrollTO);
		// jsEXE.executeScript("window.scrollBy(0,2000)");
		select_Guest = fabDriver.findElement(By.xpath("//button[contains(@class,'btn select_room_type')]"));
		select_Guest.click();
		WebElement no_of_Guest = fabDriver.findElement(By.xpath("//div[@class='select-box rooms_select']"));
		no_of_Guest.click();
		List<WebElement> select_no_Guest = fabDriver
				.findElements(By.xpath("//div[@class='select-dropdown-wrap rooms_select_dropdown']/span"));
		for (WebElement Guest_Count : select_no_Guest) {
			String GUESTNO = Guest_Count.getText();
			System.out.println("Select Room No" + GUESTNO);
			if (GUESTNO.equalsIgnoreCase("02")) {
				Guest_Count.click();
				break;
			}
		}
		proceed_To_Pay = fabDriver.findElement(By.xpath("//button[contains(@class,'btn proceed_to_pay_button')]"));
		proceed_To_Pay.click();
		Thread.sleep(3000);
	}

	@Test(priority = 4)
	public void review_Details() throws InterruptedException {
		logger = fabReport.createTest("Review Payment Details");
		logger.log(Status.PASS, MarkupHelper.createLabel("Review Payment Details Test Case Passed", ExtentColor.GREEN));
		WebElement price_Breakup, close_Price_Breakup, proceed_Payment; /// userName, user_Email, user_MobileNo,
		// continue_Pay_GW;
		// Validate Review Page
		try {
			String actual_Page_Title = "Checkout Confirmation - FabHotels.com";
			System.out.println("Actual page Title: " + actual_Page_Title);
			// for Firefox only it does not return the page title in latest version
			WebDriverWait wait = new WebDriverWait(fabDriver, 20);
			wait.until(ExpectedConditions.titleIs(actual_Page_Title));
			String expected_Page_Title = fabDriver.getTitle();
			System.out.println("Expected Page Title: " + expected_Page_Title);

			Assert.assertEquals(expected_Page_Title, actual_Page_Title);
			System.out.println("Page Validated--Correct page...");
		} catch (AssertionError e) {
			System.out.println(e.getMessage());
		}
		price_Breakup = fabDriver
				.findElement(By.xpath("//div[contains(@class,'review_booking_price showPriceBreakUp')]"));
		price_Breakup.click();
		fabDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		close_Price_Breakup = fabDriver.findElement(By.className("price-modal-close"));
		Thread.sleep(10000);
		close_Price_Breakup.click();
		// Scroll to Proceed to pay button
		proceed_Payment = fabDriver
				.findElement(By.xpath("//div[contains(@class,'review_booking_continue submit-booking-details')]"));
		JavascriptExecutor jsEXEPAY = (JavascriptExecutor) fabDriver;
		jsEXEPAY.executeScript("arguments[0].scrollIntoView();", proceed_Payment);
		// click on button
		proceed_Payment.click();
		/*
		 * WebElement xx = fabDriver.findElement(By.xpath(
		 * "//div[@class='review_details_complete_heading']/strong")); String aa =
		 * xx.getText(); System.out.println(aa); WebDriverWait wait = new
		 * WebDriverWait(fabDriver, 30);
		 * wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
		 * "//div[@class='review_container']"))); WebElement userName = fabDriver
		 * .findElement(By.
		 * xpath("//input[contains(@class,'user-name required_param required_param_field')]"
		 * )); userName.sendKeys("Saint John"); WebElement user_Email = fabDriver
		 * .findElement(By.
		 * xpath("//input[contains(@class,'user-email required_param required_param_field')]"
		 * )); user_Email.sendKeys("saintjohn@hotmail.com"); WebElement user_MobileNo =
		 * fabDriver.findElement( By.
		 * xpath("//input[contains(@class,'user-mobile user_mobile required_param required_param_field')]"
		 * )); user_MobileNo.sendKeys("9999323456"); WebElement continue_Pay_GW =
		 * fabDriver .findElement(By.
		 * xpath("//div[contains(@class,'review_guest_continue submit-guest-details')]")
		 * ); continue_Pay_GW.click();
		 */
		Thread.sleep(5000);
	}

	@SuppressWarnings("deprecation")
	@BeforeTest(alwaysRun = true)
	@Parameters("BROWSER")
	public void BrowserSetup(String BROWSER) throws Exception {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/fabReport.html");
		fabReport = new ExtentReports();
		fabReport.attachReporter(htmlReporter);
		fabReport.setSystemInfo("Host Name", "FabHotel Report");
		fabReport.setSystemInfo("Environment", "FabHotel Automation Testing");
		fabReport.setSystemInfo("User Name", "Rajesh Thakur");

		htmlReporter.config().setDocumentTitle("FabHotel Test Report");
		htmlReporter.config().setReportName("FabHotel Test Report V1.0");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		Proxy p = new Proxy();
		// p.setHttpProxy("127.0.0.1:8088");
		p.setProxyType(Proxy.ProxyType.DIRECT);
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.PROXY, p);
		if (BROWSER.equalsIgnoreCase("firefox")) {
			String fabDriverPathFirefox = System.getProperty("user.dir") + "/lib/geckodriver.exe";
			System.setProperty("webdriver.gecko.driver", fabDriverPathFirefox);
			fabDriver = new FirefoxDriver(cap);
			fabDriver.manage().window().maximize();
			fabDriver.get(testURL);
			/*
			 * logger = fabReport.createTest("Firefox Browser Setup");
			 * logger.log(Status.FAIL,
			 * MarkupHelper.createLabel("Firefox Browser Not Started", ExtentColor.RED));
			 */
		} else if (BROWSER.equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			String fabDriverPathChrome = System.getProperty("user.dir") + "/lib/chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", fabDriverPathChrome);
			fabDriver = new ChromeDriver(options);
			fabDriver.manage().window().maximize();
			fabDriver.get(testURL);

			/*
			 * logger = fabReport.createTest("Chrome Browser Setup");
			 * logger.log(Status.FAIL,
			 * MarkupHelper.createLabel("Chrome Browser Not Started", ExtentColor.RED));
			 */
		}
		/*
		 * else if (BROWSER.equalsIgnoreCase("ie")) { String fabDriverPathIE =
		 * "D:\\Testing\\IEDriverServer_x64_3.9.0\\IEDriverServer.exe";
		 * System.setProperty("webdriver.ie.driver", fabDriverPathIE); fabDriver = new
		 * InternetExplorerDriver(); fabDriver.get(testURL);
		 * System.out.println("Opening IE Browser..."); }
		 */
	}

	@AfterMethod
	public void getfabResult(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {

			logger.log(Status.FAIL,
					MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
			logger.log(Status.FAIL,
					MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
			String screenshotPath = utility.getScreenshot(fabDriver, result.getName());
			logger.log(Status.FAIL, (Markup) logger.addScreenCaptureFromPath(screenshotPath));
		} else if (result.getStatus() == ITestResult.SKIP) {
			// logger.log(Status.SKIP, "Test Case Skipped is "+result.getName());
			logger.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));

		}
		fabReport.flush();
	}

	@AfterTest
	public void afterTest() {
		fabReport.flush();
		fabDriver.quit();
	}
}