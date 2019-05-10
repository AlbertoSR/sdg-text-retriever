/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upm.parser;

import com.upm.db.Database;
import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 *
 * @author Djbosh
 */

public class PdfParser {

    public String getTextFromPDF(String pdfName, int ods) {
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        PDFTextStripper pdfStripper;
        String parsedText = "";

        File file = new File(pdfName);
        try {
            pdDoc = PDDocument.load(file);
            pdfStripper = new PDFTextStripper();
            parsedText = pdfStripper.getText(pdDoc);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (cosDoc != null) {
                    cosDoc.close();
                }
                if (pdDoc != null) {
                    pdDoc.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        Database db = new Database();
        db.insertTextIntoDatabase(ods, parsedText);
        return parsedText;
    }
    
    public String getTextFromReport(String pdfName, int startPage, int endPage, int ods){
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        PDFTextStripper pdfStripper;
        String parsedText = "";
        File file = new File(pdfName);
        try {
            pdDoc = PDDocument.load(file);
            pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(startPage);
            pdfStripper.setEndPage(endPage);
            parsedText = pdfStripper.getText(pdDoc);
            Database db = new Database();
            db.insertTextIntoDatabase(ods, parsedText);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (cosDoc != null) {
                    cosDoc.close();
                }
                if (pdDoc != null) {
                    pdDoc.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return parsedText;
    }
}
