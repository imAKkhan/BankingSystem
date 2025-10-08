import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class creditbalance extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String accountno = req.getParameter("accountno");
        long credit = Long.parseLong(req.getParameter("amount"));
        String username = ""; // Default, will be updated with the actual username.

        try {
            // Load the Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish a connection to the Oracle database
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE", "system", "system");

            // Fetch the current balance of the account
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(
                "SELECT balance, username FROM account_details WHERE accountno = '" + accountno + "'");

            // Fetch the balance and username
            while (rs.next()) {
                long amount = rs.getLong("balance");
                username = rs.getString("username"); // Retrieve the username

                long newbalance = amount + credit;

                // Update the balance in account_details table
                Statement s = con.createStatement();
                s.executeUpdate("UPDATE account_details SET balance = " + newbalance + " WHERE accountno = '" + accountno + "'");

                // Insert the credit history
                PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO credit_history (id, accountno, username, amount, transaction_date) VALUES (credit_history_seq.NEXTVAL, ?, ?, ?, SYSDATE)");
                stmt.setString(1, accountno);
                stmt.setString(2, username); // Set the username
                stmt.setLong(3, credit);
                stmt.executeUpdate();

                out.println("New balance is: " + newbalance);
            }

            // Redirect to the user home page
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
