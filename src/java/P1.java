
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pak1.DBconn;
import pak1.Pom;

/**
 * Vstupna stranka aplikacie.
 *
 * @author Stefan Veres
 */
public class P1 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {

        HttpSession sessionA = request.getSession();
        
        //sprava z pripadneho neuspechu:
        Pom.nastavMessage(sessionA, "message");
        String message = (String) sessionA.getAttribute("message");
        sessionA.setAttribute("message", "");
        
        //vycisti pripadne zostatky z predchadzajuceho usera:
        Pom.cleanSesQuest(sessionA);
        String initDb = "";
        
        //inicializacia DB
        if(!DBconn.existsAllTables()){
            initDb = Pom.initDbText();
        }

        
        // příprava odpovědi pro klienta
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();

        Pom.printHead(out, "SEM_PRACE");
        Pom.printCSS(out);

        out.println("</head>\n"

             //LOGIN TLACITKO:
            + "<div id=\"hlavicka\">"
            + "<form action = \"third\" method = \"post\">"
            + "<input type=\"submit\" name=\"page\" value=\"LOGIN\" />"
            + "</form>"
            + "</div>");
       
       //tlacitko init DB:
       out.println(initDb);
                
       out.println("<div id=\"hlavicka1\">"
            + "<form action = \"second\" method = \"post\">"
            + "<input type=\"submit\" name=\"page\" value=\"REGISTRATION\" />"
            + "</form>"
            + "</div>");
            
       out.println(" <h3 id=\"podmenu\">"
            + "WELCOME TO MY PAGES!"
            + "</h3>"
            //sprava pro pripadne neuspesne prihlaseni/registraci:
            + " <h5 id=\"podmenu1\"> <font color=\"red\">"
            + message + "</font>"
            +"</h5>"
            + "    </body>\n"
            + "</html>");

    }
    
    @Override
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {
        
        doPost(request, response);
    }
}
