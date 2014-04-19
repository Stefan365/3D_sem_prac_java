
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
 * Spracuje dotaz ze stranek dotaznikov.
 * 
 * @author Stefan Veres
 */
public class P4_1 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {
        
        HttpSession sessionE = request.getSession();
        
        
        String uid = (String) sessionE.getAttribute("uid");
        String lg = (String) sessionE.getAttribute("login");
        String pw = (String) sessionE.getAttribute("password");
        
        //A.Vylouceni neautorizovaneho pristupu:
        if (!(Pom.checkPassword(lg, pw))){
            sessionE.setAttribute("message", "LOGIN INCORRECT, PLEASE LOGIN AGAIN!");
            
            //Dispatcher:
            String go  ="first";        
            response.sendRedirect(response.encodeRedirectURL(go));
        }

        try {
            // příprava odpovědi pro klienta
            Pom.zapisDbQuest(sessionE, request);
            
            response.setContentType("text/html");
            response.setCharacterEncoding("utf-8");

            //tvorba těla odpovědi
            PrintWriter out = response.getWriter();

            Pom.printHead(out, lg);
            Pom.printCSS(out);

            out.println("</head>");

            out.println("<body>");
            out.println("<h3 id=\"podmenu\">");
            out.println("QUESTIONARY SUCCESSFULY SUBMITTED!");
            out.println("</h3>"
                + "<div id=\"paticka\">"
                + "<form action = \"fourth\" method = \"post\">"
                + "<input type=\"submit\" name=\"page\" value=\"BACK\" />"
                + "</form>"
                + "</div>");
            out.println("</body>");
            out.println("</html>");

        } catch (SQLException ex) {
            sessionE.setAttribute("message", "TRY IT AGAIN PLEASE, SOME SQL ERROR!");
            
            //Dispatcher:
            String go  ="fourth";        
            response.sendRedirect(response.encodeRedirectURL(go));            

        } finally {
        }
    }
    @Override
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {
        
        doPost(request, response);
    }
}
