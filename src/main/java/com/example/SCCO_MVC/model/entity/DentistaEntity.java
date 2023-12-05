package com.example.SCCO_MVC.model.entity;

import javax.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DentistaEntity extends PessoaEntity {

    private String cro;

    @ManyToOne
    private EspecialidadeEntity especialidadeEntity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "dentista_id")
    private List<ExpedienteEntity> expedientes;
}
