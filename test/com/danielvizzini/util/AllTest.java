package com.danielvizzini.util;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DateUtilTest.class, FileUtilTest.class, InstantiationUtilTest.class, IterableUtilTest.class,
		KnapsackTest.class, MiscUtilTest.class })
public class AllTest {

}