## Retry pattern 
- https://docs.microsoft.com/en-us/azure/architecture/patterns/retry
- https://dzone.com/articles/understanding-retry-pattern-with-exponential-back

## Circuit pattern
- https://docs.microsoft.com/en-us/azure/architecture/patterns/circuit-breaker

## API
- https://api.nasa.gov/#browseAPI

## Resilience4J References
- https://resilience4j.readme.io/docs/getting-started-3
- https://github.com/resilience4j/resilience4j-spring-boot2-demo
- https://resilience4j.readme.io/docs/circuitbreaker
- https://resilience4j.readme.io/docs/retry

## WireMock References
- http://wiremock.org/docs/
- https://cloud.spring.io/spring-cloud-contract/reference/html/project-features.html#features-wiremock-registering-stubs
- https://medium.com/faun/integration-tests-in-spring-using-wiremock-42e214618aea
- https://rieckpil.de/spring-boot-integration-tests-with-wiremock-and-junit-5/

## Jackson-annotations References
- http://fasterxml.github.io/jackson-annotations/javadoc/2.5/com/fasterxml/jackson/annotation/JsonInclude.Include.html#NON_EMPTY

## Mock-server
- https://www.mock-server.com/where/docker.html
- https://www.mock-server.com/mock_server/creating_expectations.html

## Other references
- https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html#production-ready-endpoints

## Dojo challenges (pt-br)
- Test retry - Consultar uma API que sempre retorna erro
	- executar uma chamada que irá acionar o retry e deve retornar a resposta identificando que o fallback tratou o erro;
	
- Test retry - Consultar uma API que retorna sucesso somente após as 3 primeiras tentativas, nas 3 primeiras tentativas irá retornar falha
	- executar uma chamada que irá acionar o retry 3 vezes e não deve retornar nenhuma descrição no atributo que identifica o fallback;
	
- Test circuit breaker - Consultar uma API que sempre retorna erro
	- executar a api na quantidade de vezes definida para o circuito abrir(7);

- Test circuit breaker - Consultar uma API que retorna sucesso somente após as 4 primeiras tentativas, nas 4 primeiras tentativas irá retornar falha
	- executar a api na quantidade de vezes definida para o circuito abrir(7);
	- retornar o atributo do response preenchido somente enquanto o circuito estiver aberto;
	- perceber que mesmo com a API retornando sucesso o consumo da api só será possível após a transição de CLOSE->HALF;
		- executar a API 3 vezes para que o circuit breaker mude de HALF->OPEN;
	