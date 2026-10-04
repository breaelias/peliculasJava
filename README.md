Streaming de películas (Java)

Aplicación de escritorio en Java para explorar y calificar películas. Proyecto académico de la carrera de Ingeniería en Computación (UNLP).

Que hace:

Registro e inicio de sesión de usuarios.
Catálogo de ~9000 películas, cargado desde un archivo CSV.
Pantalla principal con 10 películas: hasta que el usuario califica una película, ve las 10 mejor rankeadas del catálogo. Después de su primera calificación, en cada sesión ve 10 películas al azar.
Calificación: 1 a 5 estrellas más un comentario. Cada película se puede calificar una sola vez por usuario.
Detalle de cada película: póster, título, género y sinopsis. Con doble clic se abre la vista ampliada con más información.
Buscador conectado a la API de OMDb: permite buscar cualquier película de esa base y ver su póster, título, descripción y demás datos.
Cierre de sesión.
Tecnologías
Java 23
Java Swing (interfaz gráfica)
SQLite (persistencia), con acceso mediante JDBC y patrón DAO
Patrón MVC
API de OMDb
Cómo ejecutarlo
Descargá proyectoFinal.jar desde la sección Releases.
Necesitás Java instalado (desarrollado con Java 23; en otras versiones puede no funcionar).
Ejecutalo con doble clic o desde una terminal:
java -jar proyectoFinal.jar

La base de datos SQLite se crea automáticamente la primera vez que se ejecuta la aplicación. Se necesita conexión a internet para el buscador y los pósters.

Ejecutarlo desde el código fuente

El código está en la carpeta src y se puede abrir con Eclipse (el repositorio incluye los archivos .project y .classpath). El buscador necesita una clave gratuita de OMDb, que se obtiene en https://www.omdbapi.com/apikey.aspx. Pegala en src/model/streaming/service/OMDbService.java, reemplazando TU_API_KEY.

Estructura
src/: código fuente.
doc/: documentación Javadoc.
movies.csv: catálogo de películas.
