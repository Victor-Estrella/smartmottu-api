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


## Publicar a API (Deploy)

Esta aplicação é Spring Boot 3 (Java 17) e usa Oracle como banco externo. As opções abaixo funcionam bem sem precisar administrar servidores:

- Render (Docker) – simples, bom free tier; define env vars e roda um container.
- Google Cloud Run – serverless por request, escala bem; cobra por uso.
- Azure App Service – PaaS estável; roda container ou jar diretamente.

Variáveis de ambiente necessárias (perfil prod):

- DB_URL: jdbc:oracle:thin:@host:1521:orcl
- DB_USERNAME: seu usuário
- DB_PASSWORD: sua senha
- PORT: fornecida pela plataforma (Cloud Run/Render definem automaticamente)

O perfil ativo padrão em container é "prod" (SPRING_PROFILES_ACTIVE=prod). A porta é lida de PORT, com fallback 8080.

### 1) Deploy com Docker (Render/Azure App Service/qualquer orquestrador)

1. Build da imagem localmente (opcional):
    - Requer Docker Desktop. Na raiz do projeto: `docker build -t smartmottu:latest .`
2. Rodar localmente (opcional):
    - `docker run --rm -p 8080:8080 -e SPRING_PROFILES_ACTIVE=prod -e DB_URL=... -e DB_USERNAME=... -e DB_PASSWORD=... smartmottu:latest`
3. Render
    - Crie um novo serviço Web > Build & Deploy from Git > Docker.
    - Configure Environment: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`.
    - Render usa a porta via `PORT` automaticamente.
    - Health check: `/actuator/health` (se adicionar actuator futuramente) ou `/swagger-ui/index.html` para validar UI.
4. Azure App Service (Linux, Docker)
    - Crie um Web App for Containers.
    - Publique a imagem no ACR ou Docker Hub.
    - Configure App Settings: `SPRING_PROFILES_ACTIVE=prod`, `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`.

### 2) Google Cloud Run (recomendado p/ escala)

1. Build e push da imagem para Artifact Registry ou Docker Hub.
2. Crie um serviço no Cloud Run apontando para a imagem.
3. Set de variáveis: `SPRING_PROFILES_ACTIVE=prod`, `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`.
4. Defina a porta 8080 (Cloud Run usa o env PORT automaticamente).

### 3) Deploy sem Docker (jar)

É possível usar o jar gerado pelo Maven em VMs/App Services que suportam Java 17:

- Gere o jar: `./mvnw -DskipTests clean package`
- Rode com: `java -Dspring.profiles.active=prod -Dserver.port=$PORT -jar target/*.jar`
- Configure as variáveis `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` no host.

Observações sobre Oracle

- A aplicação usa o driver `ojdbc11` (runtime). Garanta que o host da aplicação tenha acesso de rede ao servidor Oracle.
- Ajuste pool (Hikari) por envs `DB_MAX_POOL_SIZE`, etc., se necessário.

