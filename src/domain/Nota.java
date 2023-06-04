package domain;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Nota {
    private int id;
    private int estudianteId;
    private int profesorId;
    private int materiaId;
    private double valor;
    private int periodo;

    //CONSTRUCTOR
    public Nota(int estudianteId, int profesorId, int materiaId, double valor, int periodo) {
        this.estudianteId = estudianteId;
        this.profesorId = profesorId;
        this.materiaId = materiaId;
        this.valor = valor;
        this.periodo = periodo;
    }
    
    // Método para guardar la nota en la base de datos
    public void guardar(Connection conexion) throws SQLException {
        String sql = "INSERT INTO nota (estudiante_id, profesor_id, materia_id, valor, periodo) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement consulta = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            consulta.setInt(1, estudianteId);
            consulta.setInt(2, profesorId);
            consulta.setInt(3, materiaId);
            consulta.setDouble(4, valor);
            consulta.setInt(5, periodo);
            
            consulta.executeUpdate();
            
            // Obtener el ID generado por la base de datos
            ResultSet resultado = consulta.getGeneratedKeys();
            if (resultado.next()) {
                this.id = resultado.getInt(1);
            }
        }
    }
    
    //CONSULTAR NOTA
    public static List<Nota> consultarPorEstudiante(Connection conexion, int estudianteId) throws SQLException {
    List<Nota> notas = new ArrayList<>();

    String consulta = "SELECT id, estudiante_id, profesor_id, materia_id, valor, periodo FROM nota WHERE estudiante_id = ?";
    try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
        statement.setInt(1, estudianteId);
        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) {
            int id = resultado.getInt("id");
            int notaEstudianteId = resultado.getInt("estudiante_id");
            int profesorId = resultado.getInt("profesor_id");
            int materiaId = resultado.getInt("materia_id");
            double valor = resultado.getDouble("valor");
            int periodo = resultado.getInt("periodo");

            Nota nota = new Nota(notaEstudianteId, profesorId, materiaId, valor, periodo);
            nota.setId(id);
            notas.add(nota);
        }
    }

    return notas;
}


    // Getter y Setter para el ID
    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public int getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(int estudianteId) {
        this.estudianteId = estudianteId;
    }

    public int getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(int materiaId) {
        this.materiaId = materiaId;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }
    
    
}