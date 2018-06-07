export class Vault {
    id: string;
    key: string;
    val: string;
    description: string;
    org: Org = new Org();

}

export class Org {
    id: string;
    name: string;
}