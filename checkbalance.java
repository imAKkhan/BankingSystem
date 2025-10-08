import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class checkbalance extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String accountno = req.getParameter("accountno");

        try {
            // Load the Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish a connection to the Oracle database
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE", "system", "system");

            // Fetch the balance of the account
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(
                "SELECT balance FROM account_details WHERE accountno = '" + accountno + "'");

            if (rs.next()) {
                long balance = Long.parseLong(rs.getString(1));
                out.println("<h3>Account Number: " + accountno + "</h3>");
                out.println("<h3>Current Balance: " + balance + "</h3>");
            } else {
                out.println("<h3>Account not found!</h3>");
            }

            // Redirect back to the user home page
            RequestDispatcher disp = req.getRequestDispatcher("userhome.html");
            disp.include(req, res);

        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        } finally {
            out.close();
        }
    }
}
