/*
 * Copyright (C) 2013-2017 Pierre-François Gimenez
 * Distributed under the MIT License.
 */

package pfg.config;

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
	
}
