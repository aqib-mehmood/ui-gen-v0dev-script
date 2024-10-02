package qa.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
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
		String inputFilePath = "D:\\ScriptTesting\\Web3_Landing_Page_UI\\prompts_dataset.txt";

		// path to the folder inside that you want to save the 1-100 folders and
		// respective files
		String folderPath = "D:\\ScriptTesting\\Web3_Landing_Page_UI";

		try {
			// Read all lines from the text file into a List of Strings
			List<String> lines = Files.readAllLines(Paths.get(inputFilePath));

			// Loop through each line and its index
			for (int i = 5; i < lines.size(); i++) {
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

				System.out.println("Step1-Success-Processed line " + (i + 1) + ": " + lineContent);

				// Step#02 - Open the browser and v0.dev site
//				initialization();

				// checking if error occurred then reload the page
				try {
					// checking if error occurred then reload the page
					WebElement error_msg = driver
							.findElement(By.xpath("//div[h2[contains(text(), 'Application error')]]"));
					if (error_msg.isDisplayed()) {
						System.out.println("Error detected. Reloading the page.");

						// Reload the page
						driver.navigate().refresh();
						Thread.sleep(2000);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
				// validate the page title;
				softassert.assertEquals("v0 by Vercel", driver.getTitle());
				System.out.println("Checking the title of the page");

				// Step#03 - Enter the current lineContent and wait for UI generation output
				driver.findElement(By.id("chat-main-textarea")).sendKeys(lineContent + Keys.RETURN);
				System.out.println("Waiting for the result for 40 seconds");

				// Wait for 60 seconds after pressing Enter
				Thread.sleep(40000); // 40 seconds in milliseconds

				// -----------Output - Start------------//

				// Step#04 - click the .tsx button to open code tabs and copy code to files
				driver.findElement(By.xpath("//button[div[contains(text(), '.tsx')]]")).click();
				System.out.println("Output: Clicked the .tsx Button");

				// copy and paste the code for output file
				copyCodeToFile(folder, "component.tsx");

				System.out.println("Output Success - Code Copied");

				// getting the URL of Application under test
				driver.get("https://v0.dev/chat/");
				Thread.sleep(5000);

				// checking if error occurred then reload the page
				try {
					// checking if error occurred then reload the page
					WebElement error_msg = driver
							.findElement(By.xpath("//div[h2[contains(text(), 'Application error')]]"));
					if (error_msg.isDisplayed()) {
						System.out.println("Error detected. Reloading the page.");

						// Reload the page
						driver.navigate().refresh();
						Thread.sleep(2000);
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
