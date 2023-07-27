/* D SOFTWARE INCORPORATED
 * Copyright 2007-2011 D Software Incorporated
 * All Rights Reserved.
 *
 * NOTICE: D Software permits you to use, modify, and distribute this 
file
 * in accordance with the terms of the license agreement accompanying 
it.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an “AS IS? BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
implied.
 */
/*
 * This is a sample of what can be done by using API's with Zephyr through 
the JAVA coding language.
 * By creating the .java files, you can import them 
into your workspace and then call them in your custom program. 
 * 
 * Eclipse Java EE IDE for Web Developers.
Version: Neon Release (4.6.0)
Build id: 20160613-1800
 * Java- Java JDK 1.8.0_101
 * 
 * Author: Swapna Kumar Vemula, Product Support Engineer, D Software Inc.
 */

package com.thed.zapi.cloud.sample;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.thed.zephyr.cloud.rest.ZFJCloudRestClient;
import com.thed.zephyr.cloud.rest.client.JwtGenerator;

/**
 * @author swapna.vemula 12-Dec-2016
 *
 */
public class sampleJwtGenerator {

	/**
	 * @param args
	 * @author Created by swapna.vemula on 12-Dec-2016.
	 * @throws URISyntaxException
	 * @throws JobProgressException
	 * @throws JSONException
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public static void main(String[] args) throws URISyntaxException, IllegalStateException, IOException {
		// Replace Zephyr BaseUrl with the <ZAPI_CLOUD_URL> shared with ZAPI Cloud Installation
		String zephyrBaseUrl = "https://prod-api.zephyr4jiracloud.com/connect";
		// zephyr accessKey , we can get from Addons >> zapi section
		String accessKey = "NGQ0MDUyYTctMzhhNS0zMjA5LWE1MGYtOTQxNmEzYjEzZWMxIDYzYmRjZjRmMTU3ZDM2ZjQ0NDg0ODc5YSBVU0VSX0RFRkFVTFRfTkFNRQ";
		// zephyr secretKey , we can get from Addons >> zapi section
		String secretKey = "7RYBztlmPJJz-ObBTw74mefjeDGVsHKlshc7YrI6wJc";
		// Jira accountId
		String accountId = "63bdcf4f157d36f44484879a";
		ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secretKey, accountId).build();
		JwtGenerator jwtGenerator = client.getJwtGenerator();
		
		// API to which the JWT token has to be generated
		String createCycleUri = zephyrBaseUrl + "/public/rest/api/2.0/executions/search/cycle/58e2efb2-e2cf-4f05-8a20-f63e741e92f1?versionId=13250&projectId=10134";
		
		URI uri = new URI(createCycleUri);
		int expirationInSec = 360;
		String jwt = jwtGenerator.generateJWT("GET", uri, expirationInSec);
		
		// Print the URL and JWT token to be used for making the REST call
		System.out.println("FINAL API : " +uri.toString());
		System.out.println("JWT Token : " +jwt);	

	
	}

}
