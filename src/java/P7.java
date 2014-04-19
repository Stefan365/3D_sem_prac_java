
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pak1.DBconn;
import pak1.Pom;

/**
 * Adminovske prostredie na upravu dat usera. (neskvor zvazit zlucenie s P5)
 *
 * @author Stefan Veres
 */
public class P7 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {

        HttpSession sessionH = request.getSession();

        String lg = (String) sessionH.getAttribute("login");
        String uid = (String) sessionH.getAttribute("uid");

        String fn, ln, pw, role;

        //A.Vylouceni neautorizovaneho pristupu:
        if (!(Pom.isAdmin(uid))) {
            sessionH.setAttribute("message", "LOGIN INCORRECT, PLEASE LOGIN AGAIN!");
            //Dispatcher:
            String go = "first";
            response.sendRedirect(response.encodeRedirectURL(go));
        }
        
        String comboName = request.getParameter("sel_user");
        String sel_uid = Pom.getIdFromComboName(comboName);

        //zapis to do session:
        sessionH.setAttribute("sel_user", comboName);

        
        try {
            fn = DBconn.getUserFn(sel_uid);
            ln = DBconn.getUserLn(sel_uid);
            pw = DBconn.getUserPw(sel_uid);
            role = DBconn.getUserRole(sel_uid);

            // příprava odpovědi pro klienta
            response.setContentType("text/html");
            response.setCharacterEncoding("utf-8");

            PrintWriter out = response.getWriter();
            Pom.printHead(out, lg);
            Pom.printCSS(out);
            out.println("</head>");

            out.println("<body>");
            out.println("<h3>");
            out.println("DATA UPDATE:");
            out.println("</h3>");
            out.println("<div>"
                + "<form action = \"seventh_1\" method = \"post\">"
                + "FIRST NAME   : "
                + "<input type=\"text\" value=\"" + fn + "\" name=\"firstname\"/><br/>"
                + "LAST NAME    : "
                + "<input type=\"text\" value=\"" + ln + "\" name=\"lastname\"/><br/>"
                + "NEW PASSWORD : "
                + "<input type=\"password\" name=\"password\"/><br/>" 
                + "ROLE         : "
                + "<input type=\"text\" value=\"" + role + "\" name=\"role\"/><br/>"
            
                //ODOSIELACIE TLACITKO:
                + "<input type=\"submit\" value=\"SAVE\">"
                + "</form>"
                + "</div>");
            
            //DELETE USER:
            out.println("<div>"
                + "<form action = \"sixth_1\" method = \"post\">"
                + "<input type=\"submit\" value=\"DELETE USER\">"
                + "</form>"
                + "</div>");
                
            //SPATNE TLACITKO:
            out.println("<div id=\"paticka\">"
                + "<form action = \"sixth\" method = \"post\">"
                + "<input type=\"submit\" value=\"BACK\" />"
                + "</form>"
                + "</div>");
            
            out.println("</body>");
            out.println("</html>");

        } catch (SQLException ex) {
            sessionH.setAttribute("message", "TRY IT AGAIN ADMIN PLEASE, SOMETHING WENT WRONG!");
            
            //Dispatcher:
            String go = "sixth";
            response.sendRedirect(response.encodeRedirectURL(go));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {

        doPost(request, response);
    }
}
