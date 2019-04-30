/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucene;

import org.apache.lucene.analysis.id.IndonesianStemmer;

/**
 *
 * @author Aureli Isaias
 */
public class TestIndonesiaStemm {
    public static void main(String[] args) {
        IndonesianStemmer stem = new IndonesianStemmer();
        String term = "membunuh";
        char stemTerm[] = term.toCharArray();
        int hasil = stem.stem(stemTerm, term.length(), true);
        System.out.println(hasil);
        System.out.println(stemTerm);
        String temp = stemTerm.toString();
        String result = temp.substring(0, hasil);
        System.out.println(temp);
        System.out.println(result);        
    }
}
