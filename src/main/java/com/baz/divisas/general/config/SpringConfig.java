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
package com.baz.divisas.general.config;

import com.baz.divisas.scim.rest.PatchUtils;
import com.baz.divisas.scim.schema.SchemaReader;
import com.baz.divisas.scim.util.PropertyFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
@EnableScheduling
@EnableAsync
public class SpringConfig {

	@Bean
	public PropertyFactory propertyFactory() {
		return new PropertyFactory();
	}

	@Bean
	public SchemaReader schemaReader() {
		return new SchemaReader();
	}

	@Bean
	public PatchUtils patchUtils() {
		return new PatchUtils();
	}

	@Bean
	public WebClient client() {
		return WebClient.create("");
	}

}
