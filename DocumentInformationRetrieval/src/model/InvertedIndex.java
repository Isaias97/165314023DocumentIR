/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Aureli Isaias
 */
public class InvertedIndex {
    private ArrayList<Document> listOfDocument = new ArrayList<Document>();
    private ArrayList<Term> dictionary = new ArrayList<Term>();
    public InvertedIndex() {
    }
    
    public void addNewDocument(Document document){
        listOfDocument.add(document);
    }
    
    public ArrayList<Posting> getUnsortedPostingList(){
        // siapkan posting List
        ArrayList<Posting> list = new ArrayList<Posting>();
        // buat node Posting utk listofdocument
        for (int i = 0; i < listOfDocument.size(); i++) {
            // buat listOfTerm dari document ke -i
            String[] termResult = listOfDocument.get(i).getListofTerm();
            // loop sebanyak term dari document ke i
            for (int j = 0; j < termResult.length; j++) {
                // buat object tempPosting
                Posting tempPosting = new Posting(termResult[j],
                        listOfDocument.get(i));
                list.add(tempPosting);
            }
        }
        return list;
    }
    
    public ArrayList<Posting> getSortedPostingList(){
        // siapkan posting List
        ArrayList<Posting> list = new ArrayList<Posting>();
        // panggil list yang belum terurut
        list = this.getUnsortedPostingList();
        // urutkan
        Collections.sort(list);
        return list;
    }
    
    public void makeDictionary(){
        ArrayList<Posting> list = new ArrayList<Posting>();
        
        list = this.getSortedPostingList();
        
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size()-1-i; j++) {
                if (list.get(j).getTerm().equals(list.get(j+1).getTerm())) {        
                    Term tempTerm = new Term(list.get(i).getTerm());
                    dictionary.add(tempTerm);
                }                
            }
        }
        for (int i = 0; i < dictionary.size(); i++) {
            System.out.println(dictionary.get(i).getTerm()+","
                    +dictionary.get(i).getNumberOfTerm()+","
                    +dictionary.get(i).getPostingList());
        }
    }
}
