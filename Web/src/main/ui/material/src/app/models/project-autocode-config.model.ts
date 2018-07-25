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
    database: Databases[] = [];
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

export class Databases{
    name: string;
    version: number;
}