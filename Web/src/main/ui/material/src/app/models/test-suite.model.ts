    export class TestSuite {
    i: string;
    name: string;
    type1: string;
    description: string;
endpoint: string;
    project: Project = new Project();
    auth: string;
type:string;
method: string;
testCases:TestCase =new TestCase();
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