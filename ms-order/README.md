# MS Order

- URL: http://localhost:8081/api
- [Swagger - OpenAPI](https://github.com/theodoroferreira/avaliacao-individual/ms-order/src/main/resources/openapi.yaml)
- MySQL Workbench

![Cobertura de Testes](/src/main/resources/static/images/tests-ms-order.png)

![Postman](/src/main/resources/static/images/postman-ms-order.png)

## Exemplo de cURL

### POST - Criar Pedido

`/orders`

```cURL
curl --location --request POST 'http://localhost:8081/api/orders' \
--header 'Content-Type: application/json' \
--data-raw '{
    "cpf":"06069043952",
    "items":
    [
            {
                "name":"Farinha",
                "creationDate":"10-01-2023",
                "expirationDate":"11-03-2023",
                "value":10.0,
                "description":"Farinha de Trigo"
            },

            {
                "name":"Feijão",
                "creationDate":"12-01-2023",
                "expirationDate":"28-02-2023",
                "value":12.0,
                "description":"Feijão Preto"
            },

            {
                "name":"Coca-cola",
                "creationDate":"12-01-2023",
                "expirationDate":"02-02-2023",
                "value":8.0,
                "description":"2L Zero"
            }

    ],
    "address":
            {
                "cep":"83020090",
                "numero":"243"
            }
}'
```

### GET - Listar Pedidos

`/orders`

```cURL
curl --location --request GET 'http://localhost:8081/api/orders?cpf=06069043952&sort=totalValue,desc'
```

### GET - Listar Pedido

`/orders/:id`

```cURL
curl --location --request GET 'http://localhost:8081/api/orders/1'
```

### PATCH - Atualizer Item de um Pedido

`/items/:id`

```cURL
curl --location --request PATCH 'http://localhost:8081/api/items/1' \
--header 'Content-Type: application/json' \
--data-raw '{
    "creationDate": "31-01-2023",
    "expirationDate": "25-02-2023",
    "value":20,
    "description":"Farinha de Trigo Light"
}'
```

### PUT - Atualizar Pedido

`/orders/:id`

```cURL
curl --location --request PUT 'http://localhost:8081/api/orders/1' \
--header 'Content-Type: application/json' \
--data-raw '{
    "cpf":"17264178676",
    "address":
        {
            "cep":"07262130",
            "numero":"500"
        }
}'
```

### DELETE - Deleta o Pedido

`/orders/:id`

```cURL
curl --location --request DELETE 'http://localhost:8081/api/orders/1'
```