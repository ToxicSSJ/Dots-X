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
 * Esta clase contiene todos los metodos y atributos
 * importantes para la reproducción de musica durante
 * el juego.
 * 
 * @author Abraham Lora
 *
 */
public class MusicPlay implements Serializable {

	private static final long serialVersionUID = -1646154528387216409L;
	
	private static final List<MusicPlay> play = new ArrayList<MusicPlay>();
	public static final JFXPanel fxPanel = new JFXPanel();
	
	private String audioPath;
	
	private Media media;
	private MediaPlayer player;
	
	private static double volume = 1.0;
	
	/**
	 * 
	 * Este constructor permite realizar una
	 * instancia en base a la ruta de un archivo
	 * de sonido que debe estar incluída en los
	 * recursos del proyecto.
	 * 
	 * @param audioPath La ruta de la canción.
	 */
	public MusicPlay(String audioPath) {
		
		this.audioPath = audioPath;
		
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
	 * Este metodo permite inicializar la canción.
	 * 
	 */
    public void play() {
    	player.play();
    }
    
    /**
     * 
     * Este metodo permite finalizar el audio.
     * 
     */
    public void stop() {
    	player.stop();
    }
    
    /**
     * 
     * Este metodo permite adjuntar una acción
     * al finalizar la reproducción de la
     * canción.
     * 
     * @param runnable La acción desde una interfaz
     * Runnable común.
     * @see Runnable
     */
    public void attach(Runnable runnable) {
    	player.setOnEndOfMedia(runnable);
    }
    
    /**
     * 
     * Este metodo permite definir el volumen
     * del reproductor sin verse afectado por
     * la última definición global del volumen
     * de la canción.
     * 
     */
    public void setVolume(double value) {
    	player.setVolume(value);
    }
    
    /**
     * 
     * Este metodo permite definir el volumen
     * global de todas las canciones a futuro
     * y actuales.
     * 
     * @param value El valor del volumen global.
     */
    public static void setGlobalVolume(double value) {
    	
    	volume = value;
    	
    	for(MusicPlay play : play) {
    		
    		if(volume == 0.0)
    			play.player.setMute(true);
    		else
    			play.player.setMute(false);
    		
    		play.player.setVolume(value);
    		play.player.volumeProperty().set(value);
    		
    	}
    	
    }

    /**
     * 
     * Este metodo devuelve la ruta donde se
     * encuentra el archivo que está siendo
     * reproducido.
     * 
     * @return La ruta.
     */
	public String getAudioPath() {
		return audioPath;
	}
    
}
