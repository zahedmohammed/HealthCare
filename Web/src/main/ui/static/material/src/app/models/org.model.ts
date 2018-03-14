export class OrgUser {
    org: Org = new Org();
    orgRole: string;
}

export class Org {
  public id: string;
  public name: string;
}