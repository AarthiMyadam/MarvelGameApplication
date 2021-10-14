package com.harman.controller;

import java.util.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harman.dto.AvengersDto;
import com.harman.service.MarvelGameAppService;

@RestController
public class MarvelGameAppController {

	@Autowired
	MarvelGameAppService marvelGameAppService;

	int capacity = 4;
	Set<String> set = new LinkedHashSet<>();

	public HashMap<String, Integer> map = new HashMap<String, Integer>();
	public HashMap<String, Integer> newmap = new HashMap<String, Integer>();
	public List<com.harman.dto.Character> a;
	public List<com.harman.dto.Character> b;

	@Async
	@Scheduled(fixedRate = 1000)
	public List<com.harman.dto.Character> getPowerOfAvengers() throws MalformedURLException {

		URL url = new URL("http://www.mocky.io/v2/5ecfd5dc3200006200e3d64b");

		return marvelGameAppService.getPowers(url);

	}

	@Async
	@Scheduled(fixedRate = 1000)
	public List<com.harman.dto.Character> getPowersOfAntiHeroes() throws MalformedURLException {
		URL url = new URL("http://www.mocky.io/v2/5ecfd6473200009dc1e3d64e");

		return marvelGameAppService.getPowers(url);

	}

	@Async
	@Scheduled(fixedRate = 1000)
	public List<com.harman.dto.Character> getPowersOfMutants() {

		try {
			URL url = new URL("http://www.mocky.io/v2/5ecfd630320000f1aee3d64d");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			InputStream in = null;
			String result = null;
			in = new BufferedInputStream(con.getInputStream());
			result = org.apache.commons.io.IOUtils.toString(in, Charset.forName("UTF-8"));
			Integer temp = result.indexOf("]") + 1;
			result = result.substring(0, temp) + result.substring(temp + 1);

			ObjectMapper objectMapper = new ObjectMapper();
			AvengersDto avengers = objectMapper.readValue(result, AvengersDto.class);
			b = avengers.getCharacter();

		} catch (Exception e) {
			e.printStackTrace();
		} finally { // con.disconnect();
		}

		return b;
	}

	@GetMapping("/powerlevel")
	public HashMap<String, Integer> getPowerLevels() throws IOException, JSONException {
		List<List<com.harman.dto.Character>> res = new ArrayList<List<com.harman.dto.Character>>();
		List<com.harman.dto.Character> a1 = getPowerOfAvengers();
		List<com.harman.dto.Character> a2 = getPowersOfAntiHeroes();
		List<com.harman.dto.Character> a3 = getPowersOfMutants();

		res.add(a1);
		res.add(a2);
		res.add(a3);

		for (List<com.harman.dto.Character> li : res) {
			for (com.harman.dto.Character cha : li) {
				map.put(cha.getName(), cha.getMax_power());
			}
		}

		return map;

	}

	@GetMapping("/powerlevels/{character}")
	public Integer powerByCharacter(@PathVariable("character") String key) throws IOException, JSONException {

		map = getPowerLevels();
		return marvelGameAppService.getMaxPower(map,key);
		
		
		
	}
}

