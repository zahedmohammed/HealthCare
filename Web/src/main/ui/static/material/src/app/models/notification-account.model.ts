export class NotificationAccount {

    name: string;
    accessKey: string;
    secretKey: string;
    visibility: string;
    type: string;
    token: string;
    channel: string;
    org: Org = new Org();

}

export class Org {
    id: string;
    name: string;
}