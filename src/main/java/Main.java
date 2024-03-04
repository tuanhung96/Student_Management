
import java.sql.*;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while(true) {
            System.out.println("------Student Management------");
            System.out.println("1. Add student");
            System.out.println("2. Get student");
            System.out.println("3. Update student");
            System.out.println("4. Delete student");
            System.out.println("0. Exit");
            System.out.print("Choose command: ");

            String command = sc.nextLine();

            if(command.equals("0")) break;

            switch (command) {
                case "1":
                    Student newStudent = createStudent();
                    saveToDatabase(newStudent);
                    break;
                case "2":
                    System.out.print("Enter ID to get: ");
                    String idGet = sc.nextLine();
                    Student std = getStudentById(idGet);
                    if(std != null) System.out.println(std.toString());
                    else System.out.println("Invalid Id!");
                    break;
                case "3":
                    System.out.print("Enter ID to update: ");
                    String idUpdate = sc.nextLine();
                    if(!checkValidId(idUpdate)) {
                        System.out.println("Student id " + idUpdate + " doesn't exist");
                        break;
                    }
                    while (true) {
                        System.out.println("Field: ");
                        System.out.println("1. Name");
                        System.out.println("2. Phone");
                        System.out.println("0. Exit");
                        System.out.print("Choose field to update: ");
                        String field = sc.nextLine();
                        if(field.equals("0")) break;
                        switch (field) {
                            case "1":
                                System.out.print("1. New Name: ");
                                String name = sc.nextLine();
                                updateStudent(idUpdate, "name", name);
                                break;
                            case "2":
                                System.out.print("2. New Phone: ");
                                String phone = sc.nextLine();
                                updateStudent(idUpdate, "phone", phone);
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
                    boolean deleted = deleteStudentById(idDelete);
                    if(deleted) System.out.println("Delete student id " + idDelete + " successfully!");
                    else System.out.println("Invalid Id!");
                    break;

                default:
                    System.out.println("Please enter reasonable number");
                    break;
            }
        }
    }

    public static Student createStudent() {
        System.out.println("Add Student");
        System.out.print("1. Name: ");
        String name = sc.nextLine();
        System.out.print("2. Phone: ");
        String phone = sc.nextLine();
        Student student = new Student(name, phone);
        return student;
    }

    public static boolean checkValidId(String id) {
        return (getStudentById(id) != null);
    }

    public static void saveToDatabase(Student student) {
        ConnectJDBC connectJDBC = new ConnectJDBC();
        Connection conn = connectJDBC.connect();

        String query = "INSERT INTO student(id, name, phone) " +
                "VALUES (null, ?,?)";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(query);

            pstm.setString(1, student.getName());
            pstm.setString(2, student.getPhone());

            //Khi thực hiện các lệnh insert/update/delete sử dụng executeUpdate, nó sẽ trả về số hàng bị tác động
            int row = pstm.executeUpdate();
            if(row != 0) System.out.println("Add student successfully!");

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Student getStudentById(String studentId) {
        ConnectJDBC connectJDBC = new ConnectJDBC();
        Connection conn = connectJDBC.connect();

        String query = "SELECT * FROM student WHERE id = ?";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, Integer.parseInt(studentId));
            ResultSet rs = pstm.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                Student student = new Student();
                student.setId(Integer.toString(id));
                student.setName(name);
                student.setPhone(phone);
                return student;
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static boolean deleteStudentById(String studentId) {
        ConnectJDBC connectJDBC = new ConnectJDBC();
        Connection conn = connectJDBC.connect();

        String query = "DELETE FROM student WHERE id = ?";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(query);

            pstm.setInt(1, Integer.parseInt(studentId));

            int row = pstm.executeUpdate();
            if(row != 0) return true;

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static void updateStudent(String id, String key, String value) {
        ConnectJDBC connectJDBC = new ConnectJDBC();
        Connection conn = connectJDBC.connect();

        String query = "UPDATE student SET " + key + " = ? WHERE id = ?";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(query);

            pstm.setString(1, value);
            pstm.setInt(2, Integer.parseInt(id));

            int row = pstm.executeUpdate();
            if(row != 0){
                System.out.println("Update successfully!");
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}