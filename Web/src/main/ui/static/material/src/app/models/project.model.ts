export class Project {
    id: string;
    public name: string;
    public description: string;
    public url: string;
    public branch: string;
    public credsRequired: boolean = false;
    public cloudAccount: CloudAccount = new CloudAccount();
    public lastCommit: string;
    public lastSync: string;
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