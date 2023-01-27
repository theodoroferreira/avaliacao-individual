# Avaliação Individual - SpringBoot - Compass.uol   

### Microsserviços  

- [MS-Order](https://github.com/theodoroferreira/avaliacao-individual/tree/main/ms-order)
- [MS-History](https://github.com/theodoroferreira/avaliacao-individual/tree/main/ms-history)


### Documentação  

- [Swagger - MS-Order](https://github.com/theodoroferreira/avaliacao-individual/blob/main/ms-order/src/main/resources/openapi.yaml)
- [Swagger - MS-History](https://github.com/theodoroferreira/avaliacao-individual/blob/main/ms-history/src/main/resources/openapi.yaml)

### README Microsserviços

- [README - MS-Order](https://github.com/theodoroferreira/avaliacao-individual/blob/main/ms-order/README.md)
- [README - MS-History](https://github.com/theodoroferreira/avaliacao-individual/blob/main/ms-history/README.md)

## Funcionalidades  

- **/orders e /orders/{id}**: GET, POST PUT e DELETE  
- **/items/{id}**: PATCH   
- **/history**: GET  

## Informações do projeto:  

- Arquitetura Hexagonal
- ID é gerado automaticamente   
- Testes com cobertura de 70%  
- Tratamento de Erro/Exceções (exceptions / errors / handler / indicação do campo que falta)  
- Validação do CPF
- Conexão API ViaCEP  
- Banco de dados: SQL (MS-Order) e MongoDB (MS-History)  
- Mensageria: Kafka
