package mx.job.potrobus.Entities;

import java.security.MessageDigest;

public class Encrypter {

    public String getHash(byte[] input){
        String hash;
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] digestBytes = messageDigest.digest(input);
            hash = toHexString(digestBytes);
            System.out.println(hash);
            return hash;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private String toHexString(byte[] digestBytes){
        StringBuilder stringBuilder = new StringBuilder(digestBytes.length*2);
        for (byte b : digestBytes){
            int val = 0xFF & b;
            String toAppend = Integer.toHexString(val);
            stringBuilder.append(toAppend);
        }
        stringBuilder.setLength(stringBuilder.length()-1);
        return  stringBuilder.toString().toLowerCase();

    }
}


