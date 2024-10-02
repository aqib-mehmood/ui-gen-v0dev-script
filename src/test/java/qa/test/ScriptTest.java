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

public class ScriptTest extends ScriptBase {

	public ScriptTest() {
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
		String inputFilePath = "D:\\ScriptTesting\\prompts_dataset.txt";

		// path to the folder inside that you want to save the 1-100 folders and respective files
		String folderPath = "D:\\ScriptTesting\\Ui_components";

		try {
			// Read all lines from the text file into a List of Strings
			List<String> lines = Files.readAllLines(Paths.get(inputFilePath));

			// Loop through each line and its index
			for (int i = 29; i < lines.size(); i++) {
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

				// validate the page title;
				softassert.assertEquals("v0 by Vercel", driver.getTitle());
				System.out.println("Checking the title of the page");

				// Step#03 - Enter the current lineContent and wait for UI generation output
				driver.findElement(By.id("home-prompt")).sendKeys(lineContent + Keys.RETURN);
				System.out.println("Waiting for the result for 60 seconds");

				// Wait for 60 seconds after pressing Enter
				Thread.sleep(60000); // 60 seconds in milliseconds

				// -----------A Output - Start------------//

				// Step#04 - click the code button to open code tabs and copy code to files for
				// "A" output
				driver.findElement(By.xpath("//button[span[text()='Code']]")).click();
				Thread.sleep(2000);
				System.out.println("A Output: Clicked the button Code");

				// click the component.jsx tab
				driver.findElement(By.xpath("//*[text()='component.jsx']")).click();
				Thread.sleep(2000);
				System.out.println("A Output: Clicked the tab component.jsx");

				// copy and paste the code for 1-component file
				copyCodeToFile(folder, "1-comp.jsx");

				// Step#5
				// click the globals.css tab
				driver.findElement(By.xpath("//*[text()='globals.css']")).click();
				Thread.sleep(2000);
				System.out.println("A Output: Clicked the global.css tab");

				// copy and paste the code for 1-css file
				copyCodeToFile(folder, "1.css");

				// Step#6
				// click the layout.jsx tab
				driver.findElement(By.xpath("//*[text()='layout.jsx']")).click();
				Thread.sleep(2000);
				System.out.println("A Output: Clicked the layout.jsx tab");

				// copy and paste the code for 1-layout file
				copyCodeToFile(folder, "1-layout.jsx");

				// Step#7
				// click the tailwind.config.js tab
				driver.findElement(By.xpath("//*[text()='tailwind.config.js']")).click();
				Thread.sleep(2000);
				System.out.println("A Output: Clicked the tailwind.config.js tab");

				// copy and paste the code for 1-layout file
				copyCodeToFile(folder, "1.js");
				Thread.sleep(2000);
				System.out.println("A Output Success - Code Copied");

				// -----------B Output - Start------------//
				// Step#08
				// Now click the canvas button to see the B output tab
				driver.findElement(By.xpath("//button[span[text()='Canvas']]")).click();
				Thread.sleep(2000);
				System.out.println("B Output: Clicked the button Canvas");

				// click the B Output card
				driver.findElement(By.xpath("/html/body/div[2]/main/div/div/div[1]/div[1]/div[2]/div/div[2]/div[2]"))
						.click();
				System.out.println("B Output: Clicked the B iframe");
				Thread.sleep(2000);

				// checking if error occurred then reload the page
				WebElement error_msg = driver.findElement(By.id("error"));
				if (error_msg.isDisplayed()) {
					System.out.println("Error detected. Reloadin the page.");

					// Reload the page
					driver.navigate().refresh();
					Thread.sleep(5000);

					// click the B Output card
					driver.findElement(
							By.xpath("/html/body/div[2]/main/div/div/div[1]/div[1]/div[2]/div/div[2]/div[2]")).click();
					System.out.println("Again Clicked the B iframe");
				}

				Thread.sleep(2000);

				// Step#09
				// click the code button to open code tabs and copy code to files for
				// "B" output
				driver.findElement(By.xpath("//button[span[text()='Code']]")).click();
				Thread.sleep(2000);
				System.out.println("B Output: Clicked the button Code");

				// click the component.jsx tab
				driver.findElement(By.xpath("//*[text()='component.jsx']")).click();
				Thread.sleep(2000);
				System.out.println("B Output: Clicked the tab component.jsx");

				// copy and paste the code for 2-component file
				copyCodeToFile(folder, "2-comp.jsx");
				Thread.sleep(2000);

				// Step#10
				// click the globals.css tab
				driver.findElement(By.xpath("//*[text()='globals.css']")).click();
				Thread.sleep(2000);
				System.out.println("B Output: Clicked the global.css tab");

				// copy and paste the code for 2-css file
				copyCodeToFile(folder, "2.css");
				Thread.sleep(2000);

				// Step#11
				// click the layout.jsx tab
				driver.findElement(By.xpath("//*[text()='layout.jsx']")).click();
				Thread.sleep(2000);
				System.out.println("B Output: Clicked the layout.jsx tab");

				// copy and paste the code for 2-layout file
				copyCodeToFile(folder, "2-layout.jsx");
				Thread.sleep(2000);

				// Step#12
				// click the tailwind.config.js tab
				driver.findElement(By.xpath("//*[text()='tailwind.config.js']")).click();
				Thread.sleep(2000);
				System.out.println("B Output: Clicked the tailwind.config.js tab");

				// copy and paste the code for 2-js file
				copyCodeToFile(folder, "2.js");
				Thread.sleep(2000);
				System.out.println("B Output Success - Code Copied");

				// -----------C Output - Start------------//
				// Step#13
				// Now click the canvas button to see the C output tab
				driver.findElement(By.xpath("//button[span[text()='Canvas']]")).click();
				Thread.sleep(2000);
				System.out.println("C Output: Clicked the button Canvas");

				// click the C Output card
				driver.findElement(By.xpath("/html/body/div[2]/main/div/div/div[1]/div[1]/div[2]/div/div[2]/div[3]"))
						.click();
				System.out.println("C Output: Clicked the C iframe");
				Thread.sleep(2000);

				// checking if error occurred then reload the page
				WebElement error_msg1 = driver.findElement(By.id("error"));
				if (error_msg1.isDisplayed()) {
					System.out.println("Error detected. Reloadin the page.");

					// Reload the page
					driver.navigate().refresh();
					Thread.sleep(5000);

					// click the C Output card
					driver.findElement(
							By.xpath("/html/body/div[2]/main/div/div/div[1]/div[1]/div[2]/div/div[2]/div[3]")).click();
					System.out.println("Again Clicked the C iframe");
				}

				Thread.sleep(2000);

				// Step#14
				// click the code button to open code tabs and copy code to files for
				// "C" output
				driver.findElement(By.xpath("//button[span[text()='Code']]")).click();
				Thread.sleep(2000);
				System.out.println("C Output: Clicked the button Code");

				// click the component.jsx tab
				driver.findElement(By.xpath("//*[text()='component.jsx']")).click();
				Thread.sleep(2000);
				System.out.println("C Output: Clicked the tab component.jsx");

				// copy and paste the code for 3-component file
				copyCodeToFile(folder, "3-comp.jsx");
				Thread.sleep(2000);

				// Step#15
				// click the globals.css tab
				driver.findElement(By.xpath("//*[text()='globals.css']")).click();
				Thread.sleep(2000);
				System.out.println("C Output: Clicked the global.css tab");

				// copy and paste the code for 3-css file
				copyCodeToFile(folder, "3.css");
				Thread.sleep(2000);

				// Step#16
				// click the layout.jsx tab
				driver.findElement(By.xpath("//*[text()='layout.jsx']")).click();
				Thread.sleep(2000);
				System.out.println("C Output: Clicked the layout.jsx tab");

				// copy and paste the code for 3-layout file
				copyCodeToFile(folder, "3-layout.jsx");
				Thread.sleep(2000);

				// Step#17
				// click the tailwind.config.js tab
				driver.findElement(By.xpath("//*[text()='tailwind.config.js']")).click();
				Thread.sleep(2000);
				System.out.println("C Output: Clicked the tailwind.config.js tab");

				// copy and paste the code for 3-js file
				copyCodeToFile(folder, "3.js");
				Thread.sleep(2000);
				System.out.println("C Output Success - Code Copied");

				Thread.sleep(5000);
				softassert.assertAll();

				// getting the URL of Application under test
				driver.get("https://v0.dev/");

			}
			// tear down
			driver.quit();
		} catch (IOException e) {
			// Handle any I/O exceptions (like file not found, etc.)
			System.err.println("Error reading or writing files: " + e.getMessage());
		}

	}
}
