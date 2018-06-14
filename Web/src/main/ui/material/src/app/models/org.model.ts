export class OrgUser {
    org: Org = new Org();
    orgRole: string;
    status: string;
    users: Dto = new Dto();
}

export class Org {
  public id: string;
  public name: string;
  public company: string;
  public createdDate: string;
  public billingEmail: string;
  public orgType: string;
}

export class Dto {
  public id: string;
  public name: string;
  public username: string;
  public email: string;
}

export class Member {
  public orgId: string;
  public name: string;
  public username: string;
  public password: string;
  public confirmPassword: string;
  public email: string;
  public orgRole: string;
}