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

package com.toxicnether.dotsx.core.sound;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.toxicnether.dotsx.DotsX;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * 
 * Esta clase contiene las funciones primordiales
 * que se pueden utilizar para la creación y posterior
 * reproducción de sonidos por medio del Framework JavaFX.
 * 
 * @author Abraham Lora
 *
 */
public class SoundPlay implements Serializable {

	private static final long serialVersionUID = 2588877517813426436L;

	private static final List<SoundPlay> play = new ArrayList<SoundPlay>();
	public static final JFXPanel fxPanel = new JFXPanel();
	
	private Media media;
	private MediaPlayer player;
	
	private static double volume = 1.0;
	
	/**
	 * 
	 * Este constructor permite cargar los objetos principales
	 * para la generación posterior de sonidos en base al archivo
	 * que se ha utilizado. Se debe introducir el path del archivo
	 * a partir de los recursos que componen la build para así
	 * poder ser cargado y reproducido.
	 * 
	 * @param audioPath La ruta del audio en los recursos.
	 */
	public SoundPlay(String audioPath) {
		
		this.media = new Media(DotsX.getResource(audioPath).toExternalForm());
		this.player = new MediaPlayer(media);
		
		setVolume(volume);
		
		player.onEndOfMediaProperty().addListener(e -> {
			play.remove(this);
		});
		
		play.add(this);
		
	}
	
	/**
	 * 
	 * Reproduce el sonido.
	 * 
	 */
    public void play() {
    	player.play();
    }
    
    /**
     * 
     * Finaliza el sonido.
     * 
     */
    public void stop() {
    	player.stop();
    }
    
    /**
     * 
     * Define el volumen para el sonido actual
     * sin dejarse alterar por el global si este
     * fue cambiado con anterioridad.
     * 
     * @param value El nuevo valor del volumen.
     * @return La instancia.
     */
    public SoundPlay setVolume(double value) {
    	player.setVolume(value);
    	return this;
    }
    
    /**
     * 
     * Define la velocidad a la que se reproduce
     * el sonido.
     * 
     * @param value El nuevo valor de velocidad del
     * sonido.
     * @return La instancia.
     */
    public SoundPlay setRate(double value) {
    	player.setRate(value);
    	return this;
    }
    
    /**
     * 
     * Este metodo permite definir el volumen global
     * de los sonidos que se están reproduciendo durante
     * el momento de la definición.
     * 
     * @param value El nuevo volumen.
     */
    public static void setGlobalVolume(double value) {
    	
    	volume = value;
    	
    	for(SoundPlay play : play)
    		play.setVolume(value);
    	
    }
    
}
