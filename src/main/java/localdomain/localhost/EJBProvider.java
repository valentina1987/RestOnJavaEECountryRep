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

import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import java.lang.reflect.Type;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ws.rs.ext.Provider;

/**
 * This provider performs the injection and supports the @EJB annotation. 
 * This class needs to be stored in the same project as REST services.
 * 
 */

@ Provider
public   class EJBProvider   implements InjectableProvider <EJB, Type> {

	/** Technical method that tells how to create Jersey 
	 * instances of this object 
	 */
	
	public ComponentScope getScope () {
		return ComponentScope.Singleton;
	}

	/** 
	 * Method called to determine the value to inject 
	 */
	public Injectable getInjectable (ComponentContext context, EJB ejb, Type t) {
		// EJB can not be a primitive type 
		// if t is not a class, then the annotation 
		// is not loaded correctly 
		if (!(t instanceof Class)) 
			return null;

		try {
			Class clazz = (Class) t; 
			Context initialContext =   new InitialContext ();
			// The default name of our EJB is the name of the class
			String componentName = clazz.getName ();
			// If the annotation mappedName is present, then it has 
			// the name of the EJB 
			if (ejb.mappedName ()!= null) {
				componentName=ejb.mappedName() ;
			}
			// Query the directory with the name of the EJB 
			final Object ejbInstance = initialContext.lookup (componentName);
			// We finally returns an Injectable instance, according to 
			// the interface InjectableProvider 
			return   new Injectable () {
				public Object getValue () {
					return ejbInstance;
				}
			};
		}  
		catch (Exception e) {
			return null;
		}
	}
	
}