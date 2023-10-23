-- Verificar se o banco de dados "smart_rolls" existe
DO $$ 
BEGIN 
    IF NOT EXISTS (
        SELECT 1 FROM pg_database WHERE datname = 'smart_rolls'
    ) THEN 
        -- Se não existir, criar o banco de dados
        CREATE DATABASE smart_rolls;
    END IF; 
END 
$$;

-- Usar o banco de dados recém-criado
\c smart_rolls;

-- Verificar se o usuário "smart" existe
DO $$ 
BEGIN 
    IF NOT EXISTS (
        -- Se não existir, criar o usuário
        SELECT FROM pg_catalog.pg_user WHERE usename = 'smart'
    ) THEN 
        CREATE USER smart WITH PASSWORD 'smart2552';
    END IF; 
END 
$$;

-- Inserting User
INSERT INTO myuser (registration, name, cpf, email, password, type) VALUES 
('1', 'João da Silva', '1111', 'joao@example.com', 'joaospassword', 'student'),
('2', 'Natalia Rabelo', '2222', 'natalia@example.com', 'nataliapassword', 'student'),
('3', 'Hugo Bianquini', '3333', 'hugo@example.com', 'hugopassword', 'student'),
('4', 'Tais Bruno', '4444', 'tais@example.com', 'taispassword', 'student'),
('5', 'Andre Balbi', '5555', 'andre@example.com', 'andrepassword', 'student'),
('6', 'Adriano', '6666', 'adriano@example.com', 'adrianopassword', 'student'),
('7', 'Pedro Xavier', '7777', 'pedro@example.com', 'pedropassword', 'student'),
('8', 'Beatriz de Souza Matos', '8888', 'beatriz@uff.com', 'beatrizpassword', 'teacher');



-- Inserting Course Classes
INSERT INTO course_class (class_code, discipline_code, discipline, teacher, semester, total) VALUES 
('1', 'dcode1', 'Qualidade e Teste de Software', 'Beatriz de Souza Matos', '2023.1', 30),
('2', 'dcode2', 'Gerencia de Projeto e Manutenção de Software', 'Beatriz de Souza Matos', '2023.1', 30),
('3', 'dcode3', 'Programação de Computadores I', 'Beatriz de Souza Matos', '2023.1', 30),
('4', 'dcode4', 'Programação de Computadores II', 'Patrick Neves', '2023.1', 30),
('5', 'dcode5', 'Banco de Dados I', 'Daniel Oliveira', '2023.1', 30),
('6', 'dcode6', 'Computação e Sociedade', 'Marcos Vinicius Lima', '2023.1', 30);

-- Inserting Class Subscription
INSERT INTO class_subscription (registration, semester, class_code) VALUES 
('1', '2023.1', '1'),
('1', '2023.1', '2'),
('1', '2023.1', '3'),
('1', '2023.1', '5'),
('1', '2023.1', '6'),
('3', '2023.1', '1'),
('3', '2023.1', '2'),
('3', '2023.1', '3'),
('3', '2023.1', '5'),
('3', '2023.1', '6'),
('8', '2023.1', '1'),
('8', '2023.1', '2'),
('8', '2023.1', '3'),
('4', '2023.1', '1'),
('4', '2023.1', '2'),
('4', '2023.1', '3'),
('4', '2023.1', '5'),
('4', '2023.1', '6'),
('5', '2023.1', '1'),
('5', '2023.1', '2'),
('5', '2023.1', '3'),
('5', '2023.1', '5'),
('5', '2023.1', '6'),
('6', '2023.1', '1'),
('6', '2023.1', '2'),
('6', '2023.1', '3'),
('6', '2023.1', '5'),
('6', '2023.1', '6');


WITH inserted_roll AS (
    INSERT INTO roll (longitude, latitude, class_code, created_at)
    VALUES ('-47.875', '-15.794', '1', NOW())
    RETURNING id
)

INSERT INTO presence (student_registration, roll_id, certificate, message, is_present, time_present)
SELECT '1', id, 'cert1', 'Estava presente', TRUE, '10:00'
FROM inserted_roll;


-- Inserting Class Schedule (sample data for demonstration)
INSERT INTO class_schedule (class_code, day_of_week, start_time, end_time) VALUES
('1', 1, '08:00:00', '10:00:00'),
('2', 2, '10:00:00', '12:00:00'),
('3', 3, '18:00:00', '20:00:00');


