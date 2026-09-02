# Sorting Showdown

<img width="1792" height="904" alt="SortingShowdown" src="https://github.com/user-attachments/assets/4a75c9eb-9645-4eb8-aedb-9af8d4fb866c" />

Sorting Showdown is a Java-based application designed to benchmark and compare the performance of various sorting algorithms. 

## Prerequisites

You will need **Java 8 or higher** (I had originally built the project with Java 25 but decided to lower that, so forgive me for / tell me of any compatability issues).
To run the project, Apache Maven is preferred. The tests use JUnit 5.

## Download, Setup, and Running the Project

1. **Clone the Repository:** Download the project files to your local machine using Git:
   ```
   git clone https://github.com/nickrezuke/Sorting-Showdown.git
   ```
   *(Alternatively, if you downloaded the project as a .zip file, extract it to a directory of your choice).*

2. **Navigate to the Project Directory:**
   ```
   cd Sorting-Showdown
   ```

3. Now you're ready to **Run the Project:** (either with or without maven, as outlined below...)

### Running the Project (with Maven)

Maven handles all the dependencies and compiling for you. Use the following commands in your terminal inside the project root folder.

4. **Build the Project:** Compile the code and package it into a runnable format.
   ```
   mvn clean package
   ```

5. **Run the Application:** Execute the main application class.
   ```
   mvn exec:java
   ```

### Running the Project (without Maven)

This project can still be utilized if your computer doesn't have Maven.
To run this project without Maven, run this from the base Sorting Showdown folder:
   ```
   cd src/main/java && javac *.java && java SortingShowdown ; rm *.class && rm -rf target ; cd ../../..
   ```
This command will navigate to the correct folder, compile and run the code.  It will also 
clean up its own build artifacts such as the leftover java .class files and target folder 
of generated txt files of example arrays used by the program, all before navigating 
back to the original folder.

## Testing

Test cases can be found in src/test/java
