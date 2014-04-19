
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
 * Admin page
 *
 * @author Stefan Veres
 */
public class P6 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {

        HttpSession sessionG = request.getSession();
        Pom.nastavMessage(sessionG, "sel_user");

        String message = (String) sessionG.getAttribute("message");
        sessionG.setAttribute("message", "");
        //vynulovanie pripadne obsahu sel_user:
        sessionG.setAttribute("sel_user", "");

        String uid = (String) sessionG.getAttribute("uid");

        //A.Vylouceni neautorizovaneho pristupu:
        if (!Pom.isAdmin(uid)) {
            sessionG.setAttribute("message", "PLEASE LOGIN MY DEAR ADMIN!");

            //Dispatcher:
            String go = "first";
            response.sendRedirect(response.encodeRedirectURL(go));
        }
        String lg = (String) sessionG.getAttribute("login");
        String goUser = Pom.goUserText();
        String combo = "";

        try {
            combo = Pom.createComboFinal();
        } catch (SQLException ex) {

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
        out.println("SELECT USER:");
        out.println("</h3>"
            //zprava o neuspechu:
            + " <h5 id=\"podmenu1\"> <font color=\"red\">"
            + message + "</font>"
            + "</h5>");

        //Combo box/TLACITKO NA ZMENU USER DATA:
        out.println("<div>"
            + "<form action=\"seventh\" method = \"post\">"
            + combo
            + "<input type=\"submit\" value=\"USER DATA\">"
            + "</form>"
            + "</div>");

        //logout button:
        out.println("<div id=\"hlavickaR\">\n"
            + "<form action=\"first\" method=\"post\">\n"
            + "<input type=\"submit\" value=\"LOGOUT\"/>\n"
            + "</form>\n"
            + "</div>\n"
            + "\n");

        //SPATNE TLACITKO2:
        out.println("<div id=\"paticka\">"
            + "<form action = \"fourth\" method = \"post\">"
            + "<input type=\"submit\" value=\"BACK\" />"
            + "</form>"
            + "</div>");

        //go user tlacitko:
        out.println(goUser);

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
