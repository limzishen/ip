package dickyTest;
import dicky.Dicky;
import task.*;
import java.io.*;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DickyTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayOutputStream testOut;
    private String stubPath;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUpOutput() throws IOException {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        Path path = tempDir.resolve("test_data.txt");

        // 3. Assign to the CLASS variable (no 'String' keyword here)
        stubPath = path.toAbsolutePath().toString();

        // 4. (Optional) Pre-create the file to be safe
        java.nio.file.Files.createFile(path);
    }

    @AfterEach
    void restoreSystem() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    private void provideInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    @Test
    void main_todoCommand_addsTaskAndExits() {
        // Arrange: Input 1: Add a todo, Input 2: Exit
        String shellInput = String.join("\n",
                "todo read book",
                "deadline eat /by 2024-05-06 1800",
                "event party /from 2024-05-06 1800 /to 2024-05-06 2000",
                "exit"
        ) + "\n";
        provideInput(shellInput);

        // Act
        Dicky.main(new String[]{stubPath});

        // Assert
        String output = testOut.toString();
        assertTrue(output.contains("Hello I'm Dicky."), "Should show welcome message");
        assertTrue(output.contains("read book"), "Should confirm task addition");
        assertTrue(output.contains("Bye. Hope to see you again soon!"), "Should show exit message");
    }

    @Test
    void main_testMarkUnmark() {
        String shellInput = String.join("\n",
                "todo read book",
                "mark 1",
                "unmark 1",
                "exit"
        ) + "\n";

        provideInput(shellInput);

        // Act
        Dicky.main(new String[]{stubPath});

        // Assert
        String output = testOut.toString();
        // Check Todo added
        assertTrue(output.contains("Got it. I've added this task:"), "Should confirm addition");
        assertTrue(output.contains("[T] [ ] read book"), "Should show task as todo");

        // Check Mark logic
        assertTrue(output.contains("Nice! I've marked this task as done:"), "Should show mark message");
        assertTrue(output.contains("[T] [X] read book"));
        // Check Unmark logic
        assertTrue(output.contains("OK, I've marked this task as not done yet:"), "Should unmark message");
        assertTrue(output.contains("[T] [ ] read book"));

        // Check Final Exit
        assertTrue(output.contains("Bye. Hope to see you again soon!"), "Should show exit message");
    }


    @Test
    void main_invalidCommand_showsErrorMessage() {
        // Arrange
        String shellInput = "blahblah\nexit\n";
        provideInput(shellInput);

        // Act
        Dicky.main(new String[]{});

        // Assert
        String output = testOut.toString();
        System.out.println(output);
        assertTrue(output.contains("Invalid Action: blahblah"));
    }
}