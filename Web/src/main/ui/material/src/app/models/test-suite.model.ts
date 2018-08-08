    export class TestSuite {
    id: string;
    name: string;
    description: string;
    endpoint: string;
    project: Project = new Project();
    auth: string;
    type:string;
    method: string;
    inAcive:boolean;
    category:string;
    severity:string;

    assertionsText: string;
    assertions:string[];

    headersText: string;
    headers:string[];

    assertion: string[];

    tagsText: string;
    tags:string[];

    authorsText: string;
    authors:string[];

    initText: string;
    init:string[];

    cleanupText: string;
    cleanup:string[];

    testCases:TestCase[] =[];
 //   policies:Policies=new Policy();
}

    export class Policies{
     initExec:string;
      cleanupExec:string;
     logger :string;
      timeoutSeconds:number;
     repeatOnFailure:number;
     repeat:number;
      repeatModule:string;
     repeatDelay :number;
    }

    export class TestCase{
id: string;
body: string;
inactive:string;
}

export class Project {
  id: string;
  name: string;
  org: Org = new Org();
}
export class Org {
    id: string;
    name: string;
}