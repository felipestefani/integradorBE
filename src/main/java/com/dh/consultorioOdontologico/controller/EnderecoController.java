package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.entity.dto.EnderecoDTO;
import com.dh.consultorioOdontologico.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    @Autowired
    EnderecoService enderecoService;

    @PostMapping()
    public ResponseEntity salvar(@RequestBody EnderecoDTO enderecoDTO) {
        return enderecoService.salvarEndereco(enderecoDTO);
    }

    @GetMapping("/buscar")
    public List<EnderecoDTO> buscarEnderecos(){
        return enderecoService.buscarTodosEnderecos();
    }

    @GetMapping()
    public ResponseEntity buscarEndereco(@RequestBody EnderecoDTO enderecoDTO){
        return enderecoService.buscarEndereco(enderecoDTO);
    }

    @PatchMapping()
    public ResponseEntity alterarParcial(@RequestBody EnderecoDTO enderecoDTO){
        return enderecoService.alterarParcial(enderecoDTO);
    }

    @DeleteMapping()
    public ResponseEntity deletarEndereco(@RequestParam("id") Long id){
        return enderecoService.deletarEndereco(id);
    }

}
