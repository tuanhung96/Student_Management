import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Student> students = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while(true) {
            System.out.println("Hello world!");
            System.out.println("1. Add student");
            System.out.println("2. Get student");
            System.out.println("3. Update student");
            System.out.println("4. Delete student");
            System.out.println("5. Save to a file");
            System.out.println("0. Exit");
            System.out.print("Choose command: ");

            String command = sc.nextLine();

            if(command.equals("0")) break;
            switch (command) {
                case "1":
                    Student newStudent = addStudent();
                    students.add(newStudent);
                    System.out.println("Add student successfully! ");
                    break;
                case "2":
                    System.out.print("Enter ID to get: ");
                    String idGet = sc.nextLine();
                    boolean validId = false;
                    for (Student student: students) {
                        if(student.getId().equals(idGet)) {
                            validId = true;
                            System.out.println(student.toString());
                        }
                    }
                    if (!validId) System.out.println("Invalid Id!");
                    break;
                case "3":
                    System.out.print("Enter ID to update: ");
                    String idUpdate = sc.nextLine();
                    if(!checkValidId(idUpdate)) {
                        System.out.println("Invalid Id!");
                        break;
                    }
                    while (true) {
                        System.out.println("Field: ");
                        System.out.println("1. ID");
                        System.out.println("2. Name");
                        System.out.println("3. Phone");
                        System.out.println("0. Exit");
                        System.out.print("Choose field to update: ");
                        String field = sc.nextLine();
                        if(field.equals("0")) break;
                        switch (field) {
                            case "1":
                                System.out.print("1. New ID: ");
                                String id = sc.nextLine();
                                for (Student student: students) {
                                    if(student.getId().equals(idUpdate)) {
                                        student.setId(id);
                                        System.out.print("Update id " + idUpdate + " successfully!");
                                    }
                                }
                                break;
                            case "2":
                                System.out.print("2. New Name: ");
                                String name = sc.nextLine();
                                for (Student student: students) {
                                    if(student.getId().equals(idUpdate)) {
                                        student.setName(name);
                                        System.out.print("Update name id " + idUpdate + " successfully!");
                                    }
                                }
                                break;
                            case "3":
                                System.out.print("3. New Phone: ");
                                String phone = sc.nextLine();
                                for (Student student: students) {
                                    if(student.getId().equals(idUpdate)) {
                                        student.setPhone(phone);
                                        System.out.print("Update phone id " + idUpdate + " successfully!");
                                    }
                                }
                                break;
                            default:
                                System.out.println("Please enter reasonable field");
                                break;
                        }
                    }
                    break;

                case "4":
                    System.out.print("Enter ID to delete: ");
                    String idDelete = sc.nextLine();
                    boolean validIdDelete = false;
                    for (int i=0; i< students.size(); i++) {
                        if(students.get(i).getId().equals(idDelete)) {
                            validIdDelete = true;
                            students.remove(i);
                            System.out.println("Delete student id " + idDelete + " successfully!");
                        }
                    }
                    if (!validIdDelete) System.out.println("Invalid Id!");
                    break;

                case "5":
                    try(FileWriter file = new FileWriter("student.txt")) {
                        for (Student student: students) {
                            file.write(student.getId() + "," + student.getName() + "," + student.getPhone() + "\n");
                        }
                        System.out.println("Write to file successfully");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    System.out.println("Please enter reasonable number");
                    break;
            }
        }
    }

    public static boolean checkValidId(String id) {
        boolean validId = false;
        for (Student student: students) {
            if(student.getId().equals(id)) validId = true;
        }
        return validId;
    }

    public static Student addStudent() {
        System.out.println("Add Student");
        System.out.print("1. ID: ");
        String id = sc.nextLine();
        System.out.print("2. Name: ");
        String name = sc.nextLine();
        System.out.print("3. Phone: ");
        String phone = sc.nextLine();
        Student student = new Student(id, name, phone);
        return student;
    }
}