<h1 align="center">🪴 Alura Forum API 🪴</h1>

<img src="https://64.media.tumblr.com/47b36822fce08a19e48c9f918403f4db/tumblr_omvjdm4AcD1w6mwsfo10_500.png" height="120" width="100%" object-fit="cover" />

This is a simple API built with Spring Boot to simulate how Alura Forum may works in backstage. 

## Tecnhologies
* Java
* Spring Boot
* Spring Data JPA
* Javax Validation
* REST Architecture Concepts 
* Data Transfer Object - DTO


# Notes

O Spring Boot só carrega classes que foram criadas no mesmo pacote da classe `main` ou em seus subpacotes.

`@ResponseBody` - o Spring considera que o retorno do método é **o nome da página** que ele **deve carregar**, mas utilizando essa anotação, indicamos que o retorno do método deve ser **serializado e devolvido no corpo da resposta**.

- Para não precisar utilizar essa anotação nos seus métodos da controller, existe uma anotação do Spring denominada `@RestController` - é uma forma de dizer para o spring que todo método terá um response body.

Utilizando o módulo dev tools do Spring, ele reinicializa o seu projeto automaticamente ao salvar alguma alteração.

```java
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-devtools</artifactId>
	<scope>runtime</scope>
	<optional>true</optional>
</dependency>
```

### Usando DTO

Não é uma boa prática retornar classes modelos nos métodos da sua controller, pois elas podem representar uma entidade. Isto é, essa classe pode ter vários atributos no qual, nem sempre, você gostaria de retorná-los. Por isso, utilizamos uma classe denominada ***DTO - Data Transfer Object***.

- o Jackson serializa todos os atributos da classe.

### REST - Representational State Transfer

Modelo de arquitetura para *sistemas distribuídos*.

- toda arquitetura que possui um sistema conversando com outro é um sistema distribuído.

A ideia é que você tenha dois sistemas conversando e com transferência de informações via rede, pensando em como poderia projetar esse sistema para ter uma boa performance e escalabilidade sem enfrentar alguns problemas. 

- foi observado que era possível fazer integração de sistemas pela web.
    - antes era utilizado o soap.

**Conceitos para aplicar o rest**

*Recursos:* são o que é gerenciado pela aplicação.

- um sistema de matrícula gerencia os "alunos", logo, alunos seria um recurso para a arquitetura REST.

*Identificador de Recursos:* é uma maneira de diferenciar um recurso do outro, conhecido como URI.

- cada recurso terá uma uri → Aluno (/alunos)

*Manipulação de Recursos:* como a URI não é o suficiente para apresentar todas as funcionalidades de um recurso, utilizamos os verbos http para informar qual operação deseja realizar para manipular aquele recurso.

*Representação de recursos:* é a forma como os sistemas se comunicam, recebendo e retornando uma representação do recurso que acessou. 

- utilizamos os **Media Types**.
- o nome REST vem dai, pois estamos transferindo o estado atual do meu recurso.

*Comunicação Stateless:* não guarda sessions para armazenar dados dos usuários logados.

## Usando Spring Data JPA

```jsx
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

```jsx
# data source
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:alura-forum
spring.datasource.username=sa
spring.datasource.password=

# jpa
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update

# Nova propriedade a partir da versao 2.5 do Spring Boot:
spring.jpa.defer-datasource-initialization=true

# h2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console 
```

### Transformando as classes em Entidades JPA

Adicionamos a anotação `@Entity` e no atributo de id da classe colocamos também `@Id` e `@GeneratedValue(strategy = GenerationType.IDENTITY)`.

- com o generated value, informamos que a nossa chave primária será gerada automaticamente pelo banco de dados.
    - colocamos qual a estratégia para criar essas chaves:
        - identity → auto-increment.
        - sequence → se o seu banco utiliza sequence.

Quando utilizamos a anotação `@OneToMany`, adicionamos o mappedBy para que o JPA não pense que é um **novo mapeamento**, pois na outra classe já estará mapeado esse relacionamento. 

Se estiver trabalhando com enums para não armazenar a constante no banco de dados, utilizamos a anotação `@Enumerated` com um EnumType para informar o tipo que o JPA irá pegar do enum para armazenar no banco.

### Utilizando Repository

Se não estivéssemos utilizando JPA para trabalhar com banco de dados, o padrão seria criar uma classe   para lidar com todas as questões de persistência no banco de dados.

Com Spring Data, nós utilizamos o padrão *Repository*. 

- criamos um interface que herdar uma interface do Spring Data com alguns métodos prontos.
    
    ```jsx
    public interface defaultRepository extends 
    JpaRepository<EntityClassName, EntityPrimaryKeyDataType> { }
    ```
    

O JPA exige que as classes entities possuam um **construtor padrão**.

Para criar a sua própria "query" no *Repository*, basta criar o nome do seu método correspondendo o nome dos atributos que serão usados:

```jsx
public interface TopicoRepository extends JpaRepository<Topico, Long> {

  List<Topico> findByCursoNome(String nomeCurso);

}
```

- caso o nome do seu método gere ambiguidade com algum atributo que também é uma entidade, basta colocar `Curso**_**Nome` para dizer para o JPA que você deseja selecionar a entidade e não o atributo da classe com esse mesmo nome.

Para seguir a sua própria nomenclatura, talvez o JPA não irá conseguir montar a query automaticamente e você precisará fazer isso na mão. Para isso, adicione a anotação `@Query`.

```jsx
@Query("SELECT t FROM TOPICO WHERE t.CURSO.NOME = :nomeCurso")
List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);
```

## Trabalhando com POST

Os dados que são recebidos nos métodos POST, vem do corpo do requisição. E é necessário falar isso para o Spring que o nosso parâmetro virá do corpo do da requisição e não como parâmetro de URL.

- usamos a anotação `@RequestBody`.

Para métodos void na classe Controller, o Spring devolve uma resposta sem conteúdo, juntamente com o código HTTP 200, caso a requisição seja processada com sucesso.

Ao executarmos essa chamada, enviamos o cabeçalho `Content-Type` no qual, ele serve para indiciar o tipo de conteúdo que **está sendo enviado ao servidor**.

Quando trabalhamos com um `ResponseEntity` do tipo *created*, é necessário informar a uri e o body para aquele retorno.

- para informar a uri, utilizamos a `UriComponentsBuilder` chamando-a pelo parâmetro do método da controller.
    
    ```jsx
    URI uri = uBuilder.path("/topicos/{id}")
    						.buildAndExpand(topico.getId()).toUri();
    ```
    

## Validação com Bean Validation

Para fazer validações de formulário para tamanho mínimo, máximo e etc, utilizamos o Bean Validation:

- `@NotNull` - não pode ser nulo.
- `@NotEmpty` - não pode ser nulo, mas, também não pode ser uma string vazia.
- `@Leng(min/max = ...)`

Após realizar essa validações, você precisa dizer para chamar o Bean Validation.

- fazemos isso adicionando o `@Valid` no parâmetro.

É possível criar uma nova anotação com o Bean Validation.
