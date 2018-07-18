package com.fxlabs.fxt.sdk.rest;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.*;
import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.dto.run.Suite;
import com.fxlabs.fxt.dto.run.TestSuiteResponse;
import com.fxlabs.fxt.dto.users.OrgUsers;
import com.fxlabs.fxt.dto.users.Users;
import com.fxlabs.fxt.sdk.services.CredUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
public class GenericRestRespository<T> {

    public final static HashMap<Class, ParameterizedTypeReference> paramTypeRefMap = new HashMap<>();

    static {
        paramTypeRefMap.put(Users.class, new ParameterizedTypeReference<Response<Users>>() {
        });
        paramTypeRefMap.put(OrgUsers.class, new ParameterizedTypeReference<Response<OrgUsers>>() {
        });
        paramTypeRefMap.put(Project.class, new ParameterizedTypeReference<Response<Project>>() {
        });
        paramTypeRefMap.put(Environment.class, new ParameterizedTypeReference<Response<Environment>>() {
        });
        paramTypeRefMap.put(TestSuite.class, new ParameterizedTypeReference<Response<TestSuite>>() {
        });
        paramTypeRefMap.put(Job.class, new ParameterizedTypeReference<Response<Job>>() {
        });
        paramTypeRefMap.put(DataRecord.class, new ParameterizedTypeReference<Response<DataRecord>>() {
        });
        paramTypeRefMap.put(DataSet.class, new ParameterizedTypeReference<Response<DataSet>>() {
        });


        paramTypeRefMap.put(Run.class, new ParameterizedTypeReference<Response<Run>>() {
        });
        paramTypeRefMap.put(Boolean.class, new ParameterizedTypeReference<Response<Boolean>>() {
        });

        paramTypeRefMap.put(Project[].class, new ParameterizedTypeReference<Response<List<Project>>>() {
        });
        paramTypeRefMap.put(Environment[].class, new ParameterizedTypeReference<Response<List<Environment>>>() {
        });
        paramTypeRefMap.put(TestSuite[].class, new ParameterizedTypeReference<Response<List<TestSuite>>>() {
        });
        paramTypeRefMap.put(DataSet[].class, new ParameterizedTypeReference<Response<List<DataSet>>>() {
        });
        paramTypeRefMap.put(DataRecord[].class, new ParameterizedTypeReference<Response<List<DataRecord>>>() {
        });
        paramTypeRefMap.put(Job[].class, new ParameterizedTypeReference<Response<List<Job>>>() {
        });
        paramTypeRefMap.put(Run[].class, new ParameterizedTypeReference<Response<List<Run>>>() {
        });
        paramTypeRefMap.put(TestSuiteResponse[].class, new ParameterizedTypeReference<Response<List<TestSuiteResponse>>>() {
        });

        paramTypeRefMap.put(Suite[].class, new ParameterizedTypeReference<Response<List<Suite>>>() {
        });
        paramTypeRefMap.put(ProjectFile[].class, new ParameterizedTypeReference<Response<List<ProjectFile>>>() {
        });
    }

    final Logger logger = LoggerFactory.getLogger(getClass());


    //protected HttpHeaders httpHeaders;
    protected ParameterizedTypeReference reference;
    protected ParameterizedTypeReference referenceList;


    public GenericRestRespository(ParameterizedTypeReference reference, ParameterizedTypeReference referenceList) {
        this.reference = reference;
        this.referenceList = referenceList;
    }

    public Response<T> save(T t) {

        HttpEntity<T> request = new HttpEntity<>(t, this.getHeaders());

        ResponseEntity<Response<T>> response = restTemplate.exchange(getUrl(), HttpMethod.POST, request, reference);

        //logger.info(response.getBody());
        return response.getBody();

    }

    public Response<T> update(T t) {

        HttpEntity<T> request = new HttpEntity<>(t, this.getHeaders());

        ResponseEntity<Response<T>> response = restTemplate.exchange(getUrl(), HttpMethod.PUT, request, reference);

        //logger.info(response.getBody());
        return response.getBody();

    }

    public Response<List<T>> saveAll(List<T> t) {

        HttpEntity<List<T>> request = new HttpEntity<>(t, this.getHeaders());

        String url = getUrl();

        ResponseEntity<Response<List<T>>> response = restTemplate.exchange(url + "/batch", HttpMethod.POST, request, referenceList);

        //logger.info(response.getBody());
        return response.getBody();

    }

    public Response<List<T>> findAll() {

        HttpEntity<Void> request = new HttpEntity<>(this.getHeaders());

        ResponseEntity<Response<List<T>>> response = restTemplate.exchange(getUrl(), HttpMethod.GET, request, referenceList);

        //logger.info(response.getBody());
        return response.getBody();

    }


    public Response<T> findById(String id) {

        HttpEntity<Void> request = new HttpEntity<>(this.getHeaders());

        ResponseEntity<Response<T>> response = restTemplate.exchange(getUrl() + "/instance/" + id, HttpMethod.GET, request, reference);

        //logger.info(response.getBody());
        return response.getBody();

    }

    protected HttpHeaders getHttpHeaders(String u, String p) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Accept", "application/json");
        httpHeaders.set("Authorization", createBasicAuth(u, p));
        return httpHeaders;
    }

    protected String createBasicAuth(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }

    protected String getUrl() {
        return CredUtils.url.get();
    }

    protected String getUsername() {
        return CredUtils.username.get();
    }

    protected String getPassword() {
        return CredUtils.password.get();
    }

    protected HttpHeaders getHeaders() {
        return getHttpHeaders(getUsername(), getPassword());
    }


    protected RestTemplate restTemplate = new RestTemplate(httpClientFactory());


    protected HttpComponentsClientHttpRequestFactory httpClientFactory() {

        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = null;
        try {
            sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);

        // Didn't work
        /*CloseableHttpClient httpClient
                = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory
                = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);*/

        //System.out.println("Connection factory created...");
        return requestFactory;
    }


}
