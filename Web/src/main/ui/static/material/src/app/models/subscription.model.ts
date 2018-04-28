
export class Subscription {

    skill: Dto = new Dto();
    name: string;
    description: string;
    prop1: string;
    prop2: string;
    prop3: string;
    prop4: string;
    visibility: string;
    org: Dto = new Dto();
    cloudAccount: Dto = new Dto();

}

export class Dto {
    id: string;
    name: string;
    accountType: string;
}