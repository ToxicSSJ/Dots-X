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

package com.toxicnether.dotsx.core.config;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import com.toxicnether.dotsx.console.writer.component.Variable;
import com.toxicnether.dotsx.console.writer.timelines.GameTimeline;
import com.toxicnether.dotsx.console.writer.timelines.game.BomberTimeline;
import com.toxicnether.dotsx.console.writer.timelines.game.InfiniteTimeline;
import com.toxicnether.dotsx.console.writer.timelines.game.OriginalTimeline;
import com.toxicnether.dotsx.console.writer.timelines.type.GameTimelineType;
import com.toxicnether.dotsx.core.util.FileReader;
import com.toxicnether.dotsx.desktop.nodes.operations.game.GamePane;
import com.toxicnether.dotsx.desktop.nodes.operations.game.OriginalGamePane;

/**
 * 
 * Esta clase se encarga de manejar toda
 * la configuración principal y referente
 * al juego. Desde los datos en los entornos
 * de consola  y grafico hasta los estados
 * de partidas guardadas o almacenadas en
 * ambos.
 * 
 * @author Abraham Lora
 *
 */
public class Configuration {

	private static final Map<String, String> DEFAULT_KEYS = new HashMap<String, String>();
	
	public static final String PATH = FileSystemView.getFileSystemView().getDefaultDirectory().getPath().replaceAll("\\\\", "/");
	
	public static final File CONFIG_FILE = new File(PATH + "/Sustentacion/DotsX/config.properties");
	public static final Properties CONFIG_FILE_PROP = new Properties();
	
	public static final File CONSOLE_STATE_FILE = new File(PATH + "/Sustentacion/DotsX/states/console.state");
	public static final File GRAPHIC_STATE_FILE = new File(PATH + "/Sustentacion/DotsX/states/graphic.state");
	
	static {
		
		DEFAULT_KEYS.put("dots", "0");
		DEFAULT_KEYS.put("stars", "0");
		DEFAULT_KEYS.put("highscore", "0");
		DEFAULT_KEYS.put("musicvolume", "1.0");
		DEFAULT_KEYS.put("soundsvolume", "1.0");
		DEFAULT_KEYS.put("splashlogo", "true");
		DEFAULT_KEYS.put("menuvideo", "true");
		DEFAULT_KEYS.put("lastCgame", "false");
		DEFAULT_KEYS.put("lastGgame", "false");
		DEFAULT_KEYS.put("music", "true");
		DEFAULT_KEYS.put("animations", "true");
		
		DEFAULT_KEYS.put("hotkey_w", "W");
		DEFAULT_KEYS.put("hotkey_a", "A");
		DEFAULT_KEYS.put("hotkey_s", "S");
		DEFAULT_KEYS.put("hotkey_d", "D");
		DEFAULT_KEYS.put("hotkey_p", "P");
		
		DEFAULT_KEYS.put("orsize", "S5X5");
		
	}
	
	/**
	 * 
	 * Este metodo permite cargar los archivos
	 * de la configuración principal donde se verifican
	 * si ya han sido creados con el fin de crearlos
	 * y también donde se definen las propiedades
	 * del juego en base a lo obtenido de dichos
	 * archivos.
	 * 
	 */
	public static void load() {
		
		if(!CONFIG_FILE.exists()) {
			
			try {
				
				CONFIG_FILE.getParentFile().mkdirs();
				CONFIG_FILE.createNewFile();
				
				CONSOLE_STATE_FILE.getParentFile().mkdirs();
				CONSOLE_STATE_FILE.createNewFile();
				
				GRAPHIC_STATE_FILE.getParentFile().mkdirs();
				GRAPHIC_STATE_FILE.createNewFile();
				
			} catch (HeadlessException | IOException e) {
				
				e.printStackTrace();
				
				JOptionPane.showMessageDialog(null, "Ha fallado la creación del archivo de configuración. Inicie en Administrador!");
				return;
				
			}
			
		}
		
		loadProperties();
		
		for(String key : DEFAULT_KEYS.keySet())
			if(!CONFIG_FILE_PROP.containsKey(key))
				CONFIG_FILE_PROP.setProperty(key, DEFAULT_KEYS.get(key));
			else
				DEFAULT_KEYS.put(key, CONFIG_FILE_PROP.getProperty(key));
		
		saveProperties();
		loadStates();
		return;
		
	}
	
	/**
	 * 
	 * Este metodo permite cargar los estados de
	 * partidas (Partidas Guardadas), en ambos
	 * entornos.
	 * 
	 */
	public static void loadStates() {
		
		if(!CONSOLE_STATE_FILE.exists()) {
			
			try {
				
				CONSOLE_STATE_FILE.getParentFile().mkdirs();
				CONSOLE_STATE_FILE.createNewFile();
				
			} catch (HeadlessException | IOException e) {
				
				e.printStackTrace();
				
				JOptionPane.showMessageDialog(null, "Ha fallado la creación del estado de consola. Inicie en Administrador!");
				return;
				
			}
			
		}
		
		if(!GRAPHIC_STATE_FILE.exists()) {
			
			try {
				
				GRAPHIC_STATE_FILE.getParentFile().mkdirs();
				GRAPHIC_STATE_FILE.createNewFile();
				
			} catch (HeadlessException | IOException e) {
				
				e.printStackTrace();
				
				JOptionPane.showMessageDialog(null, "Ha fallado la creación del estado de consola. Inicie en Administrador!");
				return;
				
			}
			
		}
		
	}
	
	/**
	 * 
	 * Este metodo permite cargar las propiedades
	 * del archivo de configuración principal en
	 * base al archivo almacenado en la ruta
	 * especificada por el programa.
	 * 
	 */
	private static void loadProperties() {
		
		try {
			
			InputStream stream = new FileInputStream(CONFIG_FILE);
			CONFIG_FILE_PROP.load(stream);
			
			stream.close();
			
		} catch (IOException e) {}
		
	}
	
	/**
	 * 
	 * Este metodo permite guardar las propiedades
	 * actuales que tiene el programa en el archivo
	 * de configuración general existente en la ruta
	 * especificada por el archivo.
	 * 
	 */
	private static void saveProperties() {
		
		try {
			
			OutputStream stream = new FileOutputStream(CONFIG_FILE);
			CONFIG_FILE_PROP.store(stream, null);
			
			stream.close();
			
		} catch (IOException e) {
			
			JOptionPane.showMessageDialog(null, "Ha fallado la escritura del archivo de configuración. Inicie en Administrador!");
			return;
			
		}
		
	}
	
	/**
	 * 
	 * Este metodo permite resetear o volver al
	 * estado original una llave o valor del juego.
	 * 
	 * @param key La llave a resetear.
	 */
	public static void reset(String key) {
		
		if(!DEFAULT_KEYS.containsKey(key))
			return;
		
		CONFIG_FILE_PROP.put(key, DEFAULT_KEYS.get(key));
		saveProperties();
		return;
		
	}
	
	/**
	 * 
	 * Este metodo permite definir la llave y valor
	 * de una configuración existente. Cabe recalcar
	 * que también actualiza automaticamente el archivo
	 * local.
	 * 
	 * @param key La llave.
	 * @param value El valor.
	 */
	public static void set(String key, String value) {
		
		if(!DEFAULT_KEYS.containsKey(key))
			return;
		
		CONFIG_FILE_PROP.put(key, value);
		saveProperties();
		return;
		
	}
	
	/**
	 * 
	 * Este metodo devuelve un valor de
	 * las propiedades en base a la llave.
	 * 
	 * @param key La llave.
	 * @return El valor delegado a esa llave.
	 */
	public static String get(String key) {
		
		if(!DEFAULT_KEYS.containsKey(key))
			return "$ERROR";
		
		loadProperties();
		
		return CONFIG_FILE_PROP.getProperty(key);
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir un booleano
	 * en base a una llave, se considera que
	 * el valor pedido si es un booleano por tanto
	 * puede producirse un error en caso de que no
	 * lo sea.
	 * 
	 * @param key La llave.
	 * @return El valor booleano de la llave.
	 */
	public static boolean getBoolean(String key) {
		
		if(!DEFAULT_KEYS.containsKey(key))
			return false;
		
		loadProperties();
		
		return Boolean.valueOf(CONFIG_FILE_PROP.getProperty(key));
		
	}
	
	/**
	 * 
	 * Este metodo permite crear un archivo en base
	 * a un nombre dado con el fin de almacenar datos
	 * de partidas.
	 * 
	 * @param stateFile El archivo donde se guardará
	 * la información.
	 * @param state El nombre del archivo.
	 */
	public static void setState(File stateFile, String state) {
		
		if(stateFile.equals(CONSOLE_STATE_FILE) || stateFile.equals(GRAPHIC_STATE_FILE)) {
			
			try {
				
				Files.write(Paths.get(stateFile.getPath()), state.getBytes());
				
				CONFIG_FILE_PROP.put(stateFile.equals(CONSOLE_STATE_FILE) ? "lastCgame" : "lastGgame", "true");
				saveProperties();
				return;
				
			} catch (IOException e) { return; }
			
		}
		
	}
	
	/**
	 * 
	 * Este metodo permite recuperar la partida
	 * guardada en el entorno consola, donde
	 * se reciben todos los datos y son procesados
	 * para definir el tipo de partida y reponerla
	 * en función a dicha pre-información.
	 * 
	 * @return El GameTimeline especifico y listo
	 * para ser ejecutado.
	 */
	public static GameTimeline getConsoleState() {
		
		if(CONFIG_FILE_PROP.get("lastCgame").equals("false"))
			return null;
		
		GameTimeline game = null;
		LinkedList<String> state = FileReader.readFile(CONSOLE_STATE_FILE.getPath(), false);
		
		switch(GameTimelineType.getType(state)) {
		
			case BOMBER:
				
				game = BomberTimeline.continueGame(state);
				break;
				
			case ORIGINAL:
				
				game = OriginalTimeline.continueGame(state);
				break;
				
			case UNKNOW:
				break;
				
			case X:
				
				game = InfiniteTimeline.continueGame(state);
				break;
				
			default:
				break;
			
		}
		
		CONFIG_FILE_PROP.put("lastCgame", "false");
		saveProperties();
		return game;
		
	}
	
	/**
	 * 
	 * Este metodo permite guardar una partida
	 * para el modo grafico donde se maneja el Path
	 * de los estados para el modo grafico con el
	 * fin de ejercer la creación del archivo.
	 * 
	 * @param name El nombre del archivo.
	 * @param pane El panel de juego que se desea
	 * guardar.
	 */
	public static void saveGraphicGame(String name, GamePane pane) {
		
		File file = new File(PATH + "/DotsX/states/graphic/" + name);
		
		if(!file.exists()) {
			
			try {
				
				file.getParentFile().mkdirs();
				file.createNewFile();
				
			} catch (HeadlessException | IOException e) {
				
				e.printStackTrace();
				
				JOptionPane.showMessageDialog(null, "Ha fallado la creación del estado grafico. Inicie en Administrador!");
				return;
				
			}
			
		}
		
		try {
			
			pane.setSaveName(name);
			Files.write(Paths.get(file.getPath()), String.join("\n", pane.getData(name)).getBytes());
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			JOptionPane.showMessageDialog(null, "Ha fallado la creación del estado grafico. Inicie en Administrador!");
			return;
			
		}
		
	}
	
	/**
	 * 
	 * Este metodo permite cargar una partida en base
	 * a su nombre, este nombre debe existir ya que es
	 * utilizado para recibir el archivo. Este metodo
	 * solo aplica para el contexto Grafico.
	 * 
	 * @param name El nombre de la partida guardada.
	 * @return El panel de juego con los objetos precargados
	 * para ser inicializado.
	 */
	public static OriginalGamePane loadOriginalGame(String name) {
		
		File file = new File(PATH + "/DotsX/states/graphic/" + name);
		
		if(!file.exists())
			return null;
		
		OriginalGamePane pane = OriginalGamePane.makeInstance(FileReader.readFile(PATH + "/DotsX/states/graphic/" + name, false));
		return pane;
		
	}
	
	/**
	 * 
	 * Este metodo permite remover una partida guardada
	 * por si el usuario quiere finalizarla en un momento
	 * dado.
	 * 
	 * @param name El nombre del archivo previamente
	 * guardado.
	 */
	public static void removeOriginalGame(String name) {
		
		if(name.equals("$null"))
			return;
		
		File file = new File(PATH + "/DotsX/states/graphic/" + name);
		
		if(file.exists())
			file.delete();
		
		return;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir el nombre de las
	 * partidas que han sido guardadas. Se resalta que
	 * para la función de este metodo se hace uso
	 * de algunos metodos de Java 8.
	 * 
	 * @return Una lista con todos los nombres de las
	 * partidas.
	 */
	public static LinkedList<String> loadGraphicGames() {
		
		LinkedList<String> saved = new LinkedList<String>();
		
		try (Stream<Path> paths = Files.walk(Paths.get(PATH + "/DotsX/states/graphic/"))) {
			
		    paths.filter(Files::isRegularFile)
		         .forEach(e -> saved.add(e.getFileName().toString()));
		    
		} catch (IOException e) {
			
			e.printStackTrace();
			
			JOptionPane.showMessageDialog(null, "Ha fallado la carga de los estados graficos. Inicie en Administrador!");
			return saved;
			
		}
		
		return saved;
		
	}
	
	/**
	 * 
	 * Este metodo permite devolver una lista
	 * con las variables definidas en la configuración
	 * para las Hotkeys del contexto consola.
	 * 
	 * @return Una lista con todas las variables.
	 */
	public static LinkedList<Variable> getHotkeys() {
		
		LinkedList<Variable> hotkeys = new LinkedList<Variable>();
		
		hotkeys.add(new Variable("%hotkey_w", Configuration.get("hotkey_w")));
		hotkeys.add(new Variable("%hotkey_a", Configuration.get("hotkey_a")));
		hotkeys.add(new Variable("%hotkey_s", Configuration.get("hotkey_s")));
		hotkeys.add(new Variable("%hotkey_d", Configuration.get("hotkey_d")));
		hotkeys.add(new Variable("%hotkey_p", Configuration.get("hotkey_p")));
		
		return hotkeys;
		
	}
	
}
