package dickyTest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import engine.Parser;
import engine.Storage;
import engine.TaskList;
import task.*;
import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;

class StorageTest {

    @TempDir
    Path tempDir; // Automatically creates a temp folder for tests

    @Test
    void testConvertDateTimeString_validDate_success() throws Exception {
        String input = "2026-01-24 1542";
        LocalDateTime result = Parser.convertDateTimeString(input);

        assertNotNull(result);
        assertEquals(2026, result.getYear());
        assertEquals(1, result.getMonthValue());
        assertEquals(24, result.getDayOfMonth());
        assertEquals(15, result.getHour());
        assertEquals(42, result.getMinute());
    }

    @Test
    void testConvertDateTimeString_invalidDate_throwsException() {
        String badInput = "24/01/2026 15:42";
        assertThrows(exception.InvalidActionException.class, () -> {
            Parser.convertDateTimeString(badInput);
        });
    }

    @Test
    void testWriteAndRead_lifecycle_success() throws IOException {
        String filePath = tempDir.resolve("tasks.txt").toString();
        Storage storage = new Storage(filePath);
        TaskList originalTasks = new TaskList();

        originalTasks.addTask(new Task("Buy Milk", false));

        // 1. Test Writing
        storage.writeFile(originalTasks);
        File tempFile = new File(filePath);
        assertTrue(tempFile.exists(), "File should be created after writing");

        // 2. Test Reading
        Storage storageReader = new Storage(filePath);
        TaskList loadedTasks = storageReader.readFromFile();
        assertEquals(1, loadedTasks.size());
        assertEquals("Buy Milk", loadedTasks.get(0).taskName);
    }
}