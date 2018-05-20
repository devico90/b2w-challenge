# Desafio B2W

Requisitos:

- A API deve ser REST
- Para cada planeta, os seguintes dados devem ser obtidos do banco de dados da aplicação, sendo inserido manualmente:
Nome, Clima, Terreno
- Para cada planeta também devemos ter a quantidade de aparições em filmes, que podem ser obtidas pela API pública do Star Wars:  https://swapi.co/

Funcionalidades desejadas: 

- Adicionar um planeta (com nome, clima e terreno)
- Listar planetas
- Buscar por nome
- Buscar por ID
- Remover planeta

-----------------------------------------------------------------------------------------------------------

Implementação e configuração:

- A linguagem utilizada neste projeto é Java, integrado com o banco MongoDB, via Spring Boot.
- Versão do JRE: 1.8.0_144
- Versão do JDK: 1.8
- É necessário ter o MongoDB (https://www.mongodb.com/) rodando localmente, para correta execução da aplicação.
- Versão utilizada do MongoDB: mongodb-win32-x86_64-2008plus-ssl-3.6.4-signed
- Também é necessário ter uma conexão com internet para o funcionamento correto da aplicação, pois há integração com a API pública do Star Wars: https://swapi.co/
- Recomendo a utilização do Postman (https://www.getpostman.com/) para executar os requests.

-----------------------------------------------------------------------------------------------------------

