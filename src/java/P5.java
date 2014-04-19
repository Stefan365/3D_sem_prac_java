import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pak1.Pom;

/**
 * @author Stefan Veres
 */
public class P5 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {


        HttpSession sessionF = request.getSession();
      
        String lg = (String) sessionF.getAttribute("login");
        String fn = (String) sessionF.getAttribute("firstname");
        String ln = (String) sessionF.getAttribute("lastname");
        String pw = (String) sessionF.getAttribute("password");
        String uid = (String) sessionF.getAttribute("uid");
        
        //A.Vylouceni neautorizovaneho pristupu:
        if (!(Pom.checkPassword(lg, pw))){
            sessionF.setAttribute("message", "LOGIN INCORRECT, PLEASE LOGIN AGAIN!");

            //Dispatcher:
            String go  ="first";        
            response.sendRedirect(response.encodeRedirectURL(go));
        }

        
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
            //zprava o neuspechu:
            //+ " <h5 id=\"podmenu1\"> <font color=\"red\">"
            //+ message + "</font>"
            //+ "</h5>");
        out.println("<form action = \"fifth_1\" method = \"post\">");
        out.println("<p>");
        out.println("<label>");
        out.println("FIRST NAME   : ");
        out.println("<input type=\"text\" value=\"" + fn + "\" name=\"firstname\"/><br/>");
        out.println("LAST NAME    : ");
        out.println("<input type=\"text\" value=\"" + ln + "\" name=\"lastname\"/><br/>");
        out.println("NEW PASSWORD : ");
        out.println("<input type=\"password\" name=\"password\"/><br/>");
        
        //ODOSIELACIE TLACITKO:
        out.println("<input type=\"submit\" value=\"SAVE\">");

        out.println("</label>");
        out.println("</p>");
        out.println("</form>");
        //SPATNE TLACITKO2:
        out.println("<div>"
            + "<form action = \"fourth\" method = \"post\">"
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
