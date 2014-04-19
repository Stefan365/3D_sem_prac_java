
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pak1.Pom;

/**
 * registace uzivatela
 * 
 * @author Stefan Veres
 * 
 */
public class P2 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {

        
        HttpSession sessionB = request.getSession();

        //sprava z neuspesnych registracii:
        String message = (String) sessionB.getAttribute("message");
        sessionB.setAttribute("message", "");
        
        //vycisti pripadne zostatky z predchadzajuceho usera (jak sa sem niekto 
        //prepne skrz URL:
        Pom.cleanSesQuest(sessionB);

        
        // příprava odpovědi pro klienta
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        
        Pom.printHead(out, "SEM_PRACE");
        Pom.printCSS(out);
 
        out.println("</head>");

        out.println("<body>");
        out.println("<h3>");
        out.println("REGISTRATION:");
        out.println("</h3>"
            //sprava o neuspechu:
            + " <h5 id=\"podmenu1\"> <font color=\"red\">"
            + message + "</font>"
            + "</h5>");
        
        out.println("<form action = \"second_1\" method = \"post\">");
        out.println("<p>");
        out.println("<label>");
        out.println("FIRST NAME : ");
        out.println("<input type=\"text\" value=\"default\" name=\"firstname\"/><br/>");
        out.println(" LAST NAME : ");
        out.println("<input type=\"text\" name=\"lastname\"/><br/>");
        out.println("     LOGIN : ");
        out.println("<input type=\"text\" name=\"login\"/><br/>");
        out.println("  PASSWORD : ");
        out.println("<input type=\"password\" name=\"password\"/><br/>");
        //ODOSIELACIE TLACITKO:
        out.println("<input type=\"submit\" value=\"SEND\">");
        
        out.println("</label>");
        out.println("</p>");
        out.println("</form>");
        //SPATNE TLACITKO2:
        out.println("<div>"
            + "<form action = \"first\" method = \"post\">"
            + "<input type=\"submit\" value=\"BACK\" />"
            + "</form>"
            + "</div>");
        out.println("</body>");
        out.println("</html>");

    }
    
    @Override
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {
        
        doPost(request, response);
    }
}
