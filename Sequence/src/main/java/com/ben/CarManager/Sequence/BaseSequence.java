package com.ben.CarManager.Sequence;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseSequence {

	private Map<String, List<Field>> sequenceFields = new HashMap<String, List<Field>>();

	List<Field> init() {
		List<Field> list = sequenceFields.get(this.getClass().getName());

		if (null == list) {
			list = new ArrayList<Field>();
			Field[] field = this.getClass().getDeclaredFields();
			for (Field f : field) {
				if (Modifier.isFinal(f.getModifiers()))
					continue;
				f.setAccessible(true);
				list.add(f);
			}
			sequenceFields.put(this.getClass().getName(), list);
		}
		return list;
	}

	public byte[] Serializable() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StringBuffer sb=new StringBuffer();
//		sb.append

		return null;
	}

}
