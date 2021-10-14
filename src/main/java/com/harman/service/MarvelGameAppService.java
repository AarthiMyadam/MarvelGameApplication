package com.harman.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harman.dto.AvengersDto;

@Service
public class MarvelGameAppService {
	 public HashMap<String, Integer> map = new HashMap<String, Integer>();
	 public HashMap<String, Integer> newmap = new HashMap<String, Integer>();
	 public List<com.harman.dto.Character> a;
	 public List<com.harman.dto.Character> b;
	 List<String> temp=new ArrayList<String>();
	 public int capacity=3;
	
	   

/*
 * @Async public CompletableFuture<String> getPowersofAntiHeroes() throws
 * IOException {
 * 
 * 
 * URL url = new URL("http://www.mocky.io/v2/5ecfd630320000f1aee3d64d");
 * HttpURLConnection con = (HttpURLConnection) url.openConnection();
 * con.setRequestMethod("GET"); InputStream in = null;
 * 
 * int status = con.getResponseCode(); System.out.println(status); in = new
 * BufferedInputStream(con.getInputStream());
 * 
 * String result = org.apache.commons.io.IOUtils.toString(in,
 * Charset.forName("UTF-8")); return CompletableFuture.completedFuture(result);
 * 
 */
	  
	  //return CompletableFuture.completedFuture(result); }
	 


	   
		public List<com.harman.dto.Character> getPowers(URL url) {

			try {
				
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				InputStream in = null;
				String result = null;

				in = new BufferedInputStream(con.getInputStream());
				result = org.apache.commons.io.IOUtils.toString(in, Charset.forName("UTF-8"));

				ObjectMapper objectMapper = new ObjectMapper();
				AvengersDto avengers = objectMapper.readValue(result, AvengersDto.class);
				b = avengers.getCharacter();

			} catch (Exception e) {
				e.printStackTrace();
			} finally { // con.disconnect();
			}

			return b;
		}
		
		
		public Integer getMaxPower(HashMap<String,Integer> map, String key)
		{
			if (map.containsKey(key)) 
			{
				System.out.println(temp.size());
			if (temp.size() == capacity) {
				newmap = countFrequencies(temp);
				List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(newmap.entrySet());

				// Sort the list
				Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
					public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
						return (o1.getValue()).compareTo(o2.getValue());
					}
				});
				int count = list.get(0).getValue();
				System.out.println(list.get(0).getKey());
				String character = list.get(0).getKey();
				String removableCharacter = list.get(0).getKey();

				System.out.println(removableCharacter);
				int i = 0;
				while (list.get(i).getValue() == count) {
					System.out.println(list.get(i).getValue());
					if (map.get(list.get(i).getKey()) < map.get(character)) {
						System.out.println(list.get(i).getKey());
						removableCharacter = list.get(i).getKey();
					}

					i++;
					if (i == capacity) {
						break;
					}
					System.out.println(removableCharacter);

				}

				System.out.println(temp);
                int index=temp.indexOf(removableCharacter);
				temp.set(index, key);
                newmap.remove(removableCharacter);
				System.out.println(temp.size());
				
				System.out.println(temp);

			}
			else
			{
			temp.add(key);
			}
			System.out.println(temp.size());
			return map.get(key);
			}
			else
			{
			  return -1;
			}
			
		
	}
		public HashMap<String,Integer> countFrequencies(List<String> list)
	    {
	  
	       
	        Set<String> st = new HashSet<String>(list);
	        for (String s : st)
	            newmap.put(s,Collections.frequency(list, s));
	        System.out.println(newmap);
	        return newmap;
	    }
}
