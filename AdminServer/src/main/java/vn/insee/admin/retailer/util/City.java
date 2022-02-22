package vn.insee.admin.retailer.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import vn.insee.util.StringUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;


public class City {
    private static final Logger LOGGER = LogManager.getLogger(City.class);
    private static Map<String, Integer> MAP_CITY_ID = new HashMap<>();
    private static Map<Integer, String> MAP_ID_CITY = new HashMap<>();
    private static Map<Integer, List<Integer>> MAP_CITY_ID_WITH_DISTRICTS = new HashMap<>();
    private static Map<String, Integer> MAP_DISTRICT_ID = new HashMap<>();
    private static Map<Integer, String> MAP_ID_DISTRICT = new HashMap<>();
    private static JSONObject DATA = new JSONObject();

    static {

        try (InputStream inputStream = City.class.getResourceAsStream("../../../../../data/data_province_3.json");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String contents = reader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            DATA = new JSONObject(contents);

            Iterator<String> keys = DATA.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                if (DATA.get(key) instanceof JSONObject) {
                    JSONObject detail = (JSONObject) DATA.get(key);
                    String city = detail.getString("name");
                    MAP_CITY_ID.put(city, Integer.parseInt(key));
                    MAP_ID_CITY.put(Integer.parseInt(key), city);
                    List<Integer> districtIds = new ArrayList<>();
                    JSONArray districts = detail.getJSONArray("districts");
                    for (int i = 0 ; i < districts.length(); i++) {
                        JSONObject district = districts.getJSONObject(i);
                        MAP_DISTRICT_ID.put(district.getString("name").toUpperCase(), district.getInt("id"));
                        MAP_ID_DISTRICT.put(district.getInt("id"), district.getString("name"));
                        districtIds.add(district.getInt("id"));
                    }
                    MAP_CITY_ID_WITH_DISTRICTS.put(Integer.parseInt(key), districtIds);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public static int findByString(String city) {
        return MAP_CITY_ID.getOrDefault(city, 0);
    }

    public static int findDistrictByString(String district) {
        district = district.toUpperCase();
        return MAP_DISTRICT_ID.getOrDefault(district, 0);
    }

    public static int detectDistrict(String text) {
        String standardized = text.toUpperCase();
        standardized = standardized.replaceAll("\\s+","");
        standardized = StringUtil.removeAccent(standardized);
        for (String district : MAP_DISTRICT_ID.keySet()) {
            String standardized_district = String.valueOf(district).toUpperCase();
            standardized_district = StringUtil.removeAccent(standardized_district);

            standardized_district = standardized_district.replace("HUYEN", "");
//            standardized_district = standardized_district.replace("QUAN", "");
            standardized_district = standardized_district.replaceAll("\\s+","");
            if (standardized.contains(standardized_district)) {
                return MAP_DISTRICT_ID.get(district);
            }
        }
        return -1;
    }

    public static int detectCity(String text) {
        String standardized = text.toUpperCase();
        standardized = StringUtil.removeAccent(standardized);
        standardized = standardized.replace("TINH", "");
        standardized = standardized.replace("THANH PHO", "");
        standardized = standardized.replaceAll("\\s+","");


        for (String city : MAP_CITY_ID.keySet()) {
            String standardized_city = String.valueOf(city).toUpperCase();
            standardized_city = StringUtil.removeAccent(standardized_city);

            standardized_city = standardized_city.replace("TINH", "");
            standardized_city = standardized_city.replace("THANH PHO", "");
            standardized_city = standardized_city.replaceAll("\\s+","");
            if (standardized.contains(standardized_city)) {
                return MAP_CITY_ID.get(city);
            }
        }
        return -1;
    }

    public static int getCityFromDistrict(int idDistrict) {
        Iterator<String> keys = DATA.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            if (DATA.get(key) instanceof JSONObject) {
                JSONObject detail = (JSONObject) DATA.get(key);
                JSONArray districts = detail.getJSONArray("districts");
                for (int i = 0 ; i < districts.length(); i++) {
                    JSONObject district = districts.getJSONObject(i);
                    int id_district = district.getInt("id");
                    if (id_district == idDistrict) {
                        return detail.getInt("id");
                    }
                }
            }
        }
        return -1;
    }

    public static int detectDistrictFrom(int cityId, String districtName) {
        districtName = districtName.toUpperCase();
        districtName = StringUtil.removeAccent(districtName);
        districtName = districtName.replace("HUYEN", "");
        districtName = districtName.replace("QUAN", "");
        districtName = districtName.replaceAll("\\s+","");

        List<Integer> districtIDs = MAP_CITY_ID_WITH_DISTRICTS.getOrDefault(cityId, new ArrayList<>());
        for (Integer district_id: districtIDs) {
            String name = MAP_ID_DISTRICT.get(district_id);
            String standardized_district = name.toUpperCase();
            standardized_district = StringUtil.removeAccent(standardized_district);
            standardized_district = standardized_district.replace("HUYEN", "");
            standardized_district = standardized_district.replace("QUAN", "");
            standardized_district = standardized_district.replaceAll("\\s+","");

            if (standardized_district.contains(districtName) || districtName.contains(standardized_district)) {
                return district_id;
            }
        }
        return -1;
    }

    public static String findCityById(int id) {
        return MAP_ID_CITY.get(id);
    }
    public static String findDistrictById(int id) {
        return MAP_ID_DISTRICT.get(id);
    }

}
