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

package localdomain.localhost;

import java.util.List;

import javax.ejb.*;
import javax.persistence.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import localdomain.localhost.domain.Country;

/**
 * This is a simple Session Bean that accesses the Entity trough the
 * EntityManager. The @PersistenceContext annotation tells the JBoss Server to
 * inject an entity manager during deployment.
 * 
 * @author valentina armenise
 */

@Stateless
public class CountryRepository {
	Logger logger = LoggerFactory.getLogger(CountryRepository.class);
	@PersistenceContext
	private EntityManager em;

	public void create(Country... clist) {
		for (Country c : clist) {
			em.persist(c);
		}
	}

	public void upadte(Country country) {

		String name = country.getName();
		Query q = em
				.createQuery(
						"select country from Country country where country.name = :name")
				.setParameter("name", name);
		Country old = (Country) q.getSingleResult();
		if (!old.equals(null)) {
			old.setCapital(country.getCapital());
		}

	}

	public Country findByName(String name) throws NonUniqueResultException,
			EntityNotFoundException {

		Query q = em
				.createQuery(
						"select country from Country country where country.name = :name")
				.setParameter("name", name);
		return (Country) q.getSingleResult();

	}

	public void delete(String name) {

		Query q = em.createQuery(
				"delete from Country country where country.name = :name")
				.setParameter("name", name);
		q.executeUpdate();

	}

	public List<Country> getAll() {

		Query q = em.createQuery("select country from Country country");
		List<Country> countries = (List<Country>) q.getResultList();
		return countries;

	}

}