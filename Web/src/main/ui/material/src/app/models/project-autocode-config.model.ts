export class AutoCodeConfig {
    databases: Database[]=[];
    logForgingPatterns: String[]=[];
    testSuites: TestSuite[]=[];
    propertyMapping : Map<string, number>;
}

export class Database {
    public name: string;
    public version: string;
    public description: string;
    public inactive: boolean;
}   

export class TestSuite {
    project: ProjectMinimalDto = new ProjectMinimalDto();
    public name: string;
    public description:string;
    public type: string;
    public endpoint: string;
    public method: string;
    public auth: string;
    headers: string[] = [];
    testCases: TestCase[]= [];
    assertions: string[]= [];
    tags: string[]= [];
    authors: string[] = [];
    init: string[] = [];
    cleanup: string[]= [];
    policy: Policies = new Policies();
    public publishToMarketplace: boolean;
    props : Map<string, string> = new Map<string, string>();
    public category: string;
    public severity: string;
}

export  class  ProjectMinimalDto {
    public name: string;
    public org: string;
}

export class TestCase {
    public id: number;
    public body: string;
    public inactive: boolean;
}

export class Policies {
    public initExec: string;
    public cleanupExec: string;
    public logger: string;
    public timeoutSeconds: number;
    public repeatOnFailure: number;
    public repeat: number;
    public repeatModule: string;
    public repeatDelay: number;
}