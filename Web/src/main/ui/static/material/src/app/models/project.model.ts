export class Project {

    public name: string;
    public url: string;
    public credsRequired: boolean = false;
    public cloudAccount: CloudAccount = new CloudAccount();
    public username: string;
    public password: string;
    public projectType: string;
    public org: Dto = new Dto();
    public visibility: string;
    public genPolicy: string;
    public openAPISpec: string;

}

export class CloudAccount {
    public name: string;
    public cloudType: string;
    public accessKey: string;
    public secretKey: string;
    public accountType: string;
}


export class Dto {
  public id: string;
  public name: string;
}