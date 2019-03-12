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
        getListOfDocument().add(document);
    }
    
    public ArrayList<Posting> getUnsortedPostingList(){
        // siapkan posting List
        ArrayList<Posting> list = new ArrayList<Posting>();
        // buat node Posting utk listofdocument
        for (int i = 0; i < getListOfDocument().size(); i++) {
            // buat listOfTerm dari document ke -i
            String[] termResult = getListOfDocument().get(i).getListofTerm();
            // loop sebanyak term dari document ke i
            for (int j = 0; j < termResult.length; j++) {
                // buat object tempPosting
                Posting tempPosting = new Posting(termResult[j],
                        getListOfDocument().get(i));
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
        // cek ada term yang frek. lebih dari 1 pada sebuah dokumen
        // buat posting list term terurut
        ArrayList<Posting> list = getSortedPostingList();
        // looping buat list of term (dictionary)
        for (int i = 0; i < list.size(); i++) {
            // cek dictionary kosong?
            if(getDictionary().isEmpty()){
                // buat term
                Term term = new Term(list.get(i).getTerm());
                // tambah posting ke posting list utk term ini
                term.getPostingList().add(list.get(i));
                // tambah ke dictionary
                getDictionary().add(term);
            } else{
                // dictionary sudah ada isinya
                Term tempTerm = new Term(list.get(i).getTerm());
                // pembandingan apakah term sudah ada atau belum
                // luaran dari binarysearch adalah posisi
                int position= Collections.binarySearch(getDictionary(), tempTerm);
                if(position<0){
                    // term baru
                    // tambah postinglist ke term
                    tempTerm.getPostingList().add(list.get(i));
                    // tambahkan term ke dictionary
                    getDictionary().add(tempTerm);
                } else{
                    // term ada
                    // tambahkan postinglist saja dari existing term
                    getDictionary().get(position).
                            getPostingList().add(list.get(i));
                    // urutkan posting list
                    Collections.sort(getDictionary().get(position)
                            .getPostingList());
                }
                // urutkan term dictionary
                Collections.sort(getDictionary());
            }  
        }
    }

    /**
     * @return the listOfDocument
     */
    public ArrayList<Document> getListOfDocument() {
        return listOfDocument;
    }

    /**
     * @return the dictionary
     */
    public ArrayList<Term> getDictionary() {
        return dictionary;
    }

    /**
     * @param listOfDocument the listOfDocument to set
     */
    public void setListOfDocument(ArrayList<Document> listOfDocument) {
        this.listOfDocument = listOfDocument;
    }

    /**
     * @param dictionary the dictionary to set
     */
    public void setDictionary(ArrayList<Term> dictionary) {
        this.dictionary = dictionary;
    }
    
    public ArrayList<Posting> search(String query){
//        makeDictionary();
        String [] tempQuery = query.split(" ");
        ArrayList<Posting> tempPosting = new ArrayList<>();
        for (int i = 0; i < tempQuery.length; i++) {
            String string = tempQuery[i];
            if (i == 0) {
                tempPosting = searchOneWord(tempQuery[i]);
            }
            else {
                ArrayList<Posting> tempPosting1 = searchOneWord(tempQuery[i]);
                tempPosting = intersection(tempPosting, tempPosting1);
            }
        }
        return tempPosting;
    }
    
    public ArrayList<Posting> searchOneWord(String query){
        Term tempTerm = new Term(query);
        if(getDictionary().isEmpty()){
            // dictionary kosong
            return null;
        } else{
            int positionTerm = Collections.binarySearch(dictionary,tempTerm);
            if(positionTerm<0){
                // tidak ditemukan
                return null;
            } else{
                return dictionary.get(positionTerm).getPostingList();
            }
        }
    }
    
    public ArrayList<Posting> intersection(ArrayList<Posting> p1,
            ArrayList<Posting> p2){
        // mengecek p1 atau p2 sama dengan null?
        if (p1 == null || p2 == null) {
            // mengembalikan posting p1 atau p2
            return new ArrayList<>();
        }
        // menyiapkan posting tempPosting
        ArrayList<Posting> tempPostings = new ArrayList<>();
        // menyiapkan variable p1Index dan p2Index
        int p1Index = 0;
        int p2Index = 0;
        
        // menyiapkan variable post1 dan post2 bertipe Posting 
        Posting post1 = p1.get(p1Index);
        Posting post2 = p2.get(p2Index);

        while (true) {
            // mengecek id document post1 = id document post2?
            if (post1.getDocument().getId() == post2.getDocument().getId()) {
                try {
                    // menambahkan post1 ke tempPosting
                    tempPostings.add(post1);
                    // p1Index dan p2Index bertambah 1
                    p1Index++;
                    p2Index++;
                    
                    post1 = p1.get(p1Index);
                    post2 = p2.get(p2Index);
                } catch (Exception ex) {
                    // menghentikan program
                    break;
                }

            } // mengecek id document post1 < id document post2?
            else if (post1.getDocument().getId() < post2.getDocument().getId()) {
                try {
                    // p1Index bertambah 1
                    p1Index++;
                    post1 = p1.get(p1Index);
                } catch (Exception ex) {
                    // menghentikan program
                    break;
                }

            } 
            else {
                try {
                    // p2Index bertambah 1
                    p2Index++;
                    post2 = p2.get(p2Index);
                } catch (Exception ex) {
                    // menghentikan program
                    break;
                }
            }
        }
        // mengembalikan tempPosting
        return tempPostings;
    }
    
    public int getDocumentFrequency(String term){
        Term tempTerm = new Term(term);
        int number = Collections.binarySearch(getDictionary(), tempTerm);
        if (number > 0) {
            return getDictionary().get(number).getPostingList().size();
        }
        return 0;
    }
    
    public double getInverseDocumentFrequency(String term){
        
        double N = listOfDocument.size();
        double n = getDocumentFrequency(term);
        if (N == 0) {
            return 0;
        }
        else {
            double idf = Math.log10(N/n);
            return idf;            
        }
    }
    
    public int getTermFrequency(String term, int idDocument) {
        int temp = 0;
        for (int i = 0; i < getListOfDocument().size(); i++) {
            if (getListOfDocument().get(i).getId() == idDocument) {
                String[] terms = getListOfDocument().get(i).getListofTerm();
                for (int j = 0; j < terms.length; j++) {
                    if (term.equalsIgnoreCase(terms[j])) {
                        temp = temp + 1;
                    }
                }
            }
        }
        return temp;
}
    
    public ArrayList<Posting> getUnsortedPostingListWithTermNumber() {
        // cek untuk term yang muncul lebih dari 1 kali
        // siapkan posting List
        ArrayList<Posting> list = new ArrayList<Posting>();
        // buat node Posting utk listofdocument
        for (int i = 0; i < getListOfDocument().size(); i++) {
            // buat listOfTerm dari document ke -i
            //String[] termResult = getListOfDocument().get(i).getListofTerm();
            ArrayList<Posting> postingDocument = getListOfDocument().get(i).getListofPosting();
            // loop sebanyak term dari document ke i
            for (int j = 0; j < postingDocument.size(); j++) {
                // ambil objek posting
                Posting tempPosting = postingDocument.get(j);
                // cek kemunculan term
                list.add(tempPosting);
            }
        }
        return list;
    }
    
    public ArrayList<Posting> getSortedPostingListWithTermNumber() {
        // siapkan posting List
        ArrayList<Posting> list = new ArrayList<Posting>();
        // panggil list yang belum terurut
        list = this.getUnsortedPostingListWithTermNumber();
        // urutkan
        Collections.sort(list);
        return list;
    }
    
    public void makeDictionaryWithTermNumber(){
        // cek deteksi ada term yang frekuensinya lebih dari 
        // 1 pada sebuah dokumen
        // buat posting list term terurut
        ArrayList<Posting> list = getSortedPostingListWithTermNumber();
        // looping buat list of term (dictionary)
        for (int i = 0; i < list.size(); i++) {
            // cek dictionary kosong?
            if (getDictionary().isEmpty()) {
                // buat term
                Term term = new Term(list.get(i).getTerm());
                // tambah posting ke posting list utk term ini
                term.getPostingList().add(list.get(i));
                // tambah ke dictionary
                getDictionary().add(term);
            } 
            else {
                // dictionary sudah ada isinya
                Term tempTerm = new Term(list.get(i).getTerm());
                // pembandingan apakah term sudah ada atau belum
                // luaran dari binarysearch adalah posisi
                int position = Collections.binarySearch(getDictionary(), tempTerm);
                if (position < 0) {
                    // term baru
                    // tambah postinglist ke term
                    tempTerm.getPostingList().add(list.get(i));
                    // tambahkan term ke dictionary
                    getDictionary().add(tempTerm);
                } 
                else {
                    // term ada
                    // tambahkan postinglist saja dari existing term
                    getDictionary().get(position).
                            getPostingList().add(list.get(i));
                    // urutkan posting list
                    Collections.sort(getDictionary().get(position)
                            .getPostingList());
                }
                // urutkan term dictionary
                Collections.sort(getDictionary());
            }
        }
    }
    
    public ArrayList<Posting> makeTFIDF(int idDocument){
        Document doc = new Document();
        doc.setId(idDocument);
        // cek apakah dokument ada
        int cari = Collections.binarySearch(listOfDocument, doc);
        if (cari < 0) {
            // jika document tidak ada
            return  null;
        }
        else {
            // jika document ada
            doc = listOfDocument.get(cari);
            // membuat tempTerm Term menunjuk ke getDictionary()
            ArrayList<Term> tempTerm =  getDictionary();
            // membuat result Posting
            ArrayList<Posting> result = new ArrayList<Posting>();
            for (int i = 0; i < tempTerm.size(); i++) {
                // buat temp Posting
                Posting temp = new Posting();
                // panggil fungsi hitung idf
                double idf = getInverseDocumentFrequency(temp.getTerm());
                // panggil fungsi hitung tf
                double tf = temp.getNumberOfTerm();
                // hitung tf*idf
                double weight = tf*idf;
                // set term ke temp
                temp.setTerm(tempTerm.get(i).getTerm());
                // set weight ke temp
                temp.setWeight(weight);
                // add temp ke result
                result.add(temp);
            }
            return result;
        }
    }
    
    public double getInnerProduct(ArrayList<Posting> p1, ArrayList<Posting> p2){
        double[] innerPoduct = null;
        for (int i = 0; i < getListOfDocument().size(); i++) {
            innerPoduct[i] = p1.get(i).getWeight() * p2.get(i).getWeight();
        }
        return 0.0;
    }
    
    public ArrayList<Posting> getQueryPosting(String term){
        // menyimpan query sebagai sebuah document
        Document query = new Document(term);
        // menambahkan document baru
        addNewDocument(query); 
        // menyiapkan queryPost Posting
        ArrayList<Posting> queryPost = new ArrayList<>();
        // looping sebanyak document yang disimpan
        for (int i = 0; i < getListOfDocument().size(); i++) {
            // menampung tiap term dari document 
            String[] termQuery = getListOfDocument().get(i).getListofTerm();
            // looping sebanyak termQuery
            for (int j = 0; j < termQuery.length; j++) {
                // buat objek post Posting
                Posting post = new Posting(termQuery[j], 
                        getListOfDocument().get(i));
                // tambahkan post ke queryPost
                queryPost.add(post);
            }
        }
        // 
        double tf = 0;
        for (int i = 0; i < queryPost.size(); i++) {
            tf = getTermFrequency(queryPost.get(i).getTerm(),i);
            makeTFIDF(i);
        }
        //        
        return queryPost;
    }
}
