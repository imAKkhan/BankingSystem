import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class TransferServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String fromAccount = req.getParameter("fromAccount");
        String toAccount = req.getParameter("toAccount");
        long amount = Long.parseLong(req.getParameter("amount"));

        if (fromAccount.equals(toAccount)) {
            out.println("<h3>Transfer failed: From and To account cannot be the same.</h3>");
            RequestDispatcher disp = req.getRequestDispatcher("transfer.html");
            disp.include(req, res);
            return;
        }

        Connection con = null;
        PreparedStatement ps1 = null, ps2 = null, ps3 = null, ps4 = null, psFetchUsername = null;
        ResultSet rs1 = null, rsUsername = null;
        String username = null;

        try {
            
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "system");
            con.setAutoCommit(false);

           
            psFetchUsername = con.prepareStatement("SELECT username FROM account_details WHERE accountno = ?");
            psFetchUsername.setString(1, fromAccount);
            rsUsername = psFetchUsername.executeQuery();

            if (rsUsername.next()) {
                username = rsUsername.getString("username"); 
            } else {
                out.println("<h3>From Account not found.</h3>");
                return;
            }

           
            ps1 = con.prepareStatement("SELECT balance FROM account_details WHERE accountno = ?");
            ps1.setString(1, fromAccount);
            rs1 = ps1.executeQuery();

            if (rs1.next()) {
                long fromBalance = rs1.getLong("balance");

                if (fromBalance < amount) {
                    out.println("<h3>Insufficient balance in the From Account.</h3>");

                   
                    ps4 = con.prepareStatement(
                        "INSERT INTO transfer_history (TRANSFER_ID, USERNAME, SENDER, RECEIVER, AMOUNT, TRANSFER_DATE, STATUS) " +
                        "VALUES (transfer_history_seq.NEXTVAL, ?, ?, ?, ?, SYSDATE, 'FAILED')"
                    );
                    ps4.setString(1, username);
                    ps4.setString(2, fromAccount);
                    ps4.setString(3, toAccount);
                    ps4.setLong(4, amount);
                    ps4.executeUpdate();
                    con.commit();

                } else {
                    
                    ps2 = con.prepareStatement("UPDATE account_details SET balance = balance - ? WHERE accountno = ?");
                    ps2.setLong(1, amount);
                    ps2.setString(2, fromAccount);
                    ps2.executeUpdate();

                    
                    ps3 = con.prepareStatement("UPDATE account_details SET balance = balance + ? WHERE accountno = ?");
                    ps3.setLong(1, amount);
                    ps3.setString(2, toAccount);
                    ps3.executeUpdate();

                    
                    ps4 = con.prepareStatement(
                        "INSERT INTO transfer_history (TRANSFER_ID, USERNAME, SENDER, RECEIVER, AMOUNT, TRANSFER_DATE, STATUS) " +
                        "VALUES (transfer_history_seq.NEXTVAL, ?, ?, ?, ?, SYSDATE, 'SUCCESS')"
                    );
                    ps4.setString(1, username);
                    ps4.setString(2, fromAccount);
                    ps4.setString(3, toAccount);
                    ps4.setLong(4, amount);
                    ps4.executeUpdate();

                    con.commit();
                    out.println("<h3>Transfer successful! Amount: " + amount + " transferred to Account: " + toAccount + ".</h3>");
                }
            } else {
                out.println("<h3>From Account not found.</h3>");
            }

            RequestDispatcher disp = req.getRequestDispatcher("userhome.html");
            disp.include(req, res);

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace(out);
            }
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        } finally {
            
            try {
                if (rsUsername != null) rsUsername.close();
                if (rs1 != null) rs1.close();
                if (psFetchUsername != null) psFetchUsername.close();
                if (ps1 != null) ps1.close();
                if (ps2 != null) ps2.close();
                if (ps3 != null) ps3.close();
                if (ps4 != null) ps4.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace(out);
            }
            out.close();
        }
    }
}
