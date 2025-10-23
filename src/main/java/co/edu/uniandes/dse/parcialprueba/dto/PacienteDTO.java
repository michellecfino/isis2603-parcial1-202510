package co.edu.uniandes.dse.parcialprueba.dto;

import lombok.Data;

@Data
public class PacienteDTO {
    
    private Long id;
    private String nombre;
    private String correo;
    private String telefono;
}
