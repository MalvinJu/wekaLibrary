/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naive_bayes;

/**
 *
 * @author Scarletta's
 */
public class ListElement {
    private String DisAttrName;
    private int count;
    
    public ListElement () {
        DisAttrName = "";
        this.count = 0;
    }
    public ListElement (String name, int count) {
        DisAttrName = name;
        this.count = count;
    }
    //Setter
    public void setDisAttrName (String name) {
        DisAttrName = name;
    }
    
    public void setCount (int num) {
        count = num;
    }
    
    //Getter
    public String getDisAttrName() {
        return DisAttrName;
    }
    
    public int getCount() {
        return count;
    }
    
}
