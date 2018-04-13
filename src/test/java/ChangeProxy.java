/**
 * 
 */


import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author Rajesh.Kumar4
 *
 */
public class ChangeProxy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String DriverPath = "C:\\Users\\rajesh.kumar41\\eclipse-workspace\\fabHotel\\lib\\";
		Proxy p = new Proxy();
		//p.setHttpProxy("127.0.0.1:8088");
		p.setProxyType(Proxy.ProxyType.AUTODETECT);
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.PROXY, p);
		System.setProperty("webdriver.gecko.driver", DriverPath+"geckodriver.exe");
		@SuppressWarnings("deprecation")
		WebDriver d = new FirefoxDriver(cap);
		d.get("http://www.google.com");

	}

}
