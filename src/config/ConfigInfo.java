/*
 * Copyright (C) 2013-2017 Pierre-François Gimenez
 */


package config;

/**
 * The ConfigInfo interface : each object is a configurable value.
 * This interface can be efficiently implemented by an enum
 * @author Pierre-François Gimenez
 *
 */

public interface ConfigInfo
{
	/**
	 * This method must return the name sought in the configuration file for this ConfigInfo
	 * @return
	 */
	@Override
	public String toString();

	/**
	 * This method must return the default value of this ConfigInfo
	 * @return
	 */
	public Object getDefaultValue();
	
	/**
	 * This method must return "true" if the config manager is allowed to modified the default value
	 * @return
	 */
	public boolean isMutable();
	
}
