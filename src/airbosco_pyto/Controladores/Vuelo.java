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
                rs = queryVuelo("SELECT v.idVuelo, r.origen, r.destino, v.fechaSalida, v.matriculaAvion, r.duracion "
                        + "FROM vuelo v, ruta r "
                        + "WHERE v.idRuta = r.idRuta "
                        + "ORDER BY v.idVuelo DESC;");
            } else {
                rs = queryVuelo("SELECT v.idVuelo, r.origen, r.destino, v.fechaSalida, v.matriculaAvion, r.duracion "
                        + "FROM vuelo v, ruta r "
                        + "WHERE v.matriculaAvion ='" + matricula + "' AND v.idRuta = r.idRuta "
                        + "ORDER BY v.idVuelo DESC;");
            }

            rowsAvionesVuelo = new ArrayList();

            while (rs.next()) {
                rowsAvionesVuelo.add(new String[]{rs.getString("v.idVuelo"), rs.getString("r.origen"),
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
    
    public Object[] consultarVuelo(int idVuelo) {
        
        Object[] resultado = null;
        
        try {
            rs = queryVuelo("SELECT v.fechaSalida, v.matriculaAvion, v.idRuta, r.origen, r.destino, r.duracion "
                    + "FROM vuelo v, ruta r "
                    + "WHERE v.idVuelo ='" + idVuelo + "' AND v.idRuta = r.idRuta;");
            
            while(rs.next()) {
                resultado = new Object[] { rs.getLong("v.fechaSalida"), rs.getString("v.matriculaAvion"), 
                rs.getInt("v.idRuta") ,rs.getString("r.origen"), rs.getString("r.destino"), rs.getInt("r.duracion") };
            }
            
            rs.close();
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        return resultado;
        
    }    
    
    public void guardarVuelo(String avion, int idRuta, Long fechaSalid) {
        Main.bbddop.actualizar("INSERT INTO vuelo(fechaSalida, matriculaAvion, idRuta) VALUES('" + fechaSalid +"', '" + avion + "', " + idRuta + ");");
    }    
    
    public void actualizarVuelo(int idVuelo, int idRuta, Long fechaSalid) {
        Main.bbddop.actualizar("UPDATE vuelo "
                + "SET idRuta = " + idRuta + ", "
                + "fechaSalida = " + fechaSalid + " "
                + "WHERE idVuelo = " + idVuelo +";");
    }
    
    public void borrarVuelo(int idVuelo) {
        Main.bbddop.actualizar("DELETE FROM vuelo WHERE idVuelo = " + idVuelo + ";");
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
