
export class Subscription {

    skill: string;
    name: string;
    description: string;
    prop1: string;
    visibility: string;
    org: Org = new Org();

}

export class Org {
    id: string;
    name: string;
}