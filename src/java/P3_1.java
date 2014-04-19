
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pak1.Pom;
import static pak1.Pom.checkPassword;

/**
 * Vstupna invisible stranka. Kontrola prihlasovacich udajov.Nastavenie vstupov 
 *
 * @author Stefan Veres
 */
public class P3_1 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {

        request.setCharacterEncoding("utf-8");
        HttpSession sessionC = request.getSession();

        try {
            String lg = request.getParameter("login");
            String pw = request.getParameter("password");
            //skontroluj heslo:
            if (checkPassword(lg, pw) == false){
                throw new SQLException();
            }
            
            //po prihlaseni se nastavi hl. parametry session:
            Pom.cleanSesQuest(sessionC);//vynuluje eventualne stare hodnoty 
            Pom.zapisSesUser(sessionC, request);
            
            //Vsechno je v poradku, chod na oficialnuvstupnu stranku:
            String go  ="fourth";        
            response.sendRedirect(response.encodeRedirectURL(go));            


        } catch (SQLException ex) {
            sessionC.setAttribute("message", "TRY IT AGAIN PLEASE, INCORRECT LOGIN OR PASSWORD!");
            //Posli spat: 
            String go  ="third";        
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
