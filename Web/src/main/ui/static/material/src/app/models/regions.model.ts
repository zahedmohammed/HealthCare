export class Region {

    public status: string;
    public cloudAccount: CloudAccount;
    public name: string;
    public key: string;
    public region: string;
    public org: Dto = new Dto();
    public visibility: string;
    public min: number;
    public max: number;

}

export class CloudAccount {
    public name: string;
    public cloudType: string;
    public accessKey: string;
    public secretKey: string;
}

export class Dto {
  public id: string;
  public name: string;
}