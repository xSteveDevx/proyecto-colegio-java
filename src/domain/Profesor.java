package domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Profesor {

    private int id;
    private String nombre;
    private String apellido;
    private int materiaId;

    //CONSTRUCTOR
    public Profesor(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    // Método para guardar el profesor en la base de datos
    public void guardar(Connection conexion) throws SQLException {
        String sql = "INSERT INTO profesor (nombre, apellido) VALUES (?, ?)";
        try ( PreparedStatement consulta = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            consulta.setString(1, nombre);
            consulta.setString(2, apellido);

            consulta.executeUpdate();

//          Obtener el ID generado por la base de datos
            ResultSet resultado = consulta.getGeneratedKeys();
            if (resultado.next()) {
                id = resultado.getInt(1);
            }
        }
    }
    public void asignarMateria(Connection conexion, Materia materia) throws SQLException{
        String sql = "UPDATE profesor SET materia_id = ? WHERE id = ?";
        try (PreparedStatement consulta = conexion.prepareStatement(sql)){
            consulta.setInt(1, materia.getId());
            consulta.setInt(2, this.id);
            consulta.executeUpdate();
            
            this.materiaId = materia.getId();
            System.out.println("Se ha asignado la materia correctamente");

        } 
    }
    
    public void asignarGrado(Connection conexion, Profesor profesor, String grado) throws SQLException{
        String sql = "INSERT INTO profesor_grado (profesor_id, grado) VALUES (?, ?)";
        try (PreparedStatement consulta = conexion.prepareStatement(sql)){
            consulta.setInt(1, profesor.getId());
            consulta.setString(2, grado);
            consulta.executeUpdate();
            
            System.out.println("Se ha asignado el grado correctamente");

        } 
    }


    //CONSULTAR GRADOS
    public static List<String> consultarGrados(Connection conexion, int profesorId) throws SQLException {
        List<String> grados = new ArrayList<>();

        String sql = "SELECT grado FROM profesor_grado WHERE profesor_id = ?";
        try ( PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setInt(1, profesorId);

            try ( ResultSet resultado = consulta.executeQuery()) {
                while (resultado.next()) {
                    String grado = resultado.getString("grado");
                    grados.add(grado);
                }
            }
        }

        return grados;
    }

    //getter y setter
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(int materiaId) {
        this.materiaId = materiaId;
    }

}
