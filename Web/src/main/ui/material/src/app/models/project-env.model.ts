export class Env {
    projectId: string;
    name: string;
    baseUrl: string;
    auths: Auth[]=[];

}

export class Auth {
    username: string;
    password: string;
    name: string;
    authType: string;

}



