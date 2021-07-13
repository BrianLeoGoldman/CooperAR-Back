INSERT INTO user (nickname, firstname, lastname, password, email, birthday, province, money) VALUES
('roberto1985', 'Roberto', 'Fuentes', 'roberto12345', 'juanjuarez2020argcon@gmail.com', LOCALTIME, 'BUENOS_AIRES', 12000),
('maria_ana', 'Maria', 'Campos', 'maria12345', 'juanjuarez2020argcon@gmail.com', LOCALTIME, 'MENDOZA', 23000),
('adrian127', 'Adrian', 'Garcia', 'adrian12345', 'juanjuarez2020argcon@gmail.com', LOCALTIME, 'NEUQUEN', 7500),
('tomas_99', 'Tomas', 'Gonzalez', 'tomas12345', 'juanjuarez2020argcon@gmail.com', LOCALTIME, 'CHACO', 4900),
('abel1945', 'Abel', 'Campos', 'abel12345', 'juanjuarez2020argcon@gmail.com', LOCALTIME, 'SANTA_FE', 15800);

INSERT INTO project (name, budget, description, owner, creation_Date, finish_Date, category, percentage) VALUES
('Empezar un negocio de indumentaria deportiva', 53000, 'Estoy empezando un negocio de indumentaria deportiva para jovenes y adultos. Yo soy deportista profesional pero necesito gente con experiencia en las areas de comercio, logistica y administracion. El nombre de la empresa es Primer Puesto, y en el documento Primer_Puesto.docx puede verse el registro en la AFIP y otros tramites legales. En Primer_Puesto_logo.jpg puede verse el logo que utiliza la empresa.', 'roberto1985', LOCALTIME, null, 'COMERCIO', 50),
('Temporada alta en hotel en Mendoza', 39500, 'Durante la temporada alta, el hotel Aire de Nieve, recibe alrededor de cinco veces la cantidad usual de clientes. Necesitamos ayuda en las areas de limpieza, gastronomia y atencion al cliente. En las imagenes Aire_de_Nieve_1.jpg y Aire_de_Nieve_2.jpg puede verse el frente y el comedor del hotel. El hotel suele organizar congresos academicos y se encuentra proximo al centro de la ciudad de Mendoza.', 'maria_ana', LOCALTIME, null, 'TURISMO', 25),
('Expansion internacional de empresa de software', 91800, 'Nuestra empresa de software BitBot esta desarrollando proyectos para nuevos clientes en Asia y Europa. El equipo actual de la empresa son siete desarrolladores, dos analistas y un manager. Necesitamos ayuda de programadores con experiencia en lenguajes Python, Java, Ruby, o en manejo de bases de datos relacionales. Es requerido un buen nivel de ingles en todos los casos y se valora conocimiento de sistemas UNIX.', 'adrian127', LOCALTIME, LOCALTIME, 'PROGRAMACION', 100),
('Asistencia en asilo de ancianos', 23990, 'Necesitamos ayuda en el asilo para personas mayores Tercera Edad. Temporalmente estamos bajos de personal y no podemos asistir correctamente a todos los pacientes. Se buscan personas con experiencia en cuidado de personas mayores y con dificultades de movilidad. En Tercera_Edad.jpg puede verse el edificio, y se adjuntan otras imagenes para mostrar las areas de las instalaciones y como es el trabajo cotidiano del personal.', 'abel1945', LOCALTIME, null, 'MEDICINA', 66),
('Reparaciones en edificios antiguos', 76000, 'La zona a trabajar es un predio de edificios construidos en la decada de 1960. Varios de los edificios requieren reparaciones fisicas, asi como mejoras en las instalaciones electricas y de iluminacion. La zona albergara un complejo de oficinas y algunos negocios comerciales. Se adjunta documentacion importante, favor de leer antes de asignarse tareas, ya que es condicion necesaria cumplir con los requisitos indicados en la documentacion.', 'roberto1985', LOCALTIME, null, 'CONSTRUCCION', 33),
('Trabajo en importante restaurante', 29200, 'Se necesitan varios cocineros para asistir una la cocina de un importante restaurante local, La Marinara. El restaurante trabaja principalmente con pastas y mariscos, pero tambien se necesita gente con experiencia en reposteria. La higiene y limpieza dentro del local es de extrema importancia, asi que a los interesados por favor leer las indicaciones de higiene y limpieza del local. El ritmo de trabajo es muy acelerado y la cocina cuenta con dos chefs profesionales de alto nivel.', 'maria_ana', LOCALTIME, null, 'GASTRONOMIA', 0),
('Compra y venta de objetos de coleccion', 153000, 'Soy un coleccionista profesional y busco ayuda para la compra y venta de diversos articulos de coleccion. Adjunto dos achivos: el catalogo de compra, que incluye la lista completa de articulos que busco comprar, y el rango de precios al cual estoy dispuesto a compralos, y el catalogo de venta, que incluye objetos que estoy intersado en vender, y el rango de precios estimados de venta de los mismos.', 'roberto1985', LOCALTIME, null, 'COMERCIO', 0),
('Organizar un torneo de futbol', 900, 'Somos un equipo amateur de futbol 5 de la localidad de San Cristobal, provincia de Santa Fe. Queremos organizar un torneo de futbol de alcance provincial. Los partidos se organizarian en la ciudad de Santa Fe (ya estan aseguradas las canchas), pero necesitamos ayuda con la organizacion y difusion de la convocatoria al torneo. Estimamos que deberian ser entre unos 20 o 30 equipos, y la duracion total del toneo seria de alrededor de dos meses.', 'roberto1985', LOCALTIME, null, 'DEPORTE', 0);


INSERT INTO task (name, description, reward, project_id, creation_Date, finish_Date, difficulty, owner, worker, state) VALUES
('Programar una aplicacion Python', 'Se necesita un desarrollador de software con experiencia en el lenguaje de programación python para colaborar en tareas de desarrollo', 380, 3,LOCALTIME, null, 'EXTREMA', 'adrian127', 'roberto1985', 'FINALIZADA'),
('Reparar una puerta de madera', 'La puerta frontal de madera de ébano es muy antigua y se traba al tratar de abrirla o cerrarla', 200, 2, LOCALTIME, null, 'FACIL', 'maria_ana', 'roberto1985', 'FINALIZADA'),
('Cocinar una torta de chocolate', 'Se necesita una torta de crema y chocolate que alcance para unos 30 invitados para una fiesta de cumpleaños', 80, 4, LOCALTIME, null, 'FACIL', 'abel1945', 'maria_ana', 'EN_CURSO'),
('Limpiar el hotel', 'Las habitaciones de los 5 pisos deben ser desinfectadas y quedar limpias para recibir a los huespedes', 270, 2, LOCALTIME, null, 'DIFICIL', 'maria_ana', 'adrian127', 'COMPLETA'),
('Programar una pagina web', 'La tienda necesita una pagina web facil de usar para que los clientes puedan encargar pedidos y ver nuestro catalogo', 300, 1, LOCALTIME, null, 'REGULAR', 'roberto1985', 'abel1945', 'FINALIZADA'),
('Arreglar un par de zapatos', 'Son un par de zapatos de vestir que estan empezando a romperse en las suelas', 130, 4, LOCALTIME, null, 'REGULAR', 'abel1945', 'roberto1985', 'FINALIZADA'),
('Reparar la sala de reuniones', 'Hay un agujero en el techo de la sala. Ya estan comprados todos los materiales para la obra', 2300, 5, LOCALTIME, null, 'EXTREMA', 'roberto1985', 'maria_ana', 'FINALIZADA'),
('Atrapar un raton', 'Hay un raton en el sotano de mi casa. Es un lugar oscuro y con poco espacio para moverse', 50, 4, LOCALTIME, null, 'RIDICULA', 'abel1945', 'roberto1985', 'FINALIZADA'),
('Diseñar una campaña publicitaria', 'El producto es una campera de cuero, y la campaña debe estar orientada a la gente joven', 240, 1, LOCALTIME, null, 'DIFICIL', 'roberto1985', 'abel1945', 'FINALIZADA'),
('Atender un bar', 'Se necesita un barman con experiencia con conocimiento en preparar tragos para atender el bar', 210, 2, LOCALTIME, null, 'REGULAR', 'maria_ana', 'roberto1985', 'COMPLETA'),
('Vigilar un edificio', 'Se necesita alguien para hacer la vigilancia nocturna alrededor del edificio de unos 260 metros cuadrados', 300, 2, LOCALTIME, null, 'REGULAR', 'maria_ana', 'adrian127', 'EN_CURSO'),
('Rocoger unos paquetes', 'No puedo viajar y necesito esto envios', 110, 1, LOCALTIME, null, 'FACIL', 'roberto1985', 'SIN TRABAJADOR', 'DISPONIBLE'),
('Vender ropa', 'Tareas de venta al por menor', 225, 1, LOCALTIME, null, 'REGULAR', 'roberto1985', 'SIN TRABAJADOR', 'DISPONIBLE'),
('Reparar cañeria de gas', 'Una tuberia esta perdiendo gas y es muy peligroso', 1700, 5, LOCALTIME, null, 'REGULAR', 'roberto1985', 'SIN TRABAJADOR', 'DISPONIBLE'),
('Limpiar las oficinas', 'Las oficinas estan sin uso desde hace dos años', 700, 5, LOCALTIME, null, 'REGULAR', 'roberto1985', 'SIN TRABAJADOR', 'DISPONIBLE'),
('Preparar cena para unas 40 personas', 'Son los asistentes a un congreso', 2760, 6, LOCALTIME, null, 'EXTREMA', 'maria_ana', 'SIN TRABAJADOR', 'DISPONIBLE'),
('Conseguir un modelo de paraguas de los 50', 'Tengo una coleccion de paraguas clasicos', 900, 7, LOCALTIME, null, 'DIFICIL', 'roberto1985', 'SIN TRABAJADOR', 'DISPONIBLE');

INSERT INTO money_request (requester, money_requested, creation_date, state) VALUES
('roberto1985', 500, LOCALTIME, 'APROBADO'),
('maria_ana', 3000, LOCALTIME, 'APROBADO'),
('roberto1985', 2000, LOCALTIME, 'APROBADO'),
('tomas_99', 900, LOCALTIME, 'APROBADO'),
('maria_ana', 5000, LOCALTIME, 'APROBADO'),
('roberto1985', 500, LOCALTIME, 'APROBADO'),
('roberto1985', 750, LOCALTIME, 'ABIERTO'),
('adrian127', 500, LOCALTIME, 'APROBADO'),
('abel1945', 500, LOCALTIME, 'RECHAZADO'),
('roberto1985', 8000, LOCALTIME, 'APROBADO'),
('roberto1985', 1000, LOCALTIME, 'APROBADO'),
('maria_ana', 200, LOCALTIME, 'ABIERTO'),
('maria_ana', 2000, LOCALTIME, 'APROBADO'),
('abel1945', 500, LOCALTIME, 'RECHAZADO'),
('adrian127', 2000, LOCALTIME, 'APROBADO'),
('tomas_99', 1000, LOCALTIME, 'APROBADO'),
('maria_ana', 10000, LOCALTIME, 'APROBADO'),
('adrian127', 2000, LOCALTIME, 'APROBADO'),
('roberto1985', 2000, LOCALTIME, 'ABIERTO'),
('maria_ana', 3000, LOCALTIME, 'APROBADO'),
('abel1945', 500, LOCALTIME, 'RECHAZADO'),
('adrian127', 3000, LOCALTIME, 'APROBADO'),
('maria_ana', 800, LOCALTIME, 'ABIERTO'),
('tomas_99', 3000, LOCALTIME, 'APROBADO'),
('abel1945', 800, LOCALTIME, 'APROBADO'),
('roberto1985', 1200, LOCALTIME, 'ABIERTO'),
('adrian127', 1000, LOCALTIME, 'ABIERTO'),
('abel1945', 15000, LOCALTIME, 'APROBADO');

INSERT INTO message (publisher, date, text, task_id) VALUES
('roberto1985', '3/7/2021 16:55:00', 'Hola adrian127. Tengo dudas sobre la aprobacion de la tarea.', 1),
('adrian127', '3/7/2021 16:55:30', 'Hola roberto1985. La aplicacion debe estar funcionando en un servidor para que la tarea este aprobada.', 1),
('roberto1985', '4/7/2021 21:14:33', 'Entiendo. ¿Algun requerimiento especifico?', 1),
('adrian127', '4/7/2021 21:15:44', 'No, solo que funcione.', 1),
('roberto1985', '9/7/2021 09:39:52', '¿Las herramientas se incluyen o debo llevar las mias?', 2),
('maria_ana', '9/7/2021 09:46:11', 'Las herramientas no estan incluidas', 2),
('abel1945', '10/7/2021 19:23:00', 'En caso de alguna duda, no duden en preguntar', 3),
('roberto1985', '10/7/2021 19:29:30', 'Hola. ¿Se necesita ser repostero para esta tarea?', 3),
('abel1945', '10/7/2021 19:45:33', 'No es necesario', 3),
('roberto1985', '11/7/2021 07:16:21', 'Ok, genial', 3),
('adrian127', '5/7/2021 12:04:00', 'Pregunta: ¿que tan grande es el hotel?', 4),
('maria_ana', '5/7/2021 12:09:30', 'El hotel tiene 4 pisos, cada uno con unas 6 habitaciones. Ademas hay un comedor, una sala de estar y la recepcion', 4),
('adrian127', '6/7/2021 17:00:36', '¿Cuales son las medidas cada habitación?', 4),
('maria_ana', '6/7/2021 17:12:41', 'Son todas de 4 metros x 6 metros', 4);


