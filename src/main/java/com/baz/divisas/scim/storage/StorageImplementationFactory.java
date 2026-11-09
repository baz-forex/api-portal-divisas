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
package com.baz.divisas.scim.storage;

import com.baz.divisas.scim.schema.Schema;
import com.baz.divisas.scim.storage.impl.MongoStorage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Map;

@Component
public class StorageImplementationFactory implements ApplicationContextAware {

	private static final Logger LOGGER = LogManager.getLogger(StorageImplementationFactory.class);

	private Map<String, Storage> storageMap;

	private ApplicationContext applicationContext;
	
	
	@Autowired
	public StorageImplementationFactory(Map<String, Storage> storageMap, ApplicationContext applicationContext) {
		super();
		this.storageMap = storageMap;
		this.applicationContext = applicationContext;
	}


	/**
	 * Gets the storage implementation
	 *
	 * @param schema the schema
	 * @return the storage
	 */
	public synchronized Storage getStorageImplementation(Schema schema) {
		
		String resourceType = schema.getName();
		
		return storageMap.computeIfAbsent(resourceType, k -> buidStorage(resourceType));

	}
	
	private Storage buidStorage(String resourceType) {
		
		Storage storage = new MongoStorage();
		AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
		factory.autowireBean(storage);
		factory.initializeBean(storage, "storage" + resourceType);
		storage.initialize(resourceType);
		LOGGER.info("storage for type {} initialized", resourceType);
		return storage;
	}


	@PreDestroy
	public void destroy() {
		//No implementado
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext){
		this.applicationContext = applicationContext;
	}
}
