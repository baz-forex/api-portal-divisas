/*
*     Copyright 2019-2022 Wouter Van der Beken @ https://personify.be
*
* Generated software by personify.be

* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*          http://www.apache.org/licenses/LICENSE-2.0
*
 * Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.baz.divisas.scim.storage.impl;

import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.baz.divisas.scim.storage.ConstraintViolationException;
import com.baz.divisas.scim.storage.DataException;
import com.baz.divisas.scim.storage.Storage;
import com.baz.divisas.scim.util.Constants;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import be.personify.util.LogicalOperator;
import be.personify.util.SearchCriteria;
import be.personify.util.SearchCriterium;
import be.personify.util.SearchOperation;
import be.personify.util.StringUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author jingzhou wang
 * @author wouter vdb
 */
public class MongoStorage implements Storage {

	private static final Logger LOGGER = LogManager.getLogger(MongoStorage.class);
		
	private MongoCollection<Document> col;
	private String type;
	private static final String OID = "_id";
	private static final String SET = "$set";
	private static final String REGEX = "$regex";
	private static final String MINUS = "-1";
	private static final String DESC = "descending";
	private static final String MODULES = "modules";


	public MongoStorage() {
		super();
	}

	
	@Override
	public void initialize(String type) {
		this.type = type;
		LOGGER.info("initializing store of type {}", type);
		MongoClient client  = FactoryMongoDB.getInstance().getMongoClient();
		
		if ( type.equals(Constants.RESOURCE_TYPE_USER)) {
			col = client.getDatabase(PropBD.getAuthBD()).getCollection(PropBD.getScimUsuariosColeccion());
		}
		else if ( type.equalsIgnoreCase(Constants.RESOURCE_TYPE_GROUP)) {
			col = client.getDatabase(PropBD.getAuthBD()).getCollection(PropBD.getScimGruposColeccion());
		}
		else {
			throw new DataException("the type " + type + " is not a valid resource type");
		}
				
	}
	

	@Override
	public Map<String, Object> get(String id) {
		Document doc = col.find(new Document(OID, id)).first();
		LOGGER.info("doc {}", doc);
		if (doc != null) {
			doc.remove(OID);
			doc.remove(MODULES);
			doc.put(Constants.ID, id);
		}
		return unsafetyfyAttributes(doc);
	}
	

	@Override
	public Map<String, Object> get(String id, String version) {
		Document query = new Document(OID, id).append(Constants.KEY_META, new Document(Constants.KEY_VERSION, version));
		Document doc = col.find(query).first();
		if (doc != null) {
			doc.remove(OID);
			doc.remove(MODULES);
			doc.put(Constants.ID, id);
		}
		return unsafetyfyAttributes(doc);
	}

	
	@Override
	public List<String> getVersions(String id) {
		List<String> vs = new ArrayList<>();
		Document query = new Document(OID, id);
		Document doc = col.find(query).first();
		if (doc != null) {
			vs.add((unsafetyfyAttributes((Document) doc.get(Constants.KEY_META)).getString(Constants.KEY_VERSION)));
		}
		return vs;
	}

	
	@Override
	public boolean delete(String id) {
		return col.findOneAndDelete(new Document(OID, id)) != null;
	}

	
	@Override
	public boolean deleteAll() {
		col.deleteMany(new Document("_id",new Document("$exists",true)));
		return true;
	}

	
	@Override
	public void create(String id, Map<String, Object> object) throws ConstraintViolationException {

		object = safetyfyAttributes(object);
		Document doc = new Document(object);
		
		//check if already present
		boolean found = entryExists(id);
		
		doc.remove(Constants.ID);
		doc.put(OID, id);
		
		
		try {
			if ( !found ) {
				col.insertOne(doc);
			}
			else {
				col.findOneAndUpdate(new Document(OID, id), new Document(SET, doc));
			}
		}
		catch( MongoWriteException mwe ) {
			LOGGER.error("Error al insertar en BD", mwe);
			throw new DataException(mwe.getError().getMessage());
		}
	}


	private boolean entryExists(String id) {
		boolean found = false;
		List<Document> vlist = new ArrayList<>();
		vlist.add(new Document(OID, id));
		Document filter = new Document("$or", vlist);
		if (col.find(filter).first() != null) {
			found = true;
		}
		return found;
	}

	
	@Override
	public void update(String id, Map<String, Object> object) {

		object = safetyfyAttributes(object);
		Document doc = new Document(object);
		Document query = new Document(OID, id);
		doc.remove(Constants.ID);
		col.findOneAndUpdate(query, new Document(SET, doc));
	}
	

	@Override
	public List<Map<String, Object>> search(SearchCriteria searchCriteria, int start, int count, 
			String sortBy, String sortOrder) {
		return search(searchCriteria, start, count, sortBy, sortOrder, null);
	}

	
	@Override
	public List<Map<String, Object>> search(SearchCriteria searchCriteria, int start, int count, 
			String sortBy, String sortOrder, List<String> includeAttributes) {

		LOGGER.info("searchcriteria {}", searchCriteria);
		FindIterable<Document> finds = find(start, count, includeAttributes, getCriteria(searchCriteria));
		LOGGER.info("finds {}", finds);
		sort(sortBy, sortOrder, finds);

		List<Map<String, Object>> all = new ArrayList<>();
		String id = null;
		for (Document doc : finds) {
			id = ((String) doc.remove(OID));
			doc.remove(MODULES);
			doc.put(Constants.ID, id);
			all.add(unsafetyfyAttributes(doc));
		}
		return all;
	}

	
	private FindIterable<Document> find(int start, int count, List<String> includeAttributes, Bson query) {
		FindIterable<Document> finds;
		int skip = start -1;
		if (includeAttributes != null) {
			Document projection = new Document();
			for (String includeAttribute : includeAttributes) {
				projection.append(includeAttribute, 1);
			}
			finds = col.find(query).projection(projection).skip(skip).limit(count);
		} 
		else {
			finds = col.find(query).skip(skip).limit(count);
		}
		return finds;
	}

	
	private static void sort(String sortBy, String sortOrder, FindIterable<Document> finds) {
		if (sortBy != null) {
			int order = 1;
			if (sortOrder != null && (DESC.equals(sortOrder) || MINUS.equals(sortOrder)) ) {
				order = -1;
			}
			finds.sort(new Document(sortBy, order));
		}
	}

	
	@Override
	public long count(SearchCriteria searchCriteria) {
		return col.countDocuments(getCriteria(searchCriteria));
	}

	
	private static Bson getCriteria(SearchCriteria searchCriteria) {
		
		Bson criteria = new Document();
		
		if ( searchCriteria != null ) {
			
			List<Bson> crit = new ArrayList<>();
			for ( SearchCriterium sc : searchCriteria.getCriteria() ) {
				crit.add(getCriterium(sc));
			}
			for ( SearchCriteria sc : searchCriteria.getGroupedCriteria() ) {
				crit.add(getCriteria(sc));
			}
			
			if ( !crit.isEmpty() ) {
				
				if ( searchCriteria.getOperator() == LogicalOperator.AND) {
					criteria = Filters.and(crit);
				}
				else if ( searchCriteria.getOperator() == LogicalOperator.OR) {
					criteria = Filters.or(crit);
				}
				else {
					criteria = new Document();
				}

			}
		}
		
		return criteria;
	}
	

	private static Bson getCriterium( SearchCriterium sc ) {
		
		Bson criterium;
		SearchOperation op = sc.getSearchOperation();
		String key = sc.getKey();

		switch (op) {
		case EQUALS:
        criterium = Filters.eq(key, sc.getValue());
        break;
		case NOT_EQUALS:
        criterium = Filters.ne(key, sc.getValue());
        break;
		case CONTAINS:
        criterium = Filters.eq(key, new Document(REGEX, sc.getValue()));
        break;
		case PRESENT:
        criterium = Filters.exists(key);
        break;
		case GREATER_THEN:
        criterium = Filters.gt(key, sc.getValue());
        break;
		case GREATER_THEN_OR_EQUAL:
        criterium = Filters.gte(key, sc.getValue());
        break;
		case LESS_THEN:
        criterium = Filters.lt(key, sc.getValue());
        break;
		case LESS_THEN_EQUAL:
        criterium = Filters.lte(key, sc.getValue());
        break;
		default:
        throw new DataException("the operator " + op.name() + " is not implemented");
		}

		return criterium;
	}
	
	public Map<String,Object> safetyfyAttributes( Map<String,Object> map) {
		
		if ( type.equalsIgnoreCase(Constants.RESOURCE_TYPE_GROUP)) {
			
			Map<String,Object> newMap = new HashMap<>();
			for (Entry<String,Object> entry :  map.entrySet() ) {
				processSafetyfyAtrr(entry, map, newMap);
			}
			return newMap;
		}
		return map;
	}
	
	private void processSafetyfyAtrr(Entry<String,Object> entry, Map<String,Object> map, Map<String,Object> newMap) {
		
		
		String key = entry.getKey();
		Object o = map.get(key);
		if ( o instanceof Map ) {
			o = safetyfyAttributes((Map<String,Object>)o);
		}
		else if ( o instanceof List ) {
			List<Object> newList = new ArrayList<>();
			for ( Object oo : (List<Object>)o ) {
				if ( oo instanceof Map ) {
					oo = safetyfyAttributes((Map<String, Object>)oo);
				}
				newList.add(oo);
			}
			o = newList;
			
		}
		else {
			// Do nothing
		}
		newMap.put(safeName(key), o);
	}
	
	private static String safeName( String name ) {
		if ( name.startsWith("$")) {
			name = StringUtils.UNDERSCORE + name;
		}
		return name;
	}
	
	public Document unsafetyfyAttributes( Document map) {
		
		if ( type.equalsIgnoreCase(Constants.RESOURCE_TYPE_GROUP) && map != null) {
			Document newMap = new Document();
			
			for (Entry<String,Object> entry :  map.entrySet() ) {				
				processUnSafetyfyAtrr(entry, map, newMap);
			}
			return newMap;
		}
		return map;
	}
	
	private void processUnSafetyfyAtrr(Entry<String,Object> entry, Map<String,Object> map, Map<String,Object> newMap) {
		
		String key = entry.getKey();
		Object o = map.get(key);
		if ( o instanceof Document ) {
			o = unsafetyfyAttributes((Document)o);
		}
		else if ( o instanceof List ) {
			List<Object> newList = new ArrayList<>();
			for ( Object oo : (List<Object>)o ) {
				if ( oo instanceof Document ) {
					oo = unsafetyfyAttributes((Document)oo);
				}
				newList.add(oo);
			}
			o = newList;
			
		}
		else {
			// Do nothing
		}
		
		if ( key.startsWith("_$")) {
			key = key.substring(1,key.length());
		}
		newMap.put(key, o);
	}
	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
}
