package com.example.daniel.cmput_301_assignment_1;

import java.util.Observable;


// Habit list controller that interacts between the list of habits and the views
public class HabitListController extends Observable
{
    private static HabitList habitList = null;

    // Retrieves that habit list or creates one if a HabitList doesn't exist
    public static HabitList getHabitList()
    {
        if (habitList == null)
        {
            habitList = new HabitList();
        }

        return habitList;
    }

    // A function that adds a habit to the habit list and notifies the listeners
    public void addHabit(Habit habit)
    {
        getHabitList().addHabit(habit);
        notifyListeners();
    }

    // Function that is part of the observer pattern
    private void notifyListeners() {
    }

}
