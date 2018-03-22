export class Region {

    public status: string;
    public cloudType: string;
    public name: string;
    public key: string;
    public region: string;
    public org: Dto = new Dto();
    public visibility: string;

}

export class Dto {
  public id: string;
  public name: string;
}