package com.fxlabs.fxt.sdk.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Auth implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name = "Default";
    private AuthType authType = AuthType.BasicAuth;
    private String username;
    private String password;

    // OAuth 2.0 properties

    private String clientId;
    private String clientSecret;

    private String id;
    private String accessTokenUri;

    private AuthenticationScheme authorizationScheme;  //[form,header,none,query]

    private AuthenticationScheme clientAuthenticationScheme; //[form,header,none,query]

    private String tokenName;
    private String scope;
    private GrantType grantType;

    private String preEstablishedRedirectUri;
    private Boolean useCurrentUri;
    private String userAuthorizationUri;

}
