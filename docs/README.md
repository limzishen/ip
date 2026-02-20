# Dicky User Guide

A simple yet powerful task management chatbot to help you organize your todos, deadlines, and events all in one place!

---

## Table of Contents
1. [Getting Started](#getting-started)
2. [Creating Tasks](#creating-tasks)
3. [Managing Tasks](#managing-tasks)
4. [Finding Tasks](#finding-tasks)
5. [Task List View](#task-list-view)
6. [Tips & Tricks](#tips--tricks)
7. [Troubleshooting](#troubleshooting)

---

## Getting Started

### Launching the App

#### Option 1: Graphical Interface (GUI)
- Double-click the application or run from your terminal:
  ```bash
  java -jar dicky.jar
  ```
- A chat window will appear with Dicky greeting you
- Type commands in the input box and click "Send" or press Enter

#### Option 2: Command Line Interface (CLI)
- Run from terminal:
  ```bash
  java -cp dicky.jar engine.Dicky
  ```
- Interact with Dicky directly in your terminal
- Type commands and press Enter

### First Steps
When you start Dicky, you'll see:
```
Hello! I'm Dicky. How can I help you today?
```

Just start typing commands! Dicky recognizes:
- **todo** - Simple tasks
- **deadline** - Tasks with due dates
- **event** - Time-bound events
- **list** - View all tasks
- **mark/unmark** - Track completion
- **delete** - Remove tasks
- **find** - Search tasks
- **clear** - Delete everything
- **exit** - Save and quit

---

## Creating Tasks

### 1. Simple To-Do

Create a basic task without any deadline.

**Syntax:** `todo <description>`

**Example:**
```
todo Buy groceries
```

**Response:**
```
Got it. I've added this task:
  [T] [ ] Buy groceries
Now you have 1 task in the list.
```

### 2. Deadline Tasks

Create a task with a specific due date and time.

**Syntax:** `deadline <description> /by yyyy-MM-dd HHmm`

**Date Format Explanation:**
- `yyyy` = 4-digit year (e.g., 2024)
- `MM` = 2-digit month (01-12)
- `dd` = 2-digit day (01-31)
- `HHmm` = Time in 24-hour format (e.g., 1830 = 6:30 PM, 0900 = 9:00 AM)

**Example:**
```
deadline Submit project report /by 2024-02-25 2359
```

**Response:**
```
Got it. I've added this task:
  [D] [ ] Submit project report /by Feb 25 2024, 11:59 PM
Now you have 2 tasks in the list.
```

### 3. Event Tasks

Create an event with a start and end date/time.

**Syntax:** `event <description> /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm`

**Example:**
```
event Team building activity /from 2024-03-01 1400 /to 2024-03-01 1700
```

**Response:**
```
Got it. I've added this task:
  [E] [ ] Team building activity /from Mar 01 2024, 2:00 PM /to Mar 01 2024, 5:00 PM
Now you have 3 tasks in the list.
```

---

## Managing Tasks

### View All Tasks

Display every task in your list.

**Syntax:** `list`

**Example:**
```
list
```

**Response:**
```
Here are the tasks in your list:
1. [T] [ ] Buy groceries
2. [D] [ ] Submit project report /by Feb 25 2024, 11:59 PM
3. [E] [ ] Team building activity /from Mar 01 2024, 2:00 PM /to Mar 01 2024, 5:00 PM
```

### Mark Task as Done

Mark a task as complete when you finish it.

**Syntax:** `mark <task number>`

**Example:**
```
mark 1
```

**Response:**
```
Nice! I've marked this task as done:
  [T] [X] Buy groceries
```

### Unmark Task

If you made a mistake or need to redo a task, unmark it.

**Syntax:** `unmark <task number>`

**Example:**
```
unmark 1
```

**Response:**
```
OK, I've marked this task as not done yet:
  [T] [ ] Buy groceries
```

### Delete a Task

Remove a task you no longer need.

**Syntax:** `delete <task number>`

**Example:**
```
delete 2
```

**Response:**
```
Noted. I've removed this task:
  [D] [ ] Submit project report /by Feb 25 2024, 11:59 PM
Now you have 2 tasks in the list.
```

### Clear All Tasks

Delete every task at once. **Warning: This cannot be undone!**

**Syntax:** `clear`

**Example:**
```
clear
```

**Response:**
```
All tasks have been cleared!
```

---

## Finding Tasks

### Search by Keyword

Find tasks that contain a specific word or phrase.

**Syntax:** `find <keyword>`

**Example:**
```
find project
```

**Response:**
```
Here are the matching tasks in your list:
1. [D] [ ] Submit project report /by Feb 25 2024, 11:59 PM
```

**Tip:** The search is case-insensitive and works on partial words too.
- `find proj` will find "project"
- `find REPORT` will find "report"

---

## Task List View

### Understanding Task Icons

Each task is displayed with helpful visual indicators:

| Icon | Meaning |
|------|---------|
| `[T]` | Todo task (simple task) |
| `[D]` | Deadline task (has a due date) |
| `[E]` | Event task (has start and end time) |
| `[X]` | Task is completed ✓ |
| `[ ]` | Task is not yet done |

### Example Task List
```
1. [T] [ ] Buy groceries
2. [T] [X] Call mom
3. [D] [ ] Finish assignment /by Feb 28 2024, 11:59 PM
4. [E] [X] Doctor's appointment /from Feb 20 2024, 2:00 PM /to Feb 20 2024, 3:30 PM
```

- Task 1: Todo, not done
- Task 2: Todo, completed
- Task 3: Deadline, not done
- Task 4: Event, completed

---

## Tips & Tricks

### Best Practices

1. **Be Specific in Descriptions**
   ```
   ✓ Good:   deadline Write quarterly report analysis /by 2024-03-15 0900
   ✗ Bad:    deadline Report /by 2024-03-15 0900
   ```

2. **Use 24-Hour Time Format**
   - 9:00 AM = `0900`
   - 1:30 PM = `1330`
   - 6:00 PM = `1800`
   - 11:59 PM = `2359`

3. **Double-Check Dates Before Adding**
   - Typos can't be edited, so delete and re-add if needed
   - Use `list` command before adding overlapping deadlines

4. **Search Before Creating**
   - Use `find` to check if a similar task already exists

### Workflow Examples

#### Daily Planning Routine
```
list                                    // See what needs doing
mark 1                                  // Complete morning task
mark 3                                  // Complete another task
todo Review meeting notes                // Add new task
find deadline                            // Check urgent items
exit                                    // Save and quit
```

#### Adding a Week of Tasks
```
todo Monday standup preparation
todo Wednesday client call prep
deadline Project submission /by 2024-02-23 1700
event Sprint planning session /from 2024-02-22 1000 /to 2024-02-22 1100
event Team lunch /from 2024-02-22 1200 /to 2024-02-22 1300
list                                     // Review all added tasks
exit
```

#### Finding Overdue Tasks
```
list                                     // Check current tasks
find deadline                            // Find all deadlines
find project                             // Look for specific project
```

---

## Troubleshooting

### Common Issues

#### "Invalid date format! Use: yyyy-MM-dd HHmm"
**Problem:** Your date or time format is incorrect.

**Solutions:**
- ✓ Correct: `2024-02-25 1430`
- ✗ Wrong: `02/25/2024 2:30 PM`
- ✗ Wrong: `2024-2-25 14:30`

#### "Missing deadline! Use: /by yyyy-MM-dd HHmm"
**Problem:** You forgot the `/by` keyword for a deadline.

**Solution:**
- ✗ Wrong: `deadline Submit report 2024-03-01 1700`
- ✓ Correct: `deadline Submit report /by 2024-03-01 1700`

#### "Missing event timing! Use: /from [start] /to [end]"
**Problem:** You forgot `/from` or `/to` for an event.

**Solution:**
- ✗ Wrong: `event Meeting 2024-03-01 1000 2024-03-01 1100`
- ✓ Correct: `event Meeting /from 2024-03-01 1000 /to 2024-03-01 1100`

#### "I don't understand that command"
**Problem:** You used an invalid command word.

**Solution:** Use only these commands:
- `todo`, `deadline`, `event`
- `list`, `mark`, `unmark`, `delete`, `clear`
- `find`, `exit`

#### Tasks disappeared after restart
**Problem:** You didn't save your tasks before closing.

**Solution:**
- Always use `exit` command to save
- Closing the window without `exit` may lose recent changes

#### Task numbers change after deletion
**Problem:** This is normal behavior!

**Explanation:** When you delete a task, all tasks below it shift up one position. This is intentional to keep your list compact.

**Example:**
```
Before delete:       After delete:
1. Buy milk          1. Buy bread     ← shifted from #2
2. Buy bread         2. Milk carton   ← shifted from #3
3. Milk carton
```

---

## Keyboard Shortcuts

### GUI Mode
- **Send Message:** Click "Send" button or press Enter
- **Focus Input:** Click the text field

### Best Practices Summary
- Type commands exactly as shown (case-insensitive for commands, but not descriptions)
- Always use `exit` to save your work
- Use `find` to locate tasks quickly
- Keep descriptions concise but descriptive
- Review `list` before managing tasks

---

## Need Help?

If something isn't working as expected:
1. Check the date format: `yyyy-MM-dd HHmm`
2. Make sure you're using the correct command syntax
3. Use `list` to see your current tasks
4. Try closing and reopening the application (your data is saved)

Good luck organizing your tasks with Dicky!