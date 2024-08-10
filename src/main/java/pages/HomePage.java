package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage {
    public HomePage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//textarea[@title = 'Search']")
    public WebElement searchField;

    @FindBy(tagName = "h3")
    public List<WebElement> searchResults;

    public boolean containsWordInResults(String word) {
        for (WebElement result : searchResults) {
            if (result.getText().toLowerCase().contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
