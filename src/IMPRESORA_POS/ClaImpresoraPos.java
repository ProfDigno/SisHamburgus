/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IMPRESORA_POS;

import Evento.Mensaje.EvenMensajeJoptionpane;
import java.io.FileInputStream;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class ClaImpresoraPos {
    EvenMensajeJoptionpane evemen=new EvenMensajeJoptionpane();
    public static FileInputStream getInputStream() {
        return inputStream;
    }
    public static void setInputStream(FileInputStream aInputStream) {
        inputStream = aInputStream;
    }
    public void imprimir_ticket_Pos() {
        try {
           impresora_por_defecto(getInputStream());
        } catch (Exception e) {
           evemen.mensaje_error(e,"imprimir_ticket_Pos");
        }
    }

    private static FileInputStream inputStream = null;
    public static void impresora_por_defecto(FileInputStream inputStream) {
        DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc document = new SimpleDoc(inputStream, docFormat, null);
        PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
        if (defaultPrintService != null) {
            DocPrintJob printJob = defaultPrintService.createPrintJob();
            try {
                printJob.print(document, attributeSet);
                cortarHoja();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.err.println("No existen impresoras instaladas");
            JOptionPane.showMessageDialog(null, "No existen impresoras instaladas");
        }

    }

    public static void cortarHoja() throws PrintException {
        DocPrintJob job = PrintServiceLookup.lookupDefaultPrintService().createPrintJob();
        byte[] PARTIAL_CUT_1 = {0x1B, 0x69}; // cortar el ticket
        // byte[] PARTIAL_CUT_1 = {0x1B, 'F'}; // cortar el ticket
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        Doc doc = new SimpleDoc(PARTIAL_CUT_1, flavor, null);
        job.print(doc, null);
    }
}
