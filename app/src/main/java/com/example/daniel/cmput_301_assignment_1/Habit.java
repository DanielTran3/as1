package com.example.daniel.cmput_301_assignment_1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Habit implements Serializable
{
    // Private data that is specific to define each habit

    // Habit requires a name
    private String habitName;

    // Habit requires a date of creation
    private String habitDate;

    //Habit repeats on specific days
    private ArrayList<String> habitRepeatDays;

    // To record the number of completions
    private int completions;

    // To create a list containing strings that identify when the habit completion was made
    private ArrayList<String> completionList = new ArrayList<String>();

    // Constructor to define a habit when it is created.
    // Habit requires a name, date, and days of repetition.
    public Habit(String inputName, String inputDate, ArrayList<String> inputRepeatDays)
    {
        this.habitName = inputName;
        this.habitDate = inputDate;
        this.habitRepeatDays= inputRepeatDays;
        this.completions = 0;
    }

    // Getter for the habit name
    public String getHabitName()
    {
        return this.habitName;
    }

    // Setter for the habit name
    public void setHabitName(String name)
    {
        this.habitName = name;
    }

    // Getter for the habit completion number
    public int getHabitCompletions()
    {
        return this.completions;
    }

    // Update the completion counter and add the completion time to completionList
    public void updateHabitCompletion()
    {
        Date newCompletion = new Date();
        this.completionList.add(newCompletion.toString());
        this.completions = completionList.size();
    }

    // Remove the completion time from completionList and decrement completion count
    public void removeHabitCompletion(String removeCompletion)
    {
        completionList.remove(removeCompletion);
        this.completions = completionList.size();
    }

    // Getter for the habit date
    public String getHabitDate()
    {
        return this.habitDate;
    }

    // Setter for the days the habit should repeat
    public void setHabitRepeatDays(ArrayList<String> listOfHabitDays)
    {
        this.habitRepeatDays = listOfHabitDays;
    }

    // Getter for the habit repetition days
    public ArrayList<String> getHabitRepeatDays()
    {
        return this.habitRepeatDays;
    }

    // Getter for the list of habit completion times
    public ArrayList<String> viewCompletions()
    {
        return completionList;
    }

    // Return habit name when .toString is called
    public String toString()
    {
        return getHabitName();
    }


}
