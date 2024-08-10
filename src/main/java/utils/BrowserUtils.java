package utils;

import io.cucumber.java.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Base64;
import java.util.Calendar;
import java.util.Set;

public class BrowserUtils {

    final WebDriver driver;
    public ElementWaits Waits;

    public BrowserUtils(WebDriver driver){
        this.driver = driver;
        Waits = new ElementWaits();
    }

    public void switchToNextWindow() {
        Set<String> set = driver.getWindowHandles();
        for (String each: set){
            if (!each.equals(driver.getWindowHandle()))
                driver.switchTo().window(each);
        }
    }

    public void moveIntoViewWithJsExecutor(WebElement element){
        JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true)", element);
    }

    public WebElement highlightElement(WebElement element){
        moveIntoViewWithJsExecutor(element);
        JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
        for (int i = 0; i < 4; i++){
            if (i % 2 == 0){
                sleep(500);
                jsExecutor.executeScript("arguments[0].setAttribute('style','border: solid 2px red');", element);
                jsExecutor.executeScript("arguments[0].style.backgroundColor='yellow'", element);
            }else {
                sleep(500);
                jsExecutor.executeScript("arguments[0].setAttribute('style','border: none');", element);
                jsExecutor.executeScript("arguments[0].style.backgroundColor=null", element);
            }
        }
        return element;
    }

    public void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void clickWithJs(WebElement element){
        JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
        jsExecutor.executeScript("arguments[0].click;", element);
    }

    public class ElementWaits {
        public void waitForPageToLoad() {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                }
            });
        }

        public void waitForTitleToContain(String expectedResult){
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.titleContains(expectedResult));
        }

        public void waitForURLToContainText(String url){
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlContains(url));
        }

        public void waitNumberOfElementsToBeMoreThan(By by, int numberOfElements){
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(by, numberOfElements));
        }

        public void waitForElementToBeVisible(WebElement element){
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(element));
        }

        public WebElement waitForElementToBeVisibleWhileScrolling(WebElement element, int scrollAmount) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            return wait.until(new ExpectedCondition<WebElement>() {
                public WebElement apply(WebDriver driver) {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    long endTime = System.currentTimeMillis() + 10 * 1000;

                    while (System.currentTimeMillis() < endTime) {
                        if (element.isDisplayed()) {
                            return element;
                        } else {
                            js.executeScript("window.scrollBy(0, arguments[0]);", scrollAmount);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    return null;
                }
            });
        }

        public WebElement waitForElementToBeClickable(WebElement element) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            return wait.until(new ExpectedCondition<WebElement>() {
                public WebElement apply(WebDriver driver) {
                    if (element != null && element.isDisplayed() && element.isEnabled()) {
                        return element;
                    } else {
                        return null;
                    }
                }
            });
        }

        public boolean waitForElementToBeClickableWhileScrolling(WebElement element, int scrollAmount) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            return wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    long endTime = System.currentTimeMillis() + 10 * 1000;

                    while (System.currentTimeMillis() < endTime) {
                        try {
                            if (element.isDisplayed() && element.isEnabled()) {
                                element.click();
                                return true;
                            } else {
                                js.executeScript("window.scrollBy(0, arguments[0]);", scrollAmount);
                                Thread.sleep(500);
                            }
                        } catch (ElementNotInteractableException e) {
                            js.executeScript("window.scrollBy(0, arguments[0]);", scrollAmount);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    return false;
                }
            });
        }

        public void waitForAlertToDisplay(){
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.alertIsPresent());
        }
    }

    public String getLogTime(){
        String format = "yyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }

    public void logFailScreenshot(Scenario scenario){
        final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        // Encode the screenshot as a Base64 string
        String base64Screenshot = Base64.getEncoder().encodeToString(screenshot);

        // Create HTML content to display the screenshot with a smaller size
        String htmlContent = "<div style=\"display: flex; justify-content: center;\">" +
                "<img src=\"data:image/png;base64," + base64Screenshot + "\" " +
                "style=\"width: 900px; height: auto; cursor: pointer;\" " +
                "onclick=\"showImage(this.src)\" />" +
                "</div>" +
                "<div id=\"modal\" style=\"display:none; position:fixed; z-index:1; padding-top:10px; left:0; top:0; width:100%; height:100%; overflow:auto; background-color:rgb(0,0,0); background-color:rgba(0,0,0,0.9);\">" +
                "<span style=\"position:absolute; top:20px; right:45px; color:#f1f1f1; font-size:40px; font-weight:bold; cursor:pointer;\" onclick=\"closeModal()\">&times;</span>" +
                "<img style=\"margin:auto; display:block; width:80%; max-width:1200px;\" id=\"modal-image\">" +
                "</div>" +
                "<script>" +
                "function showImage(src) {" +
                "  var modal = document.getElementById('modal');" +
                "  var modalImg = document.getElementById('modal-image');" +
                "  modal.style.display = 'block';" +
                "  modalImg.src = src;" +
                "}" +
                "function closeModal() {" +
                "  var modal = document.getElementById('modal');" +
                "  modal.style.display = 'none';" +
                "}" +
                "</script>";

        // Attach the HTML content to the scenario
        scenario.attach(htmlContent.getBytes(), "text/html", getLogTime() + "  FAIL: " + scenario.getName());
    }

}