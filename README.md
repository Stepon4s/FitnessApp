# Fitness Tracking Application

This project is a comprehensive fitness tracking application developed in Kotlin using Jetpack Compose UI toolkit, XML layout and Room for local data persistence. The application provides a user-friendly interface for users to manage their workouts and exercises. Here are the main components of the project:

**Exercise Management** - The application allows users to add, view, and delete exercises. Each exercise has a unique title and is associated with a specific workout. The *ExerciseNameDialog* composable function is used to add new exercises, and the *ExerciseNameScreen* displays all the exercises.

**Workout Management** - Workouts are managed through the *WorkoutActivity* class. Each workout has a date and a list of exercises. The *WorkoutDatabase* class is used to interact with the Room database for CRUD operations related to workouts.

**Set Management** - Each exercise can have multiple sets, and each set includes the number of repetitions and the weight used. The *SetAdapter* class is used to display the sets associated with an exercise in a RecyclerView.

**User Interface** - The user interface is built using Jetpack Compose. The *ExerciseAdapter* and *WorkoutAdapter* classes are used to display exercises and workouts in a RecyclerView, respectively. The *MainActivity* class is the entry point of the application, and it uses a GestureDetector to handle user gestures.

**Database** - The application uses Room for local data persistence. The *WorkoutDatabase* and *ExerciseNameDatabase* classes provide access to the database and define the DAOs for interacting with the database.

**Event Handling** - The application uses a sealed interface *ExerciseNameEvent* to handle various events such as saving an exercise name, showing/hiding a dialog, sorting exercises, deleting an exercise name, and clicking an exercise name.

Please note that this project is currently **under development** and is being **updated constantly**. While the application is functional and showcases a variety of features, there are still more features to be added and improvements to be made. Stay tuned for updates!
