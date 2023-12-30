package org.grow.canteen.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public class SecurityUtil {

    private SecurityUtil(){

    }
   static int strength=10;
    public  static  String generateUUID(){
        UUID uuid=UUID.randomUUID();
        return uuid.toString();
    }

//    public static String encode(String plainText){
//        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder(strength,new SecureRandom());
//        String encodedPassword=bCryptPasswordEncoder.encode(plainText);
//        return encodedPassword;
//    }

    public static  String encode(String plainText){
        MessageDigest messageDigest=null;
        try {
            messageDigest=MessageDigest.getInstance("SHA-256");
            messageDigest.update(plainText.getBytes(StandardCharsets.UTF_8));
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        assert messageDigest!=null;
        byte[] digest= messageDigest.digest();
        return base64Encode(digest);
    }

    public static String base64Encode(byte[]var){
        return Base64.getEncoder().encodeToString(var);
    }
}
