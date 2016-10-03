package com.example.daniel.cmput_301_assignment_1;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class NewHabitScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_habit_screen);

        // Get the button information
        final Button dateButton = (Button) findViewById(R.id.calendar_button);

        // Set the current text of the button to todays date
        dateButton.setText(generateTodaysDate());

        // If the button is pressed, open the dialog to input a date
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateSelector(dateButton);
            }
        });

        // Refresh the screen
        //.invalidate() method for refresh from: http://stackoverflow.com/questions/13150073/how-to-dynamically-update-textview-text
        dateButton.invalidate();
    }

    // Get todays date and format it properly
    public String generateTodaysDate() {
        Date todaysDate = new Date();

        // Simple date format from: https://www.mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(todaysDate);
    }

    // Retrieve the date from the button's text
    public String getInputDate() {
        Button selectedDate = (Button) findViewById(R.id.calendar_button);
        return selectedDate.getText().toString();
    }

    // Get the days of the week
    public ArrayList<String> getDaysOfWeek() {
        ArrayList<String> dayChecked = new ArrayList<String>();

        // Create an array containing the id for each checkbox of the days of the week
        // int[] format from: http://stackoverflow.com/questions/26606348/how-to-make-array-of-unique-android-ids
        int[] idArray = new int[]{R.id.checkbox_Sunday, R.id.checkbox_Monday, R.id.checkbox_Tuesday,
                R.id.checkbox_Wednesday, R.id.checkbox_Thursday, R.id.checkbox_Friday, R.id.checkbox_Saturday};

        // Iterate through all of the ckeckboxes
        for (int checkBoxID : idArray) {
            CheckBox isDayChecked = (CheckBox) findViewById(checkBoxID);

            // See if a checkbox has been checked. If so, take the name and convert it to string,
            // then add it to the list of days
            if (isDayChecked.isChecked()) {
                String checkedDayName = isDayChecked.getText().toString();
                dayChecked.add(checkedDayName);
            }
        }

        // Return the list of days
        return dayChecked;
    }

    // Get the habit name that the user entered
    public String getHabitName() {
        EditText enteredHabitName = (EditText) findViewById(R.id.new_habit_text);
        return enteredHabitName.getText().toString();
    }

    // Cancel to exit the activity
    public void cancelNewHabit(View v) {
        finish();
    }

    // When all of the information is filled out and the habit is being created
    public void createNewHabit(View v) {

        // Get an instance of the HabitListController
        HabitListController hlController = new HabitListController();

        // If there is no habit name inputted, toast and do not create the habit
        if (getHabitName().equals("")) {
            Toast.makeText(this, "Please input a habit name", Toast.LENGTH_SHORT).show();
        }

        // If there is no days of the week inputted, toast and do not create the habit
        else if (getDaysOfWeek().size() == 0) {
            Toast.makeText(this, "Please select a day or multiple days to repeat", Toast.LENGTH_SHORT).show();
        }

        // Create the habit
        else {
            Toast.makeText(this, "New habit successfully created!", Toast.LENGTH_SHORT).show();

            // Grab the data inputted for the habit, then use the controller to add a new habit
            // with the specified information
            String newDate = getInputDate();
            String newName = getHabitName();
            ArrayList<String> newDaysOfWeek = getDaysOfWeek();
            hlController.addHabit(new Habit(newName, newDate, newDaysOfWeek));
            finish();
        }

    }

    // Function to open the dialog for date input
    private void dateSelector(Button dateButton) {

        // Instantiate the button
        final Button inputButton = dateButton;

        // Get the EditText that allows for user input
        // Code for inputDate from http://stackoverflow.com/questions/10903754/input-text-dialog-android
        final EditText inputDate = new EditText(this);
        inputDate.setInputType(InputType.TYPE_CLASS_DATETIME);

        // Build the dialog
        AlertDialog.Builder completeDialog = new AlertDialog.Builder(NewHabitScreen.this);
        completeDialog.setView(inputDate);
        completeDialog.setMessage("Change Habit Date (ex. 2016-01-20)");
        completeDialog.setCancelable(true);
        completeDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            // If canceled is pressed, do nothing and the dialog closes
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        // If done is pressed, update the Button's text to show the inputted date
        completeDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                inputButton.setText(inputDate.getText().toString());
            }
        });

        // Show the dialog
        completeDialog.show();
    }
}