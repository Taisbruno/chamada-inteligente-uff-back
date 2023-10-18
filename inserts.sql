-- Creating superuser in the database.
CREATE USER smart WITH SUPERUSER PASSWORD 'smart2552'; 

-- Inserting User
INSERT INTO myuser (registration, name, cpf, email, password, type) VALUES 
('1', 'João', '1111', 'joao@example.com', 'joaospassword', 'student'),
('2', 'Natalia', '2222', 'natalia@example.com', 'nataliapassword', 'student'),
('3', 'Hugo', '3333', 'hugo@example.com', 'hugopassword', 'student');

-- Inserting Course Classes
INSERT INTO course_class (class_code, discipline_code, discipline, teacher, semester, total) VALUES 
('code1', 'dcode1', 'Matemática', 'Professor A', '2023.1', 30),
('code2', 'dcode2', 'Física', 'Professor B', '2023.1', 30);

-- Inserting Class Subscription
INSERT INTO class_subscription (registration, semester, class_code) VALUES 
('1', '2023.1', 'code1'),
('1', '2023.1', 'code2');

-- Inserting Presence
INSERT INTO presence (student_registration, id_call, certificate, message, is_present, time_present) VALUES 
('1', 'call1', 'cert1', 'Estava presente', TRUE, '10:00');

-- Inserting Roll
INSERT INTO roll (longitude, latitude, class_code, created_at) VALUES 
('-47.875', '-15.794', 'code1', NOW());

-- Inserting Class Schedule (sample data for demonstration)
INSERT INTO class_schedule (class_code, day_of_week, start_time, end_time) VALUES
('code1', 1, '08:00:00', '10:00:00'),
('code2', 2, '10:00:00', '12:00:00');

