import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class TransactionHistory extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "system");

            // Query to fetch all transactions (transfer, credit, debit) using UNION
            String query = "SELECT TRANSFER_ID AS ID, USERNAME, SENDER, RECEIVER, AMOUNT, 'Transfer' AS TRANSACTION_TYPE, TRANSFER_DATE AS TRANSACTION_DATE FROM transfer_history " +
                           "UNION " +
                           "SELECT ID, USERNAME, NULL AS SENDER, ACCOUNTNO AS RECEIVER, AMOUNT, 'Credit' AS TRANSACTION_TYPE, TRANSACTION_DATE AS TRANSACTION_DATE FROM credit_history " +
                           "UNION " +
                           "SELECT ID, USERNAME, ACCOUNTNO AS SENDER, NULL AS RECEIVER, AMOUNT, 'Debit' AS TRANSACTION_TYPE, TRANSACTION_DATE AS TRANSACTION_DATE FROM debit_history " +
                           "ORDER BY TRANSACTION_DATE DESC";

            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            // Generate dynamic HTML table rows
            StringBuilder sb = new StringBuilder();
            int serialNo = 1;
            while (rs.next()) {
                sb.append("<tr>")
                  .append("<td>").append(serialNo++).append("</td>")  // Serial number
                  .append("<td>").append(rs.getString("USERNAME")).append("</td>")  // User
                  .append("<td>").append(rs.getString("SENDER") != null ? rs.getString("SENDER") : "-").append("</td>")  // Sender
                  .append("<td>").append(rs.getString("RECEIVER") != null ? rs.getString("RECEIVER") : "-").append("</td>")  // Receiver
                  .append("<td>").append(rs.getLong("AMOUNT")).append("</td>")  // Amount
                  .append("<td>").append(rs.getString("TRANSACTION_TYPE")).append("</td>")  // Transaction Type
                  .append("<td>").append(rs.getString("TRANSACTION_DATE")).append("</td>")  // Date
                  .append("</tr>");
            }

            // Send the generated table rows back to the client
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
