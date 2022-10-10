INSERT INTO frello.users (username, first_name, last_name) VALUES
    ('luiz', 'Luiz Felipe', 'Gonçalves'),
    ('yuri', 'Yuri', 'Rousseff'),
    ('edu', 'Eduardo', 'Lemos'),
    ('fernando', 'Fernando', 'Araujo');

INSERT INTO frello.admin_actors (id, is_enabled) VALUES
    ((SELECT id FROM frello.users WHERE username = 'luiz'), true),
    ((SELECT id FROM frello.users WHERE username = 'yuri'), false);

INSERT INTO frello.service_provider_actors (id)
    SELECT id FROM frello.users WHERE username IN ('luiz', 'yuri', 'edu');

INSERT INTO frello.service_consumer_actors (id)
    SELECT id FROM frello.users WHERE username IN ('luiz', 'yuri', 'fernando');

INSERT INTO frello.service_categories (name, description, hex_css_color) VALUES
    ('Programação Web', 'Serviços sobre programação web', '0000ff'),
    ('Programação de Jogos', 'Serviços sobre programação de jogos', 'ff0000'),
    ('Design', 'Serviços sobre design e afins', 'ffc0cb');