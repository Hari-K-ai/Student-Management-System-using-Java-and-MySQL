import java.sql.*;
import java.util.Scanner;

public class JDBC {
    static final String URL = "jdbc:mysql://localhost:3306/student";
    static final String USER = "root";
    static final String PASS = "PAssword";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "PAssword")) {
        	Class.forName("com.mysql.cj.jdbc.Driver");

            while (true) {
                System.out.println("\n1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Delete Student");
                System.out.println("4. Exit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                if (choice == 1) {
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter age: ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter grade: ");
                    String grade = sc.nextLine();

                    String sql = "INSERT INTO students(name, age, grade) VALUES (?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, name);
                    pstmt.setInt(2, age);
                    pstmt.setString(3, grade);
                    pstmt.executeUpdate();
                    System.out.println("✅ Student added successfully!");

                } else if (choice == 2) {
                    String sql = "SELECT * FROM students";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        System.out.println(rs.getInt("id") + " | " +
                                           rs.getString("name") + " | " +
                                           rs.getInt("age") + " | " +
                                           rs.getString("grade"));
                    }

                } else if (choice == 3) {
                    System.out.print("Enter student ID to delete: ");
                    int id = sc.nextInt();
                    String sql = "DELETE FROM students WHERE id=?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, id);
                    int rows = pstmt.executeUpdate();
                    if (rows > 0)
                        System.out.println("❌ Student deleted successfully!");
                    else
                        System.out.println("⚠️ Student not found!");

                } else if (choice == 4) {
                    System.out.println("Exiting...");
                    break;
                } else {
                    System.out.println("Invalid choice!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

