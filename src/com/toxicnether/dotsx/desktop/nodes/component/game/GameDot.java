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

package com.toxicnether.dotsx.desktop.nodes.component.game;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.toxicnether.dotsx.cgraph.bundle.DotAttribute;
import com.toxicnether.dotsx.core.color.DotColorType;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

/**
 * 
 * Esta clase es la que se encarga de manejar
 * los componentes que son utilizados para el
 * manejo de los Dots en el tablero de juego.
 * 
 * @author Abraham Lora
 *
 */
public class GameDot extends StackPane implements Serializable {

	private static final long serialVersionUID = 4803438971999346807L;
	
	private static final double RAD_PROPORTION = 1.2;
	private static final double STR_PROPORTION = 2.2;
	
	private Set<DotAttribute<?>> attributes = new HashSet<DotAttribute<?>>();
	
	private double size = 0.0;
	private double scalex = 0.0;
	private double scaley = 0.0;
	
	private DotColorType type = DotColorType.UNKNOW;
	private Image custom;
	
	private Circle circle;
	
	/**
	 * 
	 * Este constructor crea un Dot en base a
	 * un tamaño (px) y de color transparente con
	 * ciertos atributos predefinidos.
	 * 
	 * @param size El tamaño del Dot.
	 */
	public GameDot(double size) {
		
		super();
		
		this.size = size;
		this.autosize();
		
		this.circle = new Circle(size / RAD_PROPORTION);
		this.circle.setFill(Color.TRANSPARENT);
		
		this.circle.setStrokeType(StrokeType.CENTERED);
		this.circle.setStroke(type.getColor());
		this.circle.setStrokeWidth(size / STR_PROPORTION);
		
		this.getChildren().add(circle);
		StackPane.setAlignment(circle, Pos.CENTER);
		
		this.scalex = circle.getScaleX();
		this.scaley = circle.getScaleY();
		
		this.addAttribute(DotAttribute.makeInstance("uuid", UUID.randomUUID().toString()));
		
		this.addAttribute(DotAttribute.makeInstance("noline", false));
		this.addAttribute(DotAttribute.makeInstance("removed", false));
		this.addAttribute(DotAttribute.makeInstance("selected", false));
		this.addAttribute(DotAttribute.makeInstance("affect", true));
		
	}
	
	/**
	 * 
	 * Este metodo permite definir la imagen 
	 * customizada para los modos de juego
	 * que sean compuestos por tipos de dots
	 * CUSTOM.
	 * 
	 * @param image
	 */
	public void setCustom(Image image) {
		
		this.type = DotColorType.CUSTOM;
		this.custom = image;
		
		this.circle.setFill(new ImagePattern(image));
		
	}
	
	/**
	 * 
	 * Este metodo permite redefinir el tipo
	 * de dot.
	 * 
	 * @param type El nuevo tipo de dot.
	 */
	public void setType(DotColorType type) {
		
		this.type = type;
		
		if(type == DotColorType.CUSTOM)
			return;
		
		this.circle.setStroke(type.getColor());
		
	}
	
	/**
	 * 
	 * Este metodo permite adicionar un atributo
	 * al dot.
	 * 
	 * @param attribute El nuevo atributo.
	 */
	public void addAttribute(DotAttribute<?> attribute) {
		
		if(hasAttribute(attribute.getKey()))
			return;
		
		attributes.add(attribute);
		return;
		
	}
	
	/**
	 * @return Devuelve todos los atributos del dot.
	 */
	public Set<DotAttribute<?>> getAttributes() {
		return attributes;
	}
	
	/**
	 * 
	 * Este metodo permite cambiar el atributo
	 * de un dot que ya a sido predefinido en
	 * base a su llave y su valor generico.
	 * 
	 * @param key La llave.
	 * @param value El valor.
	 */
	public <E> void changeAttribute(String key, E value) {
		
		if(key.equalsIgnoreCase("selected"))
			if(value instanceof Boolean)
			    if(((Boolean) value) == true)
				    circle.setFill(type.getColor());
			    else
				    circle.setFill(Color.TRANSPARENT);
		
		for(DotAttribute<?> k : attributes)
			if(k.getKey().equalsIgnoreCase(key)) {
				
				k.setValue(value);
				return;
				
			}
		
		addAttribute(DotAttribute.makeInstance(key, value));
		return;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir un atributo
	 * en base a su llave. Si no existe se devuelve
	 * null.
	 * 
	 * @param key La llave.
	 * @return El atributo cuya llave es la
	 * definida.
	 */
	public <E> E getAttribute(String key) {
		
		for(DotAttribute<?> k : attributes)
			if(k.getKey().equalsIgnoreCase(key))
				return k.getValue();
		
		return null;
		
	}
	
	/**
	 * 
	 * Este metodo permite saber si el dot
	 * contiene un atributo con la llave definida.
	 * 
	 * @param key La llave.
	 * @return <strong>true</strong> si el dot
	 * contiene ese atributo.
	 */
	public boolean hasAttribute(String key) {
		return getAttribute(key) != null;
	}
	
	/**
	 * 
	 * Este metodo permite borrar un dot con su
	 * respectiva animación.
	 * 
	 */
	public void delete() {
		delete(true);
	}
	
	/**
	 * 
	 * Este metodo permite borrar un dot
	 * eligiendo si se desea o no desactivar
	 * la animación.
	 * 
	 * @param smooth Si se utilizará la animación.
	 */
	public void delete(boolean smooth) {
		
		if(!smooth) {
			
			circle.setScaleX(0);
			circle.setScaleY(0);
			
			return;
			
		}
		
		Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		
		KeyValue cr = new KeyValue(circle.scaleXProperty(), 0), 
				 cs = new KeyValue(circle.scaleYProperty(), 0);
		
		for(KeyValue value : Arrays.asList(cr, cs))
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(125), value));
		
		timeline.play();
		
	}
	
	/**
	 * 
	 * Este metodo permite hacer aparecer un
	 * dot sin animación.
	 * 
	 */
	public void appear() {
		
		appear(false);
		return;
		
	}
	
	/**
	 * 
	 * Este metodo permite aparecer un dot
	 * eligiendo si se desea o no desactivar
	 * la animación.
	 * 
	 * @param smooth Si se utilizará la animación.
	 */
	public void appear(boolean smooth) {
		
		if(!smooth) {
			
			circle.setScaleX(scalex);
			circle.setScaleY(scaley);
			
			changeAttribute("selected", false);
			return;
			
		}
		
		Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		
		KeyValue cr = new KeyValue(circle.scaleXProperty(), scalex), 
				 cs = new KeyValue(circle.scaleYProperty(), scaley);
		
		for(KeyValue value : Arrays.asList(cr, cs))
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(125), value));
		
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(130), e -> changeAttribute("selected", false)));
		timeline.play();
		
	}
	
	/**
	 * @return La imagen si el dot usa el tipo
	 * CUSTOM.
	 */
	public Image getCustom() {
		return custom;
	}
	
	/**
	 * @return El tipo de color que compone al dot.
	 */
	public DotColorType getColorType() {
		return type;
	}
	
	/**
	 * @return El circulo que compone al dot.
	 */
	public Circle getCircle() {
		return circle;
	}
	
	/**
	 * 
	 * Este metodo devuelve una nueva instancia
	 * de un dot en base a otro.
	 * 
	 */
	public GameDot clone() {
		
		GameDot clone = new GameDot(size);
		
		for(DotAttribute<?> att : attributes)
			clone.changeAttribute(att.getKey(), att.getValue());
		
		clone.setType(type);
		return clone;
		
	}
	
	/**
	 * 
	 * Este metodo permite conocer si un dot es
	 * igual a otro.
	 * 
	 * @param dot El segundo dot.
	 * @return <strong>true</strong> si el dot
	 * es identico al otro.
	 */
	public boolean equals(GameDot dot) {
		
		if(dot == null)
			return false;
		
		return ((String) dot.getAttribute("uuid")).equals(((String) this.getAttribute("uuid")));
		
	}
	
	/**
	 * 
	 * Este metodo permite generar una instancia de dot
	 * en base a un tamaño y un texto del color.
	 * 
	 * @param size El tamaño del dot.
	 * @param dot El texto del color.
	 * @return La instancia definida.
	 */
	public static GameDot getDot(double size, String dot) {
		
		GameDot cache = new GameDot(size);
		cache.setType(DotColorType.getType(dot));
		
		return cache;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir la ID de un
	 * dot en base a la instancia.
	 * 
	 * @param dot La instancia.
	 * @return La ID del dot.
	 */
	public static String toID(GameDot dot) {
		return dot.getColorType().name();
	}
	
	/**
	 * 
	 * Este metodo permite generar de manera
	 * al azar un dot en base al tamaño.
	 * 
	 * @param size El tamaño (px).
	 * @return La instancia.
	 */
	public static GameDot random(double size) {
		
		GameDot dot = new GameDot(size);
		dot.setType(DotColorType.values()[ThreadLocalRandom.current().nextInt(2, DotColorType.values().length)]);
		
		return dot;
		
	}
	
}
