/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naive_bayes;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.supervised.attribute.Discretize;

/**
 *
 * @author Scarletta's
 */

public class Naive_bayes {
    /**
     * @param args the command line arguments
     */
    /*
    public static boolean isExist(ArrayList<String> as ,String a) {
        boolean result=false;
        if (as.size>0) {
            
        }
        return result; 
    } 
    */
    public static void main(String[] args) throws Exception {
        ArrayList<String> atr = new ArrayList();
        ArrayList<String> kelas = new ArrayList();
        ArrayList<String> distinctAttr = new ArrayList();        
        // READ DATASET ------
        ConverterUtils.DataSource source = new ConverterUtils.DataSource("C:\\Program Files\\Weka-3-8\\data\\iris.arff");
        Instances data = source.getDataSet();
        if (data.classIndex() == -1) {
            data.setClassIndex(data.numAttributes() - 1);
        }
        //System.out.println("========  READ DATA  ========");

        //System.out.println("Data Summary");
        //System.out.println(data.toSummaryString());
        
        // Apply filter
        Discretize filter;
        filter = new Discretize();
        filter.setInputFormat(data);

        Instances newData;
        newData = Filter.useFilter(data, filter);
        System.out.println(newData);
       
        int countAttr = newData.numAttributes() - 1;
        int distinctClassValue = newData.numDistinctValues(countAttr);
        
        ArrayList<ListElement>[][] M = new ArrayList[countAttr][distinctClassValue];
        for (int i=0; i<countAttr; i++) {
            for (int j=0; j< distinctClassValue; j++) {
                M[i][j] = new ArrayList<ListElement>();
            }
        }
        boolean add;
        ListElement le = new ListElement();
        Attribute ab;
        for (int i=0; i<countAttr; i++) {
            for (int j=0; j<distinctClassValue; j++) {
                for(int k=0; k<newData.numDistinctValues(i); k++) {
                    ab = newData.attribute(i);
                    String c = ab.value((int) newData.instance(149).value(i));
                    add=M[i][j].add(le);
                }
            }
        }
        
        
        /*
        Attribute a = newData.attribute(2);
        String c = a.value((int) newData.instance(149).value(2));
        System.out.println(c)
        System.out.println(newData.instance(149).attribute(2));
        */
        
        Attribute a;
        String c;
        for (int i=0; i<newData.numInstances(); i++) {
            for (int j=0; j<newData.numAttributes()-1; j++) {
                a = newData.attribute(j);
                c = a.value((int) newData.instance(i).value(j));
                System.out.println("a = " + a);
                System.out.println("c = " + c);
                double z = newData.instance(i).classValue();
                System.out.println("z = " + z);
                int zz = (int) z;
                System.out.println("zz = " + zz);
                le.setDisAttrName(c);
                double x = newData.instance(i).value(j);
                System.out.println("x = " + x);
                int xx = (int) x;
                System.out.println("xx = " + xx);
                System.out.println("M j zz get xx = " + M[j][zz].get(xx).getDisAttrName());
                 System.out.println("M j zz get xx count = " + M[j][zz].get(xx).getCount());
                le.setCount(M[j][zz].get(xx).getCount()+1);
                System.out.println("le nama = "+le.getDisAttrName());
                System.out.println("le count = "+le.getCount());
                M[j][zz].set(xx, new ListElement(M[j][zz].get(xx).getDisAttrName(), M[j][zz].get(xx).getCount()+1));
                //break;
            }
        }
        System.out.println("pisng : " + countAttr+ "Monyet : " + distinctClassValue);
        for (int i=0; i<countAttr; i++) {
            System.out.println("nnn");
            for (int j=0; j<distinctClassValue; j++) {
                for (int k=0; k< M[i][j].size(); k++) {
                    System.out.println(M[i][j].get(k).getDisAttrName());
                    System.out.println(M[i][j].get(k).getCount());
                    System.out.println(' ');
                }
                
            }
            System.out.println();
        }
        
        
    }
    
}
