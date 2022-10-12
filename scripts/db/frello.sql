CREATE SCHEMA IF NOT EXISTS frello;

--------------------------------------------------------------------------------
-- Users
--------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS frello.users (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),

    username varchar(32) NOT NULL UNIQUE,
    password_hash text NOT NULL,
    email text NOT NULL UNIQUE,

    first_name varchar(256) NOT NULL,
    last_name varchar(256) NOT NULL,

    is_deleted boolean NOT NULL DEFAULT false,
    deletion_time timestamptz DEFAULT null,
    creation_time timestamptz DEFAULT now()
);

CREATE TABLE IF NOT EXISTS frello.admin_actors (
    id uuid PRIMARY KEY REFERENCES frello.users(id),

    -- If an administrator looses its privileges, one shouldn't remove this row
    -- (otherwise the operation's "admin_id" foreign key would dangle). Hence
    -- this `is_enabled` key.
    is_enabled boolean NOT NULL DEFAULT true
);

CREATE TABLE IF NOT EXISTS frello.service_provider_actors (
    id uuid PRIMARY KEY REFERENCES frello.users(id),

    -- C.f. `frello.admin_actors.is_enabled`.
    is_enabled boolean NOT NULL DEFAULT true
);

CREATE TABLE IF NOT EXISTS frello.service_consumer_actors (
    id uuid PRIMARY KEY REFERENCES frello.users(id),

    -- C.f. `frello.admin_actors.is_enabled`.
    is_enabled boolean NOT NULL DEFAULT true
);

--------------------------------------------------------------------------------
-- Services
--------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS frello.service_requests (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),

    -- Author ID.
    consumer_id uuid NOT NULL REFERENCES frello.service_consumer_actors(id),

    expected_price DECIMAL(20, 6) NOT NULL,
    title text NOT NULL,
    raw_markdown_page_body text NOT NULL,
    parsed_html_page_body text NOT NULL, -- Derived in the server.

    is_deleted boolean NOT NULL DEFAULT false,
    deletion_time timestamptz DEFAULT null,
    creation_time timestamptz DEFAULT now()
);

CREATE TYPE frello.service_state AS ENUM (
    'IN_PROGRESS',
    'COMPLETED',
    'WITHDRAWN'
);

CREATE TABLE IF NOT EXISTS frello.services (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),

    state frello.service_state NOT NULL DEFAULT 'IN_PROGRESS',

    request_id uuid NOT NULL REFERENCES frello.service_requests(id),
    provider_id uuid NOT NULL REFERENCES frello.service_provider_actors(id),
    consumer_id uuid NOT NULL REFERENCES frello.service_consumer_actors(id),

    creation_time timestamptz DEFAULT now()
);

-- The `service_categories` contains categories of services available to be
-- performed in the platform.
CREATE TABLE IF NOT EXISTS frello.service_categories (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),

    name varchar(512) NOT NULL,
    description text NOT NULL,

    hex_css_color varchar(6) NOT NULL DEFAULT '000000'
);

-- N:N mapping from "service_categories" to "service_requests".
CREATE TABLE IF NOT EXISTS frello.service_request_category (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    category_id uuid REFERENCES frello.service_categories(id),
    service_request_id uuid REFERENCES frello.service_requests(id),
    UNIQUE (category_id, service_request_id)
);

-- NOT YET IMPLEMENTED.
--
-- A [Service Message] represents a conversation visible within a [Service]
-- page. A message can not be updated or deleted.
CREATE TABLE IF NOT EXISTS frello.service_messages (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    service_id uuid NOT NULL REFERENCES frello.services(id),
    author_id uuid NOT NULL REFERENCES frello.users(id),

    raw_markdown_body text NOT NULL,
    parsed_html_body text NOT NULL, -- Derived in the server.

    creation_time timestamptz DEFAULT now()
);

-- NOT YET IMPLEMENTED.
--
-- A [Service Class Review] represents a consumer's review over a finished (be
-- it `completed` or `withdrawn`) [Service].
CREATE TABLE IF NOT EXISTS frello.service_reviews (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),

    -- Corresponding [Service].
    service_id uuid NOT NULL REFERENCES frello.services(id),
    -- Author.
    consumer_id uuid NOT NULL REFERENCES frello.service_consumer_actors(id),

    -- Review score (from 1 to 5) "stars".
    review_score smallint NOT NULL,
    CONSTRAINT valid_review_score
        CHECK (1 <= review_score AND review_score <= 1),

    raw_markdown_body text NOT NULL,
    parsed_html_body text NOT NULL, -- Derived in the server.

    is_deleted boolean NOT NULL DEFAULT false,
    deletion_time timestamptz DEFAULT null,
    creation_time timestamptz DEFAULT now()
);

--------------------------------------------------------------------------------
-- Misc
--------------------------------------------------------------------------------

-- NOT YET IMPLEMENTED.
CREATE TABLE IF NOT EXISTS frello.page_visit_logs (
    id bigserial PRIMARY KEY,
    page_id uuid NOT NULL REFERENCES frello.service_requests(id),
    user_id uuid NOT NULL REFERENCES frello.users(id),
    creation_time timestamptz DEFAULT now()
);

-- NOT YET IMPLEMENTED.
CREATE TABLE IF NOT EXISTS frello.admin_logs (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    admin_id uuid NOT NULL REFERENCES frello.admin_actors(id),
    log_message text NOT NULL,
    creation_time timestamptz DEFAULT now()
);
