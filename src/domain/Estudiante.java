package domain;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Estudiante {
    private int id;
    private String nombre;
    private String apellido;
    private String grado;
    private String correo;
    private String telefono;
    
    
    //CONSTRUCTOR DE LA CLASE
    public Estudiante(String nombre, String apellido, String grado, String correo, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.grado = grado;
        this.correo = correo;
        this.telefono = telefono;
    }
     // Método para insertar el estudiante en la base de datos y asignar el ID generado
    public void guardar(Connection conexion) throws SQLException {
        String sql = "INSERT INTO estudiante (nombre, apellido, grado, correo, telefono) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement consulta = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            consulta.setString(1, nombre);
            consulta.setString(2, apellido);
            consulta.setString(3, grado);
            consulta.setString(4, correo);
            consulta.setString(5, telefono);
            
            consulta.executeUpdate();
            
            // Obtener el ID generado por la base de datos
            ResultSet resultado = consulta.getGeneratedKeys();
            if (resultado.next()) {
                this.id = resultado.getInt(1);
            }
        }
    }

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

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Estudiante{" + "id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", grado=" + grado + ", correo=" + correo + ", telefono=" + telefono + '}';
    }
    
    


}
