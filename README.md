# xwingSW
API that allows CRUD maintenance of spaceships from series and movies


# xwingSW - Spring Boot Project

Desarrollar, utilizando Maven, Spring Boot, y Java, una API que permita hacer un mantenimiento CRUD de naves espaciales de series y películas.


# Detalles Implementación requeridos

## Caching
Permite mejorar el rendimiento de la aplicación almacenando en memoria los resultados de operaciones costosas, como consultas a bases de datos o cálculos complejos. Spring Boot proporciona soporte integrado para la caché a través de anotaciones como @Cacheable, @CacheEvict, @CachePut, entre otras, reduciendo así el número de ejecuciones en función de la información disponible en el caché.

## Pagination
La paginación es un mecanismo utilizado para dividir grandes conjuntos de datos en fragmentos más pequeños, llamados páginas, facilitando así la presentación y manipulación eficiente de esos datos. En el contexto de una aplicación Spring Boot, como en el fragmento de código que has proporcionado, la paginación se utiliza para recuperar solo una parte específica de los datos almacenados en una base de datos en función de un tamaño de página y un número de página determinados.

Por ejemplo, en el método **getAllSpaceships** se utiliza un objeto **Pageable** que contiene información sobre la paginación, como el número de página y el tamaño de la página. Luego, se llama al método findAll del repositorio (spaceshipRepository) pasando este objeto Pageable. Esto devuelve una página de objetos Spaceship, donde cada página contiene una parte de los resultados totales de la consulta. Esto permite cargar y mostrar los datos de forma más eficiente, especialmente cuando hay grandes cantidades de datos involucrados.


```java
@Override
@Cacheable("spaceships")
public Page<Spaceship> getAllSpaceships(Pageable pageable) {
  return spaceshipRepository.findAll(pageable);
}
```


## Aspect
Encapsulan comportamientos comunes y su aplicación en puntos específicos del código, sin necesidad de modificar directamente ese código

> En el proyecto se pide: Desarrollar un @Aspect que añada una línea de log cuando nos piden una nave con un id negativo.

```java
@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.controller.SpaceshipController.getSpaceshipById(..)) && args(id)")
    public void logIfNegativeId(Long id) {
        if (id < 0) {
        	logger.warn("Attempt to access a spaceship with a negative ID: " + id);
        }
    }

}
```


## H2 mem + FlyWay
- H2 es un sistema de gestión de bases de datos relacional escrito en Java. Es ligero y fácil de usar, y a menudo se utiliza en entornos de desarrollo y pruebas debido a su capacidad de ser embebido en aplicaciones Java y su soporte para la ejecución en memoria

- Flyway es una herramienta de migración de bases de datos que facilita la administración y el versionamiento de los cambios en las bases de datos.
> Los script de configuración sql de Flyway se suelen incluir en el apartado de recursos de la aplicación: src/main/resources bajo el directorio db/migration

```sql
-- db/migration/V1__create_spaceship_table.sql

CREATE TABLE SPACESHIP (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
    TYPE VARCHAR(255),
    DESCRIPTION VARCHAR(1000)
);
```

# Configuraciones

## Spring Security
Spring Security es un framework de apoyo al marco de trabajo Spring, que dota al mismo de una serie servicios de seguridad aplicables para sistemas basados en la arquitectura basados en J2EE, enfocado particularmente sobre proyectos construidos usando SpringFramework.

En el proyecto se ha securizado la API obligando a incluir detalles de autenticación para el uso REST con el usuario/rol: **USER** para todo lo relativo al path **/api/spaceships/** definido en el Controller y por otro lado, obligando a usar el usuario/rol: **ADMIN** para hacer uso del path **search**:

```java
@Bean
	@Order(1)
	SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
		return http
				.securityMatcher(new AntPathRequestMatcher("/api/spaceships/**"))
				.authorizeHttpRequests(auth -> {
					auth.requestMatchers("/api/spaceships/search").hasRole("ADMIN");
					auth.anyRequest().authenticated();
				})
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.httpBasic(Customizer.withDefaults())
				.csrf(AbstractHttpConfigurer::disable)
				.build();
	}
```

## OpenApi
La especificación OpenAPI, originalmente conocida como la especificación **Swagger**, es una especificación para archivos de interfaz legibles por máquina para describir, producir, consumir y visualizar servicios web RESTful.​

![imagen](https://github.com/nachobit/xwing/assets/11391616/8582aa83-ee9b-4980-82c6-e5a3edde7239)


## Docker
Se automatiza el despliegue de aplicaciones dentro de contenedores de software, proporcionando una capa adicional de abstracción y automatización de virtualización de aplicaciones en múltiples sistemas operativos

![Docker Build Status](https://hub.docker.com/layers/nachobit/demo-xwingsw/latest/images/sha256:885de2a068b9b0d043e6431eaee4f27232dba449160a40d13e0667a35d0b479a?uuid=C8AACB1B-0F70-4F41-934E-C2336FB65F03)

## RabbitMQ
Se integra RabbitMQ, una herramienta open source que se utiliza para la implementación de sistemas de encolado o negociación de mensajes.

![xwing_rabbit](https://github.com/user-attachments/assets/9bf1d957-3102-4e98-bbfb-437278aa8e57)
