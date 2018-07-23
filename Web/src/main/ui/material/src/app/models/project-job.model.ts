export class Job {
    name: string;
    cron: string;
    description: string;
    environment: string;
    tags: string[] = [];
    regions: string;
    issueTracker: Dto = new Dto();
    notifications: Noti[]=[];
    nextFire: string;
    project: ProjectMinimalDto = new ProjectMinimalDto();
}

export class Dto {
    id: string;
    name: string;
}

export class Noti {
    id: string
    name: string;
    channel: string;
    account: string;

}

export class ProjectMinimalDto {
    name: string;
    id: string;
    org: Org = new Org();
}

export class Org {
    id: string;
}

