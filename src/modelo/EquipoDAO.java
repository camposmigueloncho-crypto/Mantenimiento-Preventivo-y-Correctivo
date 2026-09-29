package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class EquipoDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean RegistrarEquipo(Equipo equipo) {

        String sql = "INSERT INTO equipo "
                + "(nombre, marca, modelo, numero_serie, procesador, memoria_ram, almacenamiento, sistema_operativo, estado, ubicacion) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";

        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, equipo.nombre);
            ps.setString(2, equipo.marca);
            ps.setString(3, equipo.modelo);
            ps.setString(4, equipo.numero_serie);
            ps.setString(5, equipo.procesador);
            ps.setString(6, equipo.memoria_ram);
            ps.setString(7, equipo.almacenamiento);
            ps.setString(8, equipo.sistema_operativo);
            ps.setString(9, equipo.estado);
            ps.setString(10, equipo.ubicacion);

            ps.execute();

            return true;

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, e.toString());
            return false;

        } finally {

            try {

                if (con != null) {
                    con.close();
                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    public List<Equipo> ListarEquipo() {

    List<Equipo> ListaEq = new ArrayList<>();

    String sql = "SELECT * FROM equipo";

    try {

        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {

            Equipo equipo = new Equipo();

            equipo.id_equipo = rs.getInt("id_equipo");
            equipo.nombre = rs.getString("nombre");
            equipo.marca = rs.getString("marca");
            equipo.modelo = rs.getString("modelo");
            equipo.numero_serie = rs.getString("numero_serie");
            equipo.procesador = rs.getString("procesador");
            equipo.memoria_ram = rs.getString("memoria_ram");
            equipo.almacenamiento = rs.getString("almacenamiento");
            equipo.sistema_operativo = rs.getString("sistema_operativo");
            equipo.estado = rs.getString("estado");
            equipo.ubicacion = rs.getString("ubicacion");

            ListaEq.add(equipo);
        }

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(null, e.toString());

    }

    return ListaEq;
}

    public boolean EliminarEquipo(int id) {

        String sql = "DELETE FROM equipo WHERE id_equipo = ?";

        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ps.execute();

            return true;

        } catch (SQLException e) {

            System.out.println(e.toString());
            return false;

        } finally {

            try {

                if (con != null) {
                    con.close();
                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    public boolean ModificarEquipo(Equipo equipo) {

        String sql = "UPDATE equipo SET "
                + "nombre=?, marca=?, modelo=?, numero_serie=?, procesador=?, "
                + "memoria_ram=?, almacenamiento=?, sistema_operativo=?, estado=?, ubicacion=? "
                + "WHERE id_equipo=?";

        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, equipo.nombre);
            ps.setString(2, equipo.marca);
            ps.setString(3, equipo.modelo);
            ps.setString(4, equipo.numero_serie);
            ps.setString(5, equipo.procesador);
            ps.setString(6, equipo.memoria_ram);
            ps.setString(7, equipo.almacenamiento);
            ps.setString(8, equipo.sistema_operativo);
            ps.setString(9, equipo.estado);
            ps.setString(10, equipo.ubicacion);
            ps.setInt(11, equipo.id_equipo);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println(e.toString());
            return false;

        } finally {

            try {

                if (con != null) {
                    con.close();
                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    public Equipo BuscarEquipo(int id) {

        Equipo equipo = new Equipo();

        String sql = "SELECT * FROM equipo WHERE id_equipo=?";

        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {

                equipo.id_equipo = rs.getInt("id_equipo");
                equipo.nombre = rs.getString("nombre");
                equipo.marca = rs.getString("marca");
                equipo.modelo = rs.getString("modelo");
                equipo.numero_serie = rs.getString("numero_serie");
                equipo.procesador = rs.getString("procesador");
                equipo.memoria_ram = rs.getString("memoria_ram");
                equipo.almacenamiento = rs.getString("almacenamiento");
                equipo.sistema_operativo = rs.getString("sistema_operativo");
                equipo.estado = rs.getString("estado");
                equipo.ubicacion = rs.getString("ubicacion");
            }

        } catch (SQLException e) {

            System.out.println(e.toString());
        }

        return equipo;
    }
public int contarEquipos(ArrayList<Equipo> lista, int posicion) {

    if (posicion == lista.size()) {
        return 0;
    }

    return 1 + contarEquipos(lista, posicion + 1);
}
 
public int contarMantenimiento(ArrayList<Equipo> lista, int posicion) {

    if (posicion == lista.size()) {
        return 0;
    }

    if (lista.get(posicion).estado != null
            && lista.get(posicion).estado.equalsIgnoreCase("En mantenimiento")) {

        return 1 + contarMantenimiento(lista, posicion + 1);
    }

    return contarMantenimiento(lista, posicion + 1);
}
    public String obtenerProximoMantenimiento(int idEquipo) {

    String sql = "SELECT MAX(fecha) AS ultima_fecha "
            + "FROM mantenimiento "
            + "WHERE id_equipo = ?";

    try {

       con = cn.getConnection();
            ps = con.prepareStatement(sql);

        ps.setInt(1, idEquipo);

        rs = ps.executeQuery();

        if (rs.next()) {

            java.sql.Date fecha =
                    rs.getDate("ultima_fecha");

            if (fecha == null) {

                return "Sin mantenimiento registrado";
            }

            java.time.LocalDate ultimaFecha =
                    fecha.toLocalDate();

            java.time.LocalDate proximoMantenimiento =
                    ultimaFecha.plusMonths(6);

            java.time.LocalDate hoy =
                    java.time.LocalDate.now();

            if (hoy.isEqual(proximoMantenimiento)) {

                return "Mantenimiento pendiente";

            } else if (hoy.isAfter(proximoMantenimiento)) {

                long dias =
                        java.time.temporal.ChronoUnit.DAYS.between(
                                proximoMantenimiento,
                                hoy
                        );

                return "Vencido hace "
                        + dias
                        + " días";

            } else {

                long dias =
                        java.time.temporal.ChronoUnit.DAYS.between(
                                hoy,
                                proximoMantenimiento
                        );

                return "Faltan "
                        + dias
                        + " días";
            }
        }

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
                null,
                "Error al calcular el mantenimiento: "
                + e.getMessage()
        );

    } finally {

        try {

            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();

        } catch (SQLException e) {

        }
    }

    return "Sin información";
}
public int contarOperativos(ArrayList<Equipo> lista, int posicion) {

    if (posicion == lista.size()) {
        return 0;
    }

    if (lista.get(posicion).estado != null
            && lista.get(posicion).estado.equalsIgnoreCase("Activo")) {

        return 1 + contarOperativos(lista, posicion + 1);
    }

    return contarOperativos(lista, posicion + 1);
}
}
