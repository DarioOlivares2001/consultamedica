package com.centrosalud.centrosalud.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.centrosalud.centrosalud.model.Paciente;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PacienteServiceTest {
    
    @Autowired
    private PacienteRepository pacienteRepository;

    @Test
    public void guardarPacienteTest(){
        Paciente pac = new Paciente();
        pac.setNombres("Mark York");
        Paciente ret = pacienteRepository.save(pac);
        assertNotNull(ret);
        assertNotNull(ret.getIdpac());
        assertEquals("Mark York", ret.getNombres());
    }

    @Test
    public void eliminarPacienteTest(){
        Paciente pac = new Paciente();
        pac.setNombres("Mark York");
        Paciente pacienteGuardado = pacienteRepository.save(pac);
        assertTrue(pacienteRepository.existsById(pacienteGuardado.getIdpac()));
        pacienteRepository.deleteById(pacienteGuardado.getIdpac());
        assertFalse(pacienteRepository.existsById(pacienteGuardado.getIdpac()));
    }

    //NUEVAS PRUEBAS
    @Test
    public void buscarPacientePorIdExistenteTest() {
        Paciente pac = new Paciente();
        pac.setNombres("John Doe");
        Paciente pacienteGuardado = pacienteRepository.save(pac);

        Optional<Paciente> pacienteOptional = pacienteRepository.findById(pacienteGuardado.getIdpac());
        assertTrue(pacienteOptional.isPresent());
        assertEquals("John Doe", pacienteOptional.get().getNombres());
    }

    @Test
    public void buscarPacientePorIdNoExistenteTest() {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(999L);
        assertFalse(pacienteOptional.isPresent());
    }

}
