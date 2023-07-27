package com.thed.zapi.cloud.sample;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import com.atlassian.jwt.*;
import com.atlassian.jwt.core.writer.*;
import com.atlassian.jwt.httpclient.CanonicalHttpUriRequest;
import com.atlassian.jwt.writer.JwtJsonBuilder;
import com.atlassian.jwt.writer.JwtWriterFactory;

public class testJwtGeneratorwithQsh {
	public String createUriWithJwt()
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        long issuedAt = System.currentTimeMillis() / 1000L;
        long expiresAt = issuedAt + 180L;
        String key = "atlassian-connect-app"; //the key from the app descriptor
        String sharedSecret = "...";    //the sharedsecret key received
                                        //during the app installation handshake
        String method = "GET";
        String baseUrl = "http://localhost:2990/jira";
        String contextPath = "/jira";
        String apiPath = "/rest/api/latest/serverInfo";

        JwtJsonBuilder jwtBuilder = new JsonSmartJwtJsonBuilder()
                .issuedAt(issuedAt)
                .expirationTime(expiresAt)
                .issuer(key);

        CanonicalHttpUriRequest canonical = new CanonicalHttpUriRequest(method,
                apiPath, contextPath, new HashMap());
        JwtClaimsBuilder.appendHttpRequestClaims(jwtBuilder, canonical);

        JwtWriterFactory jwtWriterFactory = new NimbusJwtWriterFactory();
        String jwtbuilt = jwtBuilder.build();
        String jwtToken = jwtWriterFactory.macSigningWriter(SigningAlgorithm.HS256,
                sharedSecret).jsonToJwt(jwtbuilt);

        String apiUrl = baseUrl + apiPath + "?jwt=" + jwtToken;
        return apiUrl;
    }
}
