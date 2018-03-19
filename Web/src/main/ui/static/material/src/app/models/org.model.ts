export class OrgUser {
    org: Org = new Org();
    orgRole: string;
}

export class Org {
  public id: string;
  public name: string;
  public company: string;
  public createdDate: string;
  public billingEmail: string;
  public orgType: string;
}