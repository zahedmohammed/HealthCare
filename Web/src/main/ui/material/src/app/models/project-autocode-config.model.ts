export class AutoCodeConfig {
    openAPISpec: string;
    generators: Categories[]= [];
}

export class Categories {
    type: string;
    assertions: string[]= [];
    severity: string;
    inactive: boolean;
    postfix: string;
    match: Matches[] = [];
    database: Database;
}

export class Matches{
    name: string;
    pathPatterns: string;
    methods: string;
    denyRoles: string;
    sample: string;
    queryParams: string;
    bodyProperties: string;
}

export class Database{
    name: string;
    version: number;
}