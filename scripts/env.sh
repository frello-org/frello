#!/usr/bin/env bash

export FRELLO_ENV=development

export PGHOST=localhost
export PGPORT=5432
export PGUSER=$(whoami)
export PGPASSWORD=postgres
export PGDATABASE=local-postgres

export ARGON2_ITERATIONS=10
export ARGON2_MEMORY=8192
