package com.example.daniel.cmput_301_assignment_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HabitCompletionsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completions_habit);

        // Get intent from previous activity and grab the extra information which defines the
        // specific habit to view the completion list for
        Intent intent = getIntent();
        Habit passedHabit = (Habit) intent.getSerializableExtra("com.example.daniel.cmput_301_assignment_1.data");

        // Create the views
        final ListView listOfCompletions = (ListView) findViewById(R.id.habit_completion_list);
        final TextView completionNumber = (TextView) findViewById(R.id.number_of_completions);

        // Get a list of the habits from HabitListController
        ArrayList<Habit> oldHabitList = HabitListController.getHabitList().getHabits();

        // Get the real habit that comes from the habitlist. This habit matches the habit passed
        // in from the intent
        final Habit receivedHabit = getHabit(oldHabitList, passedHabit.getHabitName());

        // Set the number of completions count
        completionNumber.setText(receivedHabit.getHabitName() + " Completions: " + Integer.toString(receivedHabit.getHabitCompletions()));

        // Create the ArrayAdapter for the ListView that will display the habit completion times
        final ArrayAdapter<String> habitCompletionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, receivedHabit.viewCompletions());

        // Set the adapter to the lsit
        listOfCompletions.setAdapter(habitCompletionAdapter);

        // When an item on the list is clicked
        listOfCompletions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id)
            {
                // Grab the string for the completion time that will be deleted
                final String deleteHabitCompletion = receivedHabit.viewCompletions().get(pos);

                // from the original list, remove the habit completion
                HabitListController.getHabitList().removeHabitCompletion(receivedHabit, deleteHabitCompletion);

                // Update the completion count
                completionNumber.setText(receivedHabit.getHabitName() + " Completions: " + Integer.toString(receivedHabit.getHabitCompletions()));

                // Notify that there was a change in the data from the deletion
                habitCompletionAdapter.notifyDataSetChanged();
                HabitListController.getHabitList().notifyListeners();

                // Update the screen
                listOfCompletions.invalidate();
            }
        });
    }

    // Function to iterate through the list of habits and return the habit that matches
    // the specified name
    public Habit getHabit(ArrayList<Habit> hl, String habitName)
    {
        for (Habit h : hl)
        {
            if (h.getHabitName().equals(habitName))
            {
                return h;
            }
        }
        return null;
    }
}
