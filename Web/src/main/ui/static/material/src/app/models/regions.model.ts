export class Region {

    public status: string;
    public account: Account = new Account();
    public name: string;
    public key: string;
    public region: string;
    public org: Dto = new Dto();
    public visibility: string;
    public min: number;
    public max: number;
    public live: number;
    manual: boolean = false;
    public manualScript: string;


}

export class Account {
    public id: string;
    public name: string;
    public cloudType: string;
    public accessKey: string;
    public secretKey: string;
    public accountType: string;
    public allowedRegions:string[]
}

export class Dto {
  public id: string;
  public name: string;
}