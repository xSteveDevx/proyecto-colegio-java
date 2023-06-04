package test;

import domain.Estudiante;
import domain.Materia;
import domain.Nota;
import domain.Profesor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Conexion {

    public static void main(String[] args) {
        // Establecer la conexión a la base de datos
        try ( Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/colegio", "root", "1234")) {
            System.out.println("Conexión exitosa a la base de datos");

            // Prueba de la clase Estudiante
            Estudiante estudiante = new Estudiante("Juan", "Pérez", "10A", "juan@gmail.com", "123456789");
            estudiante.guardar(conexion);
            System.out.println("Estudiante guardado con ID: " + estudiante.getId());

            

// Prueba de la clase Profesor
            Profesor profesor = new Profesor("John", "Doe");
            profesor.guardar(conexion);
            System.out.println("Profesor guardado con ID: " + profesor.getId());
            
//             Prueba de la clase Materia
            Materia materia = new Materia("Matemáticas");
            materia.guardar(conexion);
            System.out.println("Materia guardada con ID: " + materia.getId());
            materia.asignarProfesor(conexion, profesor);
            
            profesor.asignarMateria(conexion, materia);
            profesor.asignarGrado(conexion, profesor, "9A");


            // Prueba de la clase Nota
            Nota nota = new Nota(estudiante.getId(), profesor.getId(), materia.getId(), 3.5, 1);
            nota.guardar(conexion);
            System.out.println("Nota guardada con ID: " + nota.getId());

            // Consulta de notas de un estudiante específico
            List<Nota> notasEstudiante = Nota.consultarPorEstudiante(conexion, estudiante.getId());
            System.out.println("Notas del estudiante:");
            for (Nota n : notasEstudiante) {
                System.out.println("ID: " + n.getId() + ", Valor: " + n.getValor());
            }

            // Consulta de grados de un profesor específico
            List<String> gradosProfesor = Profesor.consultarGrados(conexion, profesor.getId());
            System.out.println("Grados del profesor:");
            for (String grado : gradosProfesor) {
                System.out.println(grado);
            }

        } catch (SQLException e) {
            System.out.println("Error al establecer la conexión a la base de datos: " + e.getMessage());
        }
    }
}
