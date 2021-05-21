/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MigracionBD;

//import ClaseUTIL.FunMensaje;
//import ClaseUTIL.SQLejecucion;
//import ClaseUTIL.SQLprepar;
import BASEDATO.EvenConexion;
import Evento.Mensaje.EvenMensajeJoptionpane;
import java.sql.*;
import javax.swing.JOptionPane;
import org.postgresql.Driver;

/**
 * Esta clase se conecta directamente y exclusivamente con postgres pero depende
 * de los datos proporcionado por la base de dato SQLite por eso es importante
 * que los datos de conexion en el SQLite esteen corectos
 *
 * @author Pc
 */
public class ConnPostgres2_bd {

    private static Connection conn1Postgres = null;
    private static Connection conn2Postgres = null;
    public static String PsDriver;
    public static String PsConexion;
    public static String PsLocalhost;
    public static String PsPort;
    public static String PsNomBD;
    public static String PsUsuario;
    public static String PsContrasena;
//    VariablesBD var = new VariablesBD();
//    ConnSQLITEdato csd = new ConnSQLITEdato();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();
    EvenConexion evconn = new EvenConexion();

    public static Connection getConn1Postgres() {
        return conn1Postgres;
    }

    public static void setConn1Postgres(Connection conn1Postgres) {
        ConnPostgres2_bd.conn1Postgres = conn1Postgres;
    }

    public static Connection getConn2Postgres() {
        return conn2Postgres;
    }

    public static void setConn2Postgres(Connection conn2Postgres) {
        ConnPostgres2_bd.conn2Postgres = conn2Postgres;
    }


    void cargar1_VariablesDirecto() {
        PsDriver = "org.postgresql.Driver";
        PsConexion = "jdbc:postgresql";
        PsLocalhost = "localhost";
        PsPort = "5432";
        PsNomBD = "bdqchurron19";
        PsUsuario = "postgres";
        PsContrasena = "4650586";
        System.out.println("++++++++++++++++Carga de variable de sqlite para la conexion postgres=" + "\n" + PsDriver + "\n" + PsUsuario + "\n" + PsContrasena);
    }
//    void cargar2_VariablesDirecto() {
//        PsDriver = "org.postgresql.Driver";
//        PsConexion = "jdbc:postgresql";
//        PsLocalhost = "localhost";
//        PsPort = "5433";
//        PsNomBD = "bdqchurron_server";
//        PsUsuario = "postgres";
//        PsContrasena = "4650586";
//        System.out.println("++++++++++++++++Carga de variable de sqlite para la conexion postgres=" + "\n" + PsDriver + "\n" + PsUsuario + "\n" + PsContrasena);
//    }
    public void Connect1_DBpostgres() {
        cargar1_VariablesDirecto();
        try {
            String connectString = "" + PsConexion + "://" + PsLocalhost + ":" + PsPort + "/" + PsNomBD + "";
            Class.forName(PsDriver);
            Connection connLocal = DriverManager.getConnection(connectString, PsUsuario, PsContrasena);
            if (connLocal != null) {
                System.out.println("++++++++++++++++Conection a posgrest suceso" + "\n" + PsDriver + "\n" + connectString + "\n" + PsUsuario + "\n" + PsContrasena);
                if (true) {
                    JOptionPane.showMessageDialog(null, "++Conection a posgrest suceso++" + "\n" + PsDriver + "\n" + connectString + "\n" + PsUsuario);
                }
            }
            setConn1Postgres(connLocal);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error en la conexion con base de datos"
                    + "\nLocal Host: " + PsLocalhost
                    + "\nPuerto: " + PsPort
                    + "\nUsuario: " + PsUsuario
                    + "\nError: " + e.getMessage(), "ERROR CONEXION", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(null, "EL SISTEMA SE VA CERRAR POR FALLA EN LA CONEXION", "ERROR CONEXION", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
//    private void Connect2_DBpostgres() {
//        cargar2_VariablesDirecto();
//        try {
//            String connectString = "" + PsConexion + "://" + PsLocalhost + ":" + PsPort + "/" + PsNomBD + "";
//            Class.forName(PsDriver);
//            Connection connLocal = DriverManager.getConnection(connectString, PsUsuario, PsContrasena);
//            if (connLocal != null) {
//                System.out.println("++++++++++++++++Conection a posgrest suceso" + "\n" + PsDriver + "\n" + connectString + "\n" + PsUsuario + "\n" + PsContrasena);
//                if (true) {
//                    JOptionPane.showMessageDialog(null, "++Conection a posgrest suceso++" + "\n" + PsDriver + "\n" + connectString + "\n" + PsUsuario);
//                }
//            }
//            setConn2Postgres(connLocal);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            JOptionPane.showMessageDialog(null, "Ocurrio un error en la conexion con base de datos"
//                    + "\nLocal Host: " + PsLocalhost
//                    + "\nPuerto: " + PsPort
//                    + "\nUsuario: " + PsUsuario
//                    + "\nError: " + e.getMessage(), "ERROR CONEXION", JOptionPane.ERROR_MESSAGE);
//            JOptionPane.showMessageDialog(null, "EL SISTEMA SE VA CERRAR POR FALLA EN LA CONEXION", "ERROR CONEXION", JOptionPane.ERROR_MESSAGE);
//            System.exit(0);
//        }
//    }
}
