package com.databilize.fastcascade;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.databilize.fastcascade.annotations.ClassPreamble;

/**
 * 提供静态方法，用于构造三级数据级联
 * 
 * @author 0x3D
 *
 */
@ClassPreamble(author = "herseus", date = "2018-03-23")
public class Cascader {

	/**
	 * 读取srcList中的每条数据，提取fieldName1、fieldName2、 fieldName3的值，构造三级数据级联
	 * 
	 * @param srcList
	 *            源数据列表
	 * @param fieldName1
	 *            第一级原始字段名
	 * @param fieldName2
	 *            第二级原始字段名
	 * @param fieldName3
	 *            第三级原始字段名
	 * @return List<LevelData>
	 */
	public static <T> List<LevelData> transform2List(List<T> srcList, String fieldName1, String fieldName2,
			String fieldName3) {
		if (null == srcList || srcList.isEmpty()) {
			return null;
		}

		if (StringUtils.isAnyBlank(fieldName1, fieldName2, fieldName3)) {
			return null;
		}

		// 三个字段值
		Object fieldValue1 = null;
		Object fieldValue2 = null;
		Object fieldValue3 = null;
		T obj = null;

		Map<Object, Map<Object, Map<Object, Object>>> tempMap = new HashMap<>();

		// 将三级数据存放在Map中
		for (int cnt = 0, size = srcList.size(); cnt < size; cnt++) {
			// 当前处理的数据
			obj = srcList.get(cnt);
			// 读取三个字段值
			fieldValue1 = getFieldValue(obj, fieldName1);
			fieldValue2 = getFieldValue(obj, fieldName2);
			fieldValue3 = getFieldValue(obj, fieldName3);

			// 默认每一级没有重复的数据，直接以字段值为key
			if (!tempMap.containsKey(fieldValue1)) {
				tempMap.put(fieldValue1, new HashMap<>());
			}

			if (!tempMap.get(fieldValue1).containsKey(fieldValue2)) {
				tempMap.get(fieldValue1).put(fieldValue2, new HashMap<>());
			}

			if (!tempMap.get(fieldValue1).get(fieldValue2).containsKey(fieldValue3)) {
				tempMap.get(fieldValue1).get(fieldValue2).put(fieldValue3, new HashMap<>());
			}
		}

		// 将Map转换为List
		List<LevelData> result = new ArrayList<>();
		LevelData level1Data = null;
		LevelData level2Data = null;
		LevelData level3Data = null;
		List<LevelData> level2DataList = null;
		List<LevelData> level3DataList = null;

		for (Map.Entry<Object, Map<Object, Map<Object, Object>>> level1Ele : tempMap.entrySet()) {
			level1Data = new LevelData();

			level1Data.setName(level1Ele.getKey().toString());

			level2DataList = new ArrayList<>();

			for (Map.Entry<Object, Map<Object, Object>> level2Ele : level1Ele.getValue().entrySet()) {
				level2Data = new LevelData();

				level2Data.setName(level2Ele.getKey().toString());

				level3DataList = new ArrayList<>();

				for (Map.Entry<Object, Object> level3Ele : level2Ele.getValue().entrySet()) {
					level3Data = new LevelData();
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

	/**
	 * 通过反射获取字段值
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
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

	/**
	 * 读取srcList中的每条数据，提取fieldName1、fieldName2、
	 * fieldName3的值，对应的目标字段名为targetFieldName1、targetFieldName2、targetFieldName3，构造三级数据级联。
	 * 
	 * 
	 * @param srcList
	 *            源数据列表
	 * @param fieldName1
	 *            第一级原始字段名
	 * @param fieldName2
	 *            第二级原始字段名
	 * @param fieldName3
	 *            第三级原始字段名
	 * @param targetFieldName1
	 *            第一级目标字段名
	 * @param targetFieldName2
	 *            第二级目标字段名
	 * @param targetFieldName3
	 *            第三级目标字段名
	 * @return Map<String, Object>
	 */
	public static <T> Map<String, Object> transform2Map(List<T> srcList, String fieldName1, String fieldName2,
			String fieldName3, String targetFieldName1, String targetFieldName2, String targetFieldName3) {
		if (null == srcList || srcList.isEmpty()) {
			return null;
		}

		if (StringUtils.isAnyBlank(fieldName1, fieldName2, fieldName3)) {
			return null;
		}

		// 三个字段值
		Object fieldValue1 = null;
		Object fieldValue2 = null;
		Object fieldValue3 = null;
		T obj = null;

		Map<Object, Map<Object, Map<Object, Object>>> tempMap = new HashMap<>();

		// 将三级数据存放在Map中
		for (int cnt = 0, size = srcList.size(); cnt < size; cnt++) {
			// 当前处理的数据
			obj = srcList.get(cnt);
			// 读取三个字段值
			fieldValue1 = getFieldValue(obj, fieldName1);
			fieldValue2 = getFieldValue(obj, fieldName2);
			fieldValue3 = getFieldValue(obj, fieldName3);

			// 默认每一级没有重复的数据，直接以字段值为key
			if (!tempMap.containsKey(fieldValue1)) {
				tempMap.put(fieldValue1, new HashMap<>());
			}

			if (!tempMap.get(fieldValue1).containsKey(fieldValue2)) {
				tempMap.get(fieldValue1).put(fieldValue2, new HashMap<>());
			}

			if (!tempMap.get(fieldValue1).get(fieldValue2).containsKey(fieldValue3)) {
				tempMap.get(fieldValue1).get(fieldValue2).put(fieldValue3, new HashMap<>());
			}
		}

		// 将Map转换为List
		Map<String, Object> result = null;
		Map<String, Object> level1Data = null;
		Map<String, Object> level2Data = null;
		Map<String, Object> level3Data = null;
		List<Map<String, Object>> level1DataList = new ArrayList<>();
		List<Map<String, Object>> level2DataList = null;
		List<Map<String, Object>> level3DataList = null;

		for (Map.Entry<Object, Map<Object, Map<Object, Object>>> level1Ele : tempMap.entrySet()) {
			level1Data = new HashMap<>();
			level1Data.put("name", level1Ele.getKey().toString());

			level2DataList = new ArrayList<>();

			for (Map.Entry<Object, Map<Object, Object>> level2Ele : level1Ele.getValue().entrySet()) {
				level2Data = new HashMap<>();
				level2Data.put("name", level2Ele.getKey().toString());

				level3DataList = new ArrayList<>();

				for (Map.Entry<Object, Object> level3Ele : level2Ele.getValue().entrySet()) {
					level3Data = new HashMap<>();
					level3Data.put("name", level3Ele.getKey().toString());
					level3Data.put("data", level3Ele.getValue());

					level3DataList.add(level3Data);

				}

				level2Data.put(targetFieldName3, level3DataList);
				level2DataList.add(level2Data);
			}

			level1Data.put(targetFieldName2, level2DataList);
			level1DataList.add(level1Data);

		}

		result = new HashMap<>();
		result.put(targetFieldName1, level1DataList);
		return result;
	}

}
