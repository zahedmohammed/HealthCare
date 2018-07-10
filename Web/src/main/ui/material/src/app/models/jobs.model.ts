export class Jobs {
    name: string;
    environment: string;
    tags: string;
    regions: string;
    issueTracker: Dto = new Dto();
    nextFire: string;
}

export class Dto {
    name: string;
}