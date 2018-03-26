export class CloudAccount {

    name: string;
    accessKey: string;
    secretKey: string;
    visibility: string;
    cloudType: string;
    org: Org = new Org();

}

export class Org {
    id: string;
    name: string;
}