/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vigenere;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Geney Alvarez & Julio Visbal
 */
public class Do_The_Hacky_Thing {
    //The first part of Kasiski Examination is to find every repeated set of letters at least three letters long in the ciphertext. These are significant, because they could indicate that they were the same letters of plaintext encrypted with the same subkeys of the key.
    //150400500e281e160448310d1704482e0a453b093205161b0161291d110528020404012e0245191b61180a500e280201500d37091709483309151509350901501b2418451f0e610000041c241e165009354c09150932184504003309005004241811151a324c091f06264c0c1e48350400500b281c0d151a35091d044661380d151b244c04020d611f0c1706280a0c13092f1849500a240f04051b244c11180d384c061f1d2d08451906250506111c244c111809354c11180d384c12151a244c11180d611f041d0d610000041c241e165007274c151c092802111510354c001e0b331515040d254c12191c294c11180d611f041d0d611f1012032415165007274c11180d6107000946
   
    //private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static TreeMap<Integer,String> Contar (ArrayList A)
    {
        
        TreeMap<Integer,String> possibleKeys = new TreeMap<Integer,String>(Collections.reverseOrder());
        ArrayList tablaKey=new ArrayList();
        ArrayList tablaCont=new ArrayList();
        
        for(int i=0;i<A.size();i++)
        {
            int prueba=(int)A.get(i);
            if(!tablaKey.contains(prueba))
            {
                tablaKey.add(prueba);
                tablaCont.add(1);
            }
            else
            {
                int j=tablaKey.indexOf(prueba);
                int k= (int)tablaCont.get(j)+1;
                tablaCont.set(j, k);
            }
        }
        
        for(int i=0;i<tablaCont.size();i++)
        {
            int ocurrences = (int)tablaCont.get(i);
            int keylength = (int)tablaKey.get(i);
            if(!possibleKeys.containsKey(ocurrences)) possibleKeys.put(ocurrences, ""+keylength/2);
            else{
                String newValue = possibleKeys.get(ocurrences) + "," +keylength/2;
                possibleKeys.put(ocurrences, newValue);
            }
        }
        
        Set set = possibleKeys.entrySet();
        Iterator i = set.iterator();
        
        while(i.hasNext()) {
          Map.Entry me = (Map.Entry)i.next();
          System.out.println("Posible longitud llave = "+me.getKey()+" ocurrencias para ["+me.getValue()+"] longitudes de llave");
        }
        
        return possibleKeys;
        
    }
    
    
    public static ArrayList podar(ArrayList A)
    {
        ArrayList result=new ArrayList();
        
        for(int i=0;i<A.size();i++)
        {
            int prueba=(int)A.get(i);
            if(prueba<=16 && prueba%2==0)
            {
                result.add(prueba);
            }
        }
          
        return result;
    }
    

    
    public static ArrayList GetDivisors(ArrayList A)
    {
        ArrayList finalI=new ArrayList();
        for(int i=0;i<A.size();i++)
        {
            int prueba=(int)A.get(i);
            //System.out.println(prueba+" ");
            
            for(int j=2;j<=(prueba/2);j++)
            {
                if(prueba%j==0 && (prueba/j) <=16)
                {                 
                        finalI.add(j);             
                }
            }
        }
        
        System.out.println("\nFactores expandidos = "+finalI.toString());
        System.out.println("\nFactores recortados = "+(podar(finalI)).toString());
        return finalI;
    }
    
    
    
    public static TreeMap<Integer,String> KasiskiExamination(String message, int tam)
    {
        ArrayList<String> p1= new ArrayList<>();
        ArrayList p2= new ArrayList();
        Secuencias answer= new Secuencias(p1,p2);
        
        ArrayList Factores=new ArrayList();
        
        for(int i=6;i<=12;i=i+2) //evalua secuancias de 3 a 6 caracteres :v
        {
            for(int j=0;j<(tam-i);j++)
            {
                String seq=message.substring(j,j+i);
                for(int k=j+i;k<(tam-i);k=k+2)
                {
                    String seq2=message.substring(k,k+i);
                    if(seq2.equals(seq))
                    {
                        if(answer.sec.contains(seq)==false)
                        {
                            answer.sec.add(seq);
                            ArrayList distancia= new ArrayList();
                            distancia.add(""+(k-j));
                            answer.cantidad.add(distancia);
                            System.out.println(seq+"\n"+answer.cantidad.toString());
                            
                            if(!Factores.contains(k-j))
                            {
                                Factores.add(k-j);
                            }

                        }
                        else
                        {
                            int x=answer.sec.indexOf(seq);
                            ArrayList distancia= new ArrayList();
                            distancia=(ArrayList) answer.cantidad.get(x);
                            distancia.add(k-j);
                            answer.cantidad.set(x, distancia);
                            System.out.println(seq+"\n"+answer.cantidad.toString());
                            
                            if(!Factores.contains(k-j))
                            {
                                Factores.add(k-j);
                            }
                        }
                        
                        

                    }
                }
                
            }       
               
        }
        System.out.println("\nCadenas repetidas = "+answer.sec.toString());
        System.out.println("\nFactores = "+Factores.toString());
        
        Factores=GetDivisors(Factores);
        return Contar((podar(Factores)));
        //System.out.println("\n\n\n\n\n\nCadenas repetidas = "+answer.cantidad.toString());
    }
    
    public static Tuple<String,String> hackVigenere(String txt)
    {
        int tam=txt.length();
        TreeMap<Integer, String> likelyKeyLengths = KasiskiExamination(txt, tam);
        
        int mostLikelyLength = Integer.parseInt(likelyKeyLengths.get(likelyKeyLengths.firstKey()).split(",")[0]);
        return attemptWithLength(txt, mostLikelyLength);
    }
    
    private static String getNthSubkey(int nth, int keylen, String text){
        
        //text = text.replaceAll("20", ""); // removes spaces
        List<String> partitionedString = Conversiones.getParts(text, 2);
        String subkey = "";
        for(int i = (nth - 1); i < partitionedString.size(); i+=keylen)
            subkey += partitionedString.get(i);
        
        return subkey;
        
    }

    public static Tuple<String, String> attemptWithLength(String text, int len) {
        
        //Dictionary <FreqScores, letter> sorted descending
        ArrayList<String> allLikelyLetters = new ArrayList<>();
        for(int nth = 1; nth <= len; nth++){
            //Get a subkey of all the Nth letters according to our supposed keylength
            String subkey = getNthSubkey(nth, len, text);
            System.out.println("\nFrequency Analysis for letter #"+nth+":\n");
            //Dictionary <FreqScores, letter> sorted descending
            TreeMap<Integer, String> freqScores = new TreeMap<Integer, String>(Collections.reverseOrder());
            //Loop through all possible letters of the key
            for(int letter = 32; letter<127; letter++){
                String decodedSample = Conversiones.decode(subkey, ""+(char)letter);
                int freqScore = FrequencyAnalysis.freqMatchScore(decodedSample);
                
                if(!freqScores.containsKey(freqScore)) freqScores.put(freqScore, ""+(char)letter);
                else{
                    String newValue = freqScores.get(freqScore) + "," + (char)letter;
                    freqScores.put(freqScore, newValue);
                }
            }
            Set set = freqScores.entrySet();
            Iterator i = set.iterator();
            
            while(i.hasNext()) {
                Map.Entry me = (Map.Entry)i.next();
                System.out.println("Frequency Score = "+me.getKey()+" for ["+me.getValue()+"] letters");
            }
            allLikelyLetters.add(freqScores.get(freqScores.firstKey()));
        }
        
        String possibleKey;
        String decodedText;
        int index = -1;
        boolean x;
        do{
            possibleKey = "";
            index++;
            for (String likelyLetters : allLikelyLetters){
                if(likelyLetters.split(",").length > 1) possibleKey += likelyLetters.split(",")[index];
                else possibleKey += likelyLetters;
            }
            decodedText = Conversiones.decode(text, possibleKey);
            x = Ventana.checkIfNormal(decodedText);
        }while(!Ventana.checkIfNormal(decodedText));
        
        System.out.println("Key = "+possibleKey);
        return new Tuple(possibleKey, decodedText);
    }
    
}
