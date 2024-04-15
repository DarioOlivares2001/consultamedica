package com.centrosalud.centrosalud.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centrosalud.centrosalud.model.ConsultaMedica;
import com.centrosalud.centrosalud.model.Especialidad;
import com.centrosalud.centrosalud.model.Paciente;
import com.centrosalud.centrosalud.model.Profesional;
import com.centrosalud.centrosalud.service.ConsultaMedicaService;
import com.centrosalud.centrosalud.service.EspecialidadService;
import com.centrosalud.centrosalud.service.PacienteService;
import com.centrosalud.centrosalud.service.ProfesionalService;

import io.micrometer.core.ipc.http.HttpSender.Response;

import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.apache.logging.log4j.spi.ObjectThreadContextMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private static final Logger log = LoggerFactory.getLogger(PacienteController.class);
    
    
    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private ProfesionalService profesionalService;

    @Autowired
    private EspecialidadService especialidadService;

    @Autowired
    private ConsultaMedicaService consultaMedicaService;

    @GetMapping
    public List<Paciente> getAllPacientes() {
        log.info("GET /pacientes");
        log.info("Retornando todos los pacientes");
        return pacienteService.getAllPacientes();
    }

    @GetMapping("/especialidades")
    public List<Especialidad> getAllEspecialidades() {
        log.info("GET /especialidades");
        log.info("Retornando todas las especialidades");
        return especialidadService.getAllEspecialidad();
    }
    
    @PostMapping("/especialidades")
    public ResponseEntity<Object> createEspecialidad(@RequestBody Especialidad especialidad) {
        List<Especialidad> listaespecial = especialidadService.getAllEspecialidad();
        for(Especialidad e : listaespecial)
        {
            if(e.getNombre().equals(especialidad.getNombre())){
                log.error("La especialidad ya existe {}", especialidad.getNombre());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se puede crear la especialidad " + especialidad.getNombre()));   
            }
        }
        return ResponseEntity.ok(especialidadService.createEspecialidad(especialidad));
    }


    @GetMapping("/profesionales")
    public List<Profesional> getAllProfesionales() {
        log.info("GET /profesionales");
        log.info("Retornando todos los profeisonales");
        return profesionalService.getAllProfesionales();
    }
    
    
    @PostMapping("/profesionales")
    public ResponseEntity<Object> createProfesional(@RequestBody Profesional profesional) 
    {
        List<Profesional> listaprofesional = profesionalService.getAllProfesionales();
        for(Profesional p : listaprofesional)
        {
            if(p.getRut().equals(profesional.getRut()))
            {
                log.error("El profesional ya existe ID {}", profesional);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("El profesional ya existe " + profesional));

            }

        }

        return ResponseEntity.ok(profesionalService.createProfesional(profesional));
    }

    @GetMapping("/consultas")
    public List<ConsultaMedica> getAllConsultaMedicas() {
        log.info("GET /consultas medicas");
        log.info("Retornando todas las consultas medicas");
        return consultaMedicaService.getAllConsultaMedica();
    }

    @PostMapping("/consultas")
    public ResponseEntity<Object> createConsultas(@RequestBody ConsultaMedica consultaMedica) 
    {
        List<Profesional> listaprofesional = profesionalService.getAllProfesionales();
        List<Paciente> listapaciente = pacienteService.getAllPacientes();
        boolean existeprof = false;
        boolean existepac = false;
        for(Profesional p : listaprofesional)
        {
            if(p.getRut().equals(consultaMedica.getProfesional().getRut()))
            {
                existeprof = true;
            }
        }

        if(!existeprof)
        {
            log.error("El profesional no existe {}", consultaMedica.getProfesional());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("El profesional no existe ID " + consultaMedica.getProfesional()));

        }

        for(Paciente pac : listapaciente)
        {
            if(pac.getRut().equals(consultaMedica.getPaciente().getRut()))
            {
                existepac = true;
            }

        }

        if(!existepac)
        {
            log.error("El paciente no existe {}", consultaMedica.getPaciente());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("El paciente no existe ID " + consultaMedica.getPaciente()));
        }

        return ResponseEntity.ok(consultaMedicaService.createConsultaMedica(consultaMedica));
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
    public ResponseEntity<Object> deletePaciente(@PathVariable Long id)
    {
        Optional<Paciente> pac = pacienteService.getPacienteById(id);
        if(pac.isEmpty())
        {
            log.error("El paciente no existe", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("El usuario que quiere eliminar no existe " + id));
        }
        log.info("Se ha eliminado el paciente");
        pacienteService.deletePaciente(id);
        return ResponseEntity.ok(pac);

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
