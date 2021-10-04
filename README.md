# entrevista-grupo-salinas

Algunos funciones extra que no implementé por falta de tiempo serían,
Caching con la técnica delta para ofrecer una mejor experiencia de usuario al cargar datos de forma local mientras
estos se cargan de forma remota para posteriormente actualizar en pantalla.
Si se usó caching pero de forma tradicional, así que la app funciona en su mayoria sin conexión a internet.
Solo hubo un detalle con la categoria Now playing ya que la busqueda de esta depende mucho del backend 
y en local era complicado implementarlo en tan poco tiempo para rooms. 
Lo logré mitigar con una variable de apoyo en la entidad de rooms.

Adicionalmente también tenía planeado el uso de Build variants para el caso de la api key 
pero por el momento solo usé un archivo de constantes, pero si lo consideré. (la api key debe de ir en el módulo de data en /utils/constants)

En resumen la app cuenta con: 

Categorización por Most Popular y Playing now.
Visualización de videos en detalle.
Funciona sin internet.
Inyección de dependencias con Hilt.
Patrón MVVM.
Clean Architecture (app, data, domain).
Y Pruebas unitarias con Mockk.
