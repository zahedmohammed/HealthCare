export class Region {

    public status: string;
    public cloudAccount: string;
    public cloudType: string;
    public name: string;
    public key: string;
    public region: string;
    public org: Dto = new Dto();
    public visibility: string;
    public min: number;
    public max: number;

}

export class Dto {
  public id: string;
  public name: string;
}