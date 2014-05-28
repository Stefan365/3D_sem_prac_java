
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import pak1.CryptMD5;
import pak1.DBconn;
import static pak1.DBconn.insertValuesUser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author User
 */
public class Skuska {

    public static void main(String[] args) throws UnsupportedEncodingException, SQLException {
        //try {
            /*
            try {
            int i = DBconn.getUserId("stefan.veres@gmail.com");
            System.out.println("i: " + i);
            } catch (SQLException ex) {
            Logger.getLogger(Skuska.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            //String str1 = "tutorials point", str2 = "tuta";
            //String text = "T_Q1";
            //CharSequence cs1 = "int";
            // string contains the specified sequence of char values
            
            /*
            String numbers = text.length() <= 2 ? text : text.substring(text.length() - 2);
            boolean retval = str1.contains(str2);
            System.out.println("i: " + retval + "*" + numbers + "*");
            
            List<String> list = new ArrayList<>();
            */
            /*
            String tn;
            List<List<String>> LA = new ArrayList<>();
            List<String> lia = new ArrayList<>(), list = new ArrayList<>();
            list.add("ahoj");
            list.add("ddfdfahoj");
            list.add("ahojerewr");
            
            lia.add("AahojA");
            lia.add("AddfdfahojA");
            lia.add("AahojerewrA");
            
            LA.add(lia);
            LA.add(list);
            
            
            System.out.println(createAllButtons(LA));
            */
            //Pom.spustiGUI("T_Q2", "1");
            //lia = createButtons(list);
            /*
            Iterator itr = lia.iterator();
            while (itr.hasNext()) {
            tn = (String) itr.next();
            System.out.println(tn);
            }
            */
            //System.out.println(Pom.checkPassword("stefan.veres@gmail.com", "pwd"));
            /*
            String cn;
            List<String> combonames = null;
            try {
            combonames = Pom.getComboNames();
            Iterator itr = combonames.iterator();
            while (itr.hasNext()) {
            cn = (String) itr.next();
            System.out.println(cn);
            }
            } catch (SQLException ex) {
            Logger.getLogger(Skuska.class.getName()).log(Level.SEVERE, null, ex);
            }
            */
            /*
            String str = "12 , werree, eer, e";
            String[] str1 = str.split(",");
            int uid = Integer.parseInt(str1[0]);
            
            for (String s : str1){
            System.out.println("*"+s+"*");
            }
            System.out.println("uid: *"+uid+"*");
            */
            //Pom.deleteDbId("7");
            
            //System.out.println("uid: *"+Pom.createComboFinal()+"*");
            
            //Pom.zapisDbUserApp(null, null);
            
            //System.out.println(CryptMD5.crypt("ahoj"));
            //DBconn.insertValuesUser("Stefan", "Veres", "admin", CryptMD5.crypt("admin"), "A");
            
            //} catch (SQLException ex) {
            //    Logger.getLogger(Skuska.class.getName()).log(Level.SEVERE, null, ex);
            //}
        
            
        //    } catch (NoSuchAlgorithmException ex) {
          //  Logger.getLogger(Skuska.class.getName()).log(Level.SEVERE, null, ex);
        //}
        
        //DBconn.dropTable("T_USER");
        
        //DBconn.dropAllTables();
        System.out.println("vsetky:" + DBconn.existsAllTables());
       
        //DBconn.createTableQ1();
        //DBconn.dropTable("VERES_T_USER");
        //DBconn.createTableUser();
        //DBconn.createTableQ1();
        //DBconn.createTableQ2();
        //DBconn.createTableQueries();        
        
        //DBconn.addFKTableQ1();
        
       
        //Window win = new Window();
        
        //JOptionPane.showMessageDialog(null,"AHOJ First window\nkokotko",
        //         "Informační okno", JOptionPane.INFORMATION_MESSAGE );
        DBconn.dropTable("VERES_T_USER");
        DBconn.dropTable("T_Q1");
        DBconn.dropTable("T_Q2");
        DBconn.dropTable("T_QUERY");
        
        System.out.println("VERES_T_USER: " + DBconn.existsTable("VERES_T_USER"));
        System.out.println("T_Q1: " + DBconn.existsTable("T_Q1"));
        System.out.println("T_Q2: " + DBconn.existsTable("T_Q2"));
        System.out.println("T_QUERY: " + DBconn.existsTable("T_QUERY"));
        
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //1.
    public static List<String> createButtons(List<String> li) {

        List<String> list = new ArrayList<>();
        String page, strBut, tn;

        Iterator itr = li.iterator();
        while (itr.hasNext()) {
            tn = (String) itr.next();
            page = tn.substring(tn.length() - 2);

            //TVORBA ODOSIELACIEHO TLACITKA:
            strBut = "";
            strBut = strBut + "<div>\n";
            strBut = strBut + "<form action=\"" + page + ".xhtml\" method=\"post\">\n";
            strBut = strBut + "<input type=\"submit\" value=\"" + page + "\"/>\n";
            strBut = strBut + "</form>\n";
            strBut = strBut + "</div>\n";

            list.add(strBut);
        }

        return list;
    }
    
    //12.
    /**
     * Vytvori vsetky potrebne tlacitka, tj. vytvori prislusny xhtml text.
     *
     * @param li login.
     * @return list of xhtml texts of buttons.
     *
     */    
    //public static List<List<String>> createAllButtons(List<List<String>> lia){
    public static String createAllButtons(List<List<String>> lia){
        
        /*
        List<List<String>> listAllButt = new ArrayList<>();
        
        List<String> listYesButt = createYesButtons(lia.get(0));
        List<String> listNoButt = createNoButtons(lia.get(1));        
        listAllButt.add(listYesButt);
        listAllButt.add(listNoButt);
        */
        String str = "";
        
        for (int i = 0; i < 2; i++){
            Iterator itr = (lia.get(i)).iterator();
            while(itr.hasNext()) {
                str = str + (String)itr.next();
            }
            str = str + "\n";
        }
        
        return str;
    }

}
