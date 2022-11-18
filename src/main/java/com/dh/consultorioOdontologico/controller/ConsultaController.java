package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.model.Consulta;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.dh.consultorioOdontologico.model.Endereco;
import com.dh.consultorioOdontologico.service.ConsultaService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {
    ConsultaService consultaService = new ConsultaService();

    @GetMapping()
    public List<Consulta> buscarTodos() throws SQLException {
        return consultaService.buscarTodos();
    }

    @DeleteMapping()
    public void excluirConsulta(@RequestBody Consulta consulta) throws SQLException {
        consultaService.excluir(consulta);
    }

    @PutMapping()
    public Consulta alterarConsulta(@RequestBody Consulta consulta) throws SQLException {
        return consultaService.modificar(consulta);
    }

    @PostMapping()
    public Consulta post(@RequestBody Consulta consulta) throws SQLException {
        return consultaService.cadastrar(consulta);
    }
}
