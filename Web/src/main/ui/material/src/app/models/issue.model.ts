export class Issue {

    public project: Project = new Project;

    public name: string;

    public parent: string;

    public description: string;

    public auth: string;

    public endpoint: string;

    public method: string;

    public headers = new Array<String>();

    public headersText: string;

    public requestBody: string;

    public responseBody: string;

    public responseHeaders: string;

    public assertions: string;

    public statusCode: string;

    public failedAssertions: string;

    public result: string;
    
    public env: string;
}
export class Project {
    id: string;
    name: string;
}