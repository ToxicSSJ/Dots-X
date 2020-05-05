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

package com.toxicnether.dotsx.desktop;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.toxicnether.dotsx.DotsX;
import com.toxicnether.dotsx.cgraph.bundle.GraphicProvider;
import com.toxicnether.dotsx.core.config.Configuration;
import com.toxicnether.dotsx.core.sound.MusicPlay;
import com.toxicnether.dotsx.core.sound.SoundPlay;
import com.toxicnether.dotsx.desktop.nodes.MainPane;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * Esta clase es el adaptador realizado
 * para el entorno grafico del juego.
 * 
 * @author Abraham Lora
 *
 */
public class DesktopAdapter extends Application implements GraphicProvider {
	
	protected static DesktopAdapter instance;
	
	protected static MainPane pane;
	protected static Stage stage;
	
	protected static Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	
	/**
	 * 
	 * Este metodo se ejecutá para dar
	 * inicio al adaptador de escritorio.
	 * 
	 */
	@Override
	public void start() {
		launch();
	}
	
	/**
	 * 
	 * Con este metodo se inicializá el adaptador de escritorio
	 * principal donde se requiere el stage sobre el cual se
	 * trabajará.
	 * 
	 * @param param El stage fabricado.
	 * 
	 */
	@Override
	public void start(Stage param) throws Exception {
		
		instance = this;
		pane = new MainPane();
		stage = param;
		
		SoundPlay.setGlobalVolume(Double.valueOf(Configuration.get("soundsvolume")));
		MusicPlay.setGlobalVolume(Double.valueOf(Configuration.get("musicvolume")));
		
		stage.setMaxHeight(dimension.getHeight());
		stage.setMaxWidth(dimension.getWidth());
		
		stage.setWidth(dimension.getWidth());
		stage.setHeight(dimension.getHeight());
		
		stage.setTitle("DOTSX (1.0.0) - Abraham Miguel Lora Vargas [Versión Alpha]"); 
		stage.setFullScreen(true);
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		stage.getIcons().add(new Image(DotsX.getResource("images/icon.png").toExternalForm()));

		System.out.println(DotsX.getResource("images/icon.png").toExternalForm());

		Scene scene = new Scene(pane);
		
		scene.addEventFilter(MouseEvent.DRAG_DETECTED , new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		        scene.startFullDrag();
		    }
		});
		
		stage.setScene(scene);
		
		if(!DotsX.isUnix())
			stage.getScene().setCursor(new ImageCursor(new Image(DotsX.getResource("images/cursors/game.png").toExternalForm())));
		
		stage.show();
		
	}
	
	/**
	 * @return La dimensión de la pantalla.
	 */
	public static Dimension getDimension() {
		return dimension;
	}
	
	/**
	 * 
	 * Este metodo devuelve el Stage sobre el cual se muestra
	 * el juego en su totalidad.
	 * 
	 * @return El Stage del juego.
	 */
	public static Stage getStage() {
		return stage;
	}
	
	/**
	 * Este metodo devolverá el panel principal del
	 * adaptador donde se muestran todos los componentes.
	 * 
	 * @return
	 */
	public static MainPane getPane() {
		return pane;
	}
	
	/**
	 * 
	 * Este metodo estatico fabrica un adaptador de escritorio
	 * con todos sus datos y sin ningún parametro en
	 * el constructor.
	 * 
	 * @return Una instancia nueva de DesktopAdapter.
	 */
	public static DesktopAdapter createProvider() {
		return new DesktopAdapter();
	}
	
}
