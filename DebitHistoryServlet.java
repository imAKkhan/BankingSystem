import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class DebitHistoryServlet extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // Get account number from request
        String accountno = req.getParameter("accountno");

        if (accountno != null && !accountno.isEmpty()) {
            try {
                // Connect to Oracle DB
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "system");

                // Fetch debit history based on account number
                PreparedStatement stmt = con.prepareStatement(
                        "SELECT id, accountno, username, amount, transaction_date FROM debit_history WHERE accountno = ?");
                stmt.setString(1, accountno);
                ResultSet rs = stmt.executeQuery();

                // Generate dynamic table rows
                StringBuilder tableContent = new StringBuilder();
                int count = 1;

                // Process the result set and populate the table
                while (rs.next()) {
                    tableContent.append("<tr>");
                    tableContent.append("<td>").append(count++).append("</td>");
                    
                    tableContent.append("<td>").append(rs.getString("username")).append("</td>");
                    tableContent.append("<td>").append(rs.getDouble("amount")).append("</td>");
                    tableContent.append("<td>").append(rs.getDate("transaction_date")).append("</td>");
                    tableContent.append("</tr>");
                }

                // Output the HTML page with dynamic data
                out.println("<html>");
                out.println("<head><link rel='stylesheet' href='debitHistory.css'></head>");
                out.println("<body>");
                out.println("<h1>Debit History</h1>");
                out.println("<table class='list'><thead><tr><th>Ser No</th><th>Username</th><th>Amount</th><th>Date</th></tr></thead>");
                out.println("<tbody>");
                out.println(tableContent.toString());
                out.println("</tbody>");
                out.println("</table>");
                out.println("</body>");
                out.println("</html>");
            } catch (Exception e) {
                out.println("<h3>Error: " + e.getMessage() + "</h3>");
                e.printStackTrace(out);
            }
        } else {
            out.println("<h3>Please provide a valid account number.</h3>");
        }
    }
}
