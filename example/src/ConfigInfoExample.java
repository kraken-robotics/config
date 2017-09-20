import pfg.config.ConfigInfo;

/*
 * Copyright (C) 2013-2017 Pierre-François Gimenez
 * Distributed under the MIT License.
 */

/**
 * An example of ConfigInfo implementation
 * @author Pierre-François Gimenez
 *
 */

public enum ConfigInfoExample implements ConfigInfo
{
	SOME_INTEGER_VALUE(1337),
	SOME_DOUBLE_VALUE(255.42),
	SOME_STRING_VALUE("default-value"),
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

	/**
	 * Just a getter
	 */
	@Override
	public Object getDefaultValue()
	{
		return defaultValue;
	}
	
	/**
	 * The toString() method is already adapted
	 */
}
