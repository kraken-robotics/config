/*
 * Copyright (C) 2013-2017 Pierre-François Gimenez
 * Distributed under the MIT License.
 */

package pfg.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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
	private volatile Properties properties = new Properties();
	private boolean overloaded = false;
	private boolean loadCompleted = false;
	private ConfigInfo[] allConfigInfo = null;
	private boolean verbose;
	private String configfile;

	/**
	 * Constructor of Config
	 * @param allConfigInfo
	 * @param configfile
	 * @param verbose
	 */
	public Config(ConfigInfo[] allConfigInfo, String configfile, boolean verbose)
	{
		this.allConfigInfo = allConfigInfo;
		this.configfile = configfile;
		this.verbose = verbose;
		reload();
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
	 * Récupère un double de la config
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
	 * Méthode de récupération des paramètres de configuration
	 * 
	 * @param nom
	 * @return
	 */
	public String getString(ConfigInfo nom)
	{
		String o = properties.getProperty(nom.toString());
		if(o == null)
			System.err.println("Unknown ConfigInfo : " + nom);
		return o;
	}

	private void printChangedValues()
	{
		if(overloaded)
		{
			System.out.println("Delta configuration :");
			for(ConfigInfo info : allConfigInfo)
				if(!info.getDefaultValue().toString().equals(getString(info)))
					System.out.println(info + " = " + getString(info));
		}
		else
			System.out.println("No overloaded configuration");
	}

	/**
	 * Complete the configuration file with the default values
	 */
	private void completeConfig()
	{
		if(loadCompleted)
		{
			for(ConfigInfo info : allConfigInfo)
			{
				if(!properties.containsKey(info.toString()))
					properties.setProperty(info.toString(), info.getDefaultValue().toString());
				else if(!info.isMutable())
				{
//					if(verbose)
//						System.err.println(info + " can't be overloaded with the configuration file");
					properties.setProperty(info.toString(), info.getDefaultValue().toString());
				}
				else if(!info.getDefaultValue().equals(properties.getProperty(info.toString())))
					overloaded = true;
			}
			for(String cle : properties.stringPropertyNames())
			{
				if(cle.contains("#"))
				{
					properties.remove(cle);
					continue;
				}
				boolean found = false;
				for(ConfigInfo info : allConfigInfo)
					if(info.toString().compareTo(cle) == 0)
					{
						found = true;
						break;
					}
				if(!found)
					System.err.println("Unknown " + cle + " configuration key !");
			}
		}
		else
		{
			for(ConfigInfo info : allConfigInfo)
				properties.setProperty(info.toString(), info.getDefaultValue().toString());
		}
	}
	
	/**
	 * Reload the configuration
	 */
	public void reload()
	{
		try
		{
			FileInputStream f = new FileInputStream(configfile);
			properties.load(f);
			f.close();
			loadCompleted = true;
		}
		catch(IOException e)
		{
			System.err.println("Configuration loading error from : " + System.getProperty("user.dir") + " : " + e+". Default values loaded instead.");
		}
		completeConfig();
		if(verbose)
			printChangedValues();
	}

}
