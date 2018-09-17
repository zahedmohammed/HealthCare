export class AutoCodeConfig {
    genPolicy: string;
    openAPISpec: string;
    generators: Categories[]= [];
}

export class Categories {
    type: string;
    assertions: string[]= [];
    assertionsText: string;
    severity: string;
    inactive: boolean;
    displayHeaderLabel: string;
    displayHeaderDescription: string;
    assertionDescription: string;
    postfix: string;
    match: Matches[] = [];
    database: Database = new Database();
}

export class Matches{
    name: string;
    pathPatterns: string;
    methods: string;
    denyRoles: string;
    allowRoles: string;
    sample: string;
    queryParams: string;
    bodyProperties: string;
}

export class Database{
    name: string;
    version: number;
}