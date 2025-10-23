package co.edu.uniandes.dse.parcialprueba.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.services.PacienteService;
import co.edu.uniandes.dse.parcialprueba.dto.PacienteDTO;
import co.edu.uniandes.dse.parcialprueba.dto.PacienteDetailDTO;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PacienteDetailDTO create(@RequestBody PacienteDTO premio) throws IllegalOperationException {
        PacienteEntity pacienteEntity = modelMapper.map(premio, PacienteEntity.class);
        pacienteService.createPaciente(pacienteEntity);
        return modelMapper.map(pacienteEntity, PacienteDetailDTO.class);
    }
}