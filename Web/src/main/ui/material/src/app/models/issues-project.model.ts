export class Project {
    id: string;
    public name: string;
    public description: string;
    public org: Dto = new Dto();

}

export class Dto {
  public id: string;
  public name: string;
}