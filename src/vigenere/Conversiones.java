/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vigenere;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Geney Alvarez & Julio Visbal
 */
public class Conversiones {
    
    public static final String ORDER = "ETAOINSHRDLCUMWFGYPBVKJXQZ";
    public static final Comparator ETAOIN = new Comparator<String>() {
        
            @Override
            public int compare(String o1, String o2) {
                return ORDER.indexOf(o1) -  ORDER.indexOf(o2);
            }
        };
    
    
    public static String encode (String text, String key)
    {
        char[] mensaje=text.toCharArray(); int i=mensaje.length;
        char[] llave=key.toCharArray(); int j=llave.length;
        String superllave1 = "";
       
        for (int y=0; y<i ; y++)
        {
            superllave1+=llave[y%j];
        }
        
        char[] superllave = superllave1.toCharArray();
 
        String strF="";
        
        for(int x=0; x<i;x++)
        {
            int ascii1 = (int) mensaje[x];
            int ascii2 = (int) superllave[x];
            int answer=ascii1^ascii2;
            String s =  Integer.toString(answer, 16);
            if(s.length()==1)
            {
                s="0"+s;
            }
            
            strF+=s;
        }
        
        return strF;
    }
    
    public static String decode (String text, String key)
    {
        int tam=text.length() /2;
        int ciphertext[] = new int[tam];
        int piv=0;
        
        for(int i=0;i<(tam*2);i=i+2)
        {
            String p=text.substring(i, i+2);
            int aux=Integer.parseInt(p,16);
            ciphertext[piv]=aux;
            piv++;
        }         
        
        char[] llave=key.toCharArray(); int j=llave.length;
        String superllave1 = ""; 
        for (int y=0; y<tam ; y++)
        {
            superllave1+=llave[y%j];
        }
        char[] superllave = superllave1.toCharArray();
        
        String strF="";
        
        for(int x=0; x<tam;x++)
        {
            int ascii1 = (int) ciphertext[x];
            int ascii2 = (int) superllave[x];
            int answer=ascii1^ascii2;
            //int answer = ciphertext[x]^ascii2;
            String answer1=Integer.toBinaryString(answer);
            answer=Integer.parseInt(answer1, 2);

            String s =  new Character ((char) answer).toString();
            
            strF+=s;
        }     
        
        return strF;
    }
    
    public static List<String> getParts(String string, int partitionSize) {
        List<String> parts = new ArrayList<String>();
        int len = string.length();
        for (int i=0; i<len; i+=partitionSize)
        {
            parts.add(string.substring(i, Math.min(len, i + partitionSize)));
        }
        return parts;
    }
    
}
