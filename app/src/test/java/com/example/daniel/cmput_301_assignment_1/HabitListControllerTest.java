package com.example.daniel.cmput_301_assignment_1;

import junit.framework.TestCase;

import java.util.ArrayList;

public class HabitListControllerTest extends TestCase
{
    public void testFunctions()
    {
        String testName = "Work";
        String testDate = "2016-02-23";
        ArrayList<String> testHabitRepeatDays = new ArrayList<String>();
        testHabitRepeatDays.add("Monday");

        Habit habit = new Habit(testName, testDate, testHabitRepeatDays);
        HabitListController hlc = new HabitListController();

        assertTrue(hlc.getHabitList().equals(new HabitList()));
        assertTrue(hlc.getHabitList().getHabits().isEmpty());
        hlc.addHabit(habit);
        assertFalse(hlc.getHabitList().getHabits().isEmpty());
        hlc.getHabitList().removeHabit(habit);
        assertTrue(hlc.getHabitList().getHabits().isEmpty());

        HabitList hl = hlc.getHabitList();
        hlc.addHabit(habit);
        assertTrue(hl.getHabits().size() == 1);
        assertTrue(hlc.getHabitList().getHabits().size() == 1);
        assertTrue(hl.getHabits().get(0).equals(habit));
        assertTrue(hlc.getHabitList().getHabits().get(0).equals(habit));

        hlc.getHabitList().removeHabit(habit);
        assertTrue(hl.getHabits().size() == 0);
        assertTrue(hlc.getHabitList().getHabits().size() == 0);
        assertFalse(hl.getHabits().get(0).equals(habit));
        assertFalse(hlc.getHabitList().getHabits().get(0).equals(habit));
    }
}
