Este desarrollo sigue el siguiente enunciado:
Se requiere construir un API para manejar una lista de franquicias. Una franquicia se compone por un nombre y un listado de sucursales y, a su vez, una sucursal está compuesta por un nombre y un listado de productos ofertados en la sucursal. Un producto se componente de un nombre y una cantidad de stock.

Para esta API se usó JAVA 17, Springboot 3.4.1. Además, para almacenar los datos se uso una base de datos no relacional Mongodb

Para ejecutar este desarrollo se debe tener instaldo un IDE y el SDK de java. Adicional, se debe tener instalado MongoDB en la maquina:

Dentro del archivo de aplication.properties se configura la base de datos

El desarrollo incluye:
1. Endpoint para agregar una nueva franquicia.
   curl --location 'localhost:9015/api/v1/franchise/' \
   --header 'Content-Type: application/json' \
   --data '{
   "name": "Franquicia 1"
   }'
2. Endpoint para agregar una nueva sucursal a una franquicia.
   curl --location 'localhost:9015/api/v1/branchOffice/' \
   --header 'Content-Type: application/json' \
   --data '{
   "name": "Segunda sucursal para franquicia 1",
   "franchiseId": "676a4b48c75156028182759e"
   }'
3. Endpoint para agregar un nuevo producto a una sucursal.
   curl --location 'localhost:9015/api/v1/product/' \
   --header 'Content-Type: application/json' \
   --data '{
   "name": "producto 3 sucursal 2",
   "stock": 4,
   "branchOfficeId": "676a4b9ac7515602818275a0"
   }'
4. Endpoint para eliminar un nuevo producto a una sucursal.
   curl --location --request DELETE 'localhost:9015/api/v1/product/676a3585a80faf5ac8536cd8'
5. Endpoint para modificar el stock de un producto.
   curl --location --request PUT 'localhost:9015/api/v1/product/' \
   --header 'Content-Type: application/json' \
   --data '{
   "name": "producto prueba",
   "stock": 5,
   "branchOfficeId": "676a19341e892a7bc8fd1c65"
   }'
6. Endpoint que permita mostrar cual es el producto que más stock tiene por sucursal.
   curl --location 'localhost:9015/api/v1/product/676a4b48c75156028182759e/topProductsByFranchise'
7. Endpoint que permita actualizar el nombre de una franquicia.
   curl --location --request PUT 'localhost:9015/api/v1/product/updateName/676a4bd3c7515602818275a1' \
   --header 'Content-Type: application/json' \
   --data '{
   "name": "producto 1 editado sucrusal 1"
   }'
8. Endpoint que permita actualizar el nombre de una sucursal.
   curl --location --request PUT 'localhost:9015/api/v1/branchOffice/updateName/676a4b84c75156028182759f' \
   --header 'Content-Type: application/json' \
   --data '{
   "name": "Primera sucursal para franquicia 1 editado"
   }'
9. Endpoint que permita actualizar el nombre de un producto.
   curl --location --request PUT 'localhost:9015/api/v1/franchise/updateName/676a4b48c75156028182759e' \
   --header 'Content-Type: application/json' \
   --data '{
   "name": "franquicia 1 editada"
   }'

Como una actualización, si desea ser tomada en cuenta. Se hizo una actualización a la configuración del contenedor Docker.
Dentro del proyecto hay un archivo llamado "docker-compose.yml" en este está la configuración para dockerizar la aplicación web, como la base de datos MongoDB. 
Para ver estos contenedores funcionando, primero, dentro del proyecto se debe correr el comando:

   mvn clean package -DskipTests o mvn clean package

Este comando generará el .jar necesario para que docker pueda correr la aplicación. Después de esto, solo es correr el comando:
   
docker-compose up --build

Este comando levantará los dos contenedores, el de mongo y el de la aplicación web

Y para probar los endpoints, solo se debe cambiar el puerto por el puerto 8080