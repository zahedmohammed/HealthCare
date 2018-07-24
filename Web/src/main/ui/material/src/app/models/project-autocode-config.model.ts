export class AutoCodeConfig {
    databases: Database[]=[];
    logForgingPatterns: String[]=[];
    testSuites: String[]=[];


}

export class Database {
    public name: string;
    public version: string;
    public description: string;
    public inactive: boolean;
}



