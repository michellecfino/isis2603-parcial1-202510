package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.HistoriaClinicaEntity;
import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.PacienteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@DataJpaTest
@Transactional
@Import(HistoriaClinicaService.class)
public class HistoriaClinicaServiceTest {

    @Autowired
    private HistoriaClinicaService historiaClinicaService;

    @Autowired
    private PacienteRepository pacienteRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Configuración inicial de la prueba.
     */
    @BeforeEach
    void setUp() {
        clearData();
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        entityManager.createQuery("delete from HistoriaClinicaEntity").executeUpdate();
        entityManager.createQuery("delete from PacienteEntity").executeUpdate();
    }

    @Test
    void testCrearHistoriaClinicaValida() throws IllegalOperationException {
        PacienteEntity paciente = new PacienteEntity();
        paciente.setNombre("Betsy Liliana");
        paciente.setCorreo("lagranBetsy.com");
        paciente.setTelefono("31100000000");
        paciente = pacienteRepository.save(paciente);

        HistoriaClinicaEntity h = new HistoriaClinicaEntity();
        h.setDiagnostico("Congestión");
        h.setTratamiento("Dolor en el pecho");

        HistoriaClinicaEntity creada = historiaClinicaService.crearHistoriaClinica(paciente.getId(), h);

        assertNotNull(creada.getId());
        assertEquals("Gripe", creada.getDiagnostico()); // Asumiendo que el servicio ajusta el diagnóstico
    }

    @Test
    void testCrearHistoriaPacienteConAcudiente() throws IllegalOperationException {
        PacienteEntity acudiente = new PacienteEntity();
        acudiente.setNombre("Juanfi");
        acudiente.setCorreo("juandi@gmail.com");
        acudiente.setTelefono("31111111111");
        acudiente = pacienteRepository.save(acudiente);

        HistoriaClinicaEntity hAcudiente = new HistoriaClinicaEntity();
        hAcudiente.setDiagnostico("Ansiedad");
        hAcudiente.setTratamiento("Setralina");
        historiaClinicaService.crearHistoriaClinica(acudiente.getId(), hAcudiente);

        PacienteEntity paciente = new PacienteEntity();
        paciente.setNombre("Daniela Peres");
        paciente.setTelefono("60122222222");
        paciente.setCorreo("daniperes@gmail.com");
        paciente.setAcudiente(acudiente);
        paciente = pacienteRepository.save(paciente);

        HistoriaClinicaEntity h = new HistoriaClinicaEntity();
        h.setDiagnostico("Lesión en el manguito rotador");
        h.setTratamiento("Terapia");

        HistoriaClinicaEntity creada = historiaClinicaService.crearHistoriaClinica(paciente.getId(), h);

        assertTrue(creada.getDiagnostico().startsWith("HistoriaCompartida-"));
    }
}
