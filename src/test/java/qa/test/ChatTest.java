package qa.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import qa.base.ScriptBase;

public class ChatTest extends ScriptBase {

	public ChatTest() {
		super();
	}

	@BeforeMethod
	public void setUp() throws InterruptedException {
		initialization();
	}

	@Test
	public void Test() throws InterruptedException {
		SoftAssert softassert = new SoftAssert();

		// Step#01 - Copy single prompt to a description file

		// Path to your input file - prompt dataset - should be text file
		String inputFilePath = "D:\\ScriptTesting\\Personal_Portfolio\\prompts_dataset.txt";

		// path to the folder inside that you want to save the 1-100 folders and
		// respective files
		String folderPath = "D:\\ScriptTesting\\Personal_Portfolio";

		try {

			// Read all lines from the text file into a List of Strings
			List<String> lines = Files.readAllLines(Paths.get(inputFilePath));

			// checking if error occurred then reload the page
			try {
				// Wait till the visibility of the error element
				WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(15));
				wait1.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//div[h2[contains(text(), 'Application error')]]")));

				WebElement error_msg = driver.findElement(By.xpath("//div[h2[contains(text(), 'Application error')]]"));
				if (error_msg.isDisplayed()) {
					System.out.println("Error detected. Reloading the page.");

					// Reload the page
					driver.navigate().refresh();
					Thread.sleep(2000);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			// Loop through each line and its index
			for (int i = 62; i < lines.size(); i++) {
				// Get the current line (text content)
				String lineContent = lines.get(i);

				// Create a folder named with the current line number (1-based indexing)
				String folderName = String.valueOf(i + 1);
				File folder = new File(folderPath, folderName);

				// Check if the folder doesn't exist and create it
				if (!folder.exists()) {
					folder.mkdir();
				}

				// Create the "description.txt" file inside the newly created folder
				File descriptionFile = new File(folder, "description.txt");

				// Write the line content to the description.txt file
				try (FileWriter writer = new FileWriter(descriptionFile)) {
					writer.write(lineContent);
				}

				System.out.println("Step-1: Success Processed line " + (i + 1) + ": " + lineContent);
				Thread.sleep(2000);

				// Step#03 - Enter the current lineContent and wait for UI generation output
//				driver.findElement(By.id("chat-main-textarea")).sendKeys(lineContent + Keys.ENTER);

//				Thread.sleep(1000);
//				driver.findElement(By.cssSelector("[data-testid='prompt-form-send-button']")).click();

				// checking if error occurred then reload the page
				boolean hasError = true; // Initial condition to enter the loop
				while (hasError) {
					try {

						driver.findElement(By.id("chat-main-textarea")).sendKeys(lineContent + Keys.ENTER);

						// Wait till the visibility of the error element
						WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(5));
						wait2.until(ExpectedConditions.visibilityOfElementLocated(
								By.xpath("//div[h2[contains(text(), 'Application error')]]")));

						WebElement error_msg = driver
								.findElement(By.xpath("//div[h2[contains(text(), 'Application error')]]"));
						if (error_msg.isDisplayed()) {
							System.out.println("Error detected. Reloading the page.");

							// Reload the page
							driver.navigate().refresh();
							Thread.sleep(2000);
//							driver.findElement(By.id("chat-main-textarea")).sendKeys(lineContent + Keys.ENTER);
//							Thread.sleep(1000);
						}
					} catch (TimeoutException e) {
						// Agar error element nahi milta, iska matlab hai ke error khatam ho gaya hai
						hasError = false;
					} catch (Exception e) {
						// Handle any other exceptions
						e.printStackTrace();
					}

				}

				System.out.println("Waiting for the result");

				// Wait till the visibility of the span element
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(180));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Retry']")));
				System.out.println("Output: Now span element visible");

				Thread.sleep(1000); // 2 seconds in milliseconds

				// -----------Output - Start------------//

				// Step#04 - click the .tsx button to open code tabs and copy code to files
				driver.findElement(By.xpath("//button[div[contains(text(), '.')]]")).click();
				Thread.sleep(1000);
				System.out.println("Output: Clicked the .tsx Button");

				// getting the type of file
				String fileName = driver.findElement(By.xpath("//button[div[contains(text(), '.')]]")).getText();

				// changing the filename if contains the slash
				fileName = fileName.replace("/", "-");
//				System.out.println("Output: Slash replaced with hyphen");

				// copy and paste the code for output file
				copyCodeToFile(folder, fileName);

				System.out.println("Output " + (i + 1) + ": Successfully Code Copied");

				// getting the URL of Application under test
				driver.get("https://v0.dev/chat/");
//				Thread.sleep(5000);

				// checking if error occurred then reload the page
				try {
					// checking if error occurred then reload the page

					// Wait till the visibility of the error element
					WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
					wait1.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//div[h2[contains(text(), 'Application error')]]")));

					WebElement error_msg = driver
							.findElement(By.xpath("//div[h2[contains(text(), 'Application error')]]"));
					if (error_msg.isDisplayed()) {
						System.out.println("Error detected. Reloading the page.");

						// Reload the page
						driver.navigate().refresh();
//						Thread.sleep(2000);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				softassert.assertAll();
			}
			// tear down
			driver.quit();
		} catch (IOException e) {
			// Handle any I/O exceptions (like file not found, etc.)
			System.err.println("Error reading or writing files: " + e.getMessage());
		}

	}
}
