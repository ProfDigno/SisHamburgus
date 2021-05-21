/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import FORMULARIO.BO.BO_backup;
import FORMULARIO.DAO.DAO_backup;
import FORMULARIO.ENTIDAD.backup;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Pc
 */
public class FrmCrearBackup extends javax.swing.JInternalFrame {

    private static String informebackup;
    private boolean estadoTiempo;
    private Timer tiempo;
    private int contartiempo;
    EvenJFRAME evetbl = new EvenJFRAME();
    EvenFecha evefec=new EvenFecha();
    backup bac = new backup();
    BO_backup bBO = new BO_backup();
    DAO_backup bdao = new DAO_backup();
    EvenJTextField evejtf = new EvenJTextField();
    Connection conn = ConnPostgres.getConnPosgres();
    private static String fecha;
    boolean hab_realizar_backup = true;
    private static boolean sin_error_backup=true;

    public static void realizaBackup(backup bac) throws IOException, InterruptedException {
        final List<String> comandos = new ArrayList<String>();
        comandos.add(bac.getB3direc_dump());
        comandos.add("-h");
        comandos.add(bac.getB6localhost());     //ou  comandos.add("192.168.0.1"); 
        comandos.add("-p");
        comandos.add(bac.getB7puerto());
        comandos.add("-U");
        comandos.add(bac.getB8usuario());
        comandos.add("-F");
        comandos.add("c");
        comandos.add("-b");
        comandos.add("-v");
        comandos.add("-f");
        comandos.add(bac.getB4direc_backup()+fecha);   // eu utilizei meu C:\ e D:\ para os testes e gravei o backup com sucesso.  
        comandos.add(bac.getB5basedato());
        ProcessBuilder pb = new ProcessBuilder(comandos);
        pb.environment().put("PGPASSWORD",bac.getB9senha());      //Somente coloque sua senha         
        try {
            final Process process = pb.start();
            final BufferedReader r = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));
            String line = r.readLine();
            informebackup = informebackup +  "###INFORME DE BACKUP###\n";
            while (line != null) {
                System.err.println(line);
                informebackup = informebackup + line + "\r\n";
                line = r.readLine();
            }
            r.close();
            process.waitFor();
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
            informebackup = informebackup +comandos+ e + "\r\n";
            sin_error_backup=false;
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            informebackup = informebackup +comandos+ ie + "\r\n";
            sin_error_backup=false;
        }
    }

    private void cargar_textArea(JTextArea txtarea, String text) {
        txtarea.setText(txtarea.getText() + text + "\r\n");
        txtarea.repaint();
        DefaultCaret caret = (DefaultCaret) txtarea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }

    void abrir() {
        this.setTitle("BACKUP");
        evetbl.centrar_formulario(this);
        fecha=evefec.getString_formato_fecha();
        fecha=fecha+".backup";
        iniciarTiempo();
    }

    void iniciarTiempo() {
        estadoTiempo = true;
        tiempo = new Timer();
        //le asignamos una tarea al timer
        tiempo.schedule(new FrmCrearBackup.clasetimer(), 0, 1000 * 1);
        System.out.println("Timer Iniciado backup");
    }

    void pararTiempos() {
        if (estadoTiempo) {
            tiempo.cancel();
            System.out.println("Timer parado backup");
        }
    }

    class clasetimer extends TimerTask {

        public void run() {
            contartiempo++;
            if (hab_realizar_backup) {
                if (contartiempo == 2) {
                    cargar_textArea(txtenviobackup, "####--INICIO BACKUP ESPERE...--####");
                }
                if (contartiempo == 4) {
                    try {
                        bdao.cargar_backup(bac);
                        realizaBackup(bac);
                        cargar_textArea(txtenviobackup, informebackup);
                    } catch (IOException e) {
                        e.printStackTrace();
                        sin_error_backup=false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        sin_error_backup=false;
                    }

                }
                if (contartiempo == 9) {
                    cargar_textArea(txtenviobackup, "####--INSERTADO FECHA BACKUP--####");
                }
                if (contartiempo == 10) {
                    cargar_textArea(txtenviobackup,"LOCALHOST:"+bac.getB6localhost());
                }
                if (contartiempo == 11) {
                    cargar_textArea(txtenviobackup,"DATOS:"+bac.toString());
                }
                if (contartiempo == 12) {
                    cargar_textArea(txtenviobackup, "####---backup Terminado---####");
                    if(sin_error_backup){
//                        bBO.update_backup_creado_hoy();
                    }
                }
                if (contartiempo == 20) {
                    cerrar_formulario();
                    pararTiempos();
                    bBO.update_backup_creado_hoy();
                }
            } else {
                if (contartiempo == 5) {
                    cargar_textArea(txtenviobackup, "####---NO HAY DATOS DE DIRECCION PARA BACKUP---####");
                }
                if (contartiempo == 10) {
                    cerrar_formulario();
                    pararTiempos();
                }
            }
        }
    }

    void cerrar_formulario() {
        this.dispose();
    }

    public FrmCrearBackup() {
        initComponents();
        abrir();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtenviobackup = new javax.swing.JTextArea();

        setClosable(true);
        setTitle("CREAR BACKUP");

        txtenviobackup.setColumns(20);
        txtenviobackup.setRows(5);
        jScrollPane1.setViewportView(txtenviobackup);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtenviobackup;
    // End of variables declaration//GEN-END:variables
}
