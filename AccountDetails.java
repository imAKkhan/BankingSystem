import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class AccountDetails extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try {
            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish Database Connection
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "system");

            // Prepare SQL Query
            PreparedStatement ps = con.prepareStatement("SELECT * FROM account_details");
            ResultSet rs = ps.executeQuery();

            // Process Result Set and generate dynamic HTML rows
            int serno = 1;
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + serno + "</td>");
                out.println("<td>" + rs.getString("username") + "</td>");
                out.println("<td>" + rs.getString("accountno") + "</td>");
                out.println("<td>" + rs.getString("aadharno") + "</td>");
                out.println("<td>" + rs.getString("mobileno") + "</td>");
                out.println("<td>" + rs.getString("email") + "</td>");
                out.println("<td>" + rs.getString("fathername") + "</td>");
                out.println("<td>" + rs.getString("accounttype") + "</td>");
                out.println("<td>" + rs.getString("balance") + "</td>");
                out.println("<td>" + rs.getString("gender") + "</td>");
                out.println("<td><a href='update.html?accountno=" + rs.getString("accountno") + "'>UPDATE</a></td>");
                out.println("</tr>");
                serno++;
            }

            // Close Resources
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            out.println("<tr><td colspan='11' style='color:red;'>Error: " + e.getMessage() + "</td></tr>");
        }
    }
}
