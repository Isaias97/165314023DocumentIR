/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import model.Document;
import model.Posting;

/**
 *
 * @author Aureli Isaias
 */
public class testTFIDF2 {
    public static void main(String[] args) {
//        Document doc1 = new Document(1, "Shipment of gold damaged in a fire");
        Document doc2 = new Document(2, "delivery of silver arrived in a silver truck");
//        Document doc3 = new Document(3, "shipment of gold arrived in a truck");

        ArrayList<Posting> result = doc2.getListofPosting();
        
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i).getTerm() + " = " 
                    + result.get(i).getNumberOfTerm());
        }
    }
}
