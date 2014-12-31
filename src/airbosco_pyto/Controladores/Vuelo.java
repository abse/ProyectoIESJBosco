package airbosco_pyto.Controladores;

import airbosco_pyto.Main;
import airbosco_pyto.Vistas.VueloMain;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author danielblancoparla
 */
public class Vuelo {

    ResultSet rs;

    public void showVueloMain() {
        VueloMain vueloMain = new VueloMain();
        vueloMain.setVisible(true);
    }

    private ResultSet queryVuelo(String query) {
        rs = Main.bbddop.consultar(query);
        return rs;
    }

    public ArrayList vueloAvionArrayList(String matricula) {
        ArrayList rowsAvionesVuelo = null;
        try {

            if (matricula.equals("TODOS")) {
                rs = queryVuelo("SELECT r.origen, r.destino, v.fechaSalida, v.matriculaAvion, r.duracion "
                        + "FROM vuelo v, ruta r "
                        + "WHERE v.idRuta = r.idRuta;");
            } else {
                rs = queryVuelo("SELECT r.origen, r.destino, v.fechaSalida, v.matriculaAvion, r.duracion "
                        + "FROM vuelo v, ruta r "
                        + "WHERE v.matriculaAvion ='" + matricula + "' AND v.idRuta = r.idRuta;");
            }

            rowsAvionesVuelo = new ArrayList();

            while (rs.next()) {
                rowsAvionesVuelo.add(new String[]{rs.getString("r.origen"),
                    rs.getString("r.destino"), Long.toString(rs.getLong("v.fechaSalida")), rs.getString("v.matriculaAvion"),
                    rs.getString("r.duracion")});
            }

            rs.close();

            return rowsAvionesVuelo;

        } catch (Exception ex) {
            Main.consola.insertarRow("Vuelo", ex.toString());
        }

        return rowsAvionesVuelo;
    }
    
//      METODOS PARA TRATAR VUELOS
    
    public void guardarVuelo(String avion, int idRuta, Long fechaSalida) {
        Main.bbddop.actualizar("INSERT INTO vuelo(fechaSalida, matriculaAvion, idRuta) VALUES(" + fechaSalida +", " + avion + ", " + idRuta + ");");
    }    

//    
//    METODOS PARA TRATAR RUTAS
//            
    public ArrayList rutasListar() {
        ArrayList rowsRutas = null;

        try {

            rs = queryVuelo("SELECT * FROM ruta;");

            rowsRutas = new ArrayList();

            while (rs.next()) {
                rowsRutas.add(new String[]{Integer.toString(rs.getInt("idRuta")),
                    rs.getString("origen"), rs.getString("destino"), Integer.toString(rs.getInt("duracion"))});
            }

            rs.close();

        } catch (Exception e) {
            Main.consola.insertarRow("Vuelo", e.toString());
        }

        return rowsRutas;
    }

    public void modificarRuta(String[] datosRuta) {
        Main.bbddop.actualizar("UPDATE ruta "
                + "SET origen = '" + datosRuta[1] + "',"
                + "destino = '" + datosRuta[2] + "',"
                + "duracion = '" + datosRuta[3] + "' "
                + "WHERE idRuta = '" + datosRuta[0] + "';"); // La condicion es la clave primaria

        Main.insertarConsola("Vuelo", "Modificada ruta: " + datosRuta[0]);

    }

    public void insertarRuta(String[] datosRuta) {
        Main.bbddop.actualizar("INSERT INTO ruta(origen, destino, duracion) "
                + "VALUES('" + datosRuta[0] + "', '" + datosRuta[1] + "', '" + datosRuta[2] + "');"); // La condicion es la clave primaria

        Main.insertarConsola("Vuelo", "Insertada ruta: " + datosRuta[0] + " - " + datosRuta[1]);

    }

    public void eliminarRuta(int idRuta) {
        Main.bbddop.actualizar("DELETE FROM ruta WHERE idRuta = " + idRuta + ";");

        Main.insertarConsola("Vuelo", "Eliminada ruta: " + idRuta);

    }

}
