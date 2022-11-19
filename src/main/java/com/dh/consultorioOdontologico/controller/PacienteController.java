package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.model.Consulta;
import com.dh.consultorioOdontologico.model.Paciente;
import com.dh.consultorioOdontologico.service.PacienteService;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.dh.consultorioOdontologico.dao.impl.PacienteDao;
import com.dh.consultorioOdontologico.service.ConsultaService;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;

import com.dh.consultorioOdontologico.model.Endereco;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.SQLException;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    PacienteService pacienteService = new PacienteService();

    @GetMapping()
    public List<Paciente> buscarTodos() throws SQLException {
        return pacienteService.buscarTodos();
    }
    @DeleteMapping()
    public void excluirPaciente(@RequestBody Paciente paciente) throws SQLException {
        pacienteService.excluir(paciente);

    }

    @PutMapping("/{id}")
    public Paciente alterarPaciente(@PathVariable("id")  int id, @RequestBody Paciente paciente) throws SQLException {  //Felipe comment: acho que nao precisa passar o id como PathVariable, pq podemos retirar o id do paciente direto
        System.out.println();
        return pacienteService.modificar(paciente);
    }

    @PostMapping()
    public Paciente post(@RequestBody Paciente paciente) throws SQLException {
        return pacienteService.cadastrar(paciente);
    }

    @PatchMapping()
    public Paciente alterarPacienteParcial(@RequestBody Paciente paciente) throws SQLException {
        return pacienteService.modificar(paciente);
    }
}
