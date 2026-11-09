package com.baz.divisas.scim.rest;

import be.personify.iam.provisioning.connectors.scim.schema.SchemaAttributeType;
import be.personify.util.StringUtils;
import be.personify.util.scim.PatchOperation;

import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.LogApp;
import com.baz.divisas.scim.schema.Schema;
import com.baz.divisas.scim.schema.SchemaAttribute;
import com.baz.divisas.scim.schema.SchemaReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class that bundles patch operations
 * 
 * @author wouter
 *
 */
public class PatchUtils {


	private static final String URN = "urn:";

	@Autowired
	private SchemaReader schemaReader;

	public PatchUtils() {
		super();
	}

	public void patchEntity(Map<String, Object> existingEntity, PatchOperation opType, 
			String path, Object value, Schema schema) {

		Object entry = null;

		//ADD
		if ( opType == PatchOperation.add) {
			entry = getPath(path, existingEntity, true, schema);
			if ( entry == null ) {
				existingEntity.put(removeUrnFromString(path), value);
			}
			else if (entry instanceof List) {
				processAddList(entry, value);
			} 
			else if (entry instanceof Map) {
				processAddMap(path, entry, value);
			} 
			else {
				LogApp.mensaje("Cannot perform add patch: path "+path+" "+value);
			}

		}
		//REMOVE
		else if ( opType == PatchOperation.remove) {
			List<String> segs = getPathSegments(path);
			if (segs.size() == 1) {
				processRemove(existingEntity, path, value);
			}
			else {
				LogApp.mensaje("Cannot perform remove patch: path "+path+" "+value);

			}
		}
		//REPLACE
		else if ( opType == PatchOperation.replace) {
			
			entry = getPath(path, existingEntity, false, schema);
			processReplace(existingEntity, entry, path, value);
			
		}
		else {
			LogApp.mensaje("Cannot perform operation");
		}


	}
	
	private static void processAddList(Object entry, Object value) {
		List<Object> eList = (List)entry;
		if ( value instanceof List ) {
			List<Object> ll = (List)value;
			for ( Object o : ll ) {
				if ( !eList.contains(o)) {
					eList.add(o);
				}
			}
		}
		else if ( value instanceof Map ) {
			eList.add(value);
		}
		else {
			//Do nothing
		}
	}
	
	private static void processAddMap(String path, Object entry, Object value) {
		
		Map<String, Object> eMap = (Map) entry;
		if ( value instanceof Map ) {

			Map<String, Object> aMap = (Map) value;
			for (Entry<String, Object> ent : aMap.entrySet()) {

				String key = ent.getKey();
				if (eMap.containsKey(key)) {
					processAMap(aMap, eMap, key);
				} 
				else {
					eMap.put(key, aMap.get(key));
				}					}
		}
		else if ( value instanceof List ) {
			eMap.put(removeUrnFromString(path), value);
		}
		else {
			// Do nothing
		}
	} 

	
	private static void processAMap(Map<String, Object> aMap, Map<String, Object> eMap, String key) {		
		
		Object e1 = eMap.get(key);
		if (e1 instanceof List) {
			Collection<Object> c = (Collection) aMap.get(key);
			List<Object> currentList = ((List) e1);
			for ( Object co : c ) {
				if ( !currentList.contains(co) ){
					((List<Object>) e1).add(co);
				}
			}
		} 
		else {
			eMap.put(key, aMap.get(key));
		}
	}

	private static void processRemove(Map<String, Object> existingEntity,
			String path, Object value) {

		if ( value instanceof List ) {
			List<Object> entriesToRemove = (List)value;
			Object existingAttribute = existingEntity.get(path);
			if ( existingAttribute instanceof List ) {
				List<Object> existingAttributeList = (List)existingAttribute;
				List<Object> newList = new ArrayList<>();
				for (Object oo : existingAttributeList ) {
					processExistingAtrrList(newList, oo, entriesToRemove);
				}
				existingEntity.put(path, newList);
			}
		}
		else {
			existingEntity.remove(path);
		}

	}
	
	private static void processExistingAtrrList(List<Object> newList, Object oo, List<Object> entriesToRemove) {
		
		if ( oo instanceof Map ) {
			Map<Object, Object> ooo = (Map)oo;
			for ( Object ee : entriesToRemove ) {
				if ( ee instanceof Map && !areEqual(ooo, (Map<Object, Object>)ee) ) {
					newList.add(oo);
				}
			}
		}
	}
	
	private static void processReplace(Map<String, Object> existingEntity, Object entry,
			String path, Object value) {
		
		if (entry instanceof Map) {
			((Map<String, Object>) entry).putAll((Map) value);
		}
		else if (entry instanceof List) {
			((List<Object>) entry).clear();
			((List<Object>) entry).addAll((List) value);
		}
		else if ( entry instanceof String ) {
			existingEntity.put(removeUrnFromString(path), value);
		}
		else {
			LogApp.mensaje("Cannot perform replace patch: path "+path+" value "+value);
		}
		
	}
	
	/**
	 * This function needs to handle paths with selectors... doesn't yet. See
	 * example user_patch4 .. (Microsoft AD example).
	 *
	 * @param path
	 * @param entity
	 * @return
	 */
	private Object getPath(String path, Map<String, Object> entity, boolean createTree, Schema schema ) {
		
		if (StringUtils.isEmpty(path)) {
			return entity;
		}
		
		List<String> segs = getPathSegments(path);
		Object current = entity;
		
		while (!segs.isEmpty()) {
			
			String seg = segs.remove(0);

			if (current instanceof Map) {

				seg = processSegments(seg);
				
				current = ((Map<String, Object>) current).get(seg);

				boolean empty = false; 
				if ( current instanceof List && ((List)current).isEmpty() ) {
					empty = true;
				}

				SchemaAttribute attribute = schema.getAttribute(seg);

				if ( (current == null || empty ) && createTree ) {

					attribute = processAttribute(path, attribute, seg);
					current = processMultivalued(attribute, current, seg, entity, path);
				}
			}
			else {
				return null;
			}
		}
		
		return current;
	}
	
	private static String processSegments(String seg) {
		if ( seg.endsWith("]")) {
			int startCondition = seg.indexOf('[');
			seg = seg.substring(0, startCondition );
		}
		return seg;
	}

	private SchemaAttribute processAttribute(String path, SchemaAttribute attribute, String seg) {
		
		if ( path.startsWith(URN)) {						
			String schemaString = path.substring(0, path.lastIndexOf(StringUtils.COLON));
			Schema customSchema = schemaReader.getSchema(schemaString);
			attribute = customSchema.getAttribute(seg);
		}
		
		return attribute;
	}
	
	private static Object processMultivalued(SchemaAttribute attribute, Object current, String seg, 
			Map<String, Object> entity, String path) {
		
		if ( attribute.isMultiValued() && 
			 SchemaAttributeType.fromString(attribute.getType()) == SchemaAttributeType.COMPLEX) {
				
			if ( current instanceof Map ) {
				List<Object> list = new ArrayList<>();
				Map<String,Object> newObject = new HashMap<>();
				list.add(newObject);
				((Map<String, Object>)current).put(seg, list);
				current = newObject;
			}
			else if ( current == null || current instanceof List ) {
				List<Object> l = new ArrayList<>();
				entity.put(path, l);
				current = l;
			}
			else {
				return current;
			}
		}
		
		return current;
		
	}
	
	public List<String> getPathSegments(String path) {

		path = removeUrnFromString(path);

		List<String> rest = new ArrayList<>();
		if (!StringUtils.isEmpty(path) && !path.contains(StringUtils.DOT)) {
			rest.add(path);
		}
		else if (!StringUtils.isEmpty(path)) {
			rest.addAll(Arrays.asList(path.split("\\.")));
		}

		// Se valida el rango del numero de segmentos proveniente de entrada de usuario
		// para evitar loops no acotados (Unchecked_Input_for_Loop_Condition, CWE-606).
		if (rest.size() > Constant.MAX_PATH_SEGMENTS) {
			LogApp.mensaje("El path SCIM excede el maximo de segmentos permitidos ("
					+ Constant.MAX_PATH_SEGMENTS + ")");
			throw new IllegalArgumentException("El path SCIM excede el maximo de segmentos permitidos");
		}

		return rest;
	}

	private static String removeUrnFromString(String path) {
		if ( path.startsWith(URN)) {
			path = path.substring(path.lastIndexOf(StringUtils.COLON) +1, path.length());
		}
		return path;
	}

	private static boolean areEqual(Map<Object, Object> first, Map<Object, Object> second) {
		
		boolean equal = true;
		
		if (first.size() != second.size()) {
			equal = false;
		}

		for (Entry<Object, Object> entry :  first.entrySet()) {
			String key = entry.getKey().toString();
			if (second.containsKey(key)) {
				if (!first.get(key).equals(second.get(key))){
					equal = false;
				}
			}
			else {
				equal = false;
			}
		}

		return equal;

	}

}
