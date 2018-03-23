package com.databilize.fastcascade;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Cascader {

	/**
	 * 三级级联 按字段名从对象中获取字段值，并按照field1为第一级、field2 为第二级、field3为第三级提取级联数据
	 * 
	 * @param list
	 * @param field1
	 * @param field2
	 * @param field3
	 * @return
	 */
	public static <T> List<Level1Data> transform(List<T> list, String fieldName1, String fieldName2,
			String fieldName3) {
		if (null == list || list.isEmpty()) {
			return null;
		}

		if (StringUtils.isAnyBlank(fieldName1, fieldName2, fieldName3)) {
			return null;
		}

		// result存储级联结果
		Object field1 = null;
		Object field2 = null;
		Object field3 = null;
		T obj = null;

		Map<Object, Map<Object, Map<Object, Object>>> tempMap = new HashMap<>();

		// 将三级数据存放在Map中
		for (int cnt = 0, size = list.size(); cnt < size; cnt++) {
			obj = list.get(cnt);
			field1 = getFieldValue(obj, fieldName1);
			field2 = getFieldValue(obj, fieldName2);
			field3 = getFieldValue(obj, fieldName3);

			// 默认每一级没有重复的数据，直接以字段值为key
			if (!tempMap.containsKey(field1)) {
				tempMap.put(field1, new HashMap<>());
			}

			if (!tempMap.get(field1).containsKey(field2)) {
				tempMap.get(field1).put(field2, new HashMap<>());
			}

			if (!tempMap.get(field1).get(field2).containsKey(field3)) {
				tempMap.get(field1).get(field2).put(field3, new HashMap<>());
			}
		}

		// 将Map转换为List
		List<Level1Data> result = new ArrayList<>();
		Level1Data level1Data = null;
		Level2Data level2Data = null;
		Level3Data level3Data = null;
		List<Level2Data> level2DataList = null;
		List<Level3Data> level3DataList = null;

		for (Map.Entry<Object, Map<Object, Map<Object, Object>>> level1Ele : tempMap.entrySet()) {
			level1Data = new Level1Data();

			level1Data.setName(level1Ele.getKey().toString());

			level2DataList = new ArrayList<>();

			for (Map.Entry<Object, Map<Object, Object>> level2Ele : level1Ele.getValue().entrySet()) {
				level2Data = new Level2Data();

				level2Data.setName(level2Ele.getKey().toString());

				level3DataList = new ArrayList<>();

				for (Map.Entry<Object, Object> level3Ele : level2Ele.getValue().entrySet()) {
					level3Data = new Level3Data();
					level3Data.setName(level3Ele.getKey().toString());
					level3Data.setData(level3Ele.getValue());

					level3DataList.add(level3Data);

				}

				level2Data.setData(level3DataList);
				level2DataList.add(level2Data);
			}

			level1Data.setData(level2DataList);
			result.add(level1Data);

		}

		return result;
	}

	// 反射
	public static <T> Object getFieldValue(T obj, String fieldName) {
		Object value = null;
		if (null == obj || StringUtils.isBlank(fieldName)) {
			return null;
		}

		try {
			Field field = obj.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			value = field.get(obj);
		} catch (Exception e) {

		}
		return value;

	}

}
