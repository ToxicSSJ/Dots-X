/*
 * 
 *	         :::::::::   :::::::: ::::::::::: ::::::::  :::    :::    
 *	       :+:    :+: :+:    :+:    :+:    :+:    :+: :+:    :+:     
 *	      +:+    +:+ +:+    +:+    +:+    +:+         +:+  +:+       
 *	     +#+    +:+ +#+    +:+    +#+    +#++:++#++   +#++:+         
 *	    +#+    +#+ +#+    +#+    +#+           +#+  +#+  +#+         
 *	   #+#    #+# #+#    #+#    #+#    #+#    #+# #+#    #+#         
 *	  #########   ########     ###     ########  ###    ###    
 * 
 * This program is free software: you can redistribute it and/or modify
 *
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.toxicnether.dotsx;

import java.io.InputStream;
import java.net.URL;

import com.toxicnether.dotsx.cgraph.GraphicAdapter;
import com.toxicnether.dotsx.console.ConsoleAdapter;
import com.toxicnether.dotsx.core.config.Configuration;
import com.toxicnether.dotsx.core.game.GameAdapter;

/**
 * 
 * Esta es la clase principal que ejecuta
 * todo el funcionamiento general del juego
 * y además tiene diferentes funciones
 * que son de gran utilidad durante la
 * interacción con recursos del juego.
 * 
 * @author Abraham Lora
 *
 */
public class DotsX {
	
	private static DotsX instance;
	
	private static InstanceType gametype = InstanceType.ANDROID_INSTANCE;
	private static GameAdapter adapter;
	
	/**
	 * 
	 * Este es el metodo principal del programa.
	 * 
	 * @param args Los argumentos con los que se
	 * iniciará el sistema. (Si existe un argumento
	 * con el nombre de 'console' se iniciará el
	 * modo consola en vez del grafico).
	 */
	public static void main(String...args) {
		
		instance = new DotsX();
		adapter = new GraphicAdapter(); // = new GraphicAdapter();
		
		for(String arg : args)
			if(arg.contains("console"))
				adapter = new ConsoleAdapter();
		
		Configuration.load();
		adapter.start();
		
	}
	
	/**
	 * 
	 * Este metodo es utilizado para recibir un archivo
	 * en los recursos del sistema en forma de InputStream.
	 * 
	 * @param resource El nombre o path del recurso.
	 * @return El InputStream de dicho recurso.
	 */
	public static InputStream getResourceAsStream(String resource) {
		return instance.getClass().getResourceAsStream(resource);
	}
	
	/**
	 * 
	 * Este metodo devuelve la URL de un recurso desde
	 * el path principal hasta donde yace el ejecutable
	 * e incluyendo el interior del mismo con el fin
	 * de extraer un Path confiable de un archivo.
	 * 
	 * @param resource El recurso.
	 * @return El Path del recurso.
	 */
	public static URL getResource(String resource) {
		return instance.getClass().getResource(resource);
	}
	
	/**
	 * 
	 * Permite recibir el ClassLoader de la
	 * instancia principal del sistema.
	 * 
	 * @return El ClassLoader.
	 * @see ClassLoader
	 */
	public static ClassLoader getLoader() {
		return instance.getClass().getClassLoader();
	}
	
	/**
	 * 
	 * Este metodo permite recibir la instancia
	 * principal del sistema para hacer uso de
	 * diferentes metodos o atributos del mismo.
	 * 
	 * @return La instancia.
	 */
	public static DotsX getInstance() {
		return instance;
	}
	
	/**
	 * 
	 * Permite recibir el tipo de instancia ejecutada
	 * por el sistema.
	 * 
	 * @return El tipo de instancia.
	 */
	public static InstanceType getGametype() {
		return gametype;
	}

	/**
	 * 
	 * Permite recibir la instancia del adaptador
	 * actual que fue ejecutado durante el inicio
	 * del sistema.
	 * 
	 * @return El adaptador.
	 * @see GameAdapter
	 */
	public static GameAdapter getAdapter() {
		return adapter;
	}

	/**
	 * 
	 * Este metodo permite determinar si el sistema
	 * operativo no es windows.
	 * 
	 * @return <strong>true</strong> si el sistema
	 * operativo no es Windows.
	 */
	public static boolean isUnix() {
		return !System.getProperty("os.name").contains("Windows");
	}
	
	/**
	 * 
	 * Este enumerador contiene los diferentes
	 * tipos de instancias validos.
	 * 
	 * @author Abraham Lora
	 *
	 */
	public static enum InstanceType {
		
		CONSOLE_INSTANCE,
		GRAPHIC_INSTANCE,
		ANDROID_INSTANCE, // TODO WIP
		
		;
		
	}
	
}
