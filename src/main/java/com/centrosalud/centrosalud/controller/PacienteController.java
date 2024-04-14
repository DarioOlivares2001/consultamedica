package com.centrosalud.centrosalud.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centrosalud.centrosalud.model.Paciente;
import com.centrosalud.centrosalud.service.PacienteService;

import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private static final Logger log = LoggerFactory.getLogger(PacienteController.class);

    
    
    
    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public List<Paciente> getAllPacientes() {
        log.info("GET /students");
        log.info("Retornando todos los pacientes");
        return pacienteService.getAllPacientes();
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPacienteById(@PathVariable Long id) {
        Optional<Paciente> paciente = pacienteService.getPacienteById(id);
        if(paciente.isEmpty())
        {
            log.error("No se encontró el paciente con ID {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se encontro el paciente con ID " + id));
        }

        return ResponseEntity.ok(paciente);
        
    }
    
    @PostMapping
    public ResponseEntity<Object> createPaciente(@RequestBody Paciente paciente) {
        Paciente pac = pacienteService.createPaciente(paciente);
        if(pac == null)
        {
            log.error("Error al crear el estudiante {}", pac);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error al crear el estudiante"));
        }
        return ResponseEntity.ok(pac);
    }

    @PutMapping("/{id}")
    public Paciente updatePaciente(@PathVariable String id, @RequestBody Paciente paciente) {
        return pacienteService.updatePaciente(null, paciente);
    }

    @DeleteMapping("/{id}")
    public void deletePaciente(@PathVariable Long id)
    {
        pacienteService.deletePaciente(id);

    }
    

    static class ErrorResponse {
        private final String message;
    
        public ErrorResponse(String message) {
            this.message = message;
        }
    
        public String getMessage() {
            return message;
        }
    }

}
