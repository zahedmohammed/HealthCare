export class Jobs {
    name: string;
    cron: string;
    environment: string;
    tags: string[] = [];
    regions: string;
    issueTracker: Dto = new Dto();
    notifications: Noti[]=[];
    nextFire: string;
    project: any;

}

export class Dto {
    name: string;
}

export class Noti {
    name: string;
    to: string;
    channel: string;
    account: string;
}

export class Cron {
  exp: string;
  desc: string;

  constructor(e: string, d: string) {
    this.exp = e;
    this.desc = d;
  }
}