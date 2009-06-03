/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito;

import org.mockito.stubbing.Answer;

/**
 * Allows mock creation with additional mock settings. 
 * <p>
 * Don't use it too often. 
 * Consider writing simple tests that use simple mocks. 
 * Repeat after me: simple tests push simple, KISSy, readable & maintainable code.
 * If you cannot write a test in a simple way - refactor the code under test.
 * <p>
 * Examples of mock settings:
 * <pre>
 *   //Creates mock with different default answer & name
 *   Foo mock = mock(Foo.class, withSettings()
 *       .defaultAnswer(RETURNS_SMART_NULLS)
 *       .name("cool mockie"));
 *       
 *   //Creates mock with different default answer, descriptive name and extra interfaces
 *   Foo mock = mock(Foo.class, withSettings()
 *       .defaultAnswer(RETURNS_SMART_NULLS)
 *       .name("cool mockie")
 *       .extraInterfaces(Bar.class));    
 * </pre>
 * {@link MockSettings} has been introduced for two reasons. 
 * Firstly, to make it easy to add another mock setting when the demand comes.
 * Secondly, to enable combining together different mock settings without introducing zillions of overloaded mock() methods.
 */
public interface MockSettings {
    
    /**
     * Specifies extra interfaces the mock should implement. Might be useful for legacy code or some corner cases.
     * For background, see issue 51 <a href="http://code.google.com/p/mockito/issues/detail?id=51">here</a>  
     * <p>
     * This mysterious feature should be used very occasionally. 
     * The object under test should know exactly its collaborators & dependencies.
     * If you happen to use it often than please make sure you are really producing simple, clean & readable code.
     * <p>   
     * Examples:
     * <pre>
     *   Foo foo = mock(Foo.class, withSettings().extraInterfaces(Bar.class, Baz.class));
     *   
     *   //now, the mock implements extra interfaces, so following casting is possible:
     *   Bar bar = (Bar) foo;
     *   Baz baz = (Baz) foo;
     * </pre>
     * 
     * @param interfaces extra interfaces the should implement.
     * @return settings instance so that you can fluently specify other settings
     */
    MockSettings extraInterfaces(Class<?>... interfaces);

    /**
     * Specifies mock name. Naming mocks can be helpful for debugging - the name is used in all verification errors. 
     * <p>
     * Beware that naming mocks is not a solution for complex code which uses too many mocks or collaborators. 
     * <b>If you have too many mocks then refactor the code</b> so that it's easy to test/debug without necessity of naming mocks.
     * <p>
     * <b>If you use &#064;Mock annotation then you've got naming mocks for free!</b> &#064;Mock uses field name as mock name. {@link Mock Read more.}
     * <p>
     * Examples:
     * <pre>
     *   Foo foo = mock(Foo.class, withSettings().name("foo"));
     *   
     *   //Below does exactly the same:
     *   Foo foo = mock(Foo.class, "foo");
     * </pre>
     * @param name the name of the mock, later used in all verification errors
     * @return settings instance so that you can fluently specify other settings
     */
    MockSettings name(String name);

    /**
     * Specifies the instance to spy on. Makes sense only for spies/partial mocks.
     * <p>
     * As usual you are going to read the partial mock warning:
     * Object oriented programming is more less tackling complexity by dividing the complexity and moving it to specific objects.
     * Partial mock is a sign that the code is not well designed. 
     * It usually means that the complexity has been moved to a different method on the same object.
     * Partial mocks are useful when dealing with code you cannot change easily (3rd party interfaces, interim refactoring of legacy code etc.)
     * I wouldn't use them for new code.
     * <p>
     * Enough warnings about partial mocks, see an example how spiedInstance() works:
     * <pre>
     *   Foo foo = mock(Foo.class, spiedInstance(fooInstance));
     *   
     *   //Below does exactly the same:
     *   Foo foo = spy(fooInstance);
     * </pre>
     * 
     * @param instance to spy on
     * @return settings instance so that you can fluently specify other settings
     */
    MockSettings spiedInstance(Object instance);

    /**
     * Specifies default answers to interactions. 
     * It's quite advanced feature and typically you don't need it to write decent tests.
     * However it can be helpful when working with legacy systems.
     * <p>
     * It is the default answer so it will be used <b>only when you don't</b> stub the method call.
     *
     * <pre>
     *   Foo mock = mock(Foo.class, withSettings().defaultAnswer(RETURNS_SMART_NULLS));
     *   Foo mockTwo = mock(Foo.class, withSettings().defaultAnswer(new YourOwnAnswer()));
     *   
     *   //Below does exactly the same:
     *   Foo mockTwo = mock(Foo.class, new YourOwnAnswer());
     * </pre>
     * 
     * @param defaultAnswer default answer to be used by mock when not stubbed
     * @return settings instance so that you can fluently specify other settings
     */
    @SuppressWarnings("unchecked")
    MockSettings defaultAnswer(Answer defaultAnswer);
}