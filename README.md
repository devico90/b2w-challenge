# Requisitos:

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

# Configuração

- A linguagem utilizada neste projeto é Java, integrado com o banco MongoDB, via Spring Boot.
- Versão do JRE: 1.8.0_144
- Versão do JDK: 1.8
- É necessário ter o MongoDB (https://www.mongodb.com/) rodando localmente, para correta execução da aplicação.
- Versão utilizada do MongoDB: mongodb-win32-x86_64-2008plus-ssl-3.6.4-signed
- Também é necessário ter uma conexão com internet para o funcionamento correto da aplicação, pois há integração com a API pública do Star Wars: https://swapi.co/
- Recomendo a utilização do Postman (https://www.getpostman.com/) para executar os requests.

-----------------------------------------------------------------------------------------------------------

# Implementação

- A aplicação, em sua primeira inicialização, estará completamente zerada.
- O usuário possui as opções:
	- Listagem
		- Listará todos os planetas cadastrados no banco
	- Busca
		- Verificará se o planeta buscado consta na base de dados, caso contrário, busca na API pública (SWAPI) e, caso encontre-o, insere automaticamente no banco.
	- Adição
		- Adicionar um novo planeta (desde que conste na API pública) manualmente
	- Remoção
		- Remover um planeta

-----------------------------------------------------------------------------------------------------------

# Tutorial de request

1) Visualizar lista de planetas já cadastrados no banco:
- Tipo de Request: GET
- Caminho: /planets 
- Retorno: String JSON (Recomendo, no Postman, alterar a visualização do body response para JSON, pois facilitará a visualizar)

2) Inserção de planeta
- Tipo de Request: PUT
- Caminho: /planets
- Exemplos de Body Request:
  - { "name": "Mustafar" }
  - { "id": "5b01145c4286ee22c8ce2f0d","name": "Mustafar" }
  - { "name": "Terra", "climate": "Quente", "terrain": "Misto" }

3) Busca por planeta (nome ou id)
- Tipo de Request: GET
- Caminho: /planets/find/
- Parâmetros:
	- name (e.g. /planets/find/?name=Tatooine
	- id (e.g. /planets/find/?id=5b01145c4286ee22c8ce2f0d
- Caso resolva passar os 2 parâmetros (e.g. /planets/find/?name=Tatooine&id=5b01145c4286ee22c8ce2f0d), o parâmetro NAME será priorizado.
- Retorno: String JSON

4) Remover planeta cadastrado
- Tipo de Request: DELETE
- Caminho: /planets
- Parâmetros:
	- name (e.g. /planets/?name=Toydaria)
	- id (e.g. /planets/?name=5b01145c4286ee22c8ce2f0d)
- Caso resolva passar os 2 parâmetros, NAME será priorizado.
- Retorno: String JSON
