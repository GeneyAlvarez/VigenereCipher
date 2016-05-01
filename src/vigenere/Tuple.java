/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vigenere;

/**
 *
 * @author Geney Alvarez & Julio Visbal
 */

/* <rant>
 * WHY DOESN'T JAVA HAVE A SIMPLE TUPLE CLASS YET? DICTIONARIES (MAPS) OFTEN MAY BE TOO MUCH FOR A SIMPLE TASK
 * AND OFTEN I DON'T WANT TO STORE TOO MANY VALUES SO REALLY JUST A TUPLE WOULD DO
 * </rant>
 */ 
public class Tuple<A,B> {

    private A elem1;
    private B elem2;

    public Tuple(A elem1, B elem2) {
        this.elem1 = elem1;
        this.elem2 = elem2;
    }

    public A getElem1() {
        return elem1;
    }

    public void setElem1(A elem1) {
        this.elem1 = elem1;
    }

    public B getElem2() {
        return elem2;
    }

    public void setElem2(B elem2) {
        this.elem2 = elem2;
    }
    
    
    
}
