package pak1;

import java.awt.Dimension;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static pak1.DBconn.connection;

/**
 *
 * @author Stefan
 */
public class Pom {

    public static void printHead(PrintWriter out, String title) {
        out.println("<?xml version = \"1.0\" encoding=\"UTF-8\"?>\n"
            + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n"
            + "    \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
            + "<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"cs\">\n"
            + "    <head>\n"
            + "        <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"/>\n"
            + "        <link href=\"newcss.css\" rel=\"stylesheet\" type=\"text/css\" />"
            + "        <title>" + title + "</title>\n");
    }

    public static void printCSS(PrintWriter out) {

        out.println("<style type=\"text/css\">\n"
            + "body{\n"
            + "	background-color: #eef;\n"
            + "	color: #000;\n"
            + "    font-size: 15;\n"
            + "    font-family: Verdana;\n"
            + "    \n"
            + "}\n"
            + "\n"
            
            + "#menu{\n"
            + "position: absolute;\n"
            + "width:189px;\n"
            + "height:477px;\n"
            + "top:210px;\n"
            + "left: 40px \n"
            + "}\n"
            + "\n"
            
            + "#podmenu{\n"
            + "position: absolute;\n"
            + "top:250px;\n"
            + "left: 40px \n"
            + "}\n"
            + "\n"
            
            + "#podmenu1{\n"
            + "position: absolute;\n"
            + "top:320px;\n"
            + "left: 40px \n"
            + "}\n"
            + "\n"
            
            + "#hlavicka {\n"
            + "position: absolute;\n"
            + "width:1000px;\n"
            + "height:42px;\n"
            + "top: 20px;\n"
            + "left: 40px\n"
            + "}\n"
            + "\n"
            
            + "#hlavickaR {\n"
            + "position: absolute;\n"
            + "width:1000px;\n"
            + "height:42px;\n"
            + "top: 20px;\n"
            + "left: 440px\n"
            + "}\n"
            + "\n"
            
            + "#hlavicka1 {\n"
            + "position: absolute;\n"
            + "width:1000px;\n"
            + "height:42px;\n"
            + "top: 20px;\n"
            + "left: 100px\n"
            + "}\n"
            + "\n"
            
            + "#paticka {\n"
            + "position: absolute;\n"
            + "width:900px;\n"
            + "height:89px;\n"
            + "overflow: hidden;\n"
            + "bottom:50px;\n"
            + "left: 40px \n"
            + "}\n"
            + "\n"
            
            + "#patickaR {\n"
            + "position: absolute;\n"
            + "width:900px;\n"
            + "height:89px;\n"
            + "overflow: hidden;\n"
            + "bottom:50px;\n"
            + "left: 440px \n"
            + "}"
            
            + "#podpaticka {\n"
            + "position: absolute;\n"
            + "width:900px;\n"
            + "height:89px;\n"
            + "overflow: hidden;\n"
            + "bottom:30px;\n"
            + "left: 40px \n"
            + "} </style>");

    }

    //1.0
    /**
     * Zapise atributy do DB.
     *
     * @param request pozadavek od klienta.
     * @throws java.sql.SQLException
     *
     */
    public static void zapisDbUser(HttpServletRequest request) throws SQLException {
        String fn, ln, lg, pw;

        fn = request.getParameter("firstname");
        ln = request.getParameter("lastname");
        lg = request.getParameter("login");
        pw = request.getParameter("password");
        //sifrovanie hesla:
        pw = CryptMD5.crypt(pw);

        //zapis hodnoty do DB:
        DBconn.insertValuesUser(fn, ln, lg, pw, "U");

    }

    //1.1
    /**
     * Zapise atributy do DB.
     *
     * @param uid
     * @param request pozadavek od klienta.
     * @throws java.sql.SQLException
     *
     */
    public static void updateDbUserApp(String uid, HttpServletRequest request) throws SQLException {

        String fn, ln, pw, pwOld, role;

        fn = request.getParameter("firstname");
        ln = request.getParameter("lastname");
        role = request.getParameter("role");
        pw = request.getParameter("password");
        
        //pokud je policko prazdne, tak neudelej nic, jinak hesla vymen:
        if (pw.equals("")){
            pwOld = DBconn.getUserPw(uid);
            pw = pwOld;
        } else {
            //sifrovanie hesla:
            pw = CryptMD5.crypt(pw);
        }

        //zapis hodnoty do DB:
        if ((role == null) || role.equals("") ){
            DBconn.updateValuesUser(uid, fn, ln, pw);
        } else {
            DBconn.updateValuesUser(uid, fn, ln, pw, role);
        }
    }
    
    //2.0
    /**
     * Zapise atributy do Session (po kontrole hesla).
     *
     * @param session klientuv session.
     * @param request pozadavek od klienta.
     * @throws java.sql.SQLException
     *
     */
    public static void zapisSesUser(HttpSession session, HttpServletRequest request) throws SQLException {
        String lg, pw, uid, fn, ln;

        lg = request.getParameter("login");
        pw = request.getParameter("password");
        //sifrovanie hesla:
        pw = CryptMD5.crypt(pw);

        uid = DBconn.getUserId(lg);
        fn = (String) DBconn.getUserFn(uid);
        ln = (String) DBconn.getUserLn(uid);

        session.setAttribute("uid", uid);
        session.setAttribute("login", lg);
        session.setAttribute("firstname", fn);
        session.setAttribute("lastname", ln);
        session.setAttribute("password", pw);

    }

    //2.1
    /**
     * Zapise tie atributy do session, ale len tie, ktore sa menia.
     *
     * @param session klientuv session.
     * @throws java.sql.SQLException
     *
     */
    public static void zapisSesFnLnPw(HttpSession session) throws SQLException {
        String fn, ln, pw, uid;

        //Nech to najprv zapise do DB, a potom, ked sa to podari sa na to odvolava.!!!
         uid = (String) session.getAttribute("uid");
         fn = (String)DBconn.getUserFn(uid);
         ln = (String)DBconn.getUserLn(uid);
         pw = (String)DBconn.getUserPw(uid);
         
        //Zapis do session, aby to bolo stale poruke:
        session.setAttribute("firstname", fn);
        session.setAttribute("lastname", ln);
        // zapis pw sa len zdanlivo bije vid metoda vyssie, tato metoda ma sirsie 
        // pouzite, preto sa tieto 2 zapisy nebiju.
        session.setAttribute("password", pw);

    }

    //2.2
    /**
     * Vynuluje hodnoty session zodpovedajuce userovi.
     *
     * @param session klientuv session.
     *
     */
    public static void cleanSesQuest(HttpSession session) {

        session.setAttribute("uid", "");
        session.setAttribute("login", "");
        session.setAttribute("firstname", "");
        session.setAttribute("lastname", "");
        session.setAttribute("password", "");
        session.setAttribute("questionaire", "");
        session.setAttribute("q_table", "");
        session.setAttribute("sel_user", "");
    }

    //3.
    /**
     * inicializuje messsage.
     *
     * @param mySession klientuv session.
     * @param attr atribut session
     *
     */
    public static void nastavMessage(HttpSession mySession, String attr) {

        String mess = (String) mySession.getAttribute(attr);
        if (mess == null) {
            //Este nebolo definovane:
            mySession.setAttribute(attr, "");
        }
    }

    //4.
    /**
     * Zapise hodnoty z dotazniku do DB.
     *
     * @param session klientuv session.
     * @param request pozadavek od klienta.
     * @throws java.sql.SQLException
     *
     */
    public static void zapisDbQuest(HttpSession session, HttpServletRequest request) throws SQLException {

        String gen, ag, ed, ig, q1, q2, q3, q4, q5, q6, q7, q_tab;
        int uid;

        gen = request.getParameter("gender");
        ag = request.getParameter("age_group");
        ed = request.getParameter("education");
        ig = request.getParameter("income");
        q1 = request.getParameter("q1");
        q2 = request.getParameter("q2");
        q3 = request.getParameter("q3");
        q4 = request.getParameter("q4");
        q5 = request.getParameter("q5");
        q6 = request.getParameter("q6");
        q7 = request.getParameter("q7");
        uid = Integer.parseInt((String) session.getAttribute("uid"));
        q_tab = request.getParameter("q_table");

        //zapis hodnoty do DB:
        if (!q_tab.equals("")) {
            DBconn.insertValuesQ(q_tab, gen, ag, ed, ig, q1, q2, q3, q4, q5, q6, q7, uid);
        }
    }

    //5.
    /**
     * Vytvori zobrazovaciu tabulku z udajov z DB pre T_Q1.
     *
     * @param tn DB table name.
     * @param uid ID usera.
     *
     */
    private static JTable createTableQ1(String uid) {

        Statement stmt1 = null;

        Vector<String> col1 = new Vector<>();
        Vector<String> col2 = new Vector<>();

        //v dalsim pokracovani bych zneni otazek vlozil do dane dotaznikov tabulky T_QUERY,
        //a vsechny polozky v dotazniku bych znacil q1, .... qn (tj. i gender, income, etc...)
        //ale ted se mi to delat nechce.
        //taky bych do dalsiho radku tabulky vlozil nazvy stloupcu, 
        //aby mohl automaticky sestavovat query1.
        col1.add("Gender");
        col1.add("Age group");
        col1.add("Education");
        col1.add("Income");
        col1.add("where did you buy your last piece of furniture?");
        col1.add("Are you satisfied with it? (1 = not at all, 5 = very)");
        col1.add("Are you going to choose the same seller again?");
        
        String query1 = "select gender, age_group, education, income, q1, q2, q3, user_id from T_Q1"
            + " WHERE user_id = " + uid;

        try {
            stmt1 = (DBconn.connection).createStatement();
            ResultSet rs1 = stmt1.executeQuery(query1);

            while (rs1.next()) {
                col2.add(rs1.getString("gender"));
                col2.add(rs1.getString("age_group"));
                col2.add(rs1.getString("education"));
                col2.add(rs1.getString("income"));
                col2.add(rs1.getString("q1"));
                col2.add(rs1.getString("q2"));
                col2.add(rs1.getString("q3"));
            }
        } catch (SQLException e) {
        } finally {
            if (stmt1 != null) {
                try {
                    stmt1.close();
                } catch (SQLException ex) {

                }
            }
        }

        DefaultTableModel model;
        model = new DefaultTableModel();

        final JTable table1 = new JTable(model);
        model.addColumn("QUESTION", col1);
        model.addColumn("ANSWER", col2);

        table1.setPreferredScrollableViewportSize(new Dimension(550, 400));
        table1.setFillsViewportHeight(true);

        table1.setEnabled(false);
        return table1;
    }

    //5.2
    /**
     * Vytvori zobrazovaciu tabulku z udajov z DB pre T_Q1.
     *
     * @param tn DB table name.
     * @param uid ID usera.
     *
     */
    private static JTable createTableQ2(String uid) {

        Statement stmt1 = null;

        Vector<String> col1 = new Vector<>();
        Vector<String> col2 = new Vector<>();

        col1.add("Gender");
        col1.add("Age group");
        col1.add("Education");
        col1.add("Income");

        col1.add("Where do you live?");
        col1.add("Where do you live?");
        col1.add("Do you have central heating?");
        col1.add("Do you have hot water tap?");
        col1.add("Do you have TV set?");
        col1.add("Do you have PC with internet?");
        col1.add("Are you happy with your living standard? \n\n( 1 = not at all, 5 = very)");

        String query1 = "select gender, age_group, education, income, q1, q2, "
            + "q3, q4, q5, q6, q7, user_id from T_Q2"
            + " WHERE user_id = " + uid;

        try {
            stmt1 = (DBconn.connection).createStatement();
            ResultSet rs1 = stmt1.executeQuery(query1);

            while (rs1.next()) {
                col2.add(rs1.getString("gender"));
                col2.add(rs1.getString("age_group"));
                col2.add(rs1.getString("education"));
                col2.add(rs1.getString("income"));
                col2.add(rs1.getString("q1"));
                col2.add(rs1.getString("q2"));
                col2.add(rs1.getString("q3"));
                col2.add(rs1.getString("q4"));
                col2.add(rs1.getString("q5"));
                col2.add(rs1.getString("q6"));
                col2.add(rs1.getString("q7"));
            }
        } catch (SQLException e) {
        } finally {
            if (stmt1 != null) {
                try {
                    stmt1.close();
                } catch (SQLException ex) {

                }
            }
        }

        DefaultTableModel model;
        model = new DefaultTableModel();

        final JTable table1 = new JTable(model);
        model.addColumn("QUESTION", col1);
        model.addColumn("ANSWER", col2);

        table1.setPreferredScrollableViewportSize(new Dimension(550, 400));
        table1.setFillsViewportHeight(true);

        table1.setEnabled(false);

        return table1;

    }

    //6.
    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    private static void createGUI(String tn, String uid) {
        //Create and set up the window.
        String header = "";
        Statement stmt2 = null;
        String query2 = "SELECT login FROM T_USER WHERE id = " + uid;

        try {
            stmt2 = (DBconn.connection).createStatement();
            ResultSet rs2 = stmt2.executeQuery(query2);
            while (rs2.next()) {
                header = rs2.getString("login") + " : " + tn.substring(tn.length() - 2);
            }
        } catch (SQLException e) {
        } finally {
            if (stmt2 != null) {
                try {
                    stmt2.close();
                } catch (SQLException ex) {
                }
            }
        }

        JFrame frame = new JFrame(header);
        JPanel panel = new JPanel();
        JTable table;
        
        switch (tn) {
            case "T_Q1":
                table = createTableQ1(uid);
                break;
            case "T_Q2":
                table = createTableQ2(uid);
                break;
            default:
                System.out.println("Invalid table name month");
                return;
        }
        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(scrollPane);
        panel.setName(header);
        panel.setOpaque(true); //content panes must be opaque

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }

    //7.
    /**
     * Spusti GUI na zobrazenie tabulky.
     *
     * @param tn DB table name.
     * @param uid user id.
     *
     */
    public static void spustiGUI(final String tn, final String uid) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGUI(tn, uid);
            }
        });
    }

    //8.
    /**
     * Zkontroluje kolko a ktore z dotaznikov dany uzivatel uz vyplnil.
     *
     * @param uid user id.
     * @return
     *
     */
    public static List<List<String>> checkDbUserQueries(String uid) {

        List<List<String>> listAll = new ArrayList<>();
        List<String> listYes = new ArrayList<>(), listNo = new ArrayList<>();

        String query1 = "SELECT q_tableName from T_QUERY";

        String preQuery = "SELECT user_id from ";
        String postQuery = " WHERE user_id = " + uid;
        String query2;
        Statement stmt1, stmt2 = null;

        String tn, userId = "";

        try {
            stmt1 = (DBconn.connection).createStatement();
            stmt2 = (DBconn.connection).createStatement();

            ResultSet rs1 = stmt1.executeQuery(query1);
            
            ResultSet rs2;
            while (rs1.next()) {
                tn = rs1.getString("q_tableName");
            
                //prohledavani dotaznikovych tabulek: 
                query2 = preQuery + tn + postQuery;

                rs2 = stmt2.executeQuery(query2);
                while (rs2.next()) {
                    userId = rs2.getString("user_id");
                }
                if ((userId != null) && !userId.equals("")) {
                    listYes.add(tn);
                    userId = "";
                } else {
                    listNo.add(tn);
                }
            }
            listAll.add(listYes);
            listAll.add(listNo);
            
            return listAll;

        } catch (SQLException e) {
            return null;
        } finally {
            if (stmt2 != null) {
                try {
                    stmt2.close();
                } catch (SQLException ex) {

                }
            }

        }
    }

    //9.
    /**
     * Zkontroluje kolko a ktore z dotaznikov dany uzivatel uz vyplnil a vytvori
     * prislusny xhtml text.
     *
     * @param li login.
     * @return list of xhtml texts of buttons.
     *
     */
    private static List<String> createYesButtons(List<String> li) {

        List<String> listYes = new ArrayList<>();
        String page, strBut, tn;

        Iterator itr = li.iterator();
        while (itr.hasNext()) {
            tn = (String) itr.next();
            page = tn.substring(tn.length() - 2);
       
            strBut = "";
            strBut = strBut + "<div>\n";
            strBut = strBut + "<form action=\"fourth\" method=\"post\">\n";
            strBut = strBut + "<input type=\"hidden\" name=\"questionaire\" value=\"" + tn + "\"/>\n";
            strBut = strBut + "<input type=\"submit\" value=\"" + page + "      \"/>\n";
            strBut = strBut + "</form>\n";
            strBut = strBut + "</div>\n";
            strBut = strBut + "\n";

            listYes.add(strBut);
        }

        return listYes;
    }

    //10.
    /**
     * Zkontroluje kolko a ktore z dotaznikov dany uzivatel este nevyplnil a
     * vytvori prislusny xhtml text.
     *
     * @param li login.
     * @return list of xhtml texts of buttons.
     *
     */
    private static List<String> createNoButtons(List<String> li) {

        List<String> listNo = new ArrayList<>();
        String page, strBut, tn;

        Iterator itr = li.iterator();
        while (itr.hasNext()) {
            tn = (String) itr.next();
            page = tn.substring(tn.length() - 2);

            //TVORBA ODOSIELACIEHO TLACITKA:
            strBut = "";
            strBut = strBut + "<div>\n";
            strBut = strBut + "<form action=\"" + page + ".xhtml\" method=\"post\">\n";
            strBut = strBut + "<input type=\"submit\" value=\"" + page + " (new)\"/>\n";
            strBut = strBut + "</form>\n";
            strBut = strBut + "</div>\n";
            strBut = strBut + "\n";

            listNo.add(strBut);
        }

        return listNo;
    }

    //11.
    /**
     * Vytvori vsetky potrebne tlacitka, tj. vytvori prislusny xhtml text.
     *
     * @param lia
     * @return list of xhtml texts of buttons.
     *
     */
     public static String createAllButtons(List<List<String>> lia) {

        List<List<String>> listAllButt = new ArrayList<>();

        List<String> listYesButt = createYesButtons(lia.get(0));
        List<String> listNoButt = createNoButtons(lia.get(1));
        listAllButt.add(listYesButt);
        listAllButt.add(listNoButt);

        String str = "";

        for (int i = 0; i < 2; i++) {
            Iterator itr = (listAllButt.get(i)).iterator();
            while (itr.hasNext()) {
                str = str + (String) itr.next();
            }
            str = str + "\n";
            str = str + "\n";
            str = str + "\n";
        }
       return str;
    }

    //12.
    /**
     * Zkontroluje jestli je user admin.
     *
     * @param uid id uzivatela.
     * @return true/false
     *
     */
    public static boolean isAdmin(String uid) {
        // neskvor, aby sa to nedalo falsovat by sa to malo spravit tak,
        // ze sa skontroluje (zo session), jestli i prihlasene meno a heslo 
        // patria tomu istemu uid
        //
        String role, query = "SELECT role from T_USER where id =" + uid;
        Statement stmt = null;

        try {
            stmt = (DBconn.connection).createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                role = rs.getString("role");
                return role.equals("A");
            }
        } catch (SQLException e) {
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {

                }
            }
        }
        return false;
    }
    
    //13.
    /**
     * Zkontroluje spravnost hesla pri prihlasovani.
     *
     * @param lg login.
     * @param pw password.
     * @return 
     *
     */
    public static boolean checkPassword(String lg, String pw) {

        String query1 = "SELECT password from T_USER WHERE login LIKE '" + lg + "'";
        String realPw = "";
        Statement stmt = null;

        try {

            stmt = (DBconn.connection).createStatement();
            ResultSet rs1 = stmt.executeQuery(query1);

            while (rs1.next()) {
                realPw = rs1.getString("password");
            }
            return (realPw == null ? false : realPw.equals(pw));

        } catch (SQLException e) {
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                }
            }
        }
    }

    //14.
    /**
     * Vrati zoznam mien + id vsetkych zaregistrovanych userov v systeme,
     * tj. len tych ktori niesu Admini.(aby sa nemohli navzajom vymazat, resp.
     * upravovat si udaje)
     *
     * @return zoznam mien + id na tvorbu combo boxu.
     *
     */
    private static List<String> getComboNames() throws SQLException {

        List<String> comboNames = new ArrayList();
        String uid, fn, ln, cn;
        String query = "SELECT id, first_name, last_name from T_USER where role NOT LIKE 'A'";
        Statement stmt = null;

        try {
            stmt = (DBconn.connection).createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                uid = "" + rs.getInt("id");
                fn = rs.getString("first_name");
                ln = rs.getString("last_name");
                cn = uid + ", " + fn + " " + ln;
                comboNames.add(cn);
            }
            return comboNames;

        } catch (SQLException e) {
            return null;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                }
            }
        }
    }

    //15.
    /**
     * Vrati zoznam html kodov pre tvorbu comboboxu.
     *
     * @return zoznam html kodu na tvorbu combo boxu.
     */
    private static List<String> createComboList(List<String> cns) {

        List<String> comboItems = new ArrayList<>();
        String strCom, cn;

        strCom = "<div>\n";
        strCom = strCom + "<select name=\"sel_user\" size=\"1\">\n";
        //vlozenie hlavicky:
        comboItems.add(strCom);

        Iterator itr = cns.iterator();
        while (itr.hasNext()) {
            cn = (String) itr.next();
            //TVORBA COMBO POLOZKY:
            strCom = "<option value=\"" + cn + "\">" + cn + "</option>\n";
            comboItems.add(strCom);
        }
        //vlozenie paticky:
        strCom = "</select>" + "\n" + "</div>" + "\n";
        comboItems.add(strCom);

        return comboItems;
    }

    //16.
    /**
     * Vytvori vsetky potrebne tlacitka, tj. vytvori prislusny xhtml text.
     *
     * @return string of xhtml texts of for creating combobox.
     * @throws java.sql.SQLException
     *
     */
    public static String createComboFinal() throws SQLException {

        List<String> combo = Pom.getComboNames();
        combo = Pom.createComboList(combo);

        String str = "";

        Iterator itr = combo.iterator();
        while (itr.hasNext()) {
            str = str + (String) itr.next();
        }
        str = str + "\n";
        str = str + "\n";
        str = str + "\n";
        
        return str;
    }

    //17.
    /**
     * Ziska user id z nazvu combo polozky.
     * 
     * @param cn combo item name
     * @return string of user id.
     */
    public static String getIdFromComboName(String cn) {

        String[] zoz = cn.split(",");
        int uid = 0;
        
        try {
            uid = Integer.parseInt(zoz[0]);
        } catch (NumberFormatException e){
        
        }
        return "" + uid;
    }

    //18.
    /**
     * Vymaze z Databazy vsetky zaznamy usera s dany id.
     * 
     * @param uid user id
     * @throws java.sql.SQLException
     */
    public static void deleteDbId(String uid) throws SQLException {
        
        List<String> queryTables = Pom.getQueryTableNames();
        List<String> queries = new ArrayList();
        String sqlq, sqlu;
        for (String s: queryTables){
            sqlq = "DELETE FROM " + s + " WHERE user_id = " + uid;
            queries.add(sqlq);
        }
        sqlu = "DELETE FROM T_USER WHERE id = " + uid;
        
        Statement stmt = null;
        
        //deleting in queries tables:
        for (String sql: queries){
            try {
                stmt = (DBconn.connection).createStatement();
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
            } finally {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                    }
                }
            }
        }
        
        //deleting in T_USER table:
        try {
             stmt = (DBconn.connection).createStatement();
             stmt.executeUpdate(sqlu);
            } catch (SQLException e) {
            } finally {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                    }
                }
            }
    }

    //19.
    /**
     * Vrati zoznam mien DB tabuliek dotaznikov
     *
     * @return zoznam databazovych tabuliek, ktore zodpovedaju dotaznikom.
     * @throws java.sql.SQLException
     */
    private static List<String> getQueryTableNames() throws SQLException {

        List<String> queryTables = new ArrayList();
        String query = "SELECT q_tableName FROM T_QUERY";
        Statement stmt = null;
        String tn;
        
        try {
            stmt = (DBconn.connection).createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                tn = rs.getString("q_tableName");
                queryTables.add(tn);
            }
            return queryTables;

        } catch (SQLException e) {
            return null;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                }
            }
        }
    }
    
    //20.
    /**
     * Vrati html text pre tvorbu tlacitka pre prepnutie sa fo admin rezimu.
     *
     * @return html string pre tvorbu tlacitka GO ADMIN.
     */
    public static String goAdminText() {
    
        String str;    //change usder data button:
        str = "<div id=\"patickaR\">\n"
        + "<form action=\"sixth\" method=\"post\">\n"
        + "<input type=\"submit\" value=\"GO ADMIN\"/>\n"
        + "</form>\n"
        + "</div>\n"
        + "\n";
        
        return str;
    }
    
    //21.
    /**
     * Vrati html text pre tvorbu tlacitka pre inicializaciu databazy.
     *
     * @return html string pre tvorbu tlacitka na inicializaci DB.
     */
    public static String initDbText() {
    
        String str;    
        str = "<div id=\"patickaR\">\n"
        + "<form action=\"first_1\" method=\"post\">\n"
        + "<input type=\"submit\" value=\"INIT DB\"/>\n"
        + "</form>\n"
        + "</div>\n"
        + "\n";
        
        return str;
    }

    //22.
    /**
     * Vrati html text pre tvorbu tlacitka pre prepnutie sa admina spat do rezimu 
     * bezneho usera.
     *
     * @return html string pre tvorbu tlacitka na prepnutie do user modu.
     */
    public static String goUserText() {
    
        String str;    //change usder data button:
        str = "<div id=\"patickaR\">\n"
        + "<form action=\"fourth\" method=\"post\">\n"
        + "<input type=\"submit\" value=\"GO USER\"/>\n"
        + "</form>\n"
        + "</div>\n"
        + "\n";
        
        return str;
    }

    //23.
    /**
     * Zjistuje jestli existuje DB tabulka T_USER, 
     * na zaklade ceho usoudi, jestli ma spustit inicializaci DB.
     *
     * @return ano/ne pro existenci T_USER v databazi.
     */
    public static boolean existsT_USER() {
        com.mysql.jdbc.Statement stmt = null;
        try {
            stmt = (com.mysql.jdbc.Statement) connection.createStatement();
            String sql = "SELECT * FROM T_USER";
            ResultSet rs = stmt.executeQuery(sql);
            return true;
        } catch (SQLException ex) {
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                }
            }
        }
    }
}
