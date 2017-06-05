import config.Config;

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
		Config config = new Config(ConfigInfoExample.values(), "config_example.ini", true);
		double valDouble = config.getDouble(ConfigInfoExample.SOME_DOUBLE_VALUE);
		System.out.println("The double value hasn't been overridden : " + valDouble + " = " + ConfigInfoExample.SOME_DOUBLE_VALUE.getDefaultValue());
		boolean valBool = config.getBoolean(ConfigInfoExample.SOME_BOOLEAN_VALUE);
		System.out.println("The boolean value has been overridden : " + valBool + " != " + ConfigInfoExample.SOME_BOOLEAN_VALUE.getDefaultValue());
	}

}
