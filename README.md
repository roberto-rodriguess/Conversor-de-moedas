# Conversor de Moedas 💱

Este é um projeto simples em Java que realiza a conversão de moedas utilizando a API [ExchangeRate API](https://www.exchangerate-api.com/). O programa permite ao usuário escolher diferentes pares de moedas e obter a conversão em tempo real.

## Funcionalidades

- Conversão entre:
  - Real (BRL), Dólar (USD), Euro (EUR) e Peso Argentino (ARS)
- Interface de terminal simples e interativa
- Consumo de API externa usando `HttpClient`
- Leitura de dados JSON com `Gson`

## Como usar

1. Configure sua chave de API da ExchangeRate como variável de ambiente:
   - Nome: `API_KEY`

2. Compile e execute o programa.

3. Escolha a operação desejada no menu interativo.

## Requisitos

- Java 17 ou superior
- Biblioteca [Gson](https://github.com/google/gson)

## Exemplo de uso

```bash
Digite o valor que deseja converter: 100
O valor 100,00 [USD] corresponde ao valor de 50600,000000 [ARS]
