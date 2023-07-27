package com.thed.zapi.cloud.sample;
import com.atlassian.jwt.SigningAlgorithm;
import com.atlassian.jwt.core.writer.JsonSmartJwtJsonBuilder;
import com.atlassian.jwt.core.writer.JwtClaimsBuilder;
import com.atlassian.jwt.core.writer.NimbusJwtWriterFactory;
import com.atlassian.jwt.httpclient.CanonicalHttpUriRequest;
import com.atlassian.jwt.writer.JwtJsonBuilder;
import com.atlassian.jwt.writer.JwtWriterFactory;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class testJwtGeneratorConnectApp {
	 //the key from the app descriptor
    private String key = "NGQ0MDUyYTctMzhhNS0zMjA5LWE1MGYtOTQxNmEzYjEzZWMxIDYzYmRjZjRmMTU3ZDM2ZjQ0NDg0ODc5YSBVU0VSX0RFRkFVTFRfTkFNRQ";

    //the sharedsecret key received during the app installation handshake
    private String sharedSecret = "7RYBztlmPJJz-ObBTw74mefjeDGVsHKlshc7YrI6wJc";

    public HttpRequest createRequestWithJwt() throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException {
        String method = "GET";
        String baseUrl = "https://prod-api.zephyr4jiracloud.com/connect/";
        String contextPath = "/";
        String apiPath = "/rest/api/latest/serverInfo";

        String jwt = createJwt(method, apiPath, contextPath);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(baseUrl + apiPath))
                .header("Content-Type", "application/json")
                .header("Authorization", "JWT " + jwt)
                .build();

        return request;

    }

    private String createJwt(String method, String apiPath, String contextPath) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        long issuedAt = System.currentTimeMillis() / 1000L;
        long expiresAt = issuedAt + 180L;

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

        return jwtToken;
    }

}
