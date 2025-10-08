import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.Date;

public class debitbalance extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String accountno = req.getParameter("accountno");
        long debit = Long.parseLong(req.getParameter("amount"));

        try {
            // Load the Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish a connection to the Oracle database
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE", "system", "system");

            // Fetch the current balance and username of the account holder
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(
                "SELECT balance, username FROM account_details WHERE accountno = '" + accountno + "'");

            String username = "";
            long amount = 0;
            while (rs.next()) {
                amount = Long.parseLong(rs.getString(1));
                username = rs.getString(2);
            }

            long newbalance = amount - debit;

            // Update the account balance
            Statement s = con.createStatement();
            s.executeUpdate("UPDATE account_details SET balance = " + newbalance + " WHERE accountno = '" + accountno + "'");

            // Insert the debit transaction into the debit_history table
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO debit_history (id, accountno, username, amount, transaction_date) VALUES (debit_history_seq.nextval, ?, ?, ?, ?)"
            );
            ps.setString(1, accountno);
            ps.setString(2, username);  // Use 'username' instead of 'USERNAME'
            ps.setLong(3, debit);
            ps.setTimestamp(4, new java.sql.Timestamp(new Date().getTime()));  // Current date and time

            ps.executeUpdate();

            out.println("New balance is: " + newbalance);

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
