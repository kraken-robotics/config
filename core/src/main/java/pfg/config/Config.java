/*
 * Copyright (C) 2013-2017 Pierre-François Gimenez
 * Distributed under the MIT License.
 */

package pfg.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;

/**
 * The configuration values are located in two places.
 * The ConfigInfo enum provides default values. A configuration file can overload these values.
 * The point of the configuration file is to change the configuration without recompiling.
 * 
 * @author Pierre-François Gimenez
 *
 */
public class Config
{
	private HashMap<ConfigInfo, Object> configValues = new HashMap<ConfigInfo, Object>();
	private List<ConfigInfo> allConfigInfo = new ArrayList<ConfigInfo>();
	private boolean verbose;

	/**
	 * Constructor of Config. No config file.
	 * @param allConfigInfo
	 * @param verbose
	 */
	public Config(ConfigInfo[] allConfigInfo, boolean verbose)
	{
		this(allConfigInfo, verbose, null, (String) null);
	}
	
	/**
	 * If you provide a config filename, you should provide at least one profile.
	 * DON'T USE this constructor !
	 * @param allConfigInfo
	 * @param verbose
	 * @param configfile
	 */
	@Deprecated
	public Config(ConfigInfo[] allConfigInfo, boolean verbose, String configfile)
	{
		throw new IllegalArgumentException("Please provide at least one profile !");
	}
	
	/**
	 * Constructor of Config with a config file.
	 * The last profiles override the first profiles
	 * @param allConfigInfo
	 * @param configfile
	 * @param profiles
	 * @param verbose
	 */
	public Config(ConfigInfo[] allConfigInfo, boolean verbose, String configfile, String... profiles)
	{
		for(ConfigInfo info : allConfigInfo)
			this.allConfigInfo.add(info);

		this.verbose = verbose;
		
		if(configfile != null)
			readConfigFile(configfile, profiles);
		
		boolean overloaded = completeConfig();
		if(verbose && overloaded)
			printChangedValues();
	}
	
	/**
	 * Put the content of the config file into the HashMap
	 * @param configfile
	 */
	private void readConfigFile(String configfile, String[] profiles)
	{
		try
		{
			InputStream is = getClass().getClassLoader().getResourceAsStream(configfile);
			if(is != null)
			{
				if(verbose)
					System.out.println("Loading config file : "+getClass().getClassLoader().getResource(configfile));
			}
			else
			{
				is = new FileInputStream(new File(configfile));
				if(verbose)
					System.out.println("Loading config file : "+System.getProperty("user.dir")+"/"+configfile);
			}
			Ini inifile = new Ini(is);
			if(profiles != null && profiles.length > 0)
				for(String profile : profiles)
				{
					Section s = (Section) inifile.get(profile);
					if(s == null)
					{
						if(verbose)
							System.err.println("Unknown config profile : "+profile+". Possible values are : "+inifile.keySet());
						continue;
					}

					for(Object st : s.keySet())
					{
						boolean ok = false;
						for(ConfigInfo info : allConfigInfo)
							if(info.toString().toLowerCase().equals(st.toString().toLowerCase()))
							{
								configValues.put(info, s.get(st));
								ok = true;
								break;
							}
						if(!ok && verbose)
							System.err.println("Unknown key : "+st);
					}
					
				}
			else
				throw new IllegalArgumentException("Please provide at least one profile !");
		}
		catch(IOException e)
		{
			if(verbose)
				System.err.println("Configuration loading error from " + System.getProperty("user.dir") + " : " + e.getMessage()+". Default values loaded instead.");
		}
	}
	
	/**
	 * Return an Object
	 * @param nom
	 * @return
	 */
	public Object getObject(ConfigInfo nom)
	{
		if(!allConfigInfo.contains(nom))
			throw new IllegalArgumentException("Unknown configuration key : "+nom);
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
		try {
			String s = getString(nom);
			if(s != null)
				return Integer.parseInt(s);
		} catch(NumberFormatException e)
		{
			if(verbose)
				System.err.println(e);
			return Integer.parseInt(nom.getDefaultValue().toString());
		}
		return null;
	}

	/**
	 * Get an object cast to a certain class
	 * @param nom
	 * @param clazz
	 * @return
	 */
	public <S> S get(ConfigInfo nom, Class<S> clazz)
	{
		if(!allConfigInfo.contains(nom))
			throw new IllegalArgumentException("Unknown configuration key : "+nom);
		return clazz.cast(configValues.get(nom));
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
		try {
			String s = getString(nom);
			if(s != null)
				return Short.parseShort(s);
		} catch(NumberFormatException e)
		{
			if(verbose)
				System.err.println(e);
			return Short.parseShort(nom.getDefaultValue().toString());
		}
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
		try {
			String s = getString(nom);
			if(s != null)
				return Byte.parseByte(s);
		} catch(NumberFormatException e)
		{
			if(verbose)
				System.err.println(e);
			return Byte.parseByte(nom.getDefaultValue().toString());
		}		return null;
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
		try {
			String s = getString(nom);
			if(s != null)
				return Long.parseLong(s);
		} catch(NumberFormatException e)
		{
			if(verbose)
				System.err.println(e);
			return Long.parseLong(nom.getDefaultValue().toString());
		}
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
		if(s != null)
			return Boolean.parseBoolean(s);
		return null;
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
		try {
			String s = getString(nom);
			if(s != null)
				return Double.parseDouble(s);
		} catch(NumberFormatException e)
		{
			if(verbose)
				System.err.println(e);
			return Double.parseDouble(nom.getDefaultValue().toString());
		}
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
		if(!allConfigInfo.contains(nom))
			throw new IllegalArgumentException("Unknown configuration key : "+nom);
		Object ob = configValues.get(nom);
		return ob == null ? null : ob.toString();
	}

	/**
	 * Print the difference between the current config and the default values
	 */
	public void printChangedValues()
	{
		boolean any = false;
		System.out.println("Configuration diff :");
		for(ConfigInfo info : allConfigInfo)
			if(!info.getDefaultValue().equals(configValues.get(info)))
			{
				System.out.println("  " + info + " = " + configValues.get(info) + " (default : "+info.getDefaultValue()+")");
				any = true;
			}
		if(!any)
			System.out.println("	(no difference)");
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
	 * Override some values with a HashMap
	 * @param override
	 */
	public void override(HashMap<ConfigInfo, Object> override)
	{
		for(ConfigInfo key : override.keySet())
		{
			if(!allConfigInfo.contains(key))
				throw new IllegalArgumentException("Unknown configuration key : "+key);
			configValues.put(key, override.get(key));
		}
	}
	
	/**
	 * Override a value
	 * @param key
	 * @param newValue
	 */
	public void override(ConfigInfo key, Object newValue)
	{
		if(key != null)
		{
			if(!allConfigInfo.contains(key))
				throw new IllegalArgumentException("Unknown configuration key : "+key);
			configValues.put(key, newValue);
		}
	}
	
}
