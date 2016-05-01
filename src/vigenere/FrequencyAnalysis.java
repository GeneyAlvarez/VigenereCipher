/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vigenere;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Geney Alvarez & Julio Visbal
 */
public class FrequencyAnalysis {
    
    
    public static int[] letterCount(String text){
        
        //(Hex) 65 = A -> (Hex) 90 = Z.
        
        int[] count = new int[26];
        for(int i = 0; i < 26; i++) count[i] = 0;
        
        for(int i = 0; i<text.length(); i++){
            int letter = text.charAt(i); 
            if(letter >= 65 && letter <=90) { 
                count[letter-65]++;
            }
        }
        
        return count;
    }
    
    public static String getFreqOrder(String text){
        
        int[] count = letterCount(text);
        TreeMap<Integer, String> freqToLetter = new TreeMap<Integer,String>(Collections.reverseOrder());
        for(int i = 0; i<26; i++){
            if(!freqToLetter.containsKey(count[i])) freqToLetter.put(count[i], "" + (char)(i+65));
            else{
                List<String> newValueArray = Conversiones.getParts(freqToLetter.get(count[i]) + (char)(i+65), 1);
                Collections.sort(newValueArray,Collections.reverseOrder(Conversiones.ETAOIN));
                String newVal = "";
                for(String s : newValueArray) {
                    newVal += s;
                }
                freqToLetter.put(count[i], newVal);
            }
        }
        
        Set set = freqToLetter.entrySet();
        Iterator i = set.iterator();
        String result = "";
        
        while(i.hasNext()) {
          Map.Entry me = (Map.Entry)i.next();
          result += me.getValue();
        }
        
        return result;
    }
    
    
    public static int freqMatchScore(String message){
        
        message = message.toUpperCase();
        String freqOrder = getFreqOrder(message);
        String firstEtaoin = Conversiones.ORDER.substring(0, 6);
        String lastEtaoin = Conversiones.ORDER.substring(Conversiones.ORDER.length() - 6);
        String first = freqOrder.substring(0, 6);
        String last = freqOrder.substring(freqOrder.length() - 6);
        int score = 0;
        
        for(int i = 0; i<first.length(); i++){
            if(firstEtaoin.indexOf(first.charAt(i)) != -1) score++;
        }
        for(int i = 0; i<last.length(); i++){
            if(lastEtaoin.indexOf(last.charAt(i)) != -1) score++;
        }
        return score;
    }
}
