/*
 * Copyright 2010-2013, CloudBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package localdomain.localhost.domain;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * object Country{name,capital} the xmlRootElement annotation makes the object
 * to be represented as an xml element
 * 
 * @author valentina armenise
 * 
 */
@Entity
@XmlRootElement
public class Country implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int id;
	@Basic
	private String capital;
	@Basic
	@Column(unique = true)
	private String name;

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Country() {
	}

	public Country(String capital, String name) {
		this.capital = capital;
		this.name = name;
	}

	public String toString() {
		return "Country(id=" + id + ", capital=" + capital + ", name=" + name
				+ ")";
	}
}