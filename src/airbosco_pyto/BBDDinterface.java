/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airbosco_pyto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author danielblancoparla
 */
public class BBDDinterface {

    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    
    public BBDDinterface() {
        conectarBBDD();
    }

    public void conectarBBDD() {
        try {

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://5.196.18.204/proyectofp?"
                    + "user=proyecto&password=proyecto");
            
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public ResultSet consultar(String cadena) {
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadena);
        } catch (Exception ex) {
            Main.insertarConsola("BBDD", "Error al realizar la consulta: " + cadena);
            System.out.println(ex);
        }
        
        return rs;
    }
    
    public void actualizar(String sentencia) {
        try {
            stmt.executeUpdate(sentencia);
            Main.insertarConsola("BBDD", "Inserción/Modificación realizada.");
        } catch (Exception ex) {
            Main.insertarConsola("BBDD", "Error al realizar la consulta: " + ex);
            System.out.println(ex);
        }
    }

}
