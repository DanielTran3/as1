# as1 - dtran3-HabitTracker

## YouTube Link to App Demonstration video:
https://www.youtube.com/watch?v=TsWpAhHfC90

## How it Works
To add a habit: On the starting screen press the large green rectangle with a plus sign <br>
->To Modify a habit's date: Click on the date that shows next to "DATE:", which will bring up a dialog for input <br>
To delete a habit: Long click on the habit and a dialog will appear. Press "DELETE" to delete the habit <br>
To complete a habit: Click on the habit and press "COMPLETE" <br>
To view a habit's completions: Click on the habit and press "VIEW COMPLETIONS" <br>
->To delete a habit completion: Click on the completion that you want to delete

## Project Information
Gradle Version: 2.14.1 <br>
Android Plugin Version: 2.1.3 <br>
Android Plugin Repository: jcenter <br>
Default Library Repository: jcenter

## App Properties
Compile SDK Version: API 19 Android 4.4 (KitKat) <br>
Build Tools Version: 24.0.1

## Dependencies
{include=[*.jar], dir=libs} <br>
junit:junit:4.12 <br>
com.android.support:appcompact-v7:24.1.1 <br>
com.google.code.gson:gson:2.7

---

### Main Activity
HabitTracker

### Other Activities
HabitCompletionsScreen <br>
NewHabitScreen

### Java Classes/Interfaces
Habit <br>
HabitList <br>
HabitListController <br>
HabitListener

