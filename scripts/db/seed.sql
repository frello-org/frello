INSERT INTO frello.users (username, email, first_name, last_name, password_hash) VALUES
    ('luiz', 'luiz@luizfelipe.dev', 'Luiz Felipe', 'Gonçalves', '$argon2i$v=19$m=8192,t=10,p=1$e5fRnYjHNGchOoW0j6IqDw$6uldhp9jQAEEHBTiAtzG0kjxp6kq4LLLqMXaQ4+rZKI'),
    ('yuri', 'a@frello.com', 'Yuri', 'Rousseff',                '$argon2i$v=19$m=8192,t=10,p=1$e5fRnYjHNGchOoW0j6IqDw$6uldhp9jQAEEHBTiAtzG0kjxp6kq4LLLqMXaQ4+rZKI'),
    ('edu', 'b@frello.com', 'Eduardo', 'Lemos',                 '$argon2i$v=19$m=8192,t=10,p=1$e5fRnYjHNGchOoW0j6IqDw$6uldhp9jQAEEHBTiAtzG0kjxp6kq4LLLqMXaQ4+rZKI'),
    ('fernando', 'c@frello.com', 'Fernando', 'Araujo',          '$argon2i$v=19$m=8192,t=10,p=1$e5fRnYjHNGchOoW0j6IqDw$6uldhp9jQAEEHBTiAtzG0kjxp6kq4LLLqMXaQ4+rZKI');

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
