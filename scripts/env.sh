#!/usr/bin/env bash

export FRELLO_ENV=development

export PGHOST=localhost
export PGPORT=5432
PGUSER=$(whoami)
export PGUSER
export PGPASSWORD=postgres
export PGDATABASE=local-postgres

export JWT_SECRET="development-secret::super-secret-token"

export ARGON2_ITERATIONS=10
export ARGON2_MEMORY=8192
