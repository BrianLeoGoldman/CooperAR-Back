INSERT INTO user (nickname, firstname, lastname, password, email, birthday, province, money) VALUES
('roberto1985', 'Roberto', 'Fuentes', '12345', 'roberto@mail.com', LOCALTIME, 'BUENOS_AIRES', 12000),
('maria_ana', 'Maria', 'Campos', '67890', 'mj@mail.com', LOCALTIME, 'SANTA_FE', 23000),
('adrian127', 'Adrian', 'Garcia', '13579', 'adrian@mail.com', LOCALTIME, 'NEUQUEN', 7500),
('tomas_99', 'Tomas', 'Gonzalez', '24680', 'thom_car@mail.com', LOCALTIME, 'CHACO', 4900),
('abel1945', 'Abel', 'Campos', '11111', 'abel1945@mail.com', LOCALTIME, 'MENDOZA', 15800);

INSERT INTO project (name, budget, description, owner, creation_Date, finish_Date, category) VALUES
('Empezar una nueva empresa', 12000, 'Quiero empezar un negocio para vender ropa', 'roberto1985', LOCALTIME, null, 'COMERCIO'),
('Asistencia requerida en un hotel', 7000, 'Hay poco personal en el hotel', 'maria_ana', LOCALTIME, null, 'GASTRONOMIA'),
('Empresa de software expandiendose', 9000, 'La empresa de software se esta expandiendo a nuevos mercados', 'adrian127', LOCALTIME, null, 'PROGRAMACION'),
('Soy anciano y necesito ayuda', 1200, 'Soy una persona mayor y necesito ayuda con las tareas diarias', 'abel1945', LOCALTIME, null, 'ENTRETENIMIENTO'),
('Arreglar problemas edilicios', 8300, 'El edificio necesita reparaciones urgentes', 'roberto1985', LOCALTIME, null, 'CONSTRUCCION'),
('Trabajo en la cocina', 2900, 'Se necesitan cocineros con experiencia', 'maria_ana', LOCALTIME, null, 'GASTRONOMIA'),
('Objetos de coleccion', 1990, 'Soy un coleccionista profesional', 'roberto1985', LOCALTIME, null, 'ENTRETENIMIENTO'),
('Organizar un torneo de ping pong', 1300, 'Soy un fanatico del ping pong', 'roberto1985', LOCALTIME, null, 'DEPORTE');

INSERT INTO task (name, description, reward) VALUES
('Programar una aplicacion Python', 'Se necesita desarrollador de software', 380),
('Reparar una puerta de madera', 'La puerta frontal esta rota', 200),
('Cocinar una torta de chocolate', 'Se necesita para una fiesta de cumpleaños', 80),
('Limpiar el hotel', 'Los huespedes llegaran proximamente', 270),
('Programar una pagina web', 'La tienda necesita una pagina web para las ventas', 300),
('Arreglar un par de zapatos', 'Necesito estos zapatos para ir a trabajar', 130),
('Reparar la sala de reuniones', 'Ya estan comprados todos los materiales', 2300),
('Atrapar un raton', 'Hay un raton en el sotano', 50),
('Diseñar una campaña publicitaria', 'El producto debe llegar a la gente joven', 240),
('Atender un bar', 'Se necesita un barman', 210),
('Vigilar un edificio', 'Se necesita alguien para hacer la vigilancia nocturna', 300),
('Rocoger unos paquetes', 'No puedo viajar y necesito esto envios', 110),
('Vender ropa', 'Tareas de venta al por menor', 225),
('Reparar cañeria de gas', 'Una tuberia esta perdiendo gas y es muy peligroso', 1700),
('Limpiar las oficinas', 'Las oficinas estan sin uso desde hace dos años', 700),
('Preparar cena para unas 40 personas', 'Son los asistentes a un congreso', 2760),
('Conseguir un modelo de paraguas de los 50', 'Tengo una coleccion de paraguas clasicos', 900);

INSERT INTO project_tasks (project_id, tasks_id) VALUES
(3, 1),
(2, 2),
(4, 3),
(2, 4),
(1, 5),
(4, 6),
(5, 7),
(4, 8),
(1, 9),
(2, 10),
(2, 11),
(1, 12),
(1, 13),
(5, 14),
(5, 15),
(6, 16),
(7, 17);






