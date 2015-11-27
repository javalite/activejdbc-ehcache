/*
Copyright 2009-2014 Igor Polevoy

Licensed under the Apache License, Version 2.0 (the "License"); 
you may not use this file except in compliance with the License. 
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0 

Unless required by applicable law or agreed to in writing, software 
distributed under the License is distributed on an "AS IS" BASIS, 
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
See the License for the specific language governing permissions and 
limitations under the License. 
*/

package org.javalite.ehcache_test;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.javalite.test.jspec.JSpec.a;

/**
 * @author Igor Polevoy
 */
public class EHCacheSpec {

    @Before
    public void before() {
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/activejdbc", "root", "p@ssw0rd");
        Base.exec("delete from people");
        for (int i = 0; i < 100; i++) {
            Person.create("name", "name: " + i, "last_name", "last_name: " + i, "dob", "1935-12-06").saveIt();
        }

    }

    @After
    public void after() {
        Base.close();
    }

    @Test
    public void shouldHitCache() {
        List<Person> people1 = Person.findAll();
        List<Person> people2 = Person.findAll();
        a(people1.get(0)).shouldBeTheSameAs(people2.get(0));
    }
}
