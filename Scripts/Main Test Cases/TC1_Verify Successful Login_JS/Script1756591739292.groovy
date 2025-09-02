import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kazurayam.ks.testobject.TestObjectExtension
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

void setTextJS(By locator, String text) {
	println "locator:${locator}"
	println "text:${text}"
	WebDriver driver = DriverFactory.getWebDriver()
	WebElement webElement = driver.findElement(locator)
	((JavascriptExecutor)driver).executeScript('arguments[0].innerHTML = arguments[1];', [webElement, text])
}

WebUI.comment('Story: Login to CURA system')

WebUI.comment('Given that the user has the valid login information')

WebUI.openBrowser(GlobalVariable.G_SiteURL)

WebUI.click(findTestObject('Page_CuraHomepage/btn_MakeAppointment'))

//WebUI.setText(findTestObject('Page_Login/txt_UserName'), Username)

TestObject tObj = findTestObject('Page_Login/txt_UserName')
setTextJS(TestObjectExtension.toBy(tObj), Username)

WebUI.setText(findTestObject('Page_Login/txt_Password'), Password)

WebUI.delay(3)
WebUI.comment('When he logins to CURA system')

WebUI.click(findTestObject('Page_Login/btn_Login'))

WebUI.comment('Then he should be able to login successfully')

landingPage = WebUI.verifyElementPresent(findTestObject('Page_CuraAppointment/div_Appointment'), GlobalVariable.G_Timeout)

WebUI.closeBrowser()
