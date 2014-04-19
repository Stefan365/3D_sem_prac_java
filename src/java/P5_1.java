
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
public class P5_1 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {

        request.setCharacterEncoding("utf-8");
        HttpSession sessionG = request.getSession();
        String lg = (String) sessionG.getAttribute("login");
        String pw = (String) sessionG.getAttribute("password");
        String uid = (String) sessionG.getAttribute("uid");

        //A.Vylouceni neautorizovaneho pristupu na stranku:
        if (!(Pom.checkPassword(lg, pw))) {
            sessionG.setAttribute("message", "LOGIN INCORRECT, PLEASE LOGIN AGAIN!");

            //Dispatcher:
            String go = "first";
            response.sendRedirect(response.encodeRedirectURL(go));
        }

        try {
            //zapis danych hodnot do DB:
            Pom.updateDbUserApp(uid, request);
            //pokud byl zapis uspesny, zapiseme ich i do session, aby boli po ruke:
            Pom.zapisSesFnLnPw(sessionG, request);

            response.setContentType("text/html");
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            Pom.printHead(out, lg);
            Pom.printCSS(out);

            out.println("</head>");
            out.println("<body>");
            out.println("<h3 id=\"podmenu\">");
            out.println("DATA UPDATE WAS SUCCESSFUL!");
            out.println("</h3>"
                + "<div id=\"paticka\">"
                + "<form action = \"fourth\" method = \"post\">"
                + "<input type=\"submit\" name=\"page\" value=\"BACK\" />"
                + "</form>"
                + "</div>");
            out.println("</body>");
            out.println("</html>");

        } catch (SQLException ex) {
            sessionG.setAttribute("message", "TRY IT AGAIN PLEASE, SOMETHING WENT WRONG!");

            //Dispatcher:
            String go = "fourth";
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
