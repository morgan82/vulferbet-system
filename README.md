# vulferbet-system
## Como levantar la aplicacion
correr java -jar vulferbet-system-{VERSION}.jar --spring.profiles.active={dev|heroku}
 
## Enpoint Disponibles
http://{domain}/weather/?day=x
http://{domain}/weather-full/?day=x
en la respuesta del recurso weather-full hay un nodo "easyFormat", el valor de este puede ser utilizado en la pag https://www.mathsisfun.com/data/cartesian-coordinates-interactive.html para ver los planetas (puntos) en un diagrama de ejes cartecianos X e Y. Boton editar e insetar los valores de "easyFormat"  
