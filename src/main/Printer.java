/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.awt.print.PrinterException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;

/**
 *
 * @author Kasun
 */
public class Printer {
    final String header,footer;
    String content;
    public Printer(){
        header="<html><head><title>Report</title></head><body bgcolor=FFFFFF>\n" +
                "<font face=\"Verdana\" color=\"#000000\">\n" 
                + "<img src="+Printer.class.getClassLoader().getSystemResource("images\\LetterHead.png").toString()+" ></img>"
                +
                "<table border=0 width=\"100%\" cellspacing=\"0\" cellpadding=\"5\">\n"
                ;
        footer="</table></td></tr>\n" +
                "</font></body></html>";
        content="";
        this.addField("Report");
        this.addField("Content");
        this.addData("wbc", "12.24");
        this.addField("end");
    }
    public String getPrintPriview(){
        return header+content+footer;
    }
    void Print(){
        JEditorPane htmlPane= new JEditorPane();
    htmlPane.setContentType( "text/html" );
    htmlPane.setText(this.getPrintPriview());
        try {
            htmlPane.print();
        } catch (PrinterException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void addField(String field){
        content=content.concat("<tr valign=\"top\" bgcolor=\"#E0E0FF\"><td width=\"900\"><b>" +field
                + "</b></td><td valign=\"center\"><small><small><font color=\"#0000A0\">&nbsp;</font></small></small></td></tr>\n" );
    }
    
    void addData(String data,String data2){
        content=content.concat("<tr valign=\"top\" bgcolor=\"#FFFFFF\"><td width=\"900\">"+data+"</td><td valign=\"center\"><font color=\"#0000A0\">"+data2+"</font></td></tr>\n" );
    }
}
