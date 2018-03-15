export class Project {

    public name: string;
    public url: string;
    public username: string;
    public password: string;
    public projectType: string;
    public org: Dto;
    public visibility: string;

}

export class Dto {
  public id: string;
  public name: string;
}