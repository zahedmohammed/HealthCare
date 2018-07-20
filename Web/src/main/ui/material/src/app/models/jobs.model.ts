export class Jobs {
    name: string;
    cron: string;
    environment: string;
    tags: string;
    regions: string;
    issueTracker: Dto = new Dto();
    notifications: Noti[]=[];
    nextFire: string;
}

export class Dto {
    name: string;
}

export class Noti {

    name: string;
    channel: string;
    account: string;

}