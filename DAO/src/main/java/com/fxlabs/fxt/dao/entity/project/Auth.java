package com.fxlabs.fxt.dao.entity.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Auth {

    private String name;

    @Enumerated(EnumType.STRING)
    private AuthType authType; // login authType
    private String username;
    private String password;

    // OAuth 2.0 properties

    private String clientId;
    private String clientSecret;

    private String id;
    private String accessTokenUri;

    @Enumerated(EnumType.STRING)
    private AuthenticationScheme authorizationScheme;  //[form,header,none,query]

    @Enumerated(EnumType.STRING)
    private AuthenticationScheme clientAuthenticationScheme; //[form,header,none,query]

    private String tokenName;
    private String scope;

    @Enumerated(EnumType.STRING)
    private GrantType grantType;

    private String preEstablishedRedirectUri;
    private Boolean useCurrentUri;
    private String userAuthorizationUri;

}

