INSERT INTO frello.users (username, first_name, last_name) VALUES
    ('luiz', 'Luiz Felipe', 'Gonçalves'),
    ('yuri', 'Yuri', 'Rousseff'),
    ('edu', 'Eduardo', 'Lemos'),
    ('fernando', 'Fernando', 'Araujo');

INSERT INTO frello.admin_users (id)
    SELECT id FROM frello.users WHERE username IN ('luiz', 'yuri');

INSERT INTO frello.service_provider_users (id)
    SELECT id FROM frello.users WHERE username IN ('luiz', 'yuri', 'edu');

INSERT INTO frello.service_consumer_users (id)
    SELECT id FROM frello.users WHERE username IN ('luiz', 'yuri', 'fernando');

INSERT INTO frello.service_categories (name, description, hex_css_color) VALUES
    ('Programação Web', 'Serviços sobre programação web', '0000ff'),
    ('Programação de Jogos', 'Serviços sobre programação de jogos', 'ff0000'),
    ('Design', 'Serviços sobre design e afins', 'ffc0cb');
