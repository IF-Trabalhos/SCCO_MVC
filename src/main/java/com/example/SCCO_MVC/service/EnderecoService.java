package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.domain.Dentista;
import com.example.SCCO_MVC.domain.Endereco;
import com.example.SCCO_MVC.dto.DentistaDTO;
import com.example.SCCO_MVC.dto.EnderecoDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.DentistaEntity;
import com.example.SCCO_MVC.model.entity.EnderecoEntity;
import com.example.SCCO_MVC.model.repository.EnderecoRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {
    private final EnderecoRepository repository;

    public EnderecoService(EnderecoRepository repository){
        this.repository = repository;
    }
    @Transactional
    public EnderecoDTO save(EnderecoDTO dto){
        Endereco endereco = dto.fromDTOToDomain();
        endereco.validate();
        EnderecoEntity entity = this.repository.save(dto.fromDTOToEntity());
        return EnderecoDTO.fromEntityToDTO(entity);
    }
}
