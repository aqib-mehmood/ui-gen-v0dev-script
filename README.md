# UI Generation Script

This is a Selenium-based automated script designed for generating UI components using the v0.dev app.

## Prerequisites

Before running the script, ensure the following configurations are made:

1. **Chrome Profile Directory**
   - Update the `profileDirectory` variable in the `ScriptBase.java` file to point to your existing Chrome profile directory.
   - This is necessary because the script uses an existing Chrome session in which the v0.dev app is already logged in, avoiding the need for a separate login process.
   - Example:
     ```java
     String profileDirectory = "C:/Users/YourUsername/AppData/Local/Google/Chrome/User Data/Profile 1";
     ```

2. **Prompt Dataset File**
   - Set the `inputFilePath` variable in the `ScriptTest.java` file to the path of a text file containing your prompts. Each prompt should be on a new line.
   - Example:
     ```java
     String inputFilePath = "C:/path/to/your/dataset/prompts.txt";
     ```

3. **Output Folder Path**
   - Update the `folderPath` variable in the `ScriptTest.java` file to specify where you want the output files and folders to be generated.
   - Example:
     ```java
     String folderPath = "C:/path/to/output/folder";
     ```

## Important Notes

- **Chrome Browser Termination:**
  Upon executing the test, all currently running Chrome browser processes will be terminated. Make sure to save any unsaved work before running the script.

- **Automation Scope:**
  The script automates UI generation by utilizing an existing logged-in session of the v0.dev app. The input is provided through a text file with prompts, and the output will be saved in the specified directory.

## How to Run

1. Ensure the required Chrome profile and prompt dataset file paths are configured in the respective Java files as mentioned above.
2. Run the test script using your preferred IDE like Eclipse or via command line but make sure you have maven installed:
   ```bash
   mvn test
   ```

## Dependencies

- Chrome Browser
- Maven
- Java

Make sure to have Chrome installed and a valid profile path set. The repository uses Maven for dependency management, so ensure Maven is installed before proceeding.
