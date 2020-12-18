#!/bin/bash
BASEDIR=$(dirname $0)
DATABASE=final_capstone
psql -U postgres -f "$BASEDIR/dropdb.sql" &&
createdb -U postgres $DATABASE &&
cat "$BASEDIR/schema.sql" "$BASEDIR/user.sql" | psql -U postgres -d $DATABASE -f -
