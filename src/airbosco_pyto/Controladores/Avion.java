
package airbosco_pyto.Controladores;

import airbosco_pyto.Main;
import airbosco_pyto.Vistas.AvionCrear;
import airbosco_pyto.Vistas.AvionMain;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author danielblancoparla
 */
public class Avion {

    AvionMain avionMain = null;
    AvionCrear avionCrear = null;

    public void showAvionMain() {
        avionMain = new AvionMain();
        avionMain.setVisible(true);
    }

    public void showAvionCrear() {
        avionCrear = new AvionCrear();
        avionCrear.setVisible(true);
    }

    public void showAvionCrear(String[] editoAvion) {
        avionCrear = new AvionCrear(editoAvion);
        avionCrear.setVisible(true);
    }

    private ResultSet queryAvion(String query) {
        ResultSet rs = Main.bbddop.consultar(query);
        return rs;
    }

    public ArrayList avionesArrayList() {
        ArrayList rowsAviones = null;
        try {
            ResultSet rs = queryAvion("SELECT * FROM avion");

            rowsAviones = new ArrayList();

            while (rs.next()) {
                System.out.println("Añadiendo: " + rs.getString("matricula"));
                rowsAviones.add(new String[]{rs.getString("matricula"), rs.getString("marca"), rs.getString("modelo"), rs.getString("año"), rs.getString("asientosNormales"), rs.getString("asientosBusiness")});
            }
            
            rs.close();

            return rowsAviones;

        } catch (Exception ex) {
            Main.consola.insertarRow("Avion", ex.toString());
        }

        return rowsAviones;
    }

    public void insertarAvion(String[] datosAvion) {
        Main.bbddop.actualizar("INSERT INTO avion(matricula, marca, modelo, año, asientosNormales, asientosBusiness) VALUES ('" + datosAvion[0] + "', '" + datosAvion[1] + "', '" + datosAvion[2] + "', '" + datosAvion[3] + "', '" + datosAvion[4] + "', '" + datosAvion[5] + "')");

        Main.insertarConsola("Avion", "Insertado avion: " + datosAvion[0]);

    }

    public void modificarAvion(String[] datosAvion, String matricula) {
        Main.bbddop.actualizar("UPDATE avion "
                + "SET matricula = '" + datosAvion[0] + "',"
                + "marca = '" + datosAvion[1] + "',"
                + "modelo = '" + datosAvion[2] + "',"
                + "año = '" + datosAvion[3] + "',"
                + "asientosNormales = '" + datosAvion[4] + "',"
                + "asientosBusiness = '" + datosAvion[5] + "' "
                + "WHERE matricula = '" + matricula + "';"); // La condicion es la clave primaria

        Main.insertarConsola("Avion", "Modificado avion: " + matricula);

    }

    public void borrarAvion(String matricula) {
        Main.bbddop.actualizar("DELETE FROM avion WHERE matricula = '" + matricula + "';");

        Main.insertarConsola("Avion", "Avion borrado: " + matricula);

    }

}
