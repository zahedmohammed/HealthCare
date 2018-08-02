export class Jobs {
    name: string;
    cron: string;
    environment: EnvDto = new EnvDto();
    tags: string[] = [];
    regions: string;
    issueTracker: Dto = new Dto();
    notifications: Noti[]=[];
    nextFire: string;
    project: any;

}

export class Dto {
    id: string;
    name: string;
    url: string;
}

export class EnvDto {
id: string;
name: string;
}

export class Noti {
    id: string;
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