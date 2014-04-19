
import java.io.IOException;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * logout, jan vymaze nastaveni session a hajde na prvni stranku.
 * 
 * @author Stefan Veres
 */
public class P4_2 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {
        
        HttpSession sessionH = request.getSession();
        
        String go  ="first";        
        response.sendRedirect(response.encodeRedirectURL(go));
        
    }
    @Override
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {
        
        doPost(request, response);
    }
}
