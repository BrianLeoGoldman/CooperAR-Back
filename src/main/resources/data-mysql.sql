INSERT INTO user (nickname, firstname, lastname, password, email, birthday, province, money) VALUES
('roberto1985', 'Roberto', 'Fuentes', 'roberto12345', 'leonel89_01@hotmail.com', LOCALTIME, 'BUENOS_AIRES', 12000),
('maria_ana', 'Maria', 'Campos', 'maria12345', 'leonel89_01@hotmail.com', LOCALTIME, 'SANTA_FE', 23000),
('adrian127', 'Adrian', 'Garcia', 'adrian12345', 'leonel89_01@hotmail.com', LOCALTIME, 'NEUQUEN', 7500),
('tomas_99', 'Tomas', 'Gonzalez', 'tomas12345', 'leonel89_01@hotmail.com', LOCALTIME, 'CHACO', 4900),
('abel1945', 'Abel', 'Campos', 'abel12345', 'leonel89_01@hotmail.com', LOCALTIME, 'MENDOZA', 15800);

INSERT INTO project (name, budget, description, owner, creation_Date, finish_Date, category, percentage) VALUES
('Empezar una nueva empresa', 12000, 'Quiero empezar un nuevo negocio para vender ropa, tanto para adultos como para niños y adolescentes', 'roberto1985', LOCALTIME, null, 'COMERCIO', 50),
('Asistencia requerida en un hotel', 7000, 'Hay poco personal en el hotel y se acerca la temporada alta, asi que espero muchos clientes', 'maria_ana', LOCALTIME, null, 'GASTRONOMIA', 25),
('Empresa de software expandiendose', 9000, 'La empresa de software se esta expandiendo a nuevos mercados en Asia y Europa y mi equipo no puede con todo el trabajo', 'adrian127', LOCALTIME, LOCALTIME, 'PROGRAMACION', 100),
('Soy anciano y necesito ayuda', 1200, 'Soy una persona mayor con dificultades de movilidad y necesito ayuda con las tareas diarias', 'abel1945', LOCALTIME, null, 'ENTRETENIMIENTO', 66),
('Arreglar problemas edilicios', 8300, 'El edificio que compre necesita reparaciones urgentes y debe ser acondionado para albergar oficinas', 'roberto1985', LOCALTIME, null, 'CONSTRUCCION', 33),
('Trabajo en la cocina', 2900, 'Se necesitan cocineros con experiencia para trabajr en una cocina con mucho personal y un ritmo rapido de trabajo', 'maria_ana', LOCALTIME, null, 'GASTRONOMIA', 0),
('Objetos de coleccion', 1990, 'Soy un coleccionista profesional y busco articulos de coleccion dificiles de conseguir', 'roberto1985', LOCALTIME, null, 'ENTRETENIMIENTO', 0),
('Organizar un torneo de ping pong', 1300, 'Soy un fanatico del ping pong y quiero organizar un torneo a nivel provincial', 'roberto1985', LOCALTIME, null, 'DEPORTE', 0);

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








