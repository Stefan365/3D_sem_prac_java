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
 * vstupna inicializacia DB.
 *
 * @author Stefan Veres
 */

public class P1_1 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {

        HttpSession sessionA = request.getSession();
        
        try {
            //inicializacia DB:
            DBconn.initDB();
            
            
            //tvorba těla odpovědi
            PrintWriter out = response.getWriter();

            Pom.printHead(out, "SEM_PRACE");
            Pom.printCSS(out);

            out.println("</head>");
            out.println("<body>");
            out.println("<h3 id=\"podmenu\">");
            out.println("DATABASE INITIALIZATION WAS SUCCESSFUL!");
            out.println("</h3>"
                + "<div id=\"paticka\">"
                + "<form action = \"first\" method = \"post\">"
                + "<input type=\"submit\" name=\"page\" value=\"BACK\" />"
                + "</form>"
                + "</div>");
            out.println("</body>");
            out.println("</html>");

        } catch (SQLException ex) {
            sessionA.setAttribute("message", "INITIALIZATION FAILED, SOMETHING WENT WRONG!");

            //Dispatcher:
            String go  ="first";        
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
