package qa.base;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ScriptBase {
	public static WebDriver driver;
	public static long PAGE_LOAD_TIMEOUT = 30;
	public static long IMPLICIT_WAIT = 20;

	@SuppressWarnings("deprecation")
	public void initialization() throws InterruptedException {
		ChromeOptions options = new ChromeOptions();

		// IMPORTANT!!! The below code will kill the currently running chrome processes
		String processNameToKill = "chrome.exe";
		try {
			// Create a process to execute a command that lists running processes.
			Process process = Runtime.getRuntime().exec("tasklist");

			// Read the output of the command.
			java.util.Scanner scanner = new java.util.Scanner(process.getInputStream());
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.contains(processNameToKill)) {
					// Extract the process ID (PID) from the line.
					String[] parts = line.split("\\s+");
					String pid = parts[1];

					// Execute a command to terminate the process by PID.
					Runtime.getRuntime().exec("taskkill /F /PID " + pid);
				}
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// In order to open the current chrome profile
		// Set path to user data directory of Chrome
		String profileDirectory = "C:\\Users\\AK Technology\\AppData\\Local\\Google\\Chrome\\User Data";

		// Set profile name
		String profileName = "Profile 2";

		options.addArguments("user-data-dir=" + profileDirectory);
		options.addArguments("profile-directory=" + profileName);

		driver = new ChromeDriver(options);

		// added the below line because was facing error: Unable to establish websocket
		// connection
//		options.addArguments("--remote-allow-origins=*");

		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT));

		// getting the URL of Application under test
		driver.get("https://v0.dev/chat");

	}

	public void copyCodeToFile(File folder, String fileName) throws IOException {
		// copy the code
		driver.findElement(By.xpath("//button[@aria-label='Copy']")).click();
		System.out.println("Output: Clicked the Copy button");

		// Get clipboard content
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		String clipboardContent = null;
		try {
			clipboardContent = (String) clipboard.getData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException e) {
			e.printStackTrace();
		}

		// Create the "N-comp.jsx" file and paste the relevant code in it
		File n_comp_jsx = new File(folder, fileName);

		// Write the clipboard content to the file
		try (FileWriter writer = new FileWriter(n_comp_jsx)) {
			writer.write(clipboardContent);
			System.out.println("Content successfully written to " + fileName);
		}
	}

}
