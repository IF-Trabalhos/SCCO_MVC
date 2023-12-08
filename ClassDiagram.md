```mermaid
classDiagram

    class Endereco {
    +logradouro: String
    +uf: String
    +cidade: String
    +numero: String
    +complemento: String
    +bairro: String
    +cep: String
    }

    class Pessoa {
    +nome: String
    +email: String
    +telefone: String
    +rg: String
    +dataNascimento: String
    }

    class Secretaria {
    +salario: Double
    +pis: String
    }

    class Convenio {
    +nome: String
    +registroAns: String
    +email: String
    +telefone: String
    +desconto: Double
    +validar()
    }

    class Paciente {
    +numProntuario: String
    +calcularGastos()
    +classificarPaciente()
    +validar()
    +gastosTotais: Double
    }

    class Consulta {
    +valorConsulta: Double
    +validar()
    +calculaValorConsulta()
    -desconto: Double
    }

    class Dentista {
    +cro: String
    +verificarDisponibilidade()
    +calcularGanhos(): Double
    }

    class DentistaComissionado {
    +percentualComissao: Double
    }

    class DentistaAssalariado {
    +salarioFixo: Double
    }

    class Especialidade {
    +nome: String
    +status: Boolean
    +validar()
    }

    class Expediente {
    +diaDaSemana: Date
    +horaInicial: Time
    +horaFinal: Time
    }

    class Procedimento {
    +nome: String
    +status: Boolean
    +valor: Double
    +validar()
    }

    class Agenda {
    +data: String
    +horaInicial: String
    +horaFinal: String
    }

    class Clinica {
    +quantidadeConsultas()
    +registrarConsulta(consulta: Consulta)
    +registrarDesconto(desconto: Double)
    +calcularLucro(): Double
    }

Pessoa *-- Endereco : 
Secretaria --|> Pessoa : 
Paciente --|> Pessoa : 
Dentista --|> Pessoa : 
DentistaComissionado --|> Dentista : 
DentistaAssalariado --|> Dentista : 
Paciente *-- Convenio : Possui
Consulta --> Paciente : Associado
Consulta --> Procedimento : Inclui
Dentista -- Expediente : Trabalha
Dentista --|> Especialidade : Possui
Procedimento --|> Especialidade : Associado
Consulta --> Agenda : Requer
Consulta --> Dentista : Requer
```
