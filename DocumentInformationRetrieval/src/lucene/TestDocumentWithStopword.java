/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucene;

import model.Document;

/**
 *
 * @author Aureli Isaias
 */
public class TestDocumentWithStopword {
    public static void main(String[] args) {
        Document doc = new Document(1, "He was a man with gun");
        System.out.println("Without stopword");
        System.out.println(doc.getContent());
        System.out.println("With stopword");
        doc.removeStopWord();
        System.out.println(doc.getContent());
    }
}
