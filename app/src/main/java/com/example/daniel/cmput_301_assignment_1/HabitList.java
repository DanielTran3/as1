package com.example.daniel.cmput_301_assignment_1;

import java.util.ArrayList;

public class HabitList implements HabitListener
{

    // Data to define the list of habits and the list of listeners
    protected ArrayList<Habit> habitList;
    protected ArrayList<HabitListener> listeners;

    // Constructor to create a new habit list and listeners list
    public HabitList()
    {
        habitList = new ArrayList<Habit>();
        listeners = new ArrayList<HabitListener>();
    }

    // Adds a habit to the habit list and notifies the listeners that there is a change in the
    // data set
    public void addHabit(Habit habit)
    {
        habitList.add(habit);
        notifyListeners();
    }

    // Function that will notify the listeners when there is an update
    public void notifyListeners()
    {
        for (HabitListener hl : listeners)
        {
            hl.update();
        }
    }

    // Adds listener to the HabitList
    public void addListener(HabitListener hl)
    {
        listeners.add(hl);
    }

    // Removes a habit from the habit list and notifies the listeners of the change
    public void removeHabit(Habit habit)
    {
        habitList.remove(habit);
        notifyListeners();
    }

    // Get the list of habits
    public ArrayList<Habit> getHabits()
    {
        return habitList;
    }

    // Function that will take in a habit from the list and call the habit's updateHabitCompletion
    // function to update the Habit data, then notify the listeners of the change.
    public void updateHabitCompletion(Habit retrieveHabit)
    {
        for (Habit h : habitList)
        {
            if (h.equals(retrieveHabit))
            {
                h.updateHabitCompletion();
                notifyListeners();
            }
        }
    }

    // Function that will take in a habit from the list and call the habit's removeHabitCompletion
    // function to update the Habit data, then notify the listeners of the change.
    public void removeHabitCompletion(Habit habit, String removeCompletion)
    {
        for (Habit h : habitList)
        {
            if (h.equals(habit))
            {
                h.removeHabitCompletion(removeCompletion);
                notifyListeners();
            }
        }
    }

    // Function that uses the HabitListener interface function
    @Override
    public void update() {

    }
}
