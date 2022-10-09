CREATE SCHEMA IF NOT EXISTS frello;

--------------------------------------------------------------------------------
-- Users
--------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS frello.users (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),

    username varchar(32) NOT NULL UNIQUE,

    first_name varchar(256) NOT NULL,
    last_name varchar(256) NOT NULL,

    is_deleted boolean NOT NULL DEFAULT false,
    deletion_time timestamptz DEFAULT null,
    creation_time timestamptz DEFAULT now()
);

CREATE TABLE IF NOT EXISTS frello.admin_users (
    id uuid PRIMARY KEY REFERENCES frello.users(id),

    -- If an administrator looses its privileges, one shouldn't remove this row
    -- (otherwise the operation's "admin_id" foreign key would dangle). Hence
    -- this `is_enabled` key.
    is_enabled boolean NOT NULL DEFAULT true
);

CREATE TABLE IF NOT EXISTS frello.service_provider_users (
    id uuid PRIMARY KEY REFERENCES frello.users(id),

    -- C.f. `frello.admin_users.is_enabled`.
    is_enabled boolean NOT NULL DEFAULT true
);

CREATE TABLE IF NOT EXISTS frello.service_consumer_users (
    id uuid PRIMARY KEY REFERENCES frello.users(id),

    -- C.f. `frello.admin_users.is_enabled`.
    is_enabled boolean NOT NULL DEFAULT true
);

--------------------------------------------------------------------------------
-- Categories
--------------------------------------------------------------------------------

-- The `service_categories` contains categories of services available to be
-- performed in the platform.
CREATE TABLE IF NOT EXISTS frello.service_categories (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),


    name varchar(512) NOT NULL,
    description text NOT NULL,

    hex_css_color varchar(6) NOT NULL DEFAULT '000000'
);

--------------------------------------------------------------------------------
-- Service Pages and Services
--
-- A [Service Class] allows a [Service Provider User] to announce its work under
-- an specific category. Publicly, it's represented as a "service page", where
-- consumers may demonstrate interest in contracting the provider.
--
-- A [Service] is an "instance" of an specific [Service Class].
--------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS frello.service_classes (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    category_id uuid REFERENCES frello.service_categories(id),

    provider_id uuid NOT NULL REFERENCES frello.service_provider_users(id),

    title text NOT NULL,
    raw_markdown_page_body text NOT NULL,
    parsed_html_page_body text NOT NULL, -- Derived in the server.

    is_deleted boolean NOT NULL DEFAULT false,
    deletion_time timestamptz DEFAULT null,
    creation_time timestamptz DEFAULT now()
);

CREATE TYPE frello.service_state AS ENUM (
    'in_progress',
    'completed',
    'withdrawn'
);

CREATE TABLE IF NOT EXISTS frello.services (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),

    state frello.service_state NOT NULL DEFAULT 'in_progress',

    class_id uuid NOT NULL REFERENCES frello.service_classes(id),
    provider_id uuid NOT NULL REFERENCES frello.service_provider_users(id),
    consumer_id uuid NOT NULL REFERENCES frello.service_consumer_users(id),

    creation_time timestamptz DEFAULT now()
);

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

-- A [Service Class Review] represents a consumer's review over a finished (be
-- it `completed` or `withdrawn`) [Service].
CREATE TABLE IF NOT EXISTS frello.service_reviews (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),

    -- Author.
    consumer_id uuid NOT NULL REFERENCES frello.service_consumer_users(id),
    -- Corresponding [Service].
    service_id uuid NOT NULL REFERENCES frello.services(id),

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

CREATE TABLE IF NOT EXISTS frello.page_visit_logs (
    id bigserial PRIMARY KEY,
    page_id uuid NOT NULL REFERENCES frello.service_classes(id),
    user_id uuid NOT NULL REFERENCES frello.users(id)
);

CREATE TABLE IF NOT EXISTS frello.admin_logs (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    admin_id uuid NOT NULL REFERENCES frello.admin_users(id),
    log_message text NOT NULL
);
