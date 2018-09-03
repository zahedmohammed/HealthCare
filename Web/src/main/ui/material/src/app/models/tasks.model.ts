export class Tasks{
    entityId: string;
    entityType: string;
    eventType: string;
    id: string;
    link: string;
    name: string;
    status: string;
    taskId: string;
    logId: string;
    org: Org = new Org();
}

export class Org{
    id: string;
    name: string;
}