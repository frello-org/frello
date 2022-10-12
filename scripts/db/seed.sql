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
    ('APIs', 'Serviços que envolvem APIs web', '00ff00'),
    ('Discord', 'Serviços que envolvem as APIs do Discord', '5865f2'),
    ('Programação Web', 'Serviços sobre programação web', '0000ff'),
    ('Programação de Jogos', 'Serviços sobre programação de jogos', 'ff0000'),
    ('Design', 'Serviços sobre design e afins', 'ffc0cb');

INSERT INTO frello.service_requests
    (consumer_id, expected_price, title, raw_markdown_page_body, parsed_html_page_body)
VALUES
    (
        (SELECT id FROM frello.users WHERE username = 'luiz'),
        50.00,
        'Bot para um Discord server',
        '**Foo** bar _baz_',
        '<strong>Foo</strong> bar <em>baz</em>'
    ),
    (
        (SELECT id FROM frello.users WHERE username = 'fernando'),
        250.245,
        'Site para Minecraft',
        'Wow (site para Minecraft). Description here.',
        'Wow (site para Minecraft). Description here.'
    );

INSERT INTO frello.services
    (state, request_id, provider_id, consumer_id)
VALUES
    (
        'IN_PROGRESS'::frello.service_state,
        (SELECT id FROM frello.service_requests WHERE expected_price = 50.00 LIMIT 1),
        (SELECT id FROM frello.users WHERE username = 'yuri'),
        (SELECT id FROM frello.users WHERE username = 'luiz')
    );

INSERT INTO frello.service_request_category (service_request_id, category_id)
VALUES
    (
        (SELECT id FROM frello.service_requests WHERE expected_price = 50.00 LIMIT 1),
        (SELECT id FROM frello.service_categories WHERE name = 'APIs' LIMIT 1)
    ),
    (
        (SELECT id FROM frello.service_requests WHERE expected_price = 50.00 LIMIT 1),
        (SELECT id FROM frello.service_categories WHERE name = 'Discord' LIMIT 1)
    ),
    (
        (SELECT id FROM frello.service_requests WHERE expected_price = 250.245 LIMIT 1),
        (SELECT id FROM frello.service_categories WHERE name = 'Programação Web' LIMIT 1)
    );
