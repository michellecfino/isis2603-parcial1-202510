package co.edu.uniandes.dse.parcialprueba.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class PacienteEntity extends BaseEntity {
    private String nombre; 
    private String correo;
    private String telefono;

    @OneToMany(mappedBy = "paciente")
    private List<HistoriaClinicaEntity> historiasClinicas = new ArrayList<>();

    @OneToOne
    private PacienteEntity acudiente;
}