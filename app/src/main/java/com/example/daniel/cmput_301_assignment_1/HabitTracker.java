package com.example.daniel.cmput_301_assignment_1;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class HabitTracker extends AppCompatActivity implements Serializable {

    //File name that the save and lod functions read and write to respectively
    private static final String FILENAME = "habit_data.sav";

    // Arraylist that will retrieve the data from FILENAME when the data is loaded
    private ArrayList<Habit> hlist = new HabitListController().getHabitList().getHabits();

    // Create an empty list of habits that will be used to reference the list of habits
    private HabitList list_of_habits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create listview that displays the habits
        final ListView habitList = (ListView) findViewById(R.id.habitTrackerMainListView);

        // Link the HabitList controller lists to the variable
        list_of_habits = HabitListController.getHabitList();

        // Load habits from FILENAME
        loadHabits(this.getApplicationContext());

        // Iterate through the loaded data and store it into list_of_habits which is linked to the HabitListController.
        for(Habit h : hlist)
        {
            list_of_habits.addHabit(h);
        }

        // Initialized the ArrayAdapter for Habit objects
        final ArrayAdapter<Habit> habitTrackerAdapter = new ArrayAdapter<Habit>(this, android.R.layout.simple_list_item_1, list_of_habits.getHabits())
        {

            // Create the view for the habits. Habits name is red if it has not been completed before
            // and green if it has been completed.
            // Code to change text from: http://android--code.blogspot.ca/2015/08/android-listview-text-color.html
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                if (hlist.get(position).getHabitCompletions() == 0)
                {
                    tv.setTextColor(Color.RED);
                }
                else
                {
                    tv.setTextColor(Color.GREEN);
                }
                return view;
            }
        };

        // Set the adapter to the HabitList habitList
        habitList.setAdapter(habitTrackerAdapter);

        // Add a listener and define the update function to refresh the habits list when there
        // is a change in the dataset, then save the data to FILENAME
        HabitListController.getHabitList().addListener(new HabitListener() {
            @Override
            public void update()
            {
                hlist.clear();
                ArrayList<Habit> habits = list_of_habits.getHabits();
                hlist.addAll(habits);
                habitTrackerAdapter.notifyDataSetChanged();
                saveHabits();
            }
        });

        // If there is a long click on a habit, prompt to remove a habit
        habitList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                final Habit habitToRemove = hlist.get(pos);
                createDeletionDialog(habitToRemove);
                return true;
            }
        });

        // If there is a click on the habit, prompt for completing the habit or viewing the
        // habit's completion history
        habitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id)
            {
                final Habit habitToComplete = hlist.get(pos);
                createCompletionDialog(habitToComplete);
                habitTrackerAdapter.notifyDataSetChanged();
            }
        });
    }

    // Function that is called to open the "create new habit" screen
    public void addNewHabit(View v)
    {
        Intent intent = new Intent(HabitTracker.this, NewHabitScreen.class);
        startActivity(intent);
    }

    // The deletion dialog from the long click on a habit, which allows for the choice to cancel
    // or delete the habit
    public void createDeletionDialog(Habit habit)
    {
        // Get the habit
        final Habit habitToRemove = habit;

        // Build the dialog
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(HabitTracker.this);
        deleteDialog.setMessage("Delete " + habitToRemove.getHabitName() + "?");
        deleteDialog.setCancelable(true);
        deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            // Don't do anything if Cancel is clicked, which closes the dialog
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        // If delete is pressed
        deleteDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Remove the habit from HabitList
                HabitListController.getHabitList().removeHabit(habitToRemove);

                // Save this change of data into FILENAME
                saveHabits();

                // Toast that the habit was deleted
                Toast.makeText(HabitTracker.this, "Habit '" + habitToRemove.getHabitName() + "' deleted", Toast.LENGTH_SHORT).show();
            }
        });

        // Show the dialog
        deleteDialog.show();
    }

    // The completion dialog from the click on a habit, allowing for a habit completion, cancel,
    // or viewing the completions
    private void createCompletionDialog(Habit habit)
    {
        // Get the habit
        final Habit habitToComplete = habit;

        // Create the complete dialog
        AlertDialog.Builder completeDialog = new AlertDialog.Builder(HabitTracker.this);
        completeDialog.setMessage("Add Completion to " + habitToComplete.getHabitName() + "?");
        completeDialog.setCancelable(true);
        completeDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            // Don't do anything if Cancel is clicked, which closes the dialog
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        // If Complete is pressed
        completeDialog.setPositiveButton("Complete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // Update the habit data with the completion
                HabitListController.getHabitList().updateHabitCompletion(habitToComplete);

                // Save the change to FILENAME
                saveHabits();

                // Toast onc Habit completion displaying how many times the habit has been completed
                Toast.makeText(HabitTracker.this,
                        "Habit '" + habitToComplete.getHabitName() +
                        "' has " + habitToComplete.getHabitCompletions() +
                        " completions", Toast.LENGTH_SHORT).show();
            }
        });

        // If View Completiosn is pressed
        completeDialog.setNeutralButton("View Completions", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // Grab the list of completions
                ArrayList<String> habitCompletions = habitToComplete.viewCompletions();

                // Toast and do not open new activity if there is no completions on the habit
                if (habitCompletions.size() == 0)
                {
                    Toast.makeText(HabitTracker.this, "Habit has no completions", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Create the intent to move to the HabitCompletionsScreen
                    Intent intent = new Intent(HabitTracker.this, HabitCompletionsScreen.class);

                    // Pass the habit into the new screen
                    intent.putExtra("com.example.daniel.cmput_301_assignment_1.data", habitToComplete);
                    startActivity(intent);
                }
            }
        });

        // Show the dialog
        completeDialog.show();
    }

    // Function to load stored data in FILENAME
    private void loadHabits(Context context)
    {
        try
        {
            // Getting file directory code to ensure file is created from:
            // stackoverflow.com/questions/5017292/how-to-create-a-file-on-android-internal-storage
            ContextWrapper cw = new ContextWrapper(context);
            File dir = cw.getDir(FILENAME, context.MODE_PRIVATE);

            // Open the file stream and buffer to load the data from FILENAME
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader br_in = new BufferedReader(new InputStreamReader(fis));

            // Instantiate Gson
            Gson gson = new Gson();

            // Type that Gson will convert the Json to
            // Code from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            Type listType = new TypeToken<ArrayList<Habit>>(){}.getType();

            // Load the data into the global variable hlist
            hlist = gson.fromJson(br_in, listType);
        }

        // If there is no file, then grab the current habit list
        catch (FileNotFoundException e) {
            hlist = HabitListController.getHabitList().getHabits();
        }
    }

    // Function to save the habits to FILENAME
    private void saveHabits()
    {
        try {
            // Open the FileOutputStream and BufferedWriter to write to FILENAME
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            // Instantiate Gson
            Gson gson = new Gson();

            // Write the data in hlist to BufferedWriter
            gson.toJson(hlist, bw);

            // Flush the buffer to prevent memory leakage and close the OutputStream
            bw.flush();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

