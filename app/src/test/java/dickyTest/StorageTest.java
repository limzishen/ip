package dickyTest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import task.*;
import dicky.Storage;
import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;

class StorageTest {

    @TempDir
    Path tempDir; // Automatically creates a temp folder for tests

    @Test
    void testConvertDateTimeString_validDate_success() throws Exception {
        String input = "2026-01-24 1542";
        LocalDateTime result = Storage.convertDateTimeString(input);

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
            Storage.convertDateTimeString(badInput);
        });
    }

    @Test
    void testWriteAndRead_lifecycle_success() throws IOException {
        File tempFile = tempDir.resolve("tasks.txt").toFile();
        ArrayList<Task> originalTasks = new ArrayList<>();

        // Assuming Task has a constructor (description, isDone)
        originalTasks.add(new Task("Buy Milk", false));

        // 1. Test Writing
        Storage.writeFile(originalTasks, tempFile);
        assertTrue(tempFile.exists(), "File should be created after writing");

        // 2. Test Reading
        ArrayList<Task> loadedTasks = Storage.readFromFile(tempFile);
        assertEquals(1, loadedTasks.size());
        assertEquals("Buy Milk", loadedTasks.get(0).taskName);
    }
}