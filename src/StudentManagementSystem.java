// Imported the libraries
import java.io.*;
import java.util.*;


class StudentManagementSystem {
    // Define a constant for the maximum number of students
    private static final int MAX_STUDENTS = 100;
    // Create an array to store student objects, with a capacity defined by MAX_STUDENTS
    private static Student[] students = new Student[MAX_STUDENTS];
    // Maintain a count of the current number of students
    private static int numStudents = 0;

    public static void main(String[] args) {
        // Create a Scanner object for reading input from the console
        Scanner scannerObj = new Scanner(System.in);
        // Variable to store the user's menu choice
        int choice;
        
        // Loop to display the menu and process user choices until the user decides to exit
        do {
            //Here is the menu for the Student Activity Management System
            System.out.println("==================================================");
            System.out.print("| ");
            System.out.print("Welcome to, Student Activity Management System");
            System.out.println(" |");
            System.out.println("==================================================");
            System.out.println(" ");

            System.out.println("1. Check available seats");
            System.out.println("2. Register student (with ID)");
            System.out.println("3. Delete student");
            System.out.println("4. Find student (with student ID)");
            System.out.println("5. Store student details into a file");
            System.out.println("6. Load student details from the file to the system");
            System.out.println("7. View the list of students based on their names");
            System.out.println("8. Add student name and module marks");
            System.out.println("9. Generate a summary of the system");
            System.out.println("10. Generate complete report with list of students");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scannerObj.nextInt();
            
            System.out.println("__________________________________________________");
            System.out.println(" ");
            System.out.println(" ");



            
            // Switch statement to handle the user's choice
            switch (choice) {
                case 1:
                    checkAvailableSeats();
                    break;
                case 2:
                    registerStudent(scannerObj);
                    break;
                case 3:
                    deleteStudent(scannerObj);
                    break;
                case 4:
                    findStudent(scannerObj);
                    break;
                case 5:
                    storeStudentDetailsToFile(scannerObj);
                    break;
                case 6:
                    loadStudentDetailsFromFile(scannerObj);
                    break;
                case 7:
                    viewStudentsByName();
                    break;
                case 8:
                    addStudentNameAndModuleMarks(scannerObj);
                    break;
                    
                case 9:
                    generateSummary();
                    break;
                case 10:
                    generateCompleteReport();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again....!!!");
                    break;

               
            }
        } while (choice != 0); // Continue until the user chooses to exit

        
        scannerObj.close();
    }

    // Method to check and display the number of available seats based on the maximum student limit and current number of students
    private static void checkAvailableSeats() {
        //Calculate the available seats
        int availableSeats = MAX_STUDENTS - numStudents;
        // Display the number of available seats
        System.out.println("Available seats: " + availableSeats);
    }


    // Method to register a new student if the maximum student limit has not been reache
    private static void registerStudent(Scanner scanner) {
        // Check if the student limit is reached
        
        if (numStudents == MAX_STUDENTS) {
            // Inform the user that no more students can be registered
            System.out.println("Student limit exceeded...You can't register more students");
            return;
        }

        System.out.print("Enter student ID: ");
        String studentID = scanner.next();
        System.out.print("Enter student First name : ");
        String studentName = scanner.next();
        int[] moduleMarks = new int[3];
        System.out.print("Enter module 1 marks: ");
        moduleMarks[0] = scanner.nextInt();
        System.out.print("Enter module 2 marks: ");
        moduleMarks[1] = scanner.nextInt();
        System.out.print("Enter module 3 marks: ");
        moduleMarks[2] = scanner.nextInt();

        // Create a new Student object and add it to the students array
        students[numStudents] = new Student(studentID, studentName, moduleMarks);
        numStudents++;

        System.out.println("Student registered successfully...!!!");
    }

    // Method to delete a student based on the student ID
    private static void deleteStudent(Scanner scanner) {
        System.out.print("Enter student ID: ");
        String studentID = scanner.next();

        // Find the index of the student in the array
        int index = findStudentIndex(studentID);
        if (index == -1) {
            System.out.println("Student not found...!!!");
            return;
        }

        // Shift all students one position to the left from the index of the deleted student
        for (int i = index; i < numStudents - 1; i++) {
            students[i] = students[i + 1];
        }
        numStudents--;

        System.out.println("Student deleted successfully...!!!");
    }

    // Method to find and display details of a student based on the student ID
    private static void findStudent(Scanner scanner) {
        System.out.print("Enter student ID: ");
        String studentID = scanner.next();

        // Find the index of the student in the array
        int index = findStudentIndex(studentID);
        if (index == -1) {
            System.out.println("Student not found...!!!");
            return;
        }

         // Retrieve the student object from the array
        Student student = students[index];

         // Display student details
        System.out.println("Student ID: " + student.getStudentID());
        System.out.println("Student Name: " + student.getStudentName());
        System.out.println("Module 1 Marks: " + student.getModuleMarks()[0]);
        System.out.println("Module 2 Marks: " + student.getModuleMarks()[1]);
        System.out.println("Module 3 Marks: " + student.getModuleMarks()[2]);
        System.out.println("Module Grade: " + student.getModuleGrade());
    }


    // Method to store details of all students to a file
    private static void storeStudentDetailsToFile(Scanner scanner) {
        System.out.print("Please Enter Student First name to save: ");
        String fileName = scanner.next();

        // Try-with-resources to automatically close PrintWriter
        try (PrintWriter writer = new PrintWriter("StudentDATA.txt")) {
            for (int i = 0; i < numStudents; i++) {
                Student student = students[i];
                writer.println(student.getStudentID() + "," + student.getStudentName() + "," +
                        student.getModuleMarks()[0] + "," + student.getModuleMarks()[1] + "," +
                        student.getModuleMarks()[2]);
            }
            System.out.println("Student details stored in file successfully...!!!");
        } catch (IOException e) {
            // Handle potential IOException by displaying an error message
            System.err.println("Error storing student details in file.");
        }
    }

    // Method to load student details from a file
    private static void loadStudentDetailsFromFile(Scanner scanner) {
        System.out.print("Press any key + (Enter) : ");
        String fileName = scanner.next("StudentDATA.txt");

        try (Scanner fileScanner = new Scanner(new File("StudentDATA.txt"))) {
            // Reset the number of students to 0
            numStudents = 0;
            while (fileScanner.hasNextLine()) {
                // Read each line from the file and split it into parts
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                String studentID = parts[0];
                String studentName = parts[1];
                int[] moduleMarks = new int[3];
                // Parse the module marks from the parts array
                moduleMarks[0] = Integer.parseInt(parts[2]);
                moduleMarks[1] = Integer.parseInt(parts[3]);
                moduleMarks[2] = Integer.parseInt(parts[4]);

                // Create a new Student object and add it to the students array
                students[numStudents] = new Student(studentID, studentName, moduleMarks);
                numStudents++;
            }
            System.out.println("Student details loaded from file successfully.");
        } catch (FileNotFoundException e) {
            // Handle potential FileNotFoundException by displaying an error message
            System.err.println("File not found.");
        }
    }

    // Method to view students sorted by name
    private static void viewStudentsByName() {
        Student[] sortedStudents = Arrays.copyOf(students, numStudents);
        Arrays.sort(sortedStudents, Comparator.comparing(Student::getStudentName));

        // Display the student details
        for (Student student : sortedStudents) {
            System.out.println("Student ID: " + student.getStudentID());
            System.out.println("Student Name: " + student.getStudentName());
            System.out.println("Module 1 Marks: " + student.getModuleMarks()[0]);
            System.out.println("Module 2 Marks: " + student.getModuleMarks()[1]);
            System.out.println("Module 3 Marks: " + student.getModuleMarks()[2]);
            System.out.println("Module Grade: " + student.getModuleGrade());
            System.out.println();
        }
    }

    // Method to add student name and module marks
    private static void addStudentNameAndModuleMarks(Scanner scanner) {
        System.out.print("Enter student ID: ");
        String studentID = scanner.next();

        // Find the index of the student in the array
        int index = findStudentIndex(studentID);
        // If student not found, display an error message and return
        if (index == -1) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter student name: ");
        String studentName = scanner.next();
        // Update the student details with the new name
        students[index] = new Student(studentID, studentName, students[index].getModuleMarks());

        // Update the module marks
        System.out.print("Enter module 1 marks: ");
        students[index].getModuleMarks()[0] = scanner.nextInt();
        System.out.print("Enter module 2 marks: ");
        students[index].getModuleMarks()[1] = scanner.nextInt();
        System.out.print("Enter module 3 marks: ");
        students[index].getModuleMarks()[2] = scanner.nextInt();

        System.out.println("Student details updated successfully.");
    }

    // Method to generate a summary of the student management system
    private static void generateSummary() {
        System.out.println("Total student registrations: " + numStudents);

        int numStudentsPassedModule1 = 0;
        int numStudentsPassedModule2 = 0;
        int numStudentsPassedModule3 = 0;

        // Count the number of students who passed each module
        for (int i = 0; i < numStudents; i++) {
            if (students[i].getModuleMarks()[0] > 40) {
                numStudentsPassedModule1++;
            }
            if (students[i].getModuleMarks()[1] > 40) {
                numStudentsPassedModule2++;
            }
            if (students[i].getModuleMarks()[2] > 40) {
                numStudentsPassedModule3++;
            }
        }

        // Display the summary
        System.out.println("Total no of students who scored more than 40 marks in Module 1: " + numStudentsPassedModule1);
        System.out.println("Total no of students who scored more than 40 marks in Module 2: " + numStudentsPassedModule2);
        System.out.println("Total no of students who scored more than 40 marks in Module 3: " + numStudentsPassedModule3);
    }

    // Method to generate a complete report with the list of students
    private static void generateCompleteReport() {
        Student[] sortedStudents = Arrays.copyOf(students, numStudents);
        Arrays.sort(sortedStudents, Comparator.comparing(Student::getModuleGrade).reversed());

        // Display the complete report
        for (Student student : sortedStudents) {
            System.out.println("Student ID: " + student.getStudentID());
            System.out.println("Student Name: " + student.getStudentName());
            System.out.println("Module 1 Marks: " + student.getModuleMarks()[0]);
            System.out.println("Module 2 Marks: " + student.getModuleMarks()[1]);
            System.out.println("Module 3 Marks: " + student.getModuleMarks()[2]);
            System.out.println("Total: " + Arrays.stream(student.getModuleMarks()).sum());
            System.out.println("Average: " + Arrays.stream(student.getModuleMarks()).average().orElse(0));
            System.out.println("Grade: " + student.getModuleGrade());
            System.out.println();
        }
    }

    // Method to find the index of a student in the students array based on the student ID
    private static int findStudentIndex(String studentID) {
        // Iterate through the students array to find the student with the given student ID
        for (int i = 0; i < numStudents; i++) {
            if (students[i].getStudentID().equals(studentID)) {
                return i;
            }
        }
        return -1;
    }

}
///////////// Defining the Student class//////////////////
class Student {
    private String studentID;
    private String studentName;
    private int[] moduleMarks;
    private String moduleGrade;

    // Constructor for the Student class
    public Student(String studentID, String studentName, int[] moduleMarks) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.moduleMarks = moduleMarks;
        this.moduleGrade = calculateModuleGrade();
    }

    // Getter method for studentID
    public String getStudentID() {
        return studentID;
    }

    // Getter method for studentName
    public String getStudentName() {
        return studentName;
    }

    // Getter method for moduleMarks
    public int[] getModuleMarks() {
        return moduleMarks;
    }

    // Getter method for moduleGrade
    public String getModuleGrade() {
        return moduleGrade;
    }

    // Private method to calculate module grade based on module marks
    private String calculateModuleGrade() {
        int totalMarks = 0;
        for (int mark : moduleMarks) {
            totalMarks += mark;
        }
        int averageMarks = totalMarks / moduleMarks.length;

        if (averageMarks >= 80) {
            return "Distinction";
        } else if (averageMarks >= 70) {
            return "Merit";
        } else if (averageMarks >= 40) {
            return "Pass";
        } else {
            return "Fail";
        }
    }
}
