package com.centrosalud.centrosalud.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centrosalud.centrosalud.model.Paciente;
import com.centrosalud.centrosalud.service.PacienteService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    
    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public List<Paciente> getAllPacientes() {
        return pacienteService.getAllPacientes();
    }
    

    @GetMapping("/{id}")
    public Optional<Paciente> getPacienteById(@PathVariable Long id) {
        return pacienteService.getPacienteById(id);
    }
    
    @PostMapping
    public Paciente createPaciente(@RequestBody Paciente paciente) {
        return pacienteService.createPaciente(paciente);
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
    


}
