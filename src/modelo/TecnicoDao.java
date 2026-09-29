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
import modelo.Tecnico;

public class TecnicoDao {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean RegistrarTecnico(Tecnico tecnico) {

        String sql = "INSERT INTO tecnico "
                + "(nombre, telefono, especialidad, correo, estado) "
                + "VALUES (?,?,?,?,?)";

        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, tecnico.nombre);
            ps.setString(2, tecnico.telefono);
            ps.setString(3, tecnico.especialidad);
            ps.setString(4, tecnico.correo);
            ps.setString(5, tecnico.estado);

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

    public List<Tecnico> ListarTecnico() {

        List<Tecnico> lista = new ArrayList<>();

        String sql = "SELECT * FROM tecnico";

        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                Tecnico tecnico = new Tecnico();

                tecnico.id_tecnico = rs.getInt("id_tecnico");
                tecnico.nombre = rs.getString("nombre");
                tecnico.telefono = rs.getString("telefono");
                tecnico.especialidad = rs.getString("especialidad");
                tecnico.correo = rs.getString("correo");
                tecnico.estado = rs.getString("estado");

                lista.add(tecnico);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, e.toString());
        }

        return lista;
    }

    public boolean ModificarTecnico(Tecnico tecnico) {

        String sql = "UPDATE tecnico SET "
                + "nombre=?, telefono=?, especialidad=?, correo=?, estado=? "
                + "WHERE id_tecnico=?";

        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, tecnico.nombre);
            ps.setString(2, tecnico.telefono);
            ps.setString(3, tecnico.especialidad);
            ps.setString(4, tecnico.correo);
            ps.setString(5, tecnico.estado);
            ps.setInt(6, tecnico.id_tecnico);

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

    public boolean EliminarTecnico(int id) {

        String sql = "DELETE FROM tecnico WHERE id_tecnico=?";

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

    public Tecnico BuscarTecnico(int id) {

        Tecnico tecnico = new Tecnico();

        String sql = "SELECT * FROM tecnico WHERE id_tecnico=?";

        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {

                tecnico.id_tecnico = rs.getInt("id_tecnico");
                tecnico.nombre = rs.getString("nombre");
                tecnico.telefono = rs.getString("telefono");
                tecnico.especialidad = rs.getString("especialidad");
                tecnico.correo = rs.getString("correo");
                tecnico.estado = rs.getString("estado");
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, e.toString());
        }

        return tecnico;
    }
}