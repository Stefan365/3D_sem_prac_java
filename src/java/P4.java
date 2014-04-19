
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pak1.Pom;


/**
 * Použití cookies pro počítání přístupů daného klienta na webový server.
 *
 * @author Jaroslav Srp
 */
public class P4 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {
        
        HttpSession sessionD = request.getSession();
        
        //pro zobrazzeni dotazniku:
        String tableDepict = request.getParameter("questionaire"); 
        //sprava o neuspechu:
        String message = (String) sessionD.getAttribute("message");
        sessionD.setAttribute("message", "");
        
        String uid = (String) sessionD.getAttribute("uid");
        String lg = (String) sessionD.getAttribute("login");
        String pw = (String) sessionD.getAttribute("password");
        String goAdmin = "";
        
        
        //A.Vylouceni neprihlaseneho usera, ktery se tu dostal napr. prez zadani url:
        if (!(Pom.checkPassword(lg, pw))){
            sessionD.setAttribute("message", "LOGIN INCORRECT, PLEASE LOGIN AGAIN!");

            //Dispatcher:
            String go  ="first";        
            response.sendRedirect(response.encodeRedirectURL(go));
        }
        if (Pom.isAdmin(uid)){
            goAdmin = Pom.goAdminText();
        }
        
        //B. Kontrola jestli bol vybrany dotaznik na zobrazenie: 
        if (!(tableDepict == null || tableDepict.equals(""))){
            Pom.spustiGUI(tableDepict, uid);
            //toto zdanlivo zbytocne presmerovanie je kvoli zabraneniu 
            //vykakovaniu posledne zvoleneho dotaznika pri stlaceni F5.
            String go  ="fourth";        
            response.sendRedirect(response.encodeRedirectURL(go));            
        }

        //Kontrola jestli dany dotaznik jiz byl vyplnen. pokud jo, otevra se jenom 
        //okno se zobrazenim daneho dotazniku.
        //tvorba prislusnych dotaznikovych tlacitek:
        String htmlButtons = Pom.createAllButtons(Pom.checkDbUserQueries(uid));

        // příprava odpovědi pro klienta
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        
        Pom.printHead(out, lg);
        Pom.printCSS(out);
        out.println("</head>");
        
        out.println("<body>");
        out.println(lg);
        
        //prislusne dotaznikove tlacitka:
        out.println(htmlButtons);
        
        //logout button:
        out.println("<div id=\"hlavickaR\">\n"
        + "<form action=\"first\" method=\"post\">\n"
        + "<input type=\"submit\" value=\"LOGOUT\"/>\n"
        + "</form>\n"
        + "</div>\n"
        + "\n");

        //change usder data button:
        out.println("<div id=\"paticka\">\n"
        + "<form action=\"fifth\" method=\"post\">\n"
        + "<input type=\"submit\" value=\"USER DATA\"/>\n"
        + "</form>\n"
        + "</div>\n"
        + "\n");
        
        //admin button:
        out.println(goAdmin);
        
        //message:
        out.println("</h3>"
            + " <h5 id=\"podmenu1\"> <font color=\"red\">"
            + message + "</font>"
            + "</h5>");

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
