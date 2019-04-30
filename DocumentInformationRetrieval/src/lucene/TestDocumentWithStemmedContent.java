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
public class TestDocumentWithStemmedContent {
    public static void main(String[] args) {
        Document doc = new Document(1, "He was a man with gun");
        System.out.println("Without stemming");
        System.out.println(doc);
        System.out.println("With stemming");
        doc.Stemming();
        System.out.println(doc);
    }
}
