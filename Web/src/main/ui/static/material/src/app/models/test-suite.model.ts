export class TestSuite {

    id: string;
    name: string;
    type: string;
    description: string;
    visibility: string;
    project: Project = new Project();

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