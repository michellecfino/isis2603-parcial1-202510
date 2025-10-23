package co.edu.uniandes.dse.parcialprueba.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.HistoriaClinicaEntity;
import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.HistoriaClinicaRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.PacienteRepository;
import jakarta.transaction.Transactional;

@Service
public class HistoriaClinicaService {

    @Autowired
    private HistoriaClinicaRepository historiaClinicaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Transactional
    public HistoriaClinicaEntity crearHistoriaClinica(Long pacienteId, HistoriaClinicaEntity historia)throws IllegalOperationException {
        PacienteEntity paciente = pacienteRepository.findById(pacienteId).orElseThrow(() -> new IllegalOperationException("Paciente no encontrado"));

        if (paciente.getAcudiente() != null) {
            historia.setDiagnostico("HistoriaCompartida-" + historia.getDiagnostico());
        }

        historia.setPaciente(paciente);
        historia.setFechaDeCreacion(LocalDate.now());
        paciente.getHistoriasClinicas().add(historia);

        return historiaClinicaRepository.save(historia);
    }
}
