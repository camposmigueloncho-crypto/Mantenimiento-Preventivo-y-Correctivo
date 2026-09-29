package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ReporteDao {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean RegistrarReporte(int idMantenimiento, String fecha, String observaciones) {

        String sql = "INSERT INTO reporte "
                + "(id_mantenimiento, fecha_reporte, observaciones) "
                + "VALUES (?,?,?)";

        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, idMantenimiento);
            ps.setString(2, fecha);
            ps.setString(3, observaciones);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al registrar el reporte: "
                    + e.getMessage()
            );

            return false;

        } finally {

            try {

                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();

            } catch (SQLException e) {

                JOptionPane.showMessageDialog(
                        null,
                        "Error al cerrar la conexión: "
                        + e.getMessage()
                );
            }
        }
    }

   
public void MostrarReportes(DefaultTableModel modelo) {

    String sql = "SELECT r.id_reporte, r.id_mantenimiento, "
            + "r.fecha_reporte, m.tipo, m.estado, r.observaciones "
            + "FROM reporte r "
            + "INNER JOIN mantenimiento m "
            + "ON r.id_mantenimiento = m.id_mantenimiento";

    try {

        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

        modelo.setRowCount(0);

        while (rs.next()) {

            Object[] fila = new Object[6];

            fila[0] = rs.getInt("id_reporte");
            fila[1] = rs.getInt("id_mantenimiento");
            fila[2] = rs.getDate("fecha_reporte");
            fila[3] = rs.getString("tipo");
            fila[4] = rs.getString("estado");
            fila[5] = rs.getString("observaciones");

            modelo.addRow(fila);
        }

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
                null,
                "Error al mostrar los reportes: "
                + e.getMessage()
        );

    } finally {

        try {

            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al cerrar la conexión: "
                    + e.getMessage()
            );
        }
    }
}
}
