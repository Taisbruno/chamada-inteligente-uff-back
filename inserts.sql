CREATE USER smart WITH SUPERUSER;


-- Inserir Usuário
INSERT INTO myuser (registration, name, email, password, type) VALUES 
('1', 'João', 'joao@example.com', 'joaospassword', 'student');

-- Inserir Classe
INSERT INTO course_class (class_code, discipline_code, discipline, teacher, semester, total) VALUES 
('code1', 'dcode1', 'Matemática', 'Professor A', '2023.1', 30);

-- Inserir Inscrição
INSERT INTO class_subscription (registration, semester, class_code) VALUES 
('1', '2023.1', 'code1');

-- Inserir Inscrição
INSERT INTO class_subscription (registration, semester, class_code) VALUES 
('2', '2023.1', 'code1');

-- Inserir Presença
INSERT INTO presence (student_registration, id_call, certificate, message, is_present, time_present) VALUES 
('1', 'call1', 'cert1', 'Estava presente', TRUE, '10:00');

-- Inserir Chamada
INSERT INTO roll (initial_date, final_date, longitude, latitude) VALUES 
('2023-01-01', '2023-01-01', '-47.875', '-15.794');
