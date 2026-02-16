

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import engine.TaskList;
import task.Task;

class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }

    @Test
    void addTask_validTask_increasesSize() {
        taskList.addTask(new Task("Read book", false));

        assertEquals(1, taskList.size(), "Size should be 1 after adding a task");
        assertEquals("Read book", taskList.get(0).taskName);
    }

    @Test
    void isEmpty_newList_returnsTrue() {
        assertTrue(taskList.isEmpty(), "New task list should be empty");
    }

    @Test
    void remove_validIndex_decreasesSize() {
        taskList.addTask(new Task("Task 1", false));
        taskList.addTask(new Task("Task 2", false));

        taskList.remove(0);

        assertEquals(1, taskList.size());
        assertEquals("Task 2", taskList.get(0).taskName);
    }

    @Test
    void printList_emptyList_returnsFriendlyMessage() {
        String result = taskList.printList();

        assertEquals("No items in list \n", result);
    }

    @Test
    void printList_withItems_returnsFormattedString() {
        taskList.addTask(new Task("Fix bugs", false));

        String result = taskList.printList();

        assertTrue(result.contains("1:"));
        assertTrue(result.contains("[T] [ ] Fix bugs"));
    }
}