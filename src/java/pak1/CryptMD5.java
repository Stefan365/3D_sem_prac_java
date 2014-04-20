package pak1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CryptMD5 {
   
   public static String crypt(String pass){
    try {
        MessageDigest md;

        md = MessageDigest.getInstance("MD5");
        
        byte[] passBytes = pass.getBytes();
        
        md.reset();
        
        byte[] digested = md.digest(passBytes);
        
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<digested.length;i++){
            sb.append(Integer.toHexString(0xff & digested[i]));
        }
        return sb.toString();
    
    } catch (NoSuchAlgorithmException ex) {
        Logger.getLogger(CryptMD5.class.getName()).log(Level.SEVERE, null, ex);
    }
        return null;
   }
}