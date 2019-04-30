/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucene;

import java.io.StringReader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.id.IndonesianAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

/**
 *
 * @author Aureli Isaias
 */
public class TestIndonesiaStopWord {
    public static void main(String[] args) {
        String text = "Dia pergi berbelanja di pusat perbelanjaan. "
                + "Namun, ibunya melarangnya karna tempat itu sangat berbahaya";
        System.out.println("Text = "+text);
        Version matchVersion = Version.LUCENE_7_7_0;
        Analyzer analyzer = new IndonesianAnalyzer();
        analyzer.setVersion(matchVersion);
        
        //ambil stopwords
        CharArraySet stopword = IndonesianAnalyzer.getDefaultStopSet();
        // buat token
        TokenStream tokenStream = analyzer.tokenStream("myField", 
                new StringReader(text.trim()));
        
        tokenStream = new StopFilter(tokenStream, stopword);
        
        StringBuilder sb = new StringBuilder();
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        try {
            tokenStream.reset();
            while (tokenStream.incrementToken()) {                
                String term = charTermAttribute.toString();
                sb.append(term + " ");
            }
        } catch (Exception e) {
            System.out.println("Exection: "+e);
        }
        String newText = sb.toString();
        System.out.println("New Text: "+newText);
    }
}
