import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class ChangePassword extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get the form data
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        // Response Writer
        PrintWriter pw = res.getWriter();

        // Check if any fields are missing or if passwords don't match
        if (currentPassword == null || newPassword == null || confirmPassword == null || !newPassword.equals(confirmPassword)) {
            pw.println("<br>Error: All fields are required or passwords don't match.");
            return;
        }

        Connection con = null;
        PreparedStatement psCheck = null;
        PreparedStatement psUpdate = null;

        try {
            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish database connection
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "system");

            // Check if the current password is correct
            psCheck = con.prepareStatement("SELECT * FROM employee WHERE password = ?");
            psCheck.setString(1, currentPassword);
            ResultSet rs = psCheck.executeQuery();

            // If the current password is correct, update to the new password
            if (rs.next()) {
                psUpdate = con.prepareStatement("UPDATE employee SET password = ? WHERE password = ?");
                psUpdate.setString(1, newPassword);
                psUpdate.setString(2, currentPassword);
                int updatedRows = psUpdate.executeUpdate();
                if (updatedRows > 0) {
                    pw.println("<br>Password updated successfully. Please <a href='home.html'>login again</a>.");
                } else {
                    pw.println("<br>Error: Password update failed.");
                }
            } else {
                pw.println("<br>Error: Current password is incorrect.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<br>Unexpected error: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (psCheck != null) psCheck.close();
                if (psUpdate != null) psUpdate.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
