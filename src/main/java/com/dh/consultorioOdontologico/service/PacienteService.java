package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Endereco;
import com.dh.consultorioOdontologico.entity.Paciente;
import com.dh.consultorioOdontologico.entity.dto.EnderecoDTO;
import com.dh.consultorioOdontologico.entity.dto.PacienteDTO;
import com.dh.consultorioOdontologico.repository.PacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    EnderecoService enderecoService;

    static final Logger logger = Logger.getLogger(PacienteService.class);

    public List<PacienteDTO> buscar(){
        logger.info("Iniciando operação para buscar os pacientes.");
        List<Paciente> pacienteList = pacienteRepository.findAll();
        List<PacienteDTO> pacienteDTOList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for(Paciente paciente: pacienteList){
            EnderecoDTO enderecoDTO = mapper.convertValue(paciente.getEndereco(), EnderecoDTO.class);
            PacienteDTO pacienteDTO = mapper.convertValue(paciente, PacienteDTO.class);
            pacienteDTO.setEnderecoDTO(enderecoDTO);
            pacienteDTOList.add(pacienteDTO);
        }
        logger.info("Exibindo os pacientes.");
        return pacienteDTOList;
    }

    public ResponseEntity salvar(PacienteDTO pacienteDTO){
        try{
            logger.info("Iniciando operação para salvar o paciente.");
            ObjectMapper mapper = new ObjectMapper();
            Paciente paciente = mapper.convertValue(pacienteDTO, Paciente.class);
            //enderecoService.salvarEndereco(pacienteDTO.getEnderecoDTO());
            paciente.setDataRegistro(Timestamp.from(Instant.now()));
            paciente.setEndereco(mapper.convertValue(pacienteDTO.getEnderecoDTO(), Endereco.class));
            Paciente pacienteSalvo = pacienteRepository.save(paciente);
//            Endereco endereco = pacienteSalvo.getEndereco();
//            enderecoService.salvarEndereco(endereco);
            logger.info("Paciente " + pacienteSalvo.getNome() + " salvo com sucesso.");
            return new ResponseEntity("Paciente " + pacienteSalvo.getNome() + " criado com sucesso!", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Erro ao salvar o paciente.");
            e.printStackTrace();
            return new ResponseEntity("Erro ao cadastrar o paciente", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity deletar(String  rg){
        logger.info("Iniciada operação para deletar o paciente com o Rg " + rg);
       Optional<Paciente> paciente = Optional.ofNullable(pacienteRepository.findByRg(rg));
       if(paciente.isEmpty()) {
           logger.error("Erro ao excluir o paciente. Não foi localizado nenhum cadastro com o rg " + rg);
           return new ResponseEntity<>("Id do paciente não existe!", HttpStatus.BAD_REQUEST);
       }
       pacienteRepository.deleteById(paciente.get().getId());
        logger.info("Paciente deletado!");
        return new ResponseEntity("Paciente delatado com sucesso!", HttpStatus.OK);
    }

    public ResponseEntity buscarPorRg(String rg) {
        logger.info("iniciando operação para buscar o paciente com o rg " + rg);
        ObjectMapper mapper = new ObjectMapper();
        Optional<Paciente> paciente = Optional.ofNullable(pacienteRepository.findByRg(rg));
        if(paciente.isEmpty()) {
            logger.error("Paciente com o rg " + rg + " não localizado.");
            return new ResponseEntity("Paciente com o rg " + rg + " não encontrado", HttpStatus.BAD_REQUEST);
        }
        PacienteDTO pacienteDTO = mapper.convertValue(paciente.get(), PacienteDTO.class);
        logger.info("Paciente com o rg " + rg + " localizado com sucesso.");
        return new ResponseEntity(pacienteDTO, HttpStatus.OK);
    }

    public ResponseEntity alterarParcialmente(PacienteDTO pacienteDTO){
        ObjectMapper mapper = new ObjectMapper();
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        logger.info("Iniciando operação para alterar parcialmente o paciente.");
        Optional<Paciente> pacienteOp = Optional.ofNullable(pacienteRepository.findByRg(pacienteDTO.getRg()));
        Optional<Endereco> enderecoOp = Optional.ofNullable(mapper.convertValue(pacienteDTO.getEnderecoDTO(), Endereco.class));
        if(enderecoOp.isEmpty()){
            Endereco endereco = pacienteOp.get().getEndereco();
            enderecoDTO = mapper.convertValue(endereco, EnderecoDTO.class);
        } else {
            if(enderecoOp.get().getRua() != null)
                pacienteOp.get().getEndereco().setRua(enderecoOp.get().getRua());

            if(enderecoOp.get().getNumero() != 0)
                pacienteOp.get().getEndereco().setNumero(enderecoOp.get().getNumero());

            if(enderecoOp.get().getCidade() != null)
                pacienteOp.get().getEndereco().setCidade(enderecoOp.get().getCidade());

            if(enderecoOp.get().getSiglaEstado() != null)
                pacienteOp.get().getEndereco().setSiglaEstado(enderecoOp.get().getSiglaEstado());
            enderecoDTO = mapper.convertValue(pacienteOp.get().getEndereco(), EnderecoDTO.class);
        }
        if(pacienteOp.isEmpty())
            return new ResponseEntity("Não existe o paciente com o rg " + pacienteDTO.getRg(), HttpStatus.NOT_FOUND);
        Paciente paciente = pacienteOp.get();
        if(pacienteDTO.getNome() != null)
            paciente.setNome(pacienteDTO.getNome());
         else
            paciente.getNome();
        if(pacienteDTO.getSobrenome() != null)
            paciente.setSobrenome(pacienteDTO.getSobrenome());
        else
            paciente.getSobrenome();
        pacienteRepository.save(paciente);
        //enderecoDTO = mapper.convertValue(paciente.getEndereco(), EnderecoDTO.class);
        PacienteDTO pacienteAlt = mapper.convertValue(paciente, PacienteDTO.class);
        pacienteAlt.setEnderecoDTO(enderecoDTO);
        logger.info("Paciente alterado com sucesso!");
        return new ResponseEntity(pacienteAlt, HttpStatus.CREATED);
    }

    public ResponseEntity alterarTudo(PacienteDTO pacienteDTO){
        ObjectMapper mapper = new ObjectMapper();
        logger.info("Iniciando operação para alterar parcialmente o paciente.");
        Optional<Paciente> pacienteOp = Optional.ofNullable(pacienteRepository.findByRg(pacienteDTO.getRg()));
        if(pacienteOp.isEmpty())
            return new ResponseEntity("Não existe o paciente com o rg " + pacienteDTO.getRg(), HttpStatus.NOT_FOUND);
        Paciente paciente = pacienteOp.get();
        if(pacienteDTO.getNome() != null)
            paciente.setNome(pacienteDTO.getNome());
        if(pacienteDTO.getSobrenome() != null)
            paciente.setSobrenome(pacienteDTO.getSobrenome());
        if(pacienteDTO.getEnderecoDTO().getRua() != null)
            paciente.getEndereco().setRua(pacienteDTO.getEnderecoDTO().getRua());
        if(pacienteDTO.getEnderecoDTO().getNumero() != 0)
            paciente.getEndereco().setNumero(pacienteDTO.getEnderecoDTO().getNumero());
        if(pacienteDTO.getEnderecoDTO().getCidade() != null)
            paciente.getEndereco().setCidade(pacienteDTO.getEnderecoDTO().getCidade());
        if(pacienteDTO.getEnderecoDTO().getSiglaEstado() != null)
            paciente.getEndereco().setSiglaEstado(pacienteDTO.getEnderecoDTO().getSiglaEstado());
        pacienteRepository.save(paciente);
        EnderecoDTO enderecoDTO = mapper.convertValue(paciente.getEndereco(), EnderecoDTO.class);
        PacienteDTO pacienteAlt = mapper.convertValue(paciente, PacienteDTO.class);
        pacienteAlt.setEnderecoDTO(enderecoDTO);
        logger.info("Paciente alterado com sucesso!");
        return new ResponseEntity(pacienteAlt, HttpStatus.CREATED);
    }
}