/*
 * Copyright 2013-2104 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package security;

import bookmarks.Account;
import bookmarks.Application;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Dave Syer
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, ApplicationTests.ExtraConfig.class},
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	public void passwordGrant() {
		MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
		request.set("username", "jlong");
		request.set("password", "password");
		request.set("grant_type", "password");
		Map<String, Object> token = testRestTemplate
			.postForObject("/oauth/token", request, Map.class);
		assertNotNull("Wrong response: " + token, token.get("access_token"));

		String accessToken = token.get("access_token").toString();
		String tokenType = token.get("token_type").toString();
		request.set("access_token", accessToken);
		request.set("token_type", tokenType);
//		Resources br1 =
//		  testRestTemplate.postForObject("/bookmarks", request, Resources.class);
//		Resources resources = testRestTemplate.getForObject("/bookmarks", Resources.class);
		String res1 = testRestTemplate.postForObject("/v1.0/databases", request, String.class);
		String res2 = testRestTemplate.getForObject("/v1.0/databases", String.class, request);
		System.out.println(res1 + " "+ res2);
		Map<String, Long> map = new HashMap<>();
		map.put("databaseId", Long.valueOf(1));
		testRestTemplate.delete("/v1.0/databases", map);
		assert(res1.equals("database created"));
	}

	@Test
	public void rest() {
		MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
		request.set("username", "jlong");
		request.set("password", "password");
		request.set("grant_type", "password");
		Map<String, Object> token = testRestTemplate
				.postForObject("/oauth/token", request, Map.class);
		String accessToken = token.get("access_token").toString();
		String tokenType = token.get("token_type").toString();
		request.set("access_token", accessToken);
		request.set("token_type", tokenType);
		HttpHeaders headers = new HttpHeaders();
//		String authorization = "Basic "
//				+ new String(Base64Utils.encode("jlong:password".getBytes()));
//		headers.set("Authorization", authorization);
		HttpEntity<Object> requestEntity = new HttpEntity<>(request, headers);

//		ResponseEntity<String> response1 =
//				testRestTemplate.exchange("/v1.0/databases", HttpMethod.POST, requestEntity, String.class);
//		String res1 = response1.getBody();
//		System.out.println(res1);

		ResponseEntity<String> response2 =
				testRestTemplate.exchange("/v1.0/databases", HttpMethod.GET, requestEntity, String.class);
		String res2 = response2.getBody();
		System.out.println(res2);

		ResponseEntity<String> response3 =
				testRestTemplate.exchange("/v1.0/databases/1", HttpMethod.DELETE, requestEntity, String.class);
		String res3 = response3.getBody();
		System.out.println(res3);
	}

	@TestConfiguration
	public static class ExtraConfig {

		@Bean
		RestTemplateBuilder restTemplateBuilder() {
			return new RestTemplateBuilder()
				.basicAuthorization("android-bookmarks", "123456");
		}
	}

}
