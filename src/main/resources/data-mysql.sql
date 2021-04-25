INSERT INTO user (nickname, email) VALUES
('roberto1985', 'roberto@mail.com'),
('maria_ana', 'mj@mail.com'),
('adrian127', 'adrian@mail.com'),
('tomas_99','thom_car@mail.com'),
('abel1945','abel1945@mail.com');

INSERT INTO project (name, budget, description, owner) VALUES
('Start a new company', 12000, 'I want to start a new company that sells fashion products', 'roberto1985'),
('Assistance required at my hotel', 7000, 'My hotel is currently low on staff', 'maria_ana'),
('Software company expanding', 9000, 'My software company is expanding to new markets', 'adrian127'),
('I am old and need help', 1200, 'I am an old person and I need help with various tasks', 'abel1945'),
('Fix building issues', 8300, 'The building I just bought need repairs', 'roberto1985'),
('Kitchen work', 2900, 'I need cookers with experience', 'maria_ana'),
('Get collectable items', 1990, 'I am a professional collector', 'roberto1985'),
('Organize a ping pong tournament', 1300, 'I am an expert playing ping pong', 'roberto1985');

INSERT INTO task (name, description, reward) VALUES
('Code a Python application', 'I need software developers', 380),
('Fix a wooden door', 'My front door is broken', 200),
('Cook a chocolate cake', 'I am hungry and I like chocolate', 80),
('Clean a hotel', 'I am expecting hosts soon', 270),
('Code a website', 'I need a website for my store', 300),
('Fix a pair of shoes', 'Its the only pair I have for work!', 130),
('Repair a meeting room', 'I already have all the materials', 2300),
('Chase a mouse', 'There is a mouse in my house!', 50),
('Design an advertising campaign', 'I want to sell my new product', 240),
('Attend a bar', 'I need a barman at my bar', 210),
('Guard a building', 'Need someone with security experience', 300),
('Pick up a package', 'I cannot travel myself', 110),
('Sell clothes', 'I need a salesman', 225),
('Fix gas pipe', 'A broken pipe is dangerous and is losing gas', 1700),
('Clean offices', 'Several offices are dirty', 700),
('Cook meals for 40 people', 'Guests must be served', 2760),
('Get me umbrellas from the 50s', 'I have a collection of umbrellas', 900);

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






