package it.windtre.tremobilitycms.ui.utils;

import com.vaadin.flow.router.QueryParameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TemplateUtil {

	public static String generateLocation(String basePage, String entityId) {
		return basePage + (entityId == null || entityId.isEmpty() ? "" : "/" + entityId);
	}

	public static QueryParameters buildQueryParams(String[] keys, String[] values) {
		Map<String,String> myMap = new HashMap<>();
		for (int i=0; i<keys.length; ++i){
			myMap.put(keys[i], values[i]);
		}
		QueryParameters params = QueryParameters.simple(myMap);
		System.out.println("generated query: " + params.getQueryString());
		return params;
	}
}
