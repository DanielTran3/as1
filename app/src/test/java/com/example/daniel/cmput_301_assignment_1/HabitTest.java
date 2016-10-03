package com.example.daniel.cmput_301_assignment_1;

import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.Date;


public class HabitTest extends TestCase
{
    public void testConstructor()
    {
        String testName = "Work";
        String testDate = "2016-02-23";
        ArrayList<String> testHabitRepeatDays = new ArrayList<String>();
        testHabitRepeatDays.add("Monday");

        Habit habit = new Habit(testName, testDate, testHabitRepeatDays);

        assertTrue(habit.getHabitName().equals("Work"));
        assertTrue(habit.getHabitDate().equals("2016-02-23"));
        assertTrue(habit.getHabitRepeatDays().equals(testHabitRepeatDays));
    }

    public void testSettersandGetters()
    {
        String testName = "Work";
        String testDate = "2016-02-23";
        ArrayList<String> testHabitRepeatDays = new ArrayList<String>();

        Habit habit = new Habit(testName, testDate, testHabitRepeatDays);

        assertTrue(habit.getHabitName().equals("Work"));
        habit.setHabitName("Yoga");
        assertFalse(habit.getHabitName().equals("Work"));
        assertTrue(habit.getHabitName().equals("Yoga"));

        ArrayList<String> newDays = new ArrayList<String>();
        newDays.add("Monday");
        newDays.add("Tuesday");
        newDays.add("Saturday");
        habit.setHabitRepeatDays(newDays);
        assertTrue(habit.getHabitRepeatDays() != testHabitRepeatDays);

        assertTrue(habit.getHabitCompletions() == 0);
        habit.updateHabitCompletion();
        assertFalse(habit.getHabitCompletions() == 0);
        assertTrue(habit.getHabitCompletions() == 1);

        assertTrue(habit.getHabitDate().equals("2016-02-23"));

    }

    public void testCompletions()
    {
        String testName = "Work";
        String testDate = "2016-02-23";
        ArrayList<String> testHabitRepeatDays = new ArrayList<String>();

        Habit habit = new Habit(testName, testDate, testHabitRepeatDays);
        habit.updateHabitCompletion();
        Date nowDate = new Date();
        assertTrue(habit.getHabitCompletions() == 1);
        assertTrue(habit.viewCompletions().get(0).equals(nowDate.toString()));
        habit.removeHabitCompletion(habit.viewCompletions().get(0));
        assertTrue(habit.viewCompletions().equals(new ArrayList<String>()));
        assertTrue(habit.getHabitCompletions() == 0);
    }
}
