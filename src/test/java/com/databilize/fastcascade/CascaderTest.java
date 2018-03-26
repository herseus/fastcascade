package com.databilize.fastcascade;

import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class CascaderTest {
	public static void main(String[] args) {
		List<AdministrativeDivision> srcList = Arrays.asList(new AdministrativeDivision("江苏", "南京", "玄武"),
				new AdministrativeDivision("江苏", "南京", "栖霞"), new AdministrativeDivision("江苏", "南京", "江宁"),
				new AdministrativeDivision("浙江", "杭州", "西湖"), new AdministrativeDivision("浙江", "杭州", "下城"));

		System.out.println(JSON.toJSONString(Cascader.transform2List(srcList, "province", "city", "county")));
		System.out.println(JSON.toJSONString(
				Cascader.transform2Map(srcList, "province", "city", "county", "provinces", "cities", "counties")));
	}
}
