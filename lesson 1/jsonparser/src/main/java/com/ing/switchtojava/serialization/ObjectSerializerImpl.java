package com.ing.switchtojava.serialization;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectSerializerImpl implements ObjectSerializer{

	@Override
	public String serialize(Object jsonObject) throws SerializationException {
		//"{\"id\":\"100\",\"number\":\"B-01-ERU\",\"seats\":\"2\"}"

		// TODO 4 Auto-generated method stub

		String json = "";
		Class cls = jsonObject.getClass();
		Method[] allMethods = cls.getDeclaredMethods();
//		Field[] str = cls.getDeclaredFields();
		for (int i=0; i<allMethods.length;i++) {
			String mname = allMethods[i].getName();

			if (!mname.startsWith("get")) {
				continue;
			}

			java.lang.reflect.Method method;
			try {
				method = jsonObject.getClass().getMethod(mname);
				try {
					String attribute = mname.substring(3).toLowerCase();
					String value = method.invoke(jsonObject).toString();

                    System.out.println(method);
					System.out.println(value);
//					json = json+"\\\""+attribute+"\\\":"+"\\\""+value+"\\\",";
                    json = json+"\""+attribute+"\":"+"\""+value+"\",";
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} catch (SecurityException  e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}

		}
		return "{"+json.substring(0,json.length()-1)+"}";
	}

}
