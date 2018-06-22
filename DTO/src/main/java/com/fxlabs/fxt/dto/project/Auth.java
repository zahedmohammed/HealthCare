package com.fxlabs.fxt.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Auth implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    private AuthType authType; // login authType
    private String username;
    private String password;

    private String header_1;
    private String header_2;
    private String header_3;

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

