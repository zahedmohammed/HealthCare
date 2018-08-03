export class Env {
    id: string;
    projectId: string;
    name: string;
    refId: string;
    baseUrl: string;
    auths: Auth[]=[];

}

export class Auth {
    name: string;
    authType: string;

    username: string;
    password: string;

    header_1: string;
    header_2: string;
    header_3: string;

    // OAuth 2.0 properties
    clientId: string;
    clientSecret: string;
    id: string;
    accessTokenUri: string;

    authorizationScheme: string;

    clientAuthenticationScheme: string;

    tokenName: string;
    scope: string;
    grantType: string;

    preEstablishedRedirectUri: string;
    useCurrentUri: string;
    userAuthorizationUri: string;

}



