package com.konsumen.test.salt.frontend.service;

import java.util.Arrays;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.konsumen.test.salt.frontend.model.Konsumen;

@Service
public class KonsumenService {

	public static final String REST_SERVICE_URI = "http://localhost:8080/api";

	/* GET */
	public Map<String, Object> getKonsumen(int id) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return restTemplate.exchange(REST_SERVICE_URI + "/konsumen/" + id, HttpMethod.GET, entity, Map.class).getBody();
	}

	/* GET */
	public Map<String, Object> getAllKonsumen(String keyword) {
		if (keyword == null)
			keyword = "";

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return restTemplate
				.exchange(REST_SERVICE_URI + "/konsumen?keyword=" + keyword, HttpMethod.GET, entity, Map.class)
				.getBody();
	}

	/* POST */
	public Map<String, Object> createKonsumen(Konsumen konsumen) {
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		Date date = formatter.parse(konsumen.getTgl_registrasi());
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Konsumen> entity = new HttpEntity<Konsumen>(konsumen, headers);
		return restTemplate.exchange(REST_SERVICE_URI + "/konsumen", HttpMethod.POST, entity, Map.class).getBody();
		// System.out.println("Location : "+uri.toASCIIString());
	}

	/* PUT */
	public Map<String, Object> updateKonsumen(Konsumen konsumen, int id) {
		konsumen.setId(id);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Konsumen> entity = new HttpEntity<Konsumen>(konsumen, headers);
		return restTemplate.exchange(REST_SERVICE_URI + "/konsumen/" + id, HttpMethod.PUT, entity, Map.class).getBody();
	}

	/* DELETE */
	public void deleteKonsumen(Konsumen konsumen, int id) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(REST_SERVICE_URI + "/konsumen/" + id);

	}

}