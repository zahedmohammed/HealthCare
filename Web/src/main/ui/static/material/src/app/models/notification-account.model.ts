export class NotificationAccount {

    name: string;
    accessKey: string;
    secretKey: string;
    visibility: string;
    type: string;
    token: string;
    cloudAccount: Dto = new Dto();
    channel: string;
    org: Org = new Org();

}

export class Org {
    id: string;
    name: string;
}

export class Dto {
 id: string;
 name: string;
 accountType: string;
}