package co.edu.uniandes.dse.parcialprueba.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class HistoriaClinicaEntity extends BaseEntity {
    private String diagnostico;
    private String tratamiento;
    private LocalDate fechaDeCreacion;

    @ManyToOne
    private PacienteEntity paciente;
}
