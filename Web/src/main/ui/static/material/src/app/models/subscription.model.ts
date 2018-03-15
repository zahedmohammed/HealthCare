
export class Subscription {

    skill: Dto = new Dto();
    name: string;
    description: string;
    prop1: string;
    visibility: string;
    org: Dto = new Dto();

}

export class Dto {
    id: string;
    name: string;
}