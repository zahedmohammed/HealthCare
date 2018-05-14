export class Account {

    name: string;
    accessKey: string;
    secretKey: string;
    visibility: string;
    cloudType: string;
    accountType: string;
    org: Org = new Org();

}

export class Org {
    id: string;
    name: string;
}