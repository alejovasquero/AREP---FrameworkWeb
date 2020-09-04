# Implementación de servidor http con conexión a mongoDB.

Este proyecto contiene la implementación de un servidor http en java por medio de sockets.
Para probar este servidor se realizó la conexión con una base de datos noSQL mongodb.

## Estado

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/a910c264521d4b86850b42ca04a70d8a)](https://www.codacy.com/manual/alejovasquero/AREP---FrameworkWeb?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=alejovasquero/AREP---FrameworkWeb&amp;utm_campaign=Badge_Grade)
[![CircleCI](https://circleci.com/gh/alejovasquero/AREP---FrameworkWeb.svg?style=svg)](https://circleci.com/gh/alejovasquero/AREP---FrameworkWeb)

[Despliegue en heroku](https://frameworkweb.herokuapp.com/)

## Empezando

Estas instrucciones te utilizar la página web, compilar el proyecto y las pruebas.
Las instrucciones se limitan a compilación, ejecución y uso. 

### Prerrequisitos

Para instalar y correr exitosamente este proyecto necesitamos:
+ **Java**
+ **Maven**
+ **Git**

### Instalación

Primeramente vamos a descargar el repositorio en nuestra máquina local, y en la carpeta de 
nuestra preferencia. En consola vamos a digitar el siguiente comando para clonar el repositorio.

```console
git clone https://github.com/alejovasquero/AREP---FrameworkWeb
```

Entremos a el directorio del proyecto

```console
cd FrameworkWeb
```

Debemos compilar el proyecto, que contiene las clases necesarias para poder correr nuestro
proyecto. Por medio de maven vamos a crear todos los compilables **.class**. Desde consola, y ubicados en la carpeta donde se encuentra
nuestra configuración de maven.

```console
mvn package
```

Ahora que nuestras clases etan compiladas vamos a ejecutar la clase principal para
ver el código en acción : )

## Servidor local

Una vez compiladas las clases vamos a correr el proyecto

```console
mvn exec:java -Dexec.mainClass="edu.escuelaing.arep.Main"
```

Ahora vamos a entrar a nuestro browser en [localhost:36000](http://localhost:36000)

## Servidor HEROKU

Si queremos hacer uso de la aplicación desde heroku entramos al siguiente [link](https://frameworkweb.herokuapp.com/)

## USO de la aplicación

Una vez en el servidor vamos a poder ver una tabla con los elementos de la base de datos.


![](img/pagina.PNG)

Otros recursos:

+ [/data.html](https://frameworkweb.herokuapp.com/data.html)
+ [/css/style.css](https://frameworkweb.herokuapp.com/css/style.css)
+ [/css/styledb.css](https://frameworkweb.herokuapp.com/css/styledb.css)
+ [/results](https://frameworkweb.herokuapp.com/results)
+ [/js/box.js](https://frameworkweb.herokuapp.com/js/box.js)


## Corriendo las pruebas

Correr las pruebas dentro del proyecto es muy sencillo, 
y lo haremos por medio de una fase de maven, la fase **test**, del ciclo de vida **default**. 

```console
mvn test
```
![](img/test.PNG)

## Especificación pruebas

##### HTMLHandlerTest
+ openTest - Revisa que sea exitoso el constenido de un archivo plano 
+ replaceTest - Revisa que se pueda sustituir el contenido de un archivo plano en memoria


##### RequestTest
+ resourceTest - Revisa que el recurso de la solicitud sea el correcto 
+ shouldBeImage - Revisa que el recurso de la solicitud sea una imagen
+ shouldNotBeImage - Revisa que el recurso de la solicitud NO sea una imagen
+ shouldGetHeaderKey - Revisa que el header de respuesta sea el correcto


##### WebFrameworkTest
+ testTotalPath - Revisa que el path home sea correcto para distintos recursos 
+ shouldNotSupportFormat - Revisa que el formato de una request NO sea soportado
+ shouldSupportFormat - Revisa que el formato de una request sea soportado
+ shouldExistResource - Revisa que el recurso haya sido exitosamente mapeado

![](resources/AllTest.PNG)

## Construido con

+ [Maven](https://maven.apache.org/) - Manejo de dependencias
+ [Git](https://git-scm.com/) - Control de versiones
+ [Java](https://www.java.com/es/) - Lenguaje de programación

## Autores

+ **David Alejandro Vasquez Carreño** - *Trabajo inicial* - [alejovasquero](https://github.com/alejovasquero)

## Licencia

Este proyecto está licenciado bajo la licencia del MIT - Vea el [LICENSE.md](LICENSE.md) para más detalles
