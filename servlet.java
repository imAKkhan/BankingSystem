import java.io.*;
import java.sql.*;

public class servlet implements Servlet {

    public void init(ServletConfig h) {
        // Initialization code (if needed)
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();

        // Get form data
        String name = req.getParameter("username");
        String pass = req.getParameter("password");
        String email = req.getParameter("email");
        String contact = req.getParameter("contact");

        try {
            // Load the Oracle driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish a connection to the Oracle database
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "system");

            // Prepare SQL insert statement
            PreparedStatement ps = con.prepareStatement("INSERT INTO employee (username, password, email, contact) VALUES (?, ?, ?, ?)");

            // Set parameters for the SQL query
            ps.setString(1, name);
            ps.setString(2, pass);
            ps.setString(3, email);
            ps.setString(4, contact);

            // Execute the insert
            ps.executeUpdate();

            // Forward to the index.html page after successful registration
            RequestDispatcher dispatcher = req.getRequestDispatcher("index.html");
            dispatcher.include(req, res);

            // Success message
            pw.println("<br>Hey " + name + ", you are registered successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<br>Error: " + e.getMessage());
        } finally {
            pw.close();
        }
    }

    public void destroy() {
        // Cleanup code (if needed)
    }

    public String getServletInfo() {
        return "Signup Servlet - Handles user registration";
    }

    public ServletConfig getServletConfig() {
        return null;
    }
}
