# Challenge

## Integrantes do Grupo

| Nome            |   RM   | Sala   |
|:----------------|:------:|:-------|
| Julia Monteiro  | 557023 | 2TDSPV |
| Victor Henrique | 556206 | 2TDSPH |
| Sofia Petruk    | 556585 | 2TDSPV |


### Solução do projeto

    O projeto propõe o desenvolvimento de uma plataforma inteligente para gestão de pátios da Mottu, integrando visão computacional, sensores IoT e QR Code.
    Com câmeras 360° instaladas no local, o sistema identificará visualmente motos em tempo real, mesmo sem placa ou com chassi oculto. Cada moto terá um QR Code 
    vinculado ao seu cadastro completo, incluindo imagem, modelo e status. A plataforma permitirá a localização rápida dos veículos, rastreabilidade de movimentações 
    e histórico de manutenções, eliminando perdas internas e aumentando a eficiência operacional. A solução visa resolver um dos principais gargalos logísticos da empresa 
    com precisão e escalabilidade

### Descrição do projeto

    Até o final do ano, o projeto contará com todas as funcionalidades propostas na solução. Por enquanto, foram criadas as classes básicas necessárias para dar início ao desenvolvimento.
    Criamos a classe do usuário, que servirá para login e cadastro, mas também permitirá acompanhar o status da própria moto ou realizar a compra de uma nova.
    Também desenvolvemos uma classe exclusiva para a moto, que será utilizada para identificá-la no pátio e registrar o que precisa ser feito nela.

## Como Rodar o Projeto

### Pré-requisitos
    IntelliJ IDEA
    JDK-23
    Oracle 11
### Clonar repositorio

    git clone https://github.com/sofiapetruk/smartmottu.git

### Abra o projeto no IntelliJ IDEA:

    Inicie o IntelliJ

    Vá em File > Open... e selecione a pasta do projeto clonado

    Aguarde a indexação e o carregamento do Maven

### Execute o projeto:

    Clique na seta verde ▶ no canto superior direito

    Ou use o atalho Shift + F10

### Endponits do projeto
| Método | Endpoint                  | Exemplo                       | Descrição                                                       |
|--------|---------------------------|-------------------------------|-----------------------------------------------------------------|
| POST   | [/usuarios]               | (http://localhost:8080/usuarios)|     Cria um novo usuario                                        |
 | POST  | [/usuarios/login]         | (http://localhost:8080/usuarios/login)| Cria um login e verifica se é o mesmo email e senha do cadastro |
| GET    | [/usuarios]               | (http://localhost:8080/usuarios)   | Retorna todos os usuarios que tem no db                         |
| GET    | [/usuarios/{idUsuario}]   | (http://localhost:8080/usuarios/1) | Retorna somente um usuario                                      |
| GET    | [/usuarios/paginacao]     | (http://localhost:8080/usuarios/paginacao) | Retorno os dados com paginação                                  |
| PUT    | [/usuarios/{idUsuario}] | (http://localhost:8080/usuarios/1) | Atualiza o usuario com id especifico                            |
| DELETE | [/usuarios/{idUsuario}] | (http://localhost:8080/usuarios/1) | Delete o usuario com o id especifico                            |

| Método | Endpoint                | Exemplo                             | Descrição                            |
|--------|-------------------------|-------------------------------------|--------------------------------------|
| POST   | [/motos]             | (http://localhost:8080/motos)      | Cria uma nova mota                   |
| GET    | [/moto]             | (http://localhost:8080/motos)      | Retorna todos as motos que tem no db |
| GET    | [/motos/{idMoto}] | (http://localhost:8080/motos/2)    | Retorna somente uma moto             |
| GET    | [/motos/paginacao]      | (http://localhost:8080/motos/paginacao) | Retorno os dados com paginação       |
| PUT    | [/motos/{idMoto}]        | (http://localhost:8080/motos/2)     | Atualiza a moto com id especifico    |
| DELETE | [/motos/{idMoto}] | (http://localhost:8080/motos/2)    | Delete a moto com o id especifico    |
|SWAGGER| ---------------|(http://localhost:8080/swagger-ui/index.html)| Verificar quais atributos temos que utilizar na nossa api|
