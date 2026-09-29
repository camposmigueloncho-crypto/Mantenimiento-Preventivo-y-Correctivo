/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Conexion;
import modelo.Mantenimiento;

public class MantenimientoDao {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public int RegistrarMantenimiento(Mantenimiento mantenimiento) {

    String sql = "INSERT INTO mantenimiento "
            + "(id_equipo, fecha, tipo, descripcion, estado, observacion) "
            + "VALUES (?,?,?,?,?,?)";

    try {

        con = cn.getConnection();

        ps = con.prepareStatement(
                sql,
                PreparedStatement.RETURN_GENERATED_KEYS
        );

        ps.setInt(1, mantenimiento.id_equipo);
        ps.setString(2, mantenimiento.fecha);
        ps.setString(3, mantenimiento.tipo);
        ps.setString(4, mantenimiento.descripcion);
        ps.setString(5, mantenimiento.estado);
        ps.setString(6, mantenimiento.observaciones);

        ps.executeUpdate();

        rs = ps.getGeneratedKeys();

        if (rs.next()) {
            return rs.getInt(1);
        }

        throw new SQLException("No se pudo obtener el ID del mantenimiento.");

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
                null,
                "Error al registrar el mantenimiento: " + e.getMessage()
        );

        return 0;

    } finally {

        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al cerrar la conexión: " + e.getMessage()
            );
        }
    }
}

    public List<Mantenimiento> ListarMantenimiento() {

        List<Mantenimiento> lista = new ArrayList<>();

        String sql = "SELECT * FROM mantenimiento";

        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                Mantenimiento mantenimiento = new Mantenimiento();

                mantenimiento.id_mantenimiento =
                        rs.getInt("id_mantenimiento");

                mantenimiento.id_equipo =
                        rs.getInt("id_equipo");

                mantenimiento.fecha =
                        rs.getString("fecha");

                mantenimiento.tipo =
                        rs.getString("tipo");

                mantenimiento.descripcion =
                        rs.getString("descripcion");

                mantenimiento.estado =
                        rs.getString("estado");

             mantenimiento.observaciones =
        rs.getString("observacion");

                lista.add(mantenimiento);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, e.toString());
        }

        return lista;
    }

    public boolean ModificarMantenimiento(Mantenimiento mantenimiento) {

      String sql = "UPDATE mantenimiento SET "
        + "id_equipo=?, fecha=?, tipo=?, descripcion=?, "
        + "estado=?, observacion=? "
        + "WHERE id_mantenimiento=?";

        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, mantenimiento.id_equipo);
            ps.setString(2, mantenimiento.fecha);
            ps.setString(3, mantenimiento.tipo);
            ps.setString(4, mantenimiento.descripcion);
            ps.setString(5, mantenimiento.estado);
            ps.setString(6, mantenimiento.observaciones);
            ps.setInt(7, mantenimiento.id_mantenimiento);

            ps.executeUpdate();

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

    public boolean EliminarMantenimiento(int id) {

        String sql =
                "DELETE FROM mantenimiento WHERE id_mantenimiento=?";

        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);
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

    public Mantenimiento BuscarMantenimiento(int id) {

        Mantenimiento mantenimiento = new Mantenimiento();

        String sql =
                "SELECT * FROM mantenimiento WHERE id_mantenimiento=?";

        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {

                mantenimiento.id_mantenimiento =
                        rs.getInt("id_mantenimiento");

                mantenimiento.id_equipo =
                        rs.getInt("id_equipo");

                mantenimiento.fecha =
                        rs.getString("fecha");

                mantenimiento.tipo =
                        rs.getString("tipo");

                mantenimiento.descripcion =
                        rs.getString("descripcion");

                mantenimiento.estado =
                        rs.getString("estado");

                mantenimiento.observaciones =
        rs.getString("observacion");
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, e.toString());
        }

        return mantenimiento;
    }
  

}