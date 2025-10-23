package co.edu.uniandes.dse.parcialprueba.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.PacienteRepository;
import jakarta.transaction.Transactional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    // Aquí va el crud con las reglas del negocio del enunciado :D

    @Transactional
    public PacienteEntity createPaciente(PacienteEntity paciente) throws IllegalOperationException {
        if (!paciente.getTelefono().matches("^(311|601)\\d{8}$")) {
            throw new IllegalOperationException("El teléfono debe tener 11 dígitos y empezar por 311 o 601");
        }
        return pacienteRepository.save(paciente);
    }

    @Transactional
    public PacienteEntity asignarAcudiente(Long pacienteId, Long acudienteId) throws IllegalOperationException {
        PacienteEntity paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new IllegalOperationException("Paciente no encontrado"));
        PacienteEntity acudiente = pacienteRepository.findById(acudienteId)
                .orElseThrow(() -> new IllegalOperationException("Acudiente no encontrado"));

        if (paciente.getAcudiente() != null)
            throw new IllegalOperationException("El paciente ya tiene acudiente");

        if (acudiente.getAcudiente() != null)
            throw new IllegalOperationException("El acudiente ya tiene su propio acudiente");

        if (acudiente.getHistoriasClinicas().isEmpty())
            throw new IllegalOperationException("El acudiente debe tener al menos una historia clínica");

        paciente.setAcudiente(acudiente);
        return pacienteRepository.save(paciente);
    }

    @Transactional
    public Optional<PacienteEntity> getPaciente(Long id) {
        return pacienteRepository.findById(id);
    }
}