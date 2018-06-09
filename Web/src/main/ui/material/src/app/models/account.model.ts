export class Account {
    id: string;
    name: string;
    accessKey: string;
    secretKey: string;
    accountType: string;
    org: Org = new Org();
    prop1: string;
    prop2: string;
    prop3: string;
    allowedRegions:string[]

}

export class Org {
    id: string;
    name: string;
}