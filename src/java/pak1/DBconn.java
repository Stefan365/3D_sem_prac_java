package pak1;


import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stefan Veres
 */
public class DBconn {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //static final String DATABASE_URL = "jdbc:mysql://localhost/IIVOS_java2";
    //static final String USER = "root";
    //static final String PASSWORD = "";
    static final String DATABASE_URL = "jdbc:mysql://project.iivos.cz:9906/iivos3Dalfa?characterEncoding=utf8";
    static final String USER = "veres";
    static final String PASSWORD = "Stefan.Veres";

    public static Connection connection;

    static {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBconn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DBconn() {
    }


    //1.0
    /**
     * Vytvori tabulku T_USER. Sluzi na ukladanie udajov o zaregistrocanych uzivateloch
     * 
     * @throws java.sql.SQLException
     */
    private static void createTableUser() throws SQLException {
        Statement stmt = (Statement) connection.createStatement();
        String sql = "CREATE TABLE T_USER"
            + " (id INTEGER not NULL AUTO_INCREMENT, " + " first_name VARCHAR(30),"
            + " last_name VARCHAR(30),  login VARCHAR(30) NOT NULL, password VARCHAR(30) NOT NULL,"
            + " role VARCHAR(1),"
            
            + " PRIMARY KEY(id),"
            + " CONSTRAINT usr_UN UNIQUE(login))";

        stmt.executeUpdate(sql);
        
        System.out.println("Created table T_USER in given database...");
    }

    //1.1
     /**
     * Vytvori tabulku T_Q1. Dotaznikova tabulka 1.
     * 
     * @throws java.sql.SQLException
     */
    private static void createTableQ1() throws SQLException {
        Statement stmt = (Statement) connection.createStatement();

        String sql = "CREATE TABLE T_Q1"
            + " (id INTEGER NOT NULL AUTO_INCREMENT, " + " gender VARCHAR(5) NOT NULL, "
            + " age_group VARCHAR(30) NOT NULL, education VARCHAR(30) NOT NULL, "
            + " income VARCHAR(30) NOT NULL,"
            + " q1 VARCHAR(15) NOT NULL, q2 VARCHAR(5) NOT NULL, q3 VARCHAR(5) NOT NULL,"
            + " user_id INTEGER NOT NULL,"
            
            + " PRIMARY KEY ( id ),"
            + " CONSTRAINT q1_FK FOREIGN KEY(user_id) REFERENCES t_user(id),"
            + " CONSTRAINT q1_UN UNIQUE(user_id))"; //uzivatel muze vyplnit anketu jen jednou

        stmt.executeUpdate(sql);
        
        System.out.println("Created table T_Q1 in given database...");
    }
    
    //1.2
    /**
     * Vytvori tabulku T_Q2. Dotaznikova tabulka 2.
     * 
     * @throws java.sql.SQLException
     */
    private static void createTableQ2() throws SQLException {
        Statement stmt = (Statement) connection.createStatement();

        String sql = "CREATE TABLE T_Q2"
            + " (id INTEGER NOT NULL AUTO_INCREMENT, " + " gender VARCHAR(5) NOT NULL,"
            + " age_group VARCHAR(30) NOT NULL,  education VARCHAR(30) NOT NULL,"
            + " income VARCHAR(30) NOT NULL,"
            + " q1 VARCHAR(15) NOT NULL, q2 VARCHAR(15) NOT NULL, q3 VARCHAR(5) NOT NULL,"
            + " q4 VARCHAR(5) NOT NULL, q5 VARCHAR(5) NOT NULL, q6 VARCHAR(5) NOT NULL,"
            + " q7 VARCHAR(5) NOT NULL,"
            + " user_id INTEGER NOT NULL,"
            
            + " PRIMARY KEY(id),"
            + " CONSTRAINT q2_FK FOREIGN KEY(user_id) REFERENCES t_user(id),"
            + " CONSTRAINT q2_UN UNIQUE(user_id))"; //uzivatel muze vyplnit anketu jen jednou
        
        stmt.executeUpdate(sql);
        
        System.out.println("Created table T_Q2 in given database...");
    }
    
    //1.3
     /**
     * Vytvori tabulku T_QUERY. Tato tabulka predstavuje zoznam 
     * vs. dotaznikovych tabuliek v systeme.
     * 
     * @throws java.sql.SQLException
     */
    private static void createTableQueries() throws SQLException {
        Statement stmt = (Statement) connection.createStatement();

        String sql = "CREATE TABLE T_QUERY"
            + " (id INTEGER NOT NULL AUTO_INCREMENT, " + " q_tableName VARCHAR(20) NOT NULL, "
            
            + " PRIMARY KEY(id),"
            + " CONSTRAINT q_UN UNIQUE(q_tableName))"; //dany dotaznik je v DB jen jednou

        stmt.executeUpdate(sql);
        
        System.out.println("Created table T_QUERY in given database...");
    }
    
    
    //2.0 Inserting new values:
    /**
     * Vlozi do tab. T_USER novy riadok.
     * 
     * @param fn first name
     * @param ln last name
     * @param lg login
     * @param pw password
     * @param rol user role
     * @throws java.sql.SQLException
     */
    public static void insertValuesUser(String fn, String ln, String lg, String pw, String rol) throws SQLException {

        String sql = "INSERT INTO T_USER (first_name, last_name, login, password, role) "
            + " VALUES (?, ?, ?, ?, ?)";
        PreparedStatement st;
        st = connection.prepareStatement(sql);
        st.setString(1, fn);
        st.setString(2, ln);
        st.setString(3, lg);
        st.setString(4, pw);
        st.setString(5, rol);
        
        st.executeUpdate();

        System.out.println("Insert values in:  T_USER in given database...");
    }

    //2.1
    /**
     * Upravi existujuce hodnoty v tab. T_USER.
     * 
     * @param uid user id
     * @param fn first name
     * @param ln last name
     * @param pw password
     * @throws java.sql.SQLException
     */
    public static void updateValuesUser(String uid, String fn, String ln, String pw) 
        throws SQLException {

        PreparedStatement st = null;
        String sql = "UPDATE T_USER SET first_name = '" + fn + "', "
            + "last_name= '" + ln + "', "
            + "password= '" + pw + "'"
            + " WHERE id = " + uid;
        
        Statement stmt = (Statement) connection.createStatement();
        stmt.executeUpdate(sql);

        System.out.println("Updated values in:  T_USER in given database...");
    }

    //2.2
    /**
     * Upravi existujuce hodnoty v tab. T_USER.
     * 
     * @param uid
     * @param fn first name
     * @param ln last name
     * @param role
     * @param pw password
     * @throws java.sql.SQLException
     */
    public static void updateValuesUser(String uid, String fn, String ln, String pw, String role) 
        throws SQLException {

        PreparedStatement st = null;
        String sql = "UPDATE T_USER SET first_name = '" + fn + "', "
            + "last_name= '" + ln + "', "
            + "password= '" + pw + "', "
            + "role= '" + role + "'"
            + " WHERE id = " + uid;
        
        Statement stmt = (Statement) connection.createStatement();
        stmt.executeUpdate(sql);

        System.out.println("Updated values in:  T_USER in given database...");
    }

    //2.3
    /**
     * Vlozi do tab. T_Q1 alebo T_Q2 novy riadok.
     * 
     * @param qTable nazov DB tabulky, (tj. T_Q1 alebo TQ_2)
     * @param gen gender
     * @param ag age group
     * @param ed education
     * @param ig income group
     * @param q1 question 1
     * @param q2 question 2
     * @param q3 question 3
     * @param q4 question 4
     * @param q5 question 5
     * @param q6 question 6
     * @param q7 question 7
     * @param uid user id
     * 
     * @throws java.sql.SQLException
     */
    public static void insertValuesQ(String qTable, String gen, String ag,
        String ed, String ig, String q1, String q2, String q3, String q4, String q5,
        String q6, String q7, int uid) throws SQLException {

        Statement stmt = (Statement) connection.createStatement();

        String sql, part1, part2, part3;
        
        part1 = "INSERT INTO " + qTable;
        
        switch (qTable) {
            case "T_Q1":
                part2 = " (gender, age_group, education, income, q1, q2, q3, user_id)";
                part3 = String.format(" VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', %d)",
                    gen, ag, ed, ig, q1, q2, q3, uid);
                break;
            case "T_Q2":
                part2 = " (gender, age_group, education, income, q1, q2, q3, q4, q5, q6, q7, user_id)";
                part3 = String.format(" VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', %d)",
                    gen, ag, ed, ig, q1, q2, q3, q4, q5, q6, q7, uid);
                break;
            default:
                System.out.println("Invalid table name month");
                return;
        }
        
        sql = part1 + part2 + part3;
        
        if (!(stmt.executeUpdate(sql) == 1)) {
            throw new SQLException();
        }
        System.out.println("Insert values in: " + qTable + " in given database...");
    }

    
    //2.4
    /**
     * Vlozi novy riadok do tabulky T_QUERY
     * 
     * @param tn DB table name
     * @throws java.sql.SQLException
     */
    public static void insertValuesT_QUERY(String tn) throws SQLException{
        PreparedStatement st;
        String sql = "INSERT INTO T_QUERY (q_tableName) "
            + " VALUES (?)";
        st = connection.prepareStatement(sql);
        st.setString(1, tn);
        
        st.executeUpdate();
    }
    
    
    
    
    //3.
    /**
     * Drop any table in DB
     * 
     * @param tn DB table name
     * @throws java.sql.SQLException
     */
    private static void dropTable(String tn) throws SQLException {
        Statement stmt;
        stmt = (Statement) connection.createStatement();
        String sql = "DROP TABLE " + tn;

        if (!(stmt.executeUpdate(sql) == 1)) {
            throw new SQLException();
        }
        System.out.println("Droped table: " + tn + " in given database...");
    }
    
    
    
    
    //4.0 gets user id
    /**
     * Ziska id daneho uzivatela na zaklade znameho login-u
     * 
     * @param login login uzivatela
     * @return 
     * @throws java.sql.SQLException
     */
    public static String getUserId(String login) throws SQLException {
        
        Statement stmt = (Statement) connection.createStatement();
        String sql = "SELECT id FROM T_USER WHERE login LIKE '" + login + "'";

        System.out.println("SELECTED table: T_USER in given database...");

        ResultSet rs = stmt.executeQuery(sql);
        int id = 0;
        while (rs.next()) {
            id = rs.getInt("id");
        }
        return "" + id;
    }

    //4.1  
    /**
     * gets users first name
     * 
     * @param uid id uzivatela
     * @return 
     * @throws java.sql.SQLException
     */
    public static String getUserFn(String uid) throws SQLException {
        
        Statement stmt = (Statement) connection.createStatement();
        String sql = "SELECT first_name FROM T_USER WHERE id = " + uid;
        //System.out.println("FN, SQL: *" + sql + "*");
        
        ResultSet rs = stmt.executeQuery(sql);
        String fn = "";
        while (rs.next()) {
            fn = rs.getString("first_name");
        }
        return fn;
    }
    
    //4.2
    /**
     * gets users last name
     * 
     * @param uid id uzivatela
     * @return 
     * @throws java.sql.SQLException
     */
    public static String getUserLn(String uid) throws SQLException {
        
        Statement stmt = (Statement) connection.createStatement();
        String sql = "SELECT last_name FROM T_USER WHERE id = " + uid;

        ResultSet rs = stmt.executeQuery(sql);
        String ln = "";
        while (rs.next()) {
            ln = rs.getString("last_name");
        }
        return ln;
    }

    //4.3
    /**
     * gets users login
     * 
     * @param uid id uzivatela
     * @return 
     * @throws java.sql.SQLException
     */
    public static String getUserLg(String uid) throws SQLException {
        
        Statement stmt = (Statement) connection.createStatement();
        String sql = "SELECT login FROM T_USER WHERE id = " + uid;

        ResultSet rs = stmt.executeQuery(sql);
        String lg = "";
        while (rs.next()) {
            lg = rs.getString("login");
        }
        return lg;
    }

    //4.4 gets users password 
    /**
     * gets users password
     * 
     * @param uid id uzivatela
     * @return 
     * @throws java.sql.SQLException
     */
    public static String getUserPw(String uid) throws SQLException {
        
        Statement stmt = (Statement) connection.createStatement();
        String sql = "SELECT password FROM T_USER WHERE id = " + uid;

        ResultSet rs = stmt.executeQuery(sql);
        String pw = "";
        while (rs.next()) {
            pw = rs.getString("password");
        }
        return pw;
    }

    //4.5 gets user role 
    /**
     * gets users role
     * 
     * @param uid id uzivatela
     * @return 
     * @throws java.sql.SQLException
     */
    public static String getUserRole(String uid) throws SQLException {
        
        Statement stmt = (Statement) connection.createStatement();
        String sql = "SELECT * FROM T_USER WHERE id = " + uid;

        ResultSet rs = stmt.executeQuery(sql);
        String role = "";
        while (rs.next()) {
            role = rs.getString("role");
        }
        return role;
    }

    //5.5 
    /**
     * initialize Database. tj. vytvori prislusne DB tabulky.
     * a naplni je nevyhnutnymi udaji.
     * 
     * @throws java.sql.SQLException
     */
    public static synchronized void initDB() throws SQLException {
        DBconn.createTableUser();
        DBconn.createTableQ1();
        DBconn.createTableQ2();
        DBconn.createTableQueries();
        DBconn.insertValuesT_QUERY("T_Q1");
        DBconn.insertValuesT_QUERY("T_Q2");
        insertValuesUser("Stefan", "Veres", "admin", "admin", "A");
    }
}
