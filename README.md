# Global Solution - Economia Azul
API do Projeto VerdeMar

## Integrantes do Grupo 5TECH Collective

BRUNO MATHEWS DE CICICO OLIVEIRA - RM 99097 - Java Advanced, Devops

ISABELLE CORSI - RM 97751 - Mastering Relational and Non-Relational Database e Mobile Application Development

JOSÉ LUIZ FERREIRA DUARTE - RM 99488 - Figma e Pitch

MARINA DE SOUZA CUCCO - RM 551569 - Complice, Quality Assurance and Tests, Disruptive Architectures e Pitch

THALITA FACHINETE DE ALENCAR - RM 99292 - Advanced Business Development With .NET e Disruptive Architectures

## Documentação Principal

Esse arquivo README contem documentação específica para duas entregas de disciplinas diferentes, que serão separados em 2 tópicos para facilitar o entendimento para cada professor fazer sua avaliação adequadamente: JAVA ADVANCED e DEVOPS

### Documentação da API

1. A aplicação roda no http://localhost:8080/home [JAVA][DEVOPS]

2. A documentação feita no Swagger roda no http://localhost:8080/docs [JAVA][DEVOPS]

3. O console de adm está no repositório do GitHub: [JAVA]
https://github.com/brunociccio/ADM-Java-Spring.git
spring.boot.admin.client.url=http://localhost:9090 [não é um requisito necessário, porém, caso queiram acessar, as credenciais estão no arquivo application.properties

4. O deploy foi feito em nuvem no Railway, abaixo os links para acesso ao projeto: [JAVA]
Home - https://verdemar-production.up.railway.app/home
Swagger - https://verdemar-production.up.railway.app/docs
Seguindo a documentação feita no Swagger, os links alteram após a barra conforme queira testar o acesso, exemplos:
https://verdemar-production.up.railway.app/pontos-coleta
https://verdemar-production.up.railway.app/cadastrar
https://verdemar-production.up.railway.app/endereco
https://verdemar-production.up.railway.app/usuario

5. Todos os endpoints foram testados no Insomnia, o arquivo JSON esta na pasta ROOT do projeto VerdeMar [JAVA][DEVOPS]

6. Foi criado na pasta ROOT os arquivos de 'Dockerfile' e 'docker-compose.yml' com as configurações para criação dos conteiners e a execução do comando 'mvn clean package' para criação dos arquivos .JAR que fazem parte da configuração dos arquivos relacionados a Docker [DEVOPS]

7. Segue o link do video no YouTube da entrega de [DEVOPS]
https://www.youtube.com/watch?v=P1i0AB9FTsE

8. Segue o link do video no YouTube da entrega de [JAVA]
https://www.youtube.com/watch?v=X6Q2RTaijO4

9. Segue o link do video no YouTube do PITCH do Projeto VerdeMar [JAVA][DEVOPS]
https://www.youtube.com/watch?v=HraqCvGrk_8

10. A página do FRONTEND feito em Mobile que tem a integração com o projeto feito no BACKEND é a pagina de cadastro dos pontos de coleta: https://verdemar-production.up.railway.app/pontos-coleta 
Aqui você verá no video Pontos de Coleta sendo cadastrados na aplicação mobile e sendo salvos no endpoint adequdamente [JAVA]

11. Diagrama de Classe e Arquitetura do Projeto estão na pasta ROOT VERDEMAR, assim como um PDF falando um pouco do que se trata o nosso Projeto VerdeMar [JAVA][DEVOPS]

## Relacionado a JAVA

 1. Arquivo .txt com o RM, Nome do aluno e a turma de cada integrante. [ok]
 2. Código fonte do software (submetido via GitHub) [ok]
 3. Links dos Deploys em nuvem, com instruções para acessos e testes (usuário, senha, etc) [ok]
 4. Link do Vídeo demonstrando a software funcionando (não é o vídeo do Pitch) com Câmera e Microfone abertos e com duração [ok]
máxima de 10 minutos (YouTube ou equivalente).[ok]
 5. Link do Vídeo Pich com duração máxima de 3 minutos.[ok]

 ## Relacionado a DEVOPS

1. Realize uma dissertação sobre seu projeto, seus objetivos e solução proposta; [ok]
2. Desenhar a Arquitetura do projeto de DevOps (Draw.io, Visual Paradigm ou Excalidraw); [ok]
3. Entregar Arquivo PDF com capa incluindo o nome do Grupo (Solução) e integrantes e RM de cada aluno; [ok]
4. Link do GitHub com os Códigos-fontes, README.md, Dockerfile e Arquivo YAML; [ok]
5. Persistência de dados acompanhada de um Volume; [ok]
6. Link do vídeo no YouTube comprovando o funcionamento do App, partindo desde o clone do repositório em sua máquina [ok]
local até a persistência de dados. Mostre todos os detalhes de cada passo. [ok]
