/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naive_bayes;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Scanner;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
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
        ListElement[] arrayOfClass = new ListElement[newData.numClasses()];
        
        for (int idx = 0; idx < newData.numClasses(); idx++) {
            arrayOfClass[idx] = new ListElement();
            a = newData.classAttribute();
            c = a.value(idx);
            arrayOfClass[idx].setDisAttrName(c);
            System.out.println("sfsdfsdf " + c);
        }
        
        for (int i = 0; i < newData.numInstances(); i++) {
            double z = newData.instance(i).classValue();
            int zz = (int) z;
            arrayOfClass[zz].setCount(arrayOfClass[zz].getCount()+1);
        }
        
        //Masukan frekuensi masing-masing atribut
        for (int i=0; i<newData.numInstances(); i++) {
            for (int j=0; j<newData.numAttributes()-1; j++) {
                a = newData.attribute(countAttr);
                c = a.value((int) newData.instance(i).value(countAttr));
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
        
        for (int j=0; j<newData.numAttributes()-1; j++) {
            for (int zz = 0; zz < newData.numClasses(); zz++) {
                for (int xx = 0; xx < newData.numDistinctValues(j); xx++) {
                    M[j][zz].set(xx, new ListElement(M[j][zz].get(xx).getDisAttrName(), M[j][zz].get(xx).getCount()/arrayOfClass[zz].getCount()));
                    
                    
                }
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
        
        for (int idx = 0; idx < newData.numClasses(); idx++) {
           
            
            System.out.println("sfsdfsdf " + arrayOfClass[idx].getCount());
        }
        
        //Classify
/*        Scanner in = new Scanner(System.in);
                            DenseInstance buffer = new DenseInstance(newData.firstInstance());
                    // Inisialisasi
                    buffer.setDataset(newData);
                    buffer.setMissing(newData.classIndex());
                    //Input
                    for (int i = 0; i < newData.classIndex(); i++) {
                        System.out.print("Enter the value for " + buffer.attribute(i).name() + ": ");
                        double val = in.nextDouble();
                        buffer.setValue(i, val);
                    }*/
                    
                    // Classify the new instance
                    DenseInstance newInstance = new DenseInstance(newData.instance(40));
                    newData.add(newInstance);
                    
                    //double res = classifier.classifyInstance(buffer);
  /*                  buffer.setValue(data.classIndex(), 0.0);
                    data.add(buffer);

                    // Un-comment this to check if the data is in or not.
                    // System.out.println(data.toSummaryString());
                    // Apply the filter
                    newData = Filter.useFilter(data, filter);*/

        float[] prob = new float[newData.numClasses()];
        for (int i = 0; i < newData.numClasses(); i++) {
            prob[i] = (float) arrayOfClass[i].getCount() / (float) newData.numInstances();
            System.out.println("ii = "+ prob[i]);
        }
        
        for (int i = 0; i < newData.numClasses(); i++) {
            for (int j = 0; j < newData.numAttributes()-1; j++) {
                System.out.println("j ="+j);
                double x = newInstance.value(j);
                System.out.println("x = " + x);
                int xx = (int) x;
                System.out.println("xx = "+ xx);
                prob[i] *= M[j][i].get(xx).getCount();
                System.out.println("lala = "+prob[i]);
            }
        }
        
        int indeksmaks = 0;
        double max = prob[indeksmaks];
        System.out.println("prob 0 = "+ prob[0] );
        for (int i = 1; i < newData.numClasses(); i++) {
            if (max < prob[i]) {
                indeksmaks = i;
                max = prob[i];
                System.out.println("prob "+ i + prob[i]);
                
            }
        }
        
        String tata = arrayOfClass[indeksmaks].getDisAttrName();
        System.out.println("kelas instance = "+ tata);
        
        
       }
    
}

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
