import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class TransactionHistoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // Retrieve account number from the request
        String accountno = req.getParameter("accountno");

        if (accountno == null || accountno.trim().isEmpty()) {
            out.println(""); // No data to return if accountno is missing
            return;
        }

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "system");

            // Query to get transaction history for a specific account number
            String query = "SELECT transfer_id, username, sender, receiver, amount, transfer_date " +
                           "FROM transfer_history WHERE sender = ? OR receiver = ? ORDER BY transfer_date DESC";
            ps = con.prepareStatement(query);
            ps.setString(1, accountno);
            ps.setString(2, accountno);
            rs = ps.executeQuery();

            // Prepare the table rows dynamically
            StringBuilder sb = new StringBuilder();
            int serialNo = 1;
            while (rs.next()) {
                sb.append("<tr>")
                  .append("<td>").append(serialNo++).append("</td>")  // Serial No
                  .append("<td>").append(rs.getString("username")).append("</td>")  // Username
                  .append("<td>").append(rs.getString("sender")).append("</td>")  // Sender
                  .append("<td>").append(rs.getString("receiver")).append("</td>")  // Receiver
                  .append("<td>").append(rs.getLong("amount")).append("</td>")  // Amount
                  .append("<td>").append(rs.getDate("transfer_date")).append("</td>")  // Transfer Date
                  .append("</tr>");
            }

            // Send the data back to the client
            out.print(sb.toString());

        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace(out);
            }
            out.close();
        }
    }
}
