/*
 * Copyright (C) 2013-2017 Pierre-François Gimenez
 * Distributed under the MIT License.
 */

package config;

/**
 * Informations accessibles par la config
 * Les informations de config.ini surchargent celles-ci
 * Certaines valeurs sont constantes, ce qui signifie qu'elles ne peuvent être
 * modifiées dynamiquement au cours d'un match.
 * Chaque variable a une valeur par défaut, afin de pouvoir lancer le programme
 * sans config.ini.
 * 
 * @author Pierre-François Gimenez
 *
 */

public enum ConfigInfoExample implements ConfigInfo
{
	
	SOME_INTEGER_VALUE(1337),
	SOME_DOUBLE_VALUE(255.42),
	SOME_STRING_VALUE("ronald"),
	SOME_BOOLEAN_VALUE(false);
	
	private Object defaultValue;

	/**
	 * Constructor with some default value
	 * @param defaultValue
	 */
	private ConfigInfoExample(Object defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	@Override
	public Object getDefaultValue()
	{
		return defaultValue;
	}

	/**
	 * For this simple example, every value can be mutated by the configuration file
	 */
	@Override
	public boolean isMutable()
	{
		return true;
	}
	
	/**
	 * The toString() method is already adapted
	 */
}
