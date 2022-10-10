#!/usr/bin/env bash

if ! echo 'select 1;' | psql &> /dev/null; then
    >&2 echo "psql is not running properly"
    exit 1
fi

has_frello="SELECT count(*) FROM information_schema.schemata
                WHERE schema_name = 'frello';"

if [ $(echo "$has_frello" | psql --quiet -A -t 2> /dev/null) = "1" ]; then
    echo "Reseting schema..."
    echo 'DROP SCHEMA frello CASCADE;' | psql &> /dev/null
    echo "  done!"
fi

echo "Creating schema..."
psql -f scripts/frello.sql &> /dev/null
echo "  done!"

echo "Seeding..."
psql -f scripts/seed.sql &> /dev/null
echo "  done!"
