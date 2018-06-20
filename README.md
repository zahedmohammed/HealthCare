
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