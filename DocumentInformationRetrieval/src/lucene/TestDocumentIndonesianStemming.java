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
public class TestDocumentIndonesianStemming {
    public static void main(String[] args) {
        Document doc = new Document(1, "Dia sedang pergi berbelanja ke pusat perbelanjaan");
        System.out.println("Tanpa stemming");
        System.out.println(doc);
        System.out.println("Dengan stemming");
        doc.IndonesiaStemming();
        System.out.println(doc);
    }
}