# parquimetroapi
Projeto fase 3 Pos Fiap - Parquimetro

Subindo hambiente deve

# $ cd ./database
# $ docker compose up

Cadastra entrada 
POST http://localhost:8080/ticket/entrada
{
"codigotag": "123451",
"valor": 10.00
}

Cadastra saída
POST http://localhost:8080/ticket/saida
{
"codigotag": "1234"
}

Pega Dados TAG pelo nro da Tag
GET http://localhost:8080/ticket/12346

Verifica se Saída está liberada
GET http://localhost:8080/ticket/saidaliberada/123451

Lista Todas as Tag em Aberto
GET http://localhost:8080/ticket/listaemaberto

Salva Tipo de Veiculo Valor e Valor da Fração
POST http://localhost:8080/valores/salvar
{
"veiculo": "carro",
"valor": 5.00,
"fracao": 10.00
}

Lista Valores e tipo de veiculos cadastrados
http://localhost:8080/valores/lista