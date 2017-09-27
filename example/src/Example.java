import pfg.config.Config;

/*
 * Copyright (C) 2013-2017 Pierre-François Gimenez
 * Distributed under the MIT License.
 */

/**
 * An example of the config library usage
 * @author Pierre-François Gimenez
 *
 */

public class Example
{
	public static void main(String[] args)
	{
		scenario1();
		System.out.println("\n");
		scenario2();
		System.out.println("\n");
		scenario3();
		System.out.println("\n");
		scenario4();
	}
	
	/**
	 * First scenario : we load the default values only
	 */
	public static void scenario1()
	{
		System.out.println("DEFAULT VALUES ONLY");
		Config config;
		config = new Config(ConfigInfoExample.values(), true);
		
		int valInteger = config.getInt(ConfigInfoExample.SOME_INTEGER_VALUE);
		System.out.println("The default integer value : " + valInteger);
	}
	
	/**
	 * Second scenario : we load the config file "config_example.ini" with the profile "default"
	 * You must specify at least one profile !
	 */
	public static void scenario2()
	{
		System.out.println("PROFILE : DEFAULT");

		Config config = new Config(ConfigInfoExample.values(), true, "config_example.ini", "default");
		
		/*
		 * There are several getters depending on the type of the variable
		 */
		double valDouble = config.getDouble(ConfigInfoExample.SOME_DOUBLE_VALUE);
		System.out.println("The double value hasn't been overridden : " + valDouble + " = " + ConfigInfoExample.SOME_DOUBLE_VALUE.getDefaultValue());

		String valString = config.getString(ConfigInfoExample.SOME_STRING_VALUE);
		System.out.println("The string value has been overridden with the config file : " + valString + " != " + ConfigInfoExample.SOME_STRING_VALUE.getDefaultValue());

		int valInteger = config.getInt(ConfigInfoExample.SOME_INTEGER_VALUE);
		System.out.println("The integer value has been overridden with the config file : " + valInteger + " != " + ConfigInfoExample.SOME_INTEGER_VALUE.getDefaultValue());

		boolean valBool = config.getBoolean(ConfigInfoExample.SOME_BOOLEAN_VALUE);
		System.out.println("The boolean value has been overridden with the config file : " + valBool + " != " + ConfigInfoExample.SOME_BOOLEAN_VALUE.getDefaultValue());

		/*
		 * We override the value of "SOME_STRING_VALUE" with "override-value"
		 */
		config.override(ConfigInfoExample.SOME_STRING_VALUE, "override-value");
		
		String newValString = config.getString(ConfigInfoExample.SOME_STRING_VALUE);
		System.out.println("The string value has been manually overridden : " + newValString + " != " + valString);		
	}
	
	/**
	 * Third scenario : we load the config file "config_example.ini" with two profiles "default" and "example"
	 * (using more than two profiles is possible)
	 * The order of the profiles is important ! The latter overrides the former.
	 */
	public static void scenario3()
	{
		System.out.println("PROFILE : DEFAULT + EXAMPLE");
		
		Config config = new Config(ConfigInfoExample.values(), true, "config_example.ini", "default", "example");
		
		int valInteger = config.getInt(ConfigInfoExample.SOME_INTEGER_VALUE);
		System.out.println("The integer value has been overridden with the profile \"example\" : " + valInteger);
	}

	public static void scenario4()
	{
		System.out.println("PROFILE : DEFAULT + ERROR");
		
		Config config = new Config(ConfigInfoExample.values(), true, "config_example.ini", "default", "error");
		
		int valInteger = config.getInt(ConfigInfoExample.SOME_INTEGER_VALUE);
		System.out.println("The integer value hasn't been overridden with the profile \"error\" because of an format error : "+valInteger);
	}

}
