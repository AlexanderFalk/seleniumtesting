import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTesting {

    private static WebDriver driver;

    @BeforeEach
    void init() {
        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\alexf\\Downloads\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();

        // And now use this to visit Google
        driver.get("http://localhost:3000/");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

    }

    // Thread sleep set to 3 seconds to let the DOM load appropriately.
    @Test
    void testDOMConstructed() throws InterruptedException {
        Thread.sleep(3000);
        int size = driver.findElements(By.xpath("//*[@id=\"tbodycars\"]/tr")).size();
        int expected = 5;
        Assertions.assertEquals(expected, size);
        driver.quit();
    }

    // Thread sleep 3 seconds to load the DOM
    // Thread sleep for 1 second after search to make sure the search is complete
    @Test
    void testWrite2002FilterTextTwoRowsReturn() throws InterruptedException {
        Thread.sleep(3000);
        WebElement element = driver.findElement(By.xpath("//*[@id=\"filter\"]"));
        element.sendKeys("2002");

        Thread.sleep(1);
        int size = driver.findElements(By.xpath("//*[@id=\"tbodycars\"]/tr")).size();
        int expected = 2;
        Assertions.assertEquals(expected, size);
        driver.quit();
    }

    @Test
    void testVerifyCleanFilterOriginalRows() throws InterruptedException {
        Thread.sleep(3000);
        WebElement element = driver.findElement(By.xpath("//*[@id=\"filter\"]"));
        element.sendKeys("2002");

        Thread.sleep(1);
        int size = driver.findElements(By.xpath("//*[@id=\"tbodycars\"]/tr")).size();
        int expected = 2;
        Assertions.assertEquals(expected, size);

        Thread.sleep(1000);
        element.clear();
        // Sends a space, since the clearing wont submit the field again
        element.sendKeys(" ");
        Thread.sleep(1000);
        int oldSize = driver.findElements(By.xpath("//*[@id=\"tbodycars\"]/tr")).size();
        int oldExpected = 5;
        Assertions.assertEquals(oldExpected, oldSize);
        driver.quit();
    }

    @Test
    void testSortOfYearByClickEvent() throws InterruptedException {
        Thread.sleep(3000);
        WebElement yearElement = driver.findElement(By.xpath("//*[@id=\"h_year\"]"));
        yearElement.click();

        Thread.sleep(1000);
        WebElement sortOne = driver.findElement(By.xpath("//*[@id=\"tbodycars\"]/tr[1]/td[1]"));
        WebElement sortTwo = driver.findElement(By.xpath("//*[@id=\"tbodycars\"]/tr[5]/td[1]"));

        Assertions.assertEquals("938", sortOne.getText());
        Assertions.assertEquals("940", sortTwo.getText());
        driver.quit();
    }

    @Test
    void testEditOfCar() throws InterruptedException {
        Thread.sleep(3000);
        WebElement carElementEdit = driver.findElement(By.xpath("//*[@id=\"tbodycars\"]/tr[1]/td[8]/a[1]"));
        carElementEdit.click();

        Thread.sleep(500);
        WebElement descriptionField = driver.findElement(By.xpath("//*[@id=\"description\"]"));
        descriptionField.clear();
        descriptionField.sendKeys("Cool Car");

        Thread.sleep(1000);
        WebElement buttonSubmit = driver.findElement(By.xpath("//*[@id=\"save\"]"));
        buttonSubmit.click();

        Thread.sleep(1000);
        WebElement carElementDescription = driver.findElement(By.xpath("//*[@id=\"tbodycars\"]/tr[1]/td[6]"));
        String expected = "Cool Car";
        Assertions.assertEquals(expected, carElementDescription.getText());
        driver.quit();
    }

    @Test
    void testVerifyCantCreateNewCarWithoutRequiredFields() throws InterruptedException {
        Thread.sleep(3000);
        WebElement buttonSubmit = driver.findElement(By.xpath("//*[@id=\"save\"]"));
        buttonSubmit.click();

        Thread.sleep(500);
        WebElement errorMessage = driver.findElement(By.xpath("//*[@id=\"submiterr\"]"));

        Thread.sleep(1000);
        String expectedError = "All fields are required";
        Assertions.assertEquals(expectedError, errorMessage.getText());

        // Checking the size
        int size = driver.findElements(By.xpath("//*[@id=\"tbodycars\"]/tr")).size();
        int expected = 5;
        Assertions.assertEquals(expected, size);
        driver.quit();
    }

    @Test
    @AfterAll
    static void testCreationOfNewCar() throws InterruptedException {
        Thread.sleep(2000);
        WebElement yearNew = driver.findElement(By.xpath("//*[@id=\"year\"]"));
        WebElement registeredNew = driver.findElement(By.xpath("//*[@id=\"registered\"]"));
        WebElement makeNew = driver.findElement(By.xpath("//*[@id=\"make\"]"));
        WebElement modelNew = driver.findElement(By.xpath("//*[@id=\"model\"]"));
        WebElement descriptionNew = driver.findElement(By.xpath("//*[@id=\"description\"]"));
        WebElement priceNew = driver.findElement(By.xpath("//*[@id=\"price\"]"));

        // Set the elements
        yearNew.sendKeys("2008");
        registeredNew.sendKeys("2002-5-5");
        makeNew.sendKeys("Kia");
        modelNew.sendKeys("Rio");
        descriptionNew.sendKeys("As new");
        priceNew.sendKeys("31000");

        Thread.sleep(2000);
        WebElement buttonSubmit = driver.findElement(By.xpath("//*[@id=\"save\"]"));
        buttonSubmit.click();

        Thread.sleep(1000);

        int size = driver.findElements(By.xpath("//*[@id=\"tbodycars\"]/tr")).size();
        int expected = 6;
        Assertions.assertEquals(expected, size);
        driver.quit();
    }
}