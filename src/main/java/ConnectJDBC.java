
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectJDBC {
    private String hostName = "localhost:3306";
    private String dbName = "student-management";
    private String username = "root";
    private String password = "Tuanhung96";

    private String connectionURL = "jdbc:mysql://"+hostName+"/"+dbName;

    public Connection connect(){
        Connection connnection = null;

        try {
            connnection = DriverManager.getConnection(connectionURL, username, password);
//            System.out.println("Connect database successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connnection;
    }
}

