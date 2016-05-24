package org.kombit.mapper;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class KommuneLosMapper {
	private static Map<String, KommuneLosMapper> singleton = new ConcurrentHashMap<String, KommuneLosMapper>();
	
	private Map<UUID, String> kommuneLosMap = new ConcurrentHashMap<UUID, String>();
	
	@SuppressWarnings("unchecked")
	private KommuneLosMapper(String kommuneConfigFile) throws FileNotFoundException {
		XMLDecoder decoder = null;
		try {
			decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(kommuneConfigFile)));
			kommuneLosMap = (Map<UUID, String>) decoder.readObject();
		}
		finally {
			if (decoder != null) decoder.close(); 
		}
	}
	
	public static KommuneLosMapper getInstance(String kommuneConfigFile) throws FileNotFoundException
	{
		if (singleton.get(kommuneConfigFile) == null)
			synchronized(KommuneLosMapper.class) {
				singleton.put(kommuneConfigFile, new KommuneLosMapper(kommuneConfigFile));
			}
		return singleton.get(kommuneConfigFile);
	}
	
	public String getLosId(UUID key)
	{
		return kommuneLosMap.get(key);
	}
}
