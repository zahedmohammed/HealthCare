## application.properties changes
# Make sure to have ES and PG accessible on the following hosts and credentials or change them according to your setup.
ELASTICSEARCH_HOST=fx-elasticsearch
POSTGRES_HOST=fx-postgres
POSTGRES_DB=fx_issues
POSTGRES_USER=fx_issues_admin
POSTGRES_PASSWORD=fx_issues_password

## Start server
./gradlew w:bootrun

## Build Dev
./gradlew w:buildAngularDev

## Build prod
./gradlew w:buildAngular

## Start proxy
cd Web/src/main/ui/material
npm start
ng build
ng build --prod --build-optimizer


## Login to remote server
http://localhost:4200/assets/html/access.html
## Then open url
http://localhost:4200
## At this point you should be able to see proxy working.

## ng commands
# https://github.com/angular/angular-cli/wiki/generate
cd Web/src/main/ui/material

# ng generate component e.g.
ng g c components/dialogs/adv-run

# Theme
http://preview.themeforest.net/item/material-design-angular-2-admin-web-app-with-bootstrap-4/full_screen_preview/19421267?_ga=2.119807680.997196640.1528418155-977591499.1527797469

# Geneate Pipe
ng generate pipe pipes/MSToDuration