import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class UpdateAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get Parameters from Form
        String username = request.getParameter("username");
        String fieldname = request.getParameter("fieldname");
        String newvalue = request.getParameter("newvalue");

        // Validate Input
        if (username == null || fieldname == null || newvalue == null || 
            username.isEmpty() || fieldname.isEmpty() || newvalue.isEmpty()) {
            out.println("<p style='color:red;'>Error: All fields are required!</p>");
            return;
        }

        Connection con = null;
        PreparedStatement ps = null;

        try {
            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish Database Connection
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "system");

            // Validate Field Name
            String[] validFields = {"aadharno", "mobileno", "email", "fathername", "accounttype", "balance", "gender"};
            boolean isValidField = false;

            for (String field : validFields) {
                if (field.equalsIgnoreCase(fieldname)) {
                    isValidField = true;
                    break;
                }
            }

            if (!isValidField) {
                out.println("<p style='color:red;'>Error: Invalid field name!</p>");
                return;
            }

            // Create SQL Query for Update
            String query = "UPDATE account_details SET " + fieldname + " = ? WHERE username = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, newvalue);
            ps.setString(2, username);

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                out.println("<p style='color:green;'>Success: Account details updated successfully!</p>");
            } else {
                out.println("<p style='color:red;'>Error: No matching user found!</p>");
            }

        } catch (Exception e) {
            out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
