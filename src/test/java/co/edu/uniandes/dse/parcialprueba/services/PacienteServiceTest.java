package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@DataJpaTest
@Transactional
@Import(PacienteService.class)
public class PacienteServiceTest {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
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
        entityManager.createQuery("delete from PacienteEntity").executeUpdate();
    }

    @Test
    void testCrearPacienteValido() throws Exception {
        PacienteEntity p = new PacienteEntity();
        p.setNombre("JuanDi Real");
        p.setCorreo("juandir@gmail.com");
        p.setTelefono("31155555555");

        PacienteEntity creado = pacienteService.createPaciente(p);

        assertNotNull(creado.getId());
    }

    @Test
    void testCrearPacienteTelefonoInvalido() {
        PacienteEntity p = new PacienteEntity();
        p.setNombre("JuanDi");
        p.setCorreo("juandi@gmail.com");
        p.setTelefono("316555555555");

        assertThrows(IllegalOperationException.class, () -> pacienteService.createPaciente(p));
    }
}
