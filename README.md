# Procesamiento de Imágenes con Morfología Matemática
Este proyecto implementa técnicas de Morfología Matemática aplicadas al procesamiento de imágenes. Estas técnicas son útiles para eliminar ruido en las imágenes mediante la construcción de filtros y suavizado, con el uso de un elemento estructurante que se desplaza sobre la imagen, causando diferentes efectos según su forma y contenido.

## Técnicas Implementadas
- **Erosión**: Busca eliminar colores claros en la imagen, lo que resulta en la eliminación de ruidos brillantes. El valor del píxel bajo análisis es reemplazado por el menor valor de los píxeles dentro del elemento estructurante.
- **Dilatación**: Expande los colores claros en la imagen, eliminando ruidos oscuros. El valor del píxel bajo análisis es reemplazado por el mayor valor de los píxeles dentro del elemento estructurante.

Estas técnicas permiten una manipulación efectiva de imágenes para la mejora y limpieza de sus características visuales.

## Requisitos del Sistema
- Java Development Kit (JDK) 8 o superior
- Un IDE compatible con Java (opcional)

## Instalación
1. Clonar el repositorio:
    ```sh
    git clone https://github.com/usuario/Proyecto1_SistemasDistribuidos.git
    ```
2. Navegar a la carpeta del proyecto:
    ```sh
    cd Proyecto1_SistemasDistribuidos
    ```

## Compilación
Situarse en la carpeta `src` y ejecutar el siguiente comando:
```sh
javac -cp . Run.java
```

## Ejecución
Ejecutar el siguiente comando:
```sh
java -cp . Run
```
O cargar el proyecto en un IDE y ejecutar el archivo `Run.java`.

## Contribución
1. Hacer un fork del proyecto.
2. Crear una nueva rama:
    ```sh
    git checkout -b feature/nueva-funcionalidad
    ```
3. Realizar los cambios y hacer commit:
    ```sh
    git commit -m 'Agregar nueva funcionalidad'
    ```
4. Subir los cambios a tu repositorio:
    ```sh
    git push origin feature/nueva-funcionalidad
    ```
5. Crear un Pull Request.

## Licencia
Este proyecto está bajo la Licencia MIT. Para más detalles, ver el archivo `LICENSE`.

## Integrantes

- Guillermo Bustamante
- Carlos Jara
