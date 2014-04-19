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
 * spracuje dotaz zo zmien dat usera.
 *
 * @author Stefan Veres
 */

public class P7_1 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {

        request.setCharacterEncoding("utf-8");
        HttpSession sessionH = request.getSession();
        String lg = (String) sessionH.getAttribute("login");
        String uid = (String) sessionH.getAttribute("uid");
        
        //A.Vylouceni neautorizovaneho pristupu na stranku:
        if (!(Pom.isAdmin(uid))){
            sessionH.setAttribute("message", "LOGIN INCORRECT DEAR ADMIN, PLEASE LOGIN AGAIN!");
            //Dispatcher:
            String go  ="first";        
            response.sendRedirect(response.encodeRedirectURL(go));
        }

        String comboName = (String)sessionH.getAttribute("sel_user");
        String sel_uid = Pom.getIdFromComboName(comboName);
        
        try {
            //zapis danych hodnot do DB:
            Pom.updateDbUserApp(sel_uid, request);
            //pokud byl vymaz uspesny, upravime tiez session:
            sessionH.setAttribute("sel_user", "");
            
            
            //priprava odpovedi:
            response.setContentType("text/html");
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            Pom.printHead(out, lg);
            Pom.printCSS(out);
            
            out.println("</head>");
            out.println("<body>");
            out.println("<h3 id=\"podmenu\">");
            out.println("ADMIN 'DATA UPDATE' WAS SUCCESSFUL!");
            out.println("</h3>"
                + "<div id=\"paticka\">"
                + "<form action = \"sixth\" method = \"post\">"
                + "<input type=\"submit\" name=\"page\" value=\"BACK\" />"
                + "</form>"
                + "</div>");
            out.println("</body>");
            out.println("</html>");

        } catch (SQLException ex) {
            sessionH.setAttribute("message", "DEAR ADMIN, TRY IT AGAIN PLEASE, SOMETHING WENT WRONG!");
            
            //Dispatcher:
            String go  ="sixth";        
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
