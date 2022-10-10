#!/usr/bin/env bash

export DBROOT="$(pwd)/.postgresql"
export PGHOST=localhost
export PGPORT=5432
export PGDATA="$DBROOT/data"
export PGDATABASE=local-postgres
export PGUSER=$(whoami)
export PGPASSWORD=postgres
export LOG_PATH="$DBROOT/log"
if [ ! -d "$DBROOT" ]; then
    mkdir -p "$DBROOT"
fi

info() {
    echo "[info] " $@
}

init_db() {
    if pg_ctl status; then
        info "database already initialized, skipping."
        return
    fi

    info "initializing the database..."
    pg_ctl init

    info "starting the database..."
    pg_ctl start
}

init_db
