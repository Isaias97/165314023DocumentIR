/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javafx.print.Collation;

/**
 *
 * @author Aureli Isaias
 */
public class Document implements Comparable<Document>{
    private int id;
    private String content;

    public Document(int id) {
        this.id = id;
    }

    public Document() {
    }

    public Document(String content) {
        this.content = content;
    }

    public Document(int id, String content) {
        this.id = id;
        this.content = content;
    }
    
    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    public String[] getListofTerm(){
        String value = this.getContent();
        value = value.replaceAll("[.,?!]", "");
        return value.split(" ");
    }
    
    public ArrayList<Posting> getListofPosting(){
        // panggil fungsi getListofTerm
        String [] term = getListofTerm();
        // buat objek Arraylist<Posting> untuk menampung hasil
        ArrayList<Posting> result = new ArrayList<Posting>();
        // buat looping sebanyak listofTerm
        for (int i = 0; i < term.length; i++) {            
            // di dalam looping
            // jika term pertama maka
            if (i == 0) {
                // buat objek tempPosting
                // set atribut document, gunakan "this"
                Posting tempPosting = new Posting(term[0],this);
                // tambahkan ke ArrayList result
                result.add(tempPosting);
            }
            // lainnya
            else {
                // sorting ArrayList result
                Collections.sort(result);
                // cek apakah term sudah ada
                // gunakan fungsi search dengan luaran indek objek yang memenuhi
                Posting tempPosting = new Posting(term[i], this);
                int cari = Collections.binarySearch(result, tempPosting);
                // jika hasil cari kurang dari 0(objek tidak ada)
                if (cari < 0) {                    
                    // set atribut document, gunakan "this"                    
                    // tambahkan ke ArrayList result
                    result.add(tempPosting);
                }
                // lainnya (objek ada)
                else {
                    // ambil postingnya,                    
                    // tambahkan atribut numberofTerm dengan 1 
                    // dng fungsi get(indekhasilCari).getNumberofTerm
                    int tempNumber = result.get(cari).getNumberOfTerm() + 1;
                    
                    result.get(cari).setNumberOfTerm(tempNumber);
                }
            }
        }
        return result;
    }
    
    public void readFile(int id, File file){
        // simpan id doc
        this.id = id;
        // baca file
        try {
            FileReader bacaFile = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(bacaFile);
            String str;
            
            str = bufReader.readLine();
            
            while (str != null) {            
                this.setContent(content);
            }

            bufReader.close();
        }  catch (FileNotFoundException f){
            System.out.println("File not found");
        }        
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    @Override
    public int compareTo(Document t) {
        return id - t.getId();
    }
}
