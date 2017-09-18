/*
 * Copyright (C) 2013-2017 Pierre-François Gimenez
 * Distributed under the MIT License.
 */

package pfg.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;

/**
 * The configuration values are located in two places.
 * The ConfigInfo must provide a default value. A configuration file can overload this value.
 * The point of the configuration file is to change the configuration without recompiling.
 * 
 * @author Pierre-François Gimenez
 *
 */
public class Config
{
	private HashMap<ConfigInfo, Object> configValues = new HashMap<ConfigInfo, Object>();
	private ConfigInfo[] allConfigInfo = null;
	private boolean verbose;

	/**
	 * Constructor of Config. No config file.
	 * @param allConfigInfo
	 * @param verbose
	 */
	public Config(ConfigInfo[] allConfigInfo, boolean verbose)
	{
		this(allConfigInfo, null, null, verbose);
	}
	
	/**
	 * Constructor of Config with a config file.
	 * @param allConfigInfo
	 * @param configfile
	 * @param verbose
	 */
	public Config(ConfigInfo[] allConfigInfo, String configfile, String profile, boolean verbose)
	{
		this.allConfigInfo = allConfigInfo;
		this.verbose = verbose;
		
		if(configfile != null)
			readConfigFile(configfile, profile);
		
		boolean overloaded = completeConfig();
		if(verbose && overloaded)
			printChangedValues();
	}
	
	/**
	 * Put the content of the config file into the HashMap
	 * @param configfile
	 */
	private void readConfigFile(String configfile, String profile)
	{
		assert configfile != null;
		try
		{
			Ini inifile = new Ini(new File(configfile));
			Section s = (Section) inifile.get(profile);
			if(s == null)
			{
				System.err.println("Unknown config profile : "+profile+". Possible values are : "+inifile.keySet());
				return;
			}
			
			for(ConfigInfo info : allConfigInfo)
			{
				Object o = s.get(info.toString());
				if(o != null)
					configValues.put(info, o);
			}
		}
		catch(IOException e)
		{
			if(verbose)
				System.err.println("Configuration loading error from : " + System.getProperty("user.dir") + " : " + e.getMessage()+". Default values loaded instead.");
		}
	}
	
	/**
	 * Return an Object
	 * @param nom
	 * @return
	 */
	public Object getObject(ConfigInfo nom)
	{
		return configValues.get(nom);
	}
	
	/**
	 * Get an integer
	 * 
	 * @param nom
	 * @return
	 * @throws NumberFormatException
	 */
	public Integer getInt(ConfigInfo nom) throws NumberFormatException
	{
		String s = getString(nom);
		if(s != null)
			return Integer.parseInt(s);
		return null;
	}

	@SuppressWarnings("unchecked")
	public <S> S get(ConfigInfo nom, Class<S> classe)
	{
		return (S) configValues.get(nom);
	}
	
	/**
	 * Get a short
	 * 
	 * @param nom
	 * @return
	 * @throws NumberFormatException
	 */
	public Short getShort(ConfigInfo nom) throws NumberFormatException
	{
		String s = getString(nom);
		if(s != null)
			return Short.parseShort(s);
		return null;
	}

	/**
	 * Get a byte
	 * 
	 * @param nom
	 * @return
	 * @throws NumberFormatException
	 */
	public Byte getByte(ConfigInfo nom) throws NumberFormatException
	{
		String s = getString(nom);
		if(s != null)
			return Byte.parseByte(s);
		return null;
	}

	/**
	 * Get a long
	 * 
	 * @param nom
	 * @return
	 * @throws NumberFormatException
	 */
	public Long getLong(ConfigInfo nom) throws NumberFormatException
	{
		String s = getString(nom);
		if(s != null)
			return Long.parseLong(s);
		return null;
	}

	/**
	 * Get a boolean
	 * 
	 * @param nom
	 * @return
	 */
	public Boolean getBoolean(ConfigInfo nom)
	{
		String s = getString(nom);
		if(s == null)
			return null;
		
		return Boolean.parseBoolean(s);
	}

	/**
	 * Get a double
	 * 
	 * @param nom
	 * @return
	 * @throws NumberFormatException
	 */
	public Double getDouble(ConfigInfo nom) throws NumberFormatException
	{
		String s = getString(nom);
		if(s != null)
			return Double.parseDouble(s);
		return null;
	}

	/**
	 * Get a String
	 * 
	 * @param nom
	 * @return
	 */
	public String getString(ConfigInfo nom)
	{
		Object ob = configValues.get(nom);
		return ob == null ? null : ob.toString();
	}

	/**
	 * Print the difference between the current config and the default values
	 */
	public void printChangedValues()
	{
		System.out.println("Configuration diff :");
		for(ConfigInfo info : allConfigInfo)
			if(!info.getDefaultValue().equals(configValues.get(info)))
				System.out.println("  " + info + " = " + configValues.get(info) + " (default : "+info.getDefaultValue()+")");
	}

	/**
	 * Complete the configuration file with the default values
	 */
	private boolean completeConfig()
	{
		boolean overloaded = false;
		for(ConfigInfo info : allConfigInfo)
		{
			/*
			 * If the value exists, it comes from the file, which has the overriding priority
			 */
			if(!configValues.containsKey(info))
				configValues.put(info, info.getDefaultValue());
			
			if(!info.getDefaultValue().equals(configValues.get(info)))
				overloaded = true;
		}

		return overloaded;
	}
	
	/**
	 * Override some values
	 * @param override
	 */
	public void override(HashMap<ConfigInfo, Object> override)
	{
		for(ConfigInfo key : override.keySet())
			configValues.put(key, override.get(key));
	}
	
	/**
	 * Override a value
	 * @param key
	 * @param newValue
	 */
	public void override(ConfigInfo key, Object newValue)
	{
		if(key != null)
			configValues.put(key, newValue);
	}
	
}
