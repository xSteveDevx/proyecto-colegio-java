package domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Materia {

    private int id;
    private String nombre;
    private int profesorId;

    // Constructor
    public Materia(String nombre) {
        this.nombre = nombre;
    }

    // Método para insertar la materia en la base de datos y asignar el ID generado
    public void guardar(Connection conexion) throws SQLException {
        String sql = "INSERT INTO materia (nombre) VALUES (?)";
        try ( PreparedStatement consulta = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            consulta.setString(1, nombre);

            consulta.executeUpdate();

            // Obtener el ID generado por la base de datos
            ResultSet resultado = consulta.getGeneratedKeys();
            if (resultado.next()) {
                this.id = resultado.getInt(1);
            }
        }
    }

    public void asignarProfesor(Connection conexion, Profesor profesor) throws SQLException {
        String sql = "UPDATE materia SET profesor_id = ? WHERE id = ?";
        try ( PreparedStatement consulta = conexion.prepareStatement(sql)) {
            
            consulta.setInt(1, profesor.getId());
            consulta.setInt(2, this.id);
            consulta.executeUpdate();

            System.out.println("Se ha asignado el profesor a la materia correctamente");

        }
    }
    
    // Método para obtener el nombre del profesor de la materia
    public String obtenerNombreProfesor(Connection conexion) throws SQLException {
        String nombreProfesor = null;
        String sql = "SELECT nombre FROM profesor WHERE id = ?";
        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setInt(1, profesorId);
            
            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                nombreProfesor = resultado.getString("nombre");
            }
        }
        return nombreProfesor;
    }

    
    
    // Resto de los métodos, getters y setters, etc.

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(int profesorId) {
        this.profesorId = profesorId;
    }

    @Override
    public String toString() {
        return "Materia{" + "id=" + id + ", nombre=" + nombre + ", profesorId=" + profesorId + '}';
    }
    
    
    
    
}




    
    


