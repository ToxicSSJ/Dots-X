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

package com.toxicnether.dotsx.desktop.nodes.operations.game;

import java.util.LinkedList;

import com.toxicnether.dotsx.desktop.nodes.component.game.GameDot;
import com.toxicnether.dotsx.desktop.nodes.operations.config.object.GameSizes;

/**
 * 
 * Esta clase es la encargada de la ejecución
 * general del modo original. También es fundamental
 * para la función del guardado de los datos y para
 * la ejecución del estado almacenado.
 * 
 * @author Abraham Lora
 *
 */
public class OriginalGamePane extends GamePane {
	
	private static final long serialVersionUID = -3603494247301313521L;

	private static final String SIZE_ID = "SIZE: ";
	private static final String NAME_ID = "NAME: ";
	private static final String REMOVE_POWERUP_ID = "RPOWERUP: ";
	private static final String CHANGE_POWERUP_ID = "CPOWERUP: ";
	private static final String DELALL_POWERUP_ID = "DPOWERUP: ";
	private static final String MODE_ID = "MODE: ";
	private static final String SCORE_ID = "SCORE: ";
	private static final String STARS_ID = "STARS: ";
	private static final String DOT_ID = "DOT: ";
	
	public OriginalGameSizes sizes = OriginalGameSizes.S5X5;
	public String saveName = "$null";
	
	/**
	 * 
	 * Este constructor recibe como parametro el
	 * enumerador que en base a su tamaño predefinido
	 * se crea el panel general del tablero.
	 * 
	 * @param size El tamaño de los componentes principales
	 * del juego.
	 * @see OriginalGameSizes
	 */
	public OriginalGamePane(OriginalGameSizes size) {
		super(size.toSize());
		
		this.sizes = size;
		
	}
	
	/**
	 * 
	 * Este constructor utiliza un conjunto de datos
	 * que son leídos y procesados por ciertos
	 * metodos para inicializar un estado de partida
	 * guardado.
	 * 
	 * @param data El conjunto de datos.
	 */
	public OriginalGamePane(LinkedList<String> data) {
		super(getSize(data).toSize());
		
		this.sizes = getSize(data);
		this.setSaveName(getName(data));
		
		LinkedList<GameDot> cache = OriginalGamePane.getDots(data, sizes);
		int index = 0;
		
		for(GameDot value : cache)
			getDots().get(index++).setType(value.getColorType());
		
		getScore().set(getScore(data));
		getStars().set(getStars(data));
		
		getUsablePU().get(0).setQuantity(getRemovePowerUpQuantity(data));
		getUsablePU().get(1).setQuantity(getChangePowerUpQuantity(data));
		getUsablePU().get(2).setQuantity(getDelAllPowerUpQuantity(data));
		
	}
	
	/**
	 * @return Este metodo devuelve el nombre
	 * del archivo donde se guardan los datos de
	 * la partida.
	 */
	@Override
	public String saveName() {
		return saveName;
	}
	
	/**
	 * 
	 * Este metodo permite redefinir el nombre del
	 * archivo donde se debe guardar la partida.
	 * 
	 * @param name El nombre de la partida.
	 */
	@Override
	public void setSaveName(String name) {
		
		this.saveName = name;
		return;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir los datos de
	 * la partida actuales en base al nombre
	 * que se utilizará para el archivo.
	 * 
	 * @param name El nombre del archivo y de la
	 * partida.
	 */
	@Override
	public LinkedList<String> getData(String name) {
		
		LinkedList<String> data = new LinkedList<String>();
		
		data.add(SIZE_ID + sizes.name());
		data.add(NAME_ID + name);
		data.add(MODE_ID + "ORIGINAL");
		data.add(SCORE_ID + getScore().get());
		data.add(REMOVE_POWERUP_ID + getUsablePU().get(0).getQuantity());
		data.add(CHANGE_POWERUP_ID + getUsablePU().get(1).getQuantity());
		data.add(DELALL_POWERUP_ID + getUsablePU().get(2).getQuantity());
		data.add(STARS_ID + getStars().get());
		
		for(GameDot dot : getDots())
			data.add(DOT_ID + GameDot.toID(dot));
		
		return data;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir instancias generadas
	 * de dots que se crean a partir de cadenas de caracteres
	 * y también en base a los tamaños predefinidos sobre los
	 * cuales se deben cargar.
	 * 
	 * @param data Los datos de la partida.
	 * @param sizes El tamaño del tablero donde se colocarán
	 * los dots.
	 * @return La lista de las instancias definidas de los
	 * dots.
	 */
	public static LinkedList<GameDot> getDots(LinkedList<String> data, OriginalGameSizes sizes) {
		
		LinkedList<GameDot> dots = new LinkedList<GameDot>();
		
		for(String info : data)
			if(info.startsWith(DOT_ID))
				dots.add(GameDot.getDot(sizes.getDotSize(), info.replaceAll(DOT_ID, "")));
		
		return dots;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir el tamaño de la
	 * partida en base a los datos previamente
	 * guardados.
	 * 
	 * @param data Los datos de la partida.
	 * @return El tamaño de la partida.
	 * @see OriginalGameSizes
	 */
	public static OriginalGameSizes getSize(LinkedList<String> data) {
		
		OriginalGameSizes size = OriginalGameSizes.S5X5;
		
		for(String info : data)
			if(info.startsWith(SIZE_ID)) {
				size = OriginalGameSizes.getSize(info.replaceAll(SIZE_ID, ""));
				break;
			}
		
		return size;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir la cantidad
	 * del PowerUp tipo 'RemovePowerUp'
	 * que se almacena en los datos rescatados de
	 * la partida.
	 * 
	 * @param data Los datos de la partida.
	 * @return La cantidad existente de ese PowerUp.
	 */
	public static int getRemovePowerUpQuantity(LinkedList<String> data) {
		
		for(String info : data)
			if(info.startsWith(REMOVE_POWERUP_ID))
				return Integer.valueOf(info.replaceAll(REMOVE_POWERUP_ID, ""));
		
		return 0;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir la cantidad
	 * del PowerUp tipo 'ChangePowerUp'
	 * que se almacena en los datos rescatados de
	 * la partida.
	 * 
	 * @param data Los datos de la partida.
	 * @return La cantidad existente de ese PowerUp.
	 */
	public static int getChangePowerUpQuantity(LinkedList<String> data) {
		
		for(String info : data)
			if(info.startsWith(CHANGE_POWERUP_ID))
				return Integer.valueOf(info.replaceAll(CHANGE_POWERUP_ID, ""));
		
		return 0;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir la cantidad
	 * del PowerUp tipo 'DeleteAllPowerUp'
	 * que se almacena en los datos rescatados de
	 * la partida.
	 * 
	 * @param data Los datos de la partida.
	 * @return La cantidad existente de ese PowerUp.
	 */
	public static int getDelAllPowerUpQuantity(LinkedList<String> data) {
		
		for(String info : data)
			if(info.startsWith(DELALL_POWERUP_ID))
				return Integer.valueOf(info.replaceAll(DELALL_POWERUP_ID, ""));
		
		return 0;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir el score de la
	 * partida que se almacena en los datos rescatados.
	 * 
	 * @param data Los datos de la partida.
	 * @return El score de la partida desde su guardado.
	 */
	public static int getScore(LinkedList<String> data) {
		
		for(String info : data)
			if(info.startsWith(SCORE_ID))
				return Integer.valueOf(info.replaceAll(SCORE_ID, ""));
		
		return 0;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir las estrellas de la
	 * partida que se almacena en los datos rescatados.
	 * 
	 * @param data Los datos de la partida.
	 * @return Las estrellas de la partida desde su guardado.
	 */
	public static int getStars(LinkedList<String> data) {
		
		for(String info : data)
			if(info.startsWith(STARS_ID))
				return Integer.valueOf(info.replaceAll(STARS_ID, ""));
		
		return 0;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir el nombre de la partida
	 * en base a los datos almacenados del juego.
	 * 
	 * @param data Los datos de la partida.
	 * @return El nombre de la partida desde su guardado.
	 */
	public static String getName(LinkedList<String> data) {
		
		for(String info : data)
			if(info.startsWith(NAME_ID))
				return String.valueOf(info.replaceAll(NAME_ID, ""));
		
		return "$null";
		
	}

	/**
	 * 
	 * Este enumerador contiene los tipos
	 * de tamaños validos para el modo de
	 * juego Original.
	 * 
	 * @author Abraham Lora
	 */
	public static enum OriginalGameSizes {
		
		S5X5(5, 25, 30, 35),
		S6X6(6, 23, 20, 30),
		S7X7(7, 18, 20, 20)

		;
		
		private int size, dotsize, spacing, linesize;
		
		/**
		 * 
		 * Este constructor debe contener los atributos
		 * mas importantes para la elaboración del GRID, donde
		 * estarán los Dots y donde se llevará a acabo toda la
		 * modalidad original.
		 * 
		 * @param size El tamaño de la proporción (px).
		 * @param dotsize El tamaño de las fichas (px).
		 * @param spacing El tamaño de los espacios entre
		 * fichas (px).
		 * @param linesize El tamaño de la linea (px).
		 */
		OriginalGameSizes(int size, int dotsize, int spacing, int linesize){
			
			this.size = size;
			this.dotsize = dotsize;
			this.spacing = spacing;
			this.linesize = linesize;
			
		}

		/**
		 * @return El tamaño de la proporción.
		 */
		public int getSize() {
			return size;
		}

		/**
		 * @return El tamaño de las fichas.
		 */
		public int getDotSize() {
			return dotsize;
		}
		
		/**
		 * @return El tamaño de los espacios entre
		 * fichas.
		 */
		public int getSpacing() {
			return spacing;
		}
		
		/**
		 * @return El tamaño de las lineas entre
		 * fichas.
		 */
		public int getLineSize() {
			return linesize;
		}
		
		/**
		 * @return El texto para la previsualización
		 * de la proporción.
		 */
		public String getVisual() {
			return size + "x" + size;
		}
		
		/**
		 * 
		 * Este metodo permite convertir el tipo de
		 * tamaño actual en una instancia del tamaño
		 * general que posee un tablero para así poder
		 * ser utilizado en el panel de juego.
		 * 
		 * @return Una instancia con los valores definidos.
		 * @see GameSizes
		 */
		public GameSizes toSize() {
			return new GameSizes(size, dotsize, spacing, linesize);
		}
		
		/**
		 * 
		 * Este metodo estatico proporciona una enumeración
		 * en base al nombre o identificador del mismo.
		 * 
		 * @param id El nombre o identificador a comparar.
		 * @return La enumeración.
		 */
		public static OriginalGameSizes getSize(String id) {
			
			for(OriginalGameSizes size : OriginalGameSizes.values())
				if(size.name().toLowerCase().contains(id.toLowerCase()))
					return size;
			
			return OriginalGameSizes.S5X5;
			
		}
		
	}
	
	/**
	 * 
	 * Este metodo permite crear una instancia del
	 * modo Original en base a los datos
	 * pre-guardados del juego.
	 * 
	 * @param data Los datos pre-guardados del juego.
	 * @return Una instancia del del panel de juego
	 * original.
	 */
	public static OriginalGamePane makeInstance(LinkedList<String> data) {
		return new OriginalGamePane(data);
	}
	
	/**
	 * 
	 * Este metodo permite crear una instancia de
	 * un panel de juego en base a los tamaños definidos
	 * durante su creación.
	 * 
	 * @param size El tamaño del juego.
	 * @return El panel del juego ya inicializado.
	 */
	public static GamePane makeInstance(OriginalGameSizes size) {
		return new OriginalGamePane(size);
	}
	
}
