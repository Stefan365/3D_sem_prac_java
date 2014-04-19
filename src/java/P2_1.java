
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pak1.Pom;

/**
 * Spracovava udaje z registracie
 *
 * @author Stefan Veres
 */
public class P2_1 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {

        request.setCharacterEncoding("utf-8");
        HttpSession sessionB = request.getSession();

        try {
            // dane udaje z registracneho formulara zapise do DB.
            Pom.zapisDbUser(request);
            
            
            // příprava odpovědi pro klienta
            response.setContentType("text/html");
            response.setCharacterEncoding("utf-8");

            //tvorba těla odpovědi
            PrintWriter out = response.getWriter();

            Pom.printHead(out, "SEM_PRACE");
            Pom.printCSS(out);

            out.println("</head>");

            out.println("<body>");
            out.println("<h3 id=\"podmenu\">");
            out.println("REGISTRATION WAS SUCCESSFUL!");
            out.println("</h3>"
                //tlacitko zpet:
                + "<div id=\"paticka\">"
                + "<form action = \"first\" method = \"post\">"
                + "<input type=\"submit\" name=\"page\" value=\"BACK\" />"
                + "</form>"
                + "</div>");
            out.println("</body>");
            out.println("</html>");

        } catch (SQLException ex) {
            sessionB.setAttribute("message", "TRY IT AGAIN PLEASE, THIS LOGIN ALREADY EXISTS!");

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
