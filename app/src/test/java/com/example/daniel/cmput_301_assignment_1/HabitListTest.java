package com.example.daniel.cmput_301_assignment_1;


import junit.framework.TestCase;

import java.util.ArrayList;

public class HabitListTest extends TestCase
{
    public void testAddandRemoveHabits()
    {
        String testName = "Work";
        String testDate = "2016-02-23";
        ArrayList<String> testHabitRepeatDays = new ArrayList<String>();

        Habit habit = new Habit(testName, testDate, testHabitRepeatDays);
        testHabitRepeatDays.add("Thursday");
        testHabitRepeatDays.add("Friday");
        testHabitRepeatDays.add("Saturday");

        HabitList habitList = new HabitList();

        assertTrue(habitList.getHabits().isEmpty());
        habitList.addHabit(habit);
        assertTrue(habitList.getHabits().get(0).equals(habit));
        assertTrue(habitList.getHabits().size() == 1);

        habitList.removeHabit(habit);
        assertTrue(habitList.getHabits().equals(new ArrayList<Habit>()));

        String testName2 = "Work2";
        String testDate2 = "2016-02-24";
        ArrayList<String> testHabitRepeatDays2 = new ArrayList<String>();
        testHabitRepeatDays.add("Monday");
        testHabitRepeatDays.add("Tuesday");
        testHabitRepeatDays.add("Wednesday");


        Habit habit2 = new Habit(testName2, testDate2, testHabitRepeatDays2);

        habitList.addHabit(habit2);
        habitList.addHabit(habit);
        ArrayList<Habit> testList = new ArrayList<Habit>();
        testList.add(habit2);
        testList.add(habit);
        assertTrue(habitList.getHabits().equals(testList));
    }

    public void testCompletionOperations()
    {
        String testName = "Work";
        String testDate = "2016-02-23";
        ArrayList<String> testHabitRepeatDays = new ArrayList<String>();

        Habit habit = new Habit(testName, testDate, testHabitRepeatDays);
        testHabitRepeatDays.add("Thursday");
        testHabitRepeatDays.add("Friday");
        testHabitRepeatDays.add("Saturday");

        HabitList habitList = new HabitList();

        String testName2 = "Work2";
        String testDate2 = "2016-02-24";
        ArrayList<String> testHabitRepeatDays2 = new ArrayList<String>();
        testHabitRepeatDays.add("Monday");
        testHabitRepeatDays.add("Tuesday");
        testHabitRepeatDays.add("Wednesday");

        Habit habit2 = new Habit(testName2, testDate2, testHabitRepeatDays2);

        habitList.addHabit(habit2);
        habitList.addHabit(habit);
        ArrayList<Habit> testList = new ArrayList<Habit>();
        testList.add(habit2);
        testList.add(habit);

        assertTrue(habit.getHabitCompletions() == 0);
        assertTrue(habit.viewCompletions().isEmpty());
        habitList.updateHabitCompletion(habit);
        assertTrue(habit.getHabitCompletions() == 1);
        assertFalse(habit.viewCompletions().isEmpty());

        assertTrue(habit2.getHabitCompletions() == 0);
        assertTrue(habit2.viewCompletions().isEmpty());
        habitList.updateHabitCompletion(habit2);
        habitList.updateHabitCompletion(habit2);
        habitList.updateHabitCompletion(habit2);
        habitList.updateHabitCompletion(habit2);
        assertTrue(habit2.getHabitCompletions() == 4);
        assertTrue(habit2.viewCompletions().size() == 4);
        habitList.removeHabitCompletion(habit2, habit2.viewCompletions().get(0));
        assertTrue(habit2.getHabitCompletions() == 3);
        assertTrue(habit2.viewCompletions().size() == 3);
    }
}
