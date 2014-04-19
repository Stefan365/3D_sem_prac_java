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
 * spracuje dotaz na vymazanie usera.
 *
 * @author Stefan Veres
 */

public class P6_1 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {

        request.setCharacterEncoding("utf-8");
        HttpSession sessionG = request.getSession();
        String lg = (String) sessionG.getAttribute("login");
        String uid = (String) sessionG.getAttribute("uid");
        
        //A.Vylouceni neautorizovaneho pristupu na stranku:
        if (!Pom.isAdmin(uid)){
            sessionG.setAttribute("message", "LOGIN INCORRECT, PLEASE LOGIN AGAIN!");

            //Dispatcher:
            String go  ="first";        
            response.sendRedirect(response.encodeRedirectURL(go));
        }

        String comboName = (String)sessionG.getAttribute("sel_user");
        String sel_uid = Pom.getIdFromComboName(comboName);
        //JOptionPane.showMessageDialog(null, "*" + comboName + "*", "infoMessage", JOptionPane.INFORMATION_MESSAGE);
        
        try {
            //vymazanie daneho id zo vsetkych DB tabuliek
            Pom.deleteDbId(sel_uid);
            
            response.setContentType("text/html");
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            Pom.printHead(out, lg);
            Pom.printCSS(out);
            
            out.println("</head>");
            out.println("<body>");
            out.println("<h3 id=\"podmenu\">");
            out.println("USER SUCCESSFULY REMOVED FROM ALL DB TABLES!");
            out.println("</h3>"
                + "<div id=\"paticka\">"
                + "<form action = \"sixth\" method = \"post\">"
                + "<input type=\"submit\" name=\"page\" value=\"BACK\" />"
                + "</form>"
                + "</div>");
            out.println("</body>");
            out.println("</html>");

        } catch (SQLException ex) {
            sessionG.setAttribute("message", "TRY IT AGAIN PLEASE, SOMETHING WENT WRONG!");
    
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
