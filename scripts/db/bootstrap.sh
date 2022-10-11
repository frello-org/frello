#!/usr/bin/env bash

export DBROOT="$(pwd)/.postgresql"
export PGDATA="$DBROOT/data"
export LOG_PATH="$DBROOT/log"

if [ ! -d "$DBROOT" ]; then
    mkdir -p "$DBROOT"
fi

info() {
    echo "[info]" $@
}

if [ ! -d "$PGDATA" ]; then
    info "initializing the database..."
    pg_ctl init
fi

if ! pg_ctl status; then
    info "the database server is not running, run \`pg_ctl start\` to start it"
fi
