# Java Sockets 101
- Projeto dedicado ao meu aprendizado sobre TCP Sockets em Java
- Projeto é mais uma prova de conceito, por isso pode ser um pouco desorganizado em um primeiro momento

# Como executar
- No pacote raiz a classe Main tem o papel de servidor, que inicializa um socket aceitando conexões na portal 1333
- Após subir o server, pode-se conectar utilizando telnet com `telnet localhost 1333`, ou utilizar a classe `Client` que já conecta no server na porta, e abre uma espécie de terminal interativo estilo telnet, informando com prefixo `C: ` a parte do cliente, e `S: ` as respostas do servidor

# Como utilizar
O server entende apenas dois comandos, são eles:

## GET {endpoint}
- Esse comando "chama" o endpoint dito, e retorna o que o método retornar.

### Como criar um novo endpoint? 
- Deve se criar uma classe dentro do pacote `resources`, com a anotação `@Resource`, e após isto criar um (ou mais) método(s) estático anotado com `@GET("endpoint")` que retorne `String`, ou que possa ser "casteado" para a mesma. Com isto feito, basta que seja chamado o mesmo como no exemplo:
    ```ts
    C: GET test
    S: oi
    ```

## END
Este comando, como o nome diz, encerra a conexão do lado do servidor, e, se utilizando a classe `Client`, também encerra do lado do cliente.