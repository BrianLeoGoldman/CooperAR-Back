INSERT INTO user (nickname, firstname, lastname, password, email, birthday, province, money) VALUES
('roberto1985', 'Roberto', 'Fuentes', 'roberto12345', 'roberto@mail.com', LOCALTIME, 'BUENOS_AIRES', 12000),
('maria_ana', 'Maria', 'Campos', 'maria12345', 'mj@mail.com', LOCALTIME, 'SANTA_FE', 23000),
('adrian127', 'Adrian', 'Garcia', 'adrian12345', 'adrian@mail.com', LOCALTIME, 'NEUQUEN', 7500),
('tomas_99', 'Tomas', 'Gonzalez', 'tomas12345', 'thom_car@mail.com', LOCALTIME, 'CHACO', 4900),
('abel1945', 'Abel', 'Campos', 'abel12345', 'abel1945@mail.com', LOCALTIME, 'MENDOZA', 15800);

INSERT INTO project (name, budget, description, owner, creation_Date, finish_Date, category) VALUES
('Empezar una nueva empresa', 12000, 'Quiero empezar un negocio para vender ropa', 'roberto1985', LOCALTIME, null, 'COMERCIO'),
('Asistencia requerida en un hotel', 7000, 'Hay poco personal en el hotel', 'maria_ana', LOCALTIME, null, 'GASTRONOMIA'),
('Empresa de software expandiendose', 9000, 'La empresa de software se esta expandiendo a nuevos mercados', 'adrian127', LOCALTIME, null, 'PROGRAMACION'),
('Soy anciano y necesito ayuda', 1200, 'Soy una persona mayor y necesito ayuda con las tareas diarias', 'abel1945', LOCALTIME, null, 'ENTRETENIMIENTO'),
('Arreglar problemas edilicios', 8300, 'El edificio necesita reparaciones urgentes', 'roberto1985', LOCALTIME, null, 'CONSTRUCCION'),
('Trabajo en la cocina', 2900, 'Se necesitan cocineros con experiencia', 'maria_ana', LOCALTIME, null, 'GASTRONOMIA'),
('Objetos de coleccion', 1990, 'Soy un coleccionista profesional', 'roberto1985', LOCALTIME, null, 'ENTRETENIMIENTO'),
('Organizar un torneo de ping pong', 1300, 'Soy un fanatico del ping pong', 'roberto1985', LOCALTIME, null, 'DEPORTE');

INSERT INTO task (name, description, reward, project_id, creation_Date, finish_Date, difficulty) VALUES
('Programar una aplicacion Python', 'Se necesita desarrollador de software', 380, 3,LOCALTIME, null, 'EXTREMA'),
('Reparar una puerta de madera', 'La puerta frontal esta rota', 200, 2, LOCALTIME, null, 'FACIL'),
('Cocinar una torta de chocolate', 'Se necesita para una fiesta de cumpleaños', 80, 4, LOCALTIME, null, 'FACIL'),
('Limpiar el hotel', 'Los huespedes llegaran proximamente', 270, 2, LOCALTIME, null, 'DIFICIL'),
('Programar una pagina web', 'La tienda necesita una pagina web para las ventas', 300, 1, LOCALTIME, null, 'REGULAR'),
('Arreglar un par de zapatos', 'Necesito estos zapatos para ir a trabajar', 130, 4, LOCALTIME, null, 'REGULAR'),
('Reparar la sala de reuniones', 'Ya estan comprados todos los materiales', 2300, 5, LOCALTIME, null, 'EXTREMA'),
('Atrapar un raton', 'Hay un raton en el sotano', 50, 4, LOCALTIME, null, 'RIDICULA'),
('Diseñar una campaña publicitaria', 'El producto debe llegar a la gente joven', 240, 1, LOCALTIME, null, 'DIFICIL'),
('Atender un bar', 'Se necesita un barman', 210, 2, LOCALTIME, null, 'REGULAR'),
('Vigilar un edificio', 'Se necesita alguien para hacer la vigilancia nocturna', 300, 2, LOCALTIME, null, 'REGULAR'),
('Rocoger unos paquetes', 'No puedo viajar y necesito esto envios', 110, 1, LOCALTIME, null, 'FACIL'),
('Vender ropa', 'Tareas de venta al por menor', 225, 1, LOCALTIME, null, 'REGULAR'),
('Reparar cañeria de gas', 'Una tuberia esta perdiendo gas y es muy peligroso', 1700, 5, LOCALTIME, null, 'REGULAR'),
('Limpiar las oficinas', 'Las oficinas estan sin uso desde hace dos años', 700, 5, LOCALTIME, null, 'REGULAR'),
('Preparar cena para unas 40 personas', 'Son los asistentes a un congreso', 2760, 6, LOCALTIME, null, 'EXTREMA'),
('Conseguir un modelo de paraguas de los 50', 'Tengo una coleccion de paraguas clasicos', 900, 7, LOCALTIME, null, 'DIFICIL');







