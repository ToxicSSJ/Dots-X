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

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.toxicnether.dotsx.DotsX;
import com.toxicnether.dotsx.cgraph.bundle.DotAttribute;
import com.toxicnether.dotsx.core.color.DotColorType;
import com.toxicnether.dotsx.core.config.Configuration;
import com.toxicnether.dotsx.core.sound.SoundPlay;
import com.toxicnether.dotsx.core.sound.type.SoundType;
import com.toxicnether.dotsx.core.util.ColorUtil;
import com.toxicnether.dotsx.desktop.DesktopAdapter;
import com.toxicnether.dotsx.desktop.bundle.TimerUtility;
import com.toxicnether.dotsx.desktop.nodes.MainPane;
import com.toxicnether.dotsx.desktop.nodes.calc.DotCalculation;
import com.toxicnether.dotsx.desktop.nodes.calc.DotResult;
import com.toxicnether.dotsx.desktop.nodes.calc.game.BackDotCalculation;
import com.toxicnether.dotsx.desktop.nodes.calc.game.ClosedSequenceCalculation;
import com.toxicnether.dotsx.desktop.nodes.calc.game.NextDotCalculation;
import com.toxicnether.dotsx.desktop.nodes.component.DialogPane;
import com.toxicnether.dotsx.desktop.nodes.component.dialog.PauseGameDialog;
import com.toxicnether.dotsx.desktop.nodes.component.game.DotPowerUp;
import com.toxicnether.dotsx.desktop.nodes.component.game.GameDot;
import com.toxicnether.dotsx.desktop.nodes.component.game.event.PowerUpDragEvent;
import com.toxicnether.dotsx.desktop.nodes.component.powerups.ChangeDotPowerUp;
import com.toxicnether.dotsx.desktop.nodes.component.powerups.RemoveAllDotPowerUp;
import com.toxicnether.dotsx.desktop.nodes.component.powerups.RemoveDotPowerUp;
import com.toxicnether.dotsx.desktop.nodes.operations.config.object.GameSizes;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * 
 * Esta clase es la de principal manejo
 * de un estado de juego y se considerá en muchos
 * contexto como el 'Panel de Juego' debido a que
 * contiene los diferentes componentes del juego.
 * Desde el tablero hasta los puntajes, powerups y
 * botones extras.
 * 
 * @author Abraham Lora
 *
 */
public abstract class GamePane extends StackPane implements Serializable {

	private static final long serialVersionUID = -8281951812169687159L;
	
	private LinkedList<GameDot> dots = new LinkedList<GameDot>();
	private ObservableList<GameDot> seld = FXCollections.observableArrayList();
	
	private ObservableList<DotPowerUp> usablePU = FXCollections.observableArrayList(RemoveDotPowerUp.makeInstance(),
																					ChangeDotPowerUp.makeInstance(false),
																					RemoveAllDotPowerUp.makeInstance());
	private ObservableList<Line> selt = FXCollections.observableArrayList();
	
	private boolean selection = false;
	private DotColorType selectiontype = null;
	
	private StackPane score = new StackPane();
	private VBox scorebox = new VBox();
	private Rectangle scoreBg = new Rectangle();
	
	private VBox powerUpsBoxes = new VBox();
	private List<HBox> powerUpsHBoxes = new ArrayList<HBox>();
	
	private Rectangle titleRect;
	private Label titleLabel;
	private Label iconLabel;
	
	private HBox mmaxscoreBox;
	private SimpleIntegerProperty maxscoreInt = new SimpleIntegerProperty();
	private Label maxscoreIcon;
	private Label maxscoreLabel;
	private Label lmaxscoreLabel;
	
	private HBox mscoreBox;
	private SimpleIntegerProperty scoreInt = new SimpleIntegerProperty();
	private Label scoreIcon;
	private Label scoreLabel;
	private Label lscoreLabel;
	
	private HBox mstarsBox;
	private SimpleIntegerProperty starsInt = new SimpleIntegerProperty();
	private Label starsIcon;
	private Label starsLabel;
	private Label lstarsLabel;
	
	private Label pauseButton;
	
	private HBox titleHBox;
	private VBox titleBox;
	
	private double comboMaxWidth;
	
	private Rectangle gridBg;
	private ImageView background;
	
	private LinkedList<HBox> hboxes = new LinkedList<HBox>();
	private VBox vbox = new VBox();
	
	private GameSizes sizes;
	private StackPane grid = new StackPane();
	
	private boolean closed = false;
	private Timer repair;
	
	private final DotCalculation[] calculations = new DotCalculation[] {
			
			BackDotCalculation.getInstance(),
			ClosedSequenceCalculation.getInstance(),
			
			NextDotCalculation.getInstance()
			
	};
	
	/**
	 * 
	 * Este metodo permite inicializar un panel de
	 * juego en base a un tamaño definido. Durante
	 * la inicialización se definin los atributos
	 * mas importantes y se crean variables temporales
	 * para ser utilizadas durante el progreso de la
	 * partida. También se inicializan ciertas animaciones
	 * y se construye un proceso para la verificación
	 * constante de posibles combinaciones para el tablero.
	 * 
	 * @param size El tamaño del tablero.
	 * @see GameSizes
	 */
	public GamePane(GameSizes size) {
		
		Font titleFont = Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 55);
		Font iconFont = Font.loadFont(DotsX.getResource("fonts/Gem.ttf").toExternalForm(), 50);
		
		this.sizes = size;
		this.setPrefSize(DesktopAdapter.getDimension().getWidth(), DesktopAdapter.getDimension().getHeight());
		
		for(int i = 0; i < size.getSize() * size.getSize(); i++) {
			
			GameDot dot = GameDot.random(size.getDotSize());
			dot.addAttribute(DotAttribute.makeInstance("selected", false));
			
			dots.add(dot);
			
		}
		
		this.repair = TimerUtility.getTimer();
		this.repair.schedule(new TimerTask() {

			@Override
			public void run() {
				
				if(dots.isEmpty() || dots.size() < 5)
					return;
				
				try {
					
					for(GameDot dot : dots)
						if(hasCombination(dot))
							return;
					
				} catch(Exception e) {
					return;
				}
				
				Timeline swap = new Timeline();
				
				swap.getKeyFrames().add(new KeyFrame(Duration.ZERO, e -> {
					dots.forEach(dot -> {
						
						dot.changeAttribute("selected", true);
						dot.delete(true);
						
					}); 
				}));
				
				swap.getKeyFrames().add(new KeyFrame(Duration.millis(130), e -> dots.forEach(dot -> dot.setType(GameDot.random(size.getDotSize()).getColorType()))));
				swap.getKeyFrames().add(new KeyFrame(Duration.millis(135), e -> dots.forEach(dot -> dot.appear(true))));
				swap.getKeyFrames().add(new KeyFrame(Duration.millis(245), e -> dots.forEach(dot -> dot.changeAttribute("selected", false))));
				
				swap.play();
				return;
				
			}
			
		}, 500, 500);
		
		Iterator<GameDot> it = dots.iterator();
		
		for(int i = 0; i < size.getSize(); i++) {
			
			if(!it.hasNext())
				break;
				
			HBox box = new HBox();
			box.setSpacing(size.getSpacing() * 2);
			
			for(int j = 0; j < size.getSize(); j++)
				box.getChildren().add(it.next());
			
			hboxes.add(box);
			
		}
		
		titleHBox = new HBox(8);
		iconLabel = new Label("q");
		
		iconLabel.setFont(iconFont);
		
		vbox.setMaxSize(10, 10);
		vbox.setPrefSize(10, 10);
		
		hboxes.stream().forEach(box -> { vbox.getChildren().add(box); VBox.setVgrow(box, Priority.ALWAYS); });
		vbox.setSpacing(size.getSpacing() * 2);
		
		gridBg = new Rectangle(550, 550);
		gridBg.setFill(Color.BLACK);
		
		gridBg.setStroke(Color.BLACK);
		gridBg.setStrokeWidth(20);
		gridBg.setStrokeLineJoin(StrokeLineJoin.ROUND);
		
		gridBg.setOpacity(.5);
		grid.getChildren().addAll(vbox, gridBg);
		
		grid.setMaxWidth(700);
		grid.setMaxHeight(700);
		
		background = new ImageView(new Image(DotsX.getResource("images/orbg.jpg").toExternalForm()));
		background.setPreserveRatio(false);
		
		background.setFitHeight(DesktopAdapter.getDimension().getHeight());
		background.setFitWidth(DesktopAdapter.getDimension().getWidth());
		
		this.getChildren().addAll(grid, background);
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		
		gridBg.toBack();
		background.toBack();
		
		score = new StackPane();
		scoreBg = new Rectangle(450, 650);
		
		scoreBg.setFill(Color.BLACK);
		scoreBg.setOpacity(.5);
		scoreBg.setStrokeLineJoin(StrokeLineJoin.ROUND);
		
		score.getChildren().add(scoreBg);
		
		titleBox = new VBox();
		
	    titleLabel = new Label("Modo Original");
		titleLabel.setTextFill(ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY));
		titleLabel.setFont(titleFont);
		
		iconLabel.setTextFill(ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY));
		
		titleRect = new Rectangle(scoreBg.getWidth() + scoreBg.getStrokeWidth() + 2, 20);
		titleRect.setFill(ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY));
		
		titleRect.setArcHeight(20);
		titleRect.setArcWidth(20);
		
		scoreBg.setArcHeight(20);
		scoreBg.setArcWidth(20);
		
		comboMaxWidth = titleRect.getWidth();
		titleRect.setWidth(0);
		
		titleHBox.getChildren().addAll(titleLabel, iconLabel);
		titleBox.getChildren().add(titleHBox);
		titleBox.getChildren().add(titleRect);
		
		titleHBox.setAlignment(Pos.CENTER);
		titleBox.setAlignment(Pos.CENTER);
		
		scorebox.getChildren().add(titleBox);
		score.getChildren().add(scorebox);
		
		vbox.setMaxSize(10, 10);
		vbox.setPrefSize(10, 10);
		
		for(int i = 0; i < usablePU.size(); i++) {
			
			HBox box = new HBox();
			box.setSpacing(40);
			
			List<DotPowerUp> powerups = new ArrayList<DotPowerUp>();
			
			powerups.add(usablePU.get(i));
			
			if(i + 1 < getUsablePU().size())
				powerups.add(usablePU.get(i += 1));
			
			box.getChildren().addAll(powerups);
			powerUpsHBoxes.add(box);
			powerUpsBoxes.getChildren().add(box);
			
		}
		
		VBox.setMargin(powerUpsBoxes, new Insets(30, 0, 0, 80));
		
		powerUpsBoxes.setSpacing(40);
		powerUpsBoxes.setAlignment(Pos.CENTER);
		
		this.mscoreBox = new HBox(10);
		this.mmaxscoreBox = new HBox(10);
		this.mstarsBox = new HBox(10);
		
		this.mscoreBox.setAlignment(Pos.CENTER);
		this.mmaxscoreBox.setAlignment(Pos.CENTER);
		this.mstarsBox.setAlignment(Pos.CENTER);
		
		this.maxscoreLabel = new Label("RECORD:");
		this.scoreLabel = new Label("SCORE:");
		this.starsLabel = new Label("STARS:");
		
		this.lmaxscoreLabel = new Label("0");
		this.lscoreLabel = new Label("0");
		this.lstarsLabel = new Label("0");
		
		this.maxscoreIcon = new Label("\ue10a");
		this.scoreIcon = new Label("\uea61");
		this.starsIcon = new Label("\ue96a");
		
		this.mmaxscoreBox.getChildren().addAll(maxscoreIcon, maxscoreLabel, lmaxscoreLabel);
		this.mscoreBox.getChildren().addAll(scoreIcon, scoreLabel, lscoreLabel);
		this.mstarsBox.getChildren().addAll(starsIcon, starsLabel, lstarsLabel);
		
		this.scoreLabel.setFont(Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 50));
		this.maxscoreLabel.setFont(Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 50));
		this.starsLabel.setFont(Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 50));
		this.maxscoreLabel.setTextFill(ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY));
		this.scoreLabel.setTextFill(ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY));
		this.starsLabel.setTextFill(ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY));
		
		this.maxscoreIcon.setFont(Font.loadFont(DotsX.getResource("fonts/FreeGame.ttf").toExternalForm(), 40));
		this.scoreIcon.setFont(Font.loadFont(DotsX.getResource("fonts/FreeGame.ttf").toExternalForm(), 35));
		this.starsIcon.setFont(Font.loadFont(DotsX.getResource("fonts/FreeGame.ttf").toExternalForm(), 40));
		this.maxscoreIcon.setTextFill(ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY));
		this.starsIcon.setTextFill(ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY));
		this.scoreIcon.setTextFill(ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY));
		
		this.lstarsLabel.setTextFill(ColorUtil.getGradient(Color.CYAN.brighter()));
		this.lmaxscoreLabel.setTextFill(ColorUtil.getGradient(Color.LIME.brighter()));
		this.lscoreLabel.setTextFill(ColorUtil.getGradient(Color.ORANGERED.brighter()));
		
		this.lmaxscoreLabel.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, .3), CornerRadii.EMPTY, Insets.EMPTY)));
		this.lscoreLabel.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, .3), CornerRadii.EMPTY, Insets.EMPTY)));
		this.lstarsLabel.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, .3), CornerRadii.EMPTY, Insets.EMPTY)));
		
		this.lmaxscoreLabel.setFont(Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 65));
		this.lscoreLabel.setFont(Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 65));
		this.lstarsLabel.setFont(Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 65));
		
		this.scoreInt.addListener(e -> {
			
			Glow glow = new Glow(1);
		    Paint color = seld.size() > 0 ? seld.get(0).getColorType().getColor() : ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY);
			
			double scaleX = scoreIcon.getScaleX();
			double scaleY = scoreIcon.getScaleY();
			
			Timeline score = new Timeline();
			
			score.getKeyFrames().add(new KeyFrame(Duration.millis(240), new KeyValue(scoreIcon.scaleXProperty(), scaleX * 1.5)));
			score.getKeyFrames().add(new KeyFrame(Duration.millis(240), new KeyValue(scoreIcon.scaleYProperty(), scaleY * 1.5)));
			score.getKeyFrames().add(new KeyFrame(Duration.millis(200), k -> scoreIcon.setTextFill(color)));
			score.getKeyFrames().add(new KeyFrame(Duration.millis(200), k -> scoreIcon.setEffect(glow)));
			score.getKeyFrames().add(new KeyFrame(Duration.millis(340), k -> scoreIcon.setEffect(null)));
			score.getKeyFrames().add(new KeyFrame(Duration.millis(340), k -> scoreIcon.setTextFill(ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY))));
			score.getKeyFrames().add(new KeyFrame(Duration.millis(340), new KeyValue(scoreIcon.scaleXProperty(), scaleX)));
			score.getKeyFrames().add(new KeyFrame(Duration.millis(340), new KeyValue(scoreIcon.scaleYProperty(), scaleY)));
			
			score.play();
			
			if(maxscoreInt.get() < scoreInt.get())
				maxscoreInt.set(scoreInt.get());
			
			lscoreLabel.setText(scoreInt.getValue() + "");
			
		});
		
		this.starsInt.addListener(e -> {
			
			lstarsLabel.setText(starsInt.getValue() + "");
			
		});
		
		this.maxscoreInt.addListener(e -> {
			
			Glow glow = new Glow(1);
			Paint color = seld.size() > 0 ? seld.get(0).getColorType().getColor() : ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY);
			
			double scaleX = maxscoreIcon.getScaleX();
			double scaleY = maxscoreIcon.getScaleY();
			
			Timeline score = new Timeline();
			
			score.getKeyFrames().add(new KeyFrame(Duration.millis(240), new KeyValue(maxscoreIcon.scaleXProperty(), scaleX * 1.5)));
			score.getKeyFrames().add(new KeyFrame(Duration.millis(240), new KeyValue(maxscoreIcon.scaleYProperty(), scaleY * 1.5)));
			score.getKeyFrames().add(new KeyFrame(Duration.millis(200), k -> maxscoreIcon.setTextFill(color)));
			score.getKeyFrames().add(new KeyFrame(Duration.millis(200), k -> maxscoreIcon.setEffect(glow)));
			score.getKeyFrames().add(new KeyFrame(Duration.millis(340), k -> maxscoreIcon.setEffect(null)));
			score.getKeyFrames().add(new KeyFrame(Duration.millis(340), k -> maxscoreIcon.setTextFill(ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY))));
			score.getKeyFrames().add(new KeyFrame(Duration.millis(340), new KeyValue(maxscoreIcon.scaleXProperty(), scaleX)));
			score.getKeyFrames().add(new KeyFrame(Duration.millis(340), new KeyValue(maxscoreIcon.scaleYProperty(), scaleY)));
			
			score.play();
			
			Configuration.set("highscore", maxscoreInt.get() + "");
			lmaxscoreLabel.setText(maxscoreInt.getValue() + "");
			
		});
		
		scorebox.getChildren().addAll(mmaxscoreBox, mscoreBox, mstarsBox, powerUpsBoxes);
		scorebox.setSpacing(8);
		
		maxscoreInt.set(Integer.valueOf(Configuration.get("highscore")));
		
		this.getChildren().add(score);
		StackPane.setMargin(scorebox, new Insets(60, 0, 0, 2));
		
		StackPane.setMargin(score, new Insets(0, 100, 0, 800));
		StackPane.setAlignment(score, Pos.CENTER_RIGHT);
		
		StackPane.setMargin(grid, new Insets(0, 0, 0, 120));
		StackPane.setAlignment(grid, Pos.CENTER_LEFT);
		
		getChildren().add(MainPane.songBox);
		
		this.pauseButton = new Label("\ue472");
		
		this.pauseButton.setTextFill(Color.WHITE);
		this.pauseButton.setFont(Font.loadFont(DotsX.getResource("fonts/FreeGame.ttf").toExternalForm(), 40));
		
		MainPane.songBox.getChildren().add(0, pauseButton);
		
		pauseButton.setAlignment(Pos.TOP_CENTER);
		HBox.setMargin(pauseButton, new Insets(-2, 0, 20, 0));
		listen();
		
	}
	
	/**
	 * 
	 * Este metodo permite inicializar la lectura de
	 * acciones que puede realizar el usuario por medio
	 * de un sistema simple de eventos que se ejecuta
	 * primordialmente en base a ciertos atributos
	 * y funciona por medio de lecturas al mouse sobre
	 * los nodos alterados.
	 * 
	 */
	public void listen() {
		
		this.pauseButton.setOnMouseClicked(e -> {
			
			if(DialogPane.openedPanes.size() > 0)
				return;
			
			DialogPane dialog = new DialogPane(730, DesktopAdapter.getDimension().getHeight() / 1.6);
			dialog.setItems(new PauseGameDialog(this, dialog)).show();
			
		});
		
		this.pauseButton.setOnMouseEntered(e -> {
			
			if(DialogPane.openedPanes.size() > 0)
				return;
			
			SoundType.CONSOLE_MENU_SELECT.getPlay().play();
			
			pauseButton.setTextFill(Color.RED);
			pauseButton.setEffect(new Glow(.7));
			
		});
		
		this.pauseButton.setOnMouseExited(e -> {
			
			pauseButton.setTextFill(Color.WHITE);
			pauseButton.setEffect(null);
			
		});
		
		this.setOnMouseDragEntered(e ->  {
			
			if(DialogPane.openedPanes.size() > 0)
				return;
			
			if(selection)
				return;
			
			selection = true;
			
			update();
			return;
			
		});
		
		this.setOnMouseDragExited(e ->  {
			
			if(DialogPane.openedPanes.size() > 0)
				return;
			
			if(!selection)
				return;
			
			selection = false;
			selectiontype = null;
			
			if(seld.size() > 1) {
				
				for(GameDot dot : dots)
					dot.setEffect(null);
				
				grid.getChildren().removeAll(selt);
				selt.clear();
				
				Timeline update = new Timeline();
				
				KeyFrame reframe = new KeyFrame(Duration.ZERO, f -> remove());
				KeyFrame drframe = new KeyFrame(Duration.millis(125), f -> drop());
				KeyFrame rfframe = new KeyFrame(Duration.millis(130), f -> refill());
				
				update.getKeyFrames().addAll(reframe, drframe, rfframe);
				update.play();
				
				SoundType.GRAPHIC_DOT_SUCESS.getPlay().setVolume(.1).play();
				return;
				
			}
			
			for(GameDot dot : seld)
				dot.changeAttribute("selected", false);
				
			seld.clear();
			
			update();
			return;
			
		});
		
		this.getStylesheets().add(DotsX.getResource("css/gradient.css").toExternalForm());
		
		seld.addListener((ListChangeListener.Change<? extends GameDot> c) -> {
			
			int size = seld.size();
			
			if(size == 0) {
				
				Timeline rotate = new Timeline();
				
				rotate.getKeyFrames().add(new KeyFrame(Duration.millis(120), new KeyValue(iconLabel.rotateProperty(), 0)));
				rotate.play();
				
				iconLabel.setRotate(0);
				titleRect.setWidth(0.0);
				
				gridBg.setFill(Color.BLACK);
				gridBg.setStroke(Color.BLACK);
				scoreBg.setFill(Color.BLACK);
				
				titleLabel.setTextFill(ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY));
				iconLabel.setTextFill(ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY));
				titleRect.setFill(ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY));
				starsLabel.setTextFill(ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY));
				scoreLabel.setTextFill(ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY));
				maxscoreLabel.setTextFill(ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY));
				maxscoreIcon.setTextFill(ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY));
				scoreIcon.setTextFill(ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY));
				starsIcon.setTextFill(ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY));
				return;
				
			}
			
			if(((boolean) seld.get(size - 1).getAttribute("affect")) == false)
				return;
			
			double one = comboMaxWidth / 10;
			
			if(size * one <= scoreBg.getWidth())
				titleRect.setWidth(size * one);
			
			Timeline rotate = new Timeline();
			
			rotate.getKeyFrames().add(new KeyFrame(Duration.millis(120), new KeyValue(iconLabel.rotateProperty(), iconLabel.getRotate() + 18)));
			rotate.play();
			
			titleLabel.setTextFill(seld.get(0).getColorType().getColor());
			iconLabel.setTextFill(seld.get(0).getColorType().getColor());
			starsLabel.setTextFill(seld.get(0).getColorType().getColor());
			scoreLabel.setTextFill(seld.get(0).getColorType().getColor());
			maxscoreLabel.setTextFill(seld.get(0).getColorType().getColor());
			maxscoreIcon.setTextFill(seld.get(0).getColorType().getColor());
			scoreIcon.setTextFill(seld.get(0).getColorType().getColor());
			starsIcon.setTextFill(seld.get(0).getColorType().getColor());
			titleRect.setFill(seld.get(0).getColorType().getColor());
			
			gridBg.setStroke(seld.get(0).getColorType().getColor().darker().darker());
			gridBg.setFill(seld.get(0).getColorType().getColor().darker().darker());
			scoreBg.setFill(seld.get(0).getColorType().getColor().darker().darker());
			
		});
		
		for(DotPowerUp power : usablePU)
			power.setEvent(new PowerUpDragEvent() {

				@Override
				public boolean fireDrag(Point2D point) {
					
					if(DialogPane.openedPanes.size() > 0)
						return false;
					
					for(GameDot dot : dots)
						if(getRelativeBounds(dot, MainPane.getInstance()).contains(point))
							if(power.getCalculation().getResult(power, vbox, dots, seld, dot) == DotResult.BREAK) {
								
								update();
								
								Timeline update = new Timeline();
								
								KeyFrame reframe = new KeyFrame(Duration.millis(70), f -> remove());
								KeyFrame sfframe = new KeyFrame(Duration.millis(70), f -> SoundType.GRAPHIC_DOT_COIN.getPlay().play());
								KeyFrame drframe = new KeyFrame(Duration.millis(125), f -> drop());
								KeyFrame rfframe = new KeyFrame(Duration.millis(130), f -> refill());
								KeyFrame tfframe = new KeyFrame(Duration.millis(70), f -> SoundType.GRAPHIC_DOT_WINLEVEL.getPlay().play());
								KeyFrame upframe = new KeyFrame(Duration.millis(130), f -> update());
								
								update.getKeyFrames().addAll(reframe, drframe, rfframe, upframe, sfframe, tfframe);
								update.play();
								return true;
								
							}
					
					return false;
				}

				@Override
				public GameDot fireMove(Point2D point) {
					
					if(DialogPane.openedPanes.size() > 0)
						return null;
					
					for(GameDot dot : dots)
						if(getRelativeBounds(dot, MainPane.getInstance()).contains(point))
							return dot;
					
					return null;
					
				}
				
			});
		
		for(GameDot dot : dots) {
			
			dot.toFront();
			
			dot.setOnMouseDragEntered(e -> {
				
				if(DialogPane.openedPanes.size() > 0)
					return;
				
				if(selectiontype == null)
					selectiontype = dot.getColorType();
				else if(!selectiontype.equals(dot.getColorType()))
					return;
				
				if(selection) {
					
					DotResult result = DotResult.CONTINUE;
					
					for(DotCalculation c : calculations)
						if((result = c.getResult(grid, vbox, dots, seld , dot)) != DotResult.CONTINUE) {
							
							if(result == DotResult.BREAK) {
								
								if(closed)
									continue;
								
								update();
								
								Timeline update = new Timeline();
								
								KeyFrame reframe = new KeyFrame(Duration.millis(70), f -> remove());
								KeyFrame sfframe = new KeyFrame(Duration.millis(70), f -> SoundType.GRAPHIC_DOT_COIN.getPlay().play());
								KeyFrame drframe = new KeyFrame(Duration.millis(125), f -> drop());
								KeyFrame rfframe = new KeyFrame(Duration.millis(130), f -> refill());
								KeyFrame upframe = new KeyFrame(Duration.millis(130), f -> update());
								KeyFrame deframe = new KeyFrame(Duration.millis(130), f -> closed = false);
								
								update.getKeyFrames().addAll(reframe, drframe, rfframe, upframe, deframe, sfframe);
								update.play();
								
								if(c.getName().equals("ClosedSequenceCalculation")) {
									
									Glow glow = new Glow(1);
									Color color = seld.get(0).getColorType().getColor();
									
									double scaleX = starsIcon.getScaleX();
									double scaleY = starsIcon.getScaleY();
									
									Timeline star = new Timeline();
									
									star.getKeyFrames().add(new KeyFrame(Duration.millis(240), new KeyValue(starsIcon.scaleXProperty(), scaleX * 1.5)));
									star.getKeyFrames().add(new KeyFrame(Duration.millis(240), new KeyValue(starsIcon.scaleYProperty(), scaleY * 1.5)));
									star.getKeyFrames().add(new KeyFrame(Duration.millis(200), k -> starsIcon.setTextFill(color)));
									star.getKeyFrames().add(new KeyFrame(Duration.millis(200), k -> starsIcon.setEffect(glow)));
									star.getKeyFrames().add(new KeyFrame(Duration.millis(200), k -> starsInt.set(starsInt.get() + 1)));
									star.getKeyFrames().add(new KeyFrame(Duration.millis(340), k -> starsIcon.setEffect(null)));
									star.getKeyFrames().add(new KeyFrame(Duration.millis(340), k -> starsIcon.setTextFill(ColorUtil.getGradient(Color.WHITESMOKE.darker(), Color.GRAY))));
									star.getKeyFrames().add(new KeyFrame(Duration.millis(340), new KeyValue(starsIcon.scaleXProperty(), scaleX)));
									star.getKeyFrames().add(new KeyFrame(Duration.millis(340), new KeyValue(starsIcon.scaleYProperty(), scaleY)));
									
									star.play();
									
									closed = true;
									int power = ThreadLocalRandom.current().nextInt(0, 101);
									int type = -1;
									
									if(power >= 70 && power <= 85)
										type = 0;
									else if(power > 85 && power <= 95)
										type = 1;
									else if(power > 96 && power <= 100)
										type = 2;
									
									if(type == -1)
										return;
									
									DotPowerUp powerup = usablePU.get(type);
									powerup.addOne(GamePane.getCenterAtGrid(seld.get(0), MainPane.getInstance()));
									
								}
								
								return;
								
							}
							
							if(result.stop()) {
								
								update();
								return;
								
							}
							
							result.getActioner().applyAction(dots, seld, dot);
							
							update();
							return;
							
						}
					
					if(!seld.contains(dot) && ((boolean) dot.getAttribute("selected")) == false) {
						
						seld.add(dot);
						dot.changeAttribute("selected", true);
						
						Circle circle = new Circle(dot.getCircle().getRadius());
						Point2D dotcoords = GamePane.getCenterAtGrid(dot, grid);
						
						circle.setFill(dot.getColorType().getColor().brighter());
						circle.setManaged(false);
						circle.toFront();
						
						grid.getChildren().add(circle);
						
						circle.setLayoutX(dotcoords.getX());
						circle.setLayoutY(dotcoords.getY());
						
						Timeline selanim = new Timeline();
						
						KeyValue[] values = new KeyValue[] {
								
								new KeyValue(circle.scaleXProperty(), dot.getCircle().getScaleX() * 2.2),
								new KeyValue(circle.scaleYProperty(), dot.getCircle().getScaleY() * 2.2)
								
						};
						
						for(KeyValue value : values)
							selanim.getKeyFrames().add(new KeyFrame(Duration.millis(425), value));
						
						selanim.getKeyFrames().add(new KeyFrame(Duration.millis(400), new KeyValue(circle.opacityProperty(), .0)));
						selanim.getKeyFrames().add(new KeyFrame(Duration.millis(425), ev -> grid.getChildren().remove(circle)));
						selanim.play();
						
						SoundPlay tick = SoundType.GRAPHIC_DOT_BLIP.getPlay();
						
						tick.setRate(seld.size() * .2);
						tick.play();
						
					}
					
					update();
					return;
					
				}
				
			});
			
		}
		
	}
	
	/**
	 * 
	 * Este metodo permite dibujar las lineas que
	 * conectan los dots por medio de cada ejecución
	 * donde se crean ciertos componentes que son
	 * añadidos al panel principal y que se basan desde
	 * los dots hasta los demás puntos conectados.
	 * También es necesario decir que se basa también
	 * que el atributo 'noline' no este afectando
	 * al dot seleccionado, debido a que si este
	 * atributo se encuentra presente en un dot se
	 * cancela la linea.
	 * 
	 */
	public void update() {
		
		for(GameDot dot : dots)
			dot.setEffect(null);
		
		grid.getChildren().removeAll(selt);
		selt.clear();
		
		if(seld.size() > 1)
			for(int i = 0; i < seld.size(); i++) {
				
				if(i + 1 >= seld.size())
					break;
				
				GameDot[] comp = new GameDot[] { seld.get(i), seld.get(i + 1) };
				
				if(((boolean) comp[0].getAttribute("noline")) == true || ((boolean) comp[1].getAttribute("noline")))
					continue;
				
				Bounds boundsa = getRelativeBounds(comp[0], grid);
				Bounds boundsb = getRelativeBounds(comp[1], grid);
				
				Point2D n1Center = getCenter(boundsa);
				Point2D n2Center = getCenter(boundsb);
				
				Line connector = new Line(n1Center.getX(), n1Center.getY(), n2Center.getX(), n2Center.getY());
				
				connector.setManaged(false);
				
				connector.setStroke(comp[0].getColorType().getColor());
				connector.setStrokeLineCap(StrokeLineCap.ROUND);
				connector.setStrokeWidth(sizes.getLineSize());
				
				selt.add(connector);
				grid.getChildren().add(connector);
				
				connector.toBack();
				
			}
		
	}
	
	/**
	 * 
	 * Este metodo permite remover los dots seleccionados
	 * aplicando ciertos metodos que crean animaciones propias
	 * de los dots y que ayudan a realizar todo el proceso
	 * consiguiente al deseleccionado.
	 * 
	 */
	public void remove() {
		
		if(seld.size() > 0)
			scoreInt.set(scoreInt.get() + (seld.size() * 10));
		
		for(GameDot dot : seld) {
			
			dot.changeAttribute("removed", true);
			dot.changeAttribute("affect", true);
			dot.changeAttribute("noline", false);
			
			dot.delete();
			
		}
		
		seld.clear();
		
	}
	
	/**
	 * 
	 * Este metodo permite bajar los dots aplicando ciertas animaciones
	 * sobre ellos.
	 * 
	 */
	public void drop() {
		
		Set<GameDot> clonanim = new HashSet<GameDot>();
		Map<Map.Entry<GameDot, GameDot>, Point2D> dropanim = new HashMap<Map.Entry<GameDot, GameDot>, Point2D>();
		
		boolean redrop = true;
		
		while(redrop) {
			
			redrop = false;
			
			for(GameDot dot : dots) {
				
				GameDot down = getDown(dot);
				
				if(down == null)
					continue;
				
				if(((boolean) dot.getAttribute("removed")) == true)
					continue;
				
				if(((boolean) down.getAttribute("removed")) == true) {
					
					boolean craftclon = true;
					
					_clon : for(GameDot c : clonanim)
						if(c.equals(dot)) {
							
							craftclon = false;
							break _clon;
							
						}
					
					if(craftclon) {
						
						GameDot clon = dot.clone();
						Point2D dotcoords = getCenterAtGrid(dot);
						
						grid.getChildren().add(clon);
						
						clon.setManaged(false);
						clon.toFront();
						
						clon.setLayoutX(dotcoords.getX());
						clon.setLayoutY(dotcoords.getY());
						
						clonanim.add(clon);
						
					}
				
					redrop = true;
					dot.changeAttribute("removed", true);
					dot.delete(false);
					
					down.setType(dot.getColorType());
					dot.setType(DotColorType.UNKNOW);
					
					down.changeAttribute("removed", false);
					down.changeAttribute("uuid", dot.getAttribute("uuid"));
					down.delete(false);
					
					dot.changeAttribute("uuid", UUID.randomUUID().toString());
					continue;
					
				}
				
			}
		}
		
		for(GameDot clon : clonanim)
			for(GameDot dot : dots)
				if(clon.equals(dot))
					dropanim.put(new AbstractMap.SimpleEntry<GameDot, GameDot>(clon, dot), getCenterAtGrid(dot));
		
		for(Map.Entry<Map.Entry<GameDot, GameDot>, Point2D> entry : dropanim.entrySet()) {
			
			GameDot clon = entry.getKey().getKey();
			GameDot original = entry.getKey().getValue();
			
			Point2D point = entry.getValue();
			
			Timeline drop = new Timeline();
			
			KeyValue keyx = new KeyValue(clon.layoutXProperty(), point.getX());
			KeyValue keyy = new KeyValue(clon.layoutYProperty(), point.getY());
			
			drop.getKeyFrames().add(new KeyFrame(Duration.millis(125), keyx, keyy));
			drop.getKeyFrames().add(new KeyFrame(Duration.millis(125), e -> grid.getChildren().remove(clon)));
			drop.getKeyFrames().add(new KeyFrame(Duration.millis(125), e -> original.appear(false)));
			drop.play();
			continue;
			
		}
		
	}
	
	/**
	 * 
	 * Este metodo permite generar nuevos dots y por medio de
	 * animaciones hacer el efecto de bajado de dots hasta los
	 * puntos vacios del tablero.
	 * 
	 */
	public void refill() {
		
		Set<GameDot> clonanim = new HashSet<GameDot>();
		Map<Map.Entry<GameDot, GameDot>, Point2D> dropanim = new HashMap<Map.Entry<GameDot, GameDot>, Point2D>();
		
		for(GameDot dot : dots) {
			
			if(((boolean) dot.getAttribute("removed")) == true) {
				
				GameDot random = GameDot.random(sizes.getSize());
				GameDot full = getFullUp(dot);
				GameDot clon = dot.clone();
				
				clon.appear(true);
				clon.setType(random.getColorType());
				
				Point2D dotcoords = getCenterAtGrid(full);
				grid.getChildren().add(clon);
				
				clon.setManaged(false);
				clon.toFront();
				
				clon.setLayoutX(dotcoords.getX());
				clon.setLayoutY(dotcoords.getY() - 250);
				
				clonanim.add(clon);
				
				dot.setType(random.getColorType());
				dot.changeAttribute("removed", false);
				dot.delete(false);
				continue;
				
			}
			
		}
		
		for(GameDot clon : clonanim)
			for(GameDot dot : dots)
				if(clon.equals(dot))
					dropanim.put(new AbstractMap.SimpleEntry<GameDot, GameDot>(clon, dot), getCenterAtGrid(dot));
		
		for(Map.Entry<Map.Entry<GameDot, GameDot>, Point2D> entry : dropanim.entrySet()) {
			
			GameDot clon = entry.getKey().getKey();
			GameDot original = entry.getKey().getValue();
			
			Point2D point = entry.getValue();
			
			Timeline drop = new Timeline();
			
			KeyValue keyx = new KeyValue(clon.layoutXProperty(), point.getX());
			KeyValue keyy = new KeyValue(clon.layoutYProperty(), point.getY());
			
			drop.getKeyFrames().add(new KeyFrame(Duration.millis(125), keyx, keyy));
			drop.getKeyFrames().add(new KeyFrame(Duration.millis(125), e -> grid.getChildren().remove(clon)));
			drop.getKeyFrames().add(new KeyFrame(Duration.millis(125), e -> original.appear(false)));
			drop.play();
			continue;
			
		}
		
	}
	
	/**
	 * 
	 * Este metodo permite generar una instancia del dot
	 * al azar.
	 * 
	 * @return Una instancia de dot al azar.
	 */
	public GameDot getRandom() {
		return new GameDot(sizes.getSize());
	}
	
	/**
	 * 
	 * Este metodo permite recibir los powerups habilitados
	 * para esta modalidad.
	 * 
	 * @return La lita observable con las instancias de los PowerUp
	 * previamente inicializados.
	 */
	public ObservableList<DotPowerUp> getUsablePU(){
		return usablePU;
	}
	
	/**
	 * 
	 * Este metodo permite recibir el dot que se
	 * encuentra mas arriba del tablero.
	 * 
	 * @param dot El dot a partir del cual se
	 * recibirá dicha posición.
	 * @return El dot de mas arriba.
	 * @see GameDot
	 */
	public GameDot getFullUp(GameDot dot) {
		
		HBox dbox = getDotBox(dot);
		HBox ubox = getBoxes().get(0);
		
		LinkedList<GameDot> ddots = getDots(dbox);
		LinkedList<GameDot> udots = getDots(ubox);
		
		GameDot udot = udots.get(ddots.indexOf(dot));
		return udot;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir el
	 * dot que se encuentra encima de otro.
	 * 
	 * @param dot El dot del cual se quiere
	 * saber cual se encuentra encima de él.
	 * @return El dot que se encuentra encima.
	 */
	public GameDot getUp(GameDot dot) {
		
		LinkedList<HBox> boxes = getBoxes();
		
		HBox dbox = getDotBox(dot);
		
		if(boxes.indexOf(dbox) == 0)
			return null;
		
		HBox ubox = getBoxes().get(boxes.indexOf(dbox) - 1);
		
		LinkedList<GameDot> ddots = getDots(dbox);
		LinkedList<GameDot> udots = getDots(ubox);
		
		GameDot udot = udots.get(ddots.indexOf(dot));
		return udot;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir el dot que
	 * se encuentra a la izquierda del referenciado.
	 * 
	 * @param dot El dot referenciado.
	 * @return El dot que se encuentra a la izquierda
	 * del referenciado.
	 */
	public GameDot getLeft(GameDot dot) {
		
		HBox dbox = getDotBox(dot);
		
		if(dbox.getChildren().indexOf(dot) == 0)
			return null;
		
		GameDot udot = (GameDot) dbox.getChildren().get(dbox.getChildren().indexOf(dot) - 1);
		return udot;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir el dot que
	 * se encuentra a la derecha del referenciado.
	 * 
	 * @param dot El dot referenciado.
	 * @return El dot que se encuentra a la derecha
	 * del referenciado.
	 */
	public GameDot getRight(GameDot dot) {
		
		HBox dbox = getDotBox(dot);
		
		if(dbox.getChildren().indexOf(dot) == dbox.getChildren().size() - 1)
			return null;
		
		GameDot udot = (GameDot) dbox.getChildren().get(dbox.getChildren().indexOf(dot) + 1);
		return udot;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir el
	 * dot que se encuentra abajo de otro.
	 * 
	 * @param dot El dot del cual se quiere
	 * saber cual se encuentra abajo de él.
	 * @return El dot que se encuentra abajo.
	 */
	public GameDot getDown(GameDot dot) {
		
		LinkedList<HBox> boxes = getBoxes();
		
		HBox abox = getDotBox(dot);
		
		if(boxes.indexOf(abox) == boxes.size() - 1)
			return null;
		
		HBox dbox = getBoxes().get(boxes.indexOf(abox) + 1);
		
		LinkedList<GameDot> adots = getDots(abox);
		LinkedList<GameDot> ddots = getDots(dbox);
		
		GameDot udot = ddots.get(adots.indexOf(dot));
		return udot;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir todos los dots
	 * que se encuentran al rededor de un dot.
	 * 
	 * @param dot El dot referenciado.
	 * @return Los dots que se encuentran al rededor
	 * del referenciado.
	 */
	public List<GameDot> getAround(GameDot dot) {
		
		List<GameDot> around = new ArrayList<GameDot>();
		
		GameDot left = getLeft(dot), 
				right = getRight(dot),
				up = getUp(dot),
				down = getDown(dot);
		
		if(left != null)
			around.add(left);
		
		if(right != null)
			around.add(right);
		
		if(up != null)
			around.add(up);
		
		if(down != null)
			around.add(down);
		
		return around;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir los dots que
	 * estan alrededor de un dot referenciado, sin
	 * embargo, si alguno de esos dots no tiene
	 * el mismo color del dot referenciado no se
	 * devuelve.
	 * 
	 * @param dot El dot referenciado.
	 * @return La lista de dots que tienen el mismo
	 * color del dot referenciado.
	 */
	public List<GameDot> getAroundColor(GameDot dot) {
		
		List<GameDot> around = getAround(dot);
		List<GameDot> result = new ArrayList<GameDot>();
		
		for(GameDot a : around)
			if(a.getColorType().equals(dot.getColorType()))
				result.add(a);
		
		return result;
		
	}
	
	/**
	 * 
	 * Este metodo permite saber si existe una combinación
	 * disponible alrededor de cierto dot.
	 * 
	 * @param dot El dot referenciado.
	 * @return <s>true</s> si existe una combinación disponible
	 * para ese dot.
	 */
	public boolean hasCombination(GameDot dot) {
		
		if(dot == null)
			return true;
		
		List<GameDot> around = getAroundColor(dot);
		return around.size() > 0;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir la caja horizontal
	 * que tiene como hijo cierto dot.
	 * 
	 * @param dot El dot al cual se le desea extraer
	 * su caja.
	 * @return La caja horizontal (HBox).
	 * @see HBox
	 */
	public HBox getDotBox(GameDot dot) {
		
		for(HBox box : getBoxes())
			if(box.getChildren().contains(dot))
				return box;
		
		return null;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir las diferentes
	 * cajas existentes en el tablero.
	 * 
	 * @return Las cajas horizontales que componen el tablero.
	 */
	public LinkedList<HBox> getBoxes(){
		
		LinkedList<HBox> result = new LinkedList<HBox>();
		
		for(Node node : vbox.getChildren())
			result.add((HBox) node);
		
		return result;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir una lista de dots
	 * en base a una caja.
	 * 
	 * @return La lista de dots.
	 */
	public LinkedList<GameDot> getDots(HBox box){
		
		LinkedList<GameDot> result = new LinkedList<GameDot>();
		
		for(Node node : box.getChildren())
			result.add((GameDot) node);
		
		return result;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir la
	 * diferencia entre dos enteros.
	 * 
	 * @param a El primer entero.
	 * @param b El segundo entero.
	 * @return La diferencia entre los enteros.
	 */
	public int getDifference(int a, int b) {
		return a > b ? a - b : b - a;
	}
	
	/**
	 * @return Devuelve la lista de dots.
	 * @see GameDot
	 */
	public LinkedList<GameDot> getDots(){
		return dots;
	}
	
	/**
	 * @return Devuelve la instancia de la propiedad
	 * del Score.
	 */
	public SimpleIntegerProperty getScore(){
		return scoreInt;
	}
	
	/**
	 * @return Devuelve la instancia de la propiedad
	 * de Estrellas.
	 */
	public SimpleIntegerProperty getStars(){
		return starsInt;
	}
	
	/**
	 * 
	 * Este metodo abstracto permite recibir los datos
	 * compuestos del estado de una partida en base a un
	 * nombre predefinido.
	 * 
	 * @param name El nombre de la partida.
	 * @return La lista con los datos organizados.
	 */
	public abstract LinkedList<String> getData(String name);
	
	/**
	 * 
	 * Este metodo permite definirle un nombre al guardado de
	 * la partida.
	 * 
	 * @param name El nombre.
	 */
	public abstract void setSaveName(String name);
	
	/**
	 * 
	 * Este metodo permite recibir el nombre de la partida
	 * que fue guardado previamente.
	 * 
	 * @return El nombre de la partida.
	 */
	public abstract String saveName();
	
	/**
	 * 
	 * Este metodo permite recibir los limites relativos
	 * de un nodo con otro.
	 * 
	 * @param node El nodo.
	 * @param relativeTo El nodo relativo.
	 * 
	 * @return Los limites relativos entre los nodos.
	 * @see Bounds
	 */
	public static Bounds getRelativeBounds(Node node, Node relativeTo) {
	    return relativeTo.sceneToLocal(node.localToScene(node.getBoundsInLocal()));
	}

	/**
	 * 
	 * Este metodo permite recibir el punto central
	 * basandose en el limite especificado.
	 * 
	 * @param b El limite.
	 * @return El punto central.
	 */
	public static Point2D getCenter(Bounds b) {
	    return new Point2D(b.getMinX() + b.getWidth() / 2, b.getMinY() + b.getHeight() / 2);
	}
	
	/**
	 * 
	 * Este metodo permite recibir el punto minimo
	 * basandose en el limite especificado.
	 * 
	 * @param b El limite.
	 * @return El punto minimo.
	 */
	public static Point2D getPoint(Bounds b) {
	    return new Point2D(b.getMinX(), b.getMinY());
	}
	
	/**
	 * 
	 * Este metodo permite recibir el punto
	 * central del panel donde está el nodo.
	 * 
	 * @param node El nodo.
	 * @return El punto central del nodo en el
	 * GRID.
	 */
	private Point2D getCenterAtGrid(Node node) {
		return getCenter(getRelativeBounds(node, grid));
	}
	
	/**
	 * 
	 * Este metodo permite recibir el punto
	 * central de un nodo en base al panel (Parent)
	 * que lo contiene.
	 * 
	 * @param node El nodo.
	 * @return El punto central del nodo.
	 */
	public static Point2D getCenterAtGrid(Node node, Node major) {
		return getCenter(getRelativeBounds(node, major));
	}
	
	/**
	 * 
	 * Este metodo permite recibir el punto minimo
	 * de un nodo relativo a otro.
	 * 
	 * @param node El nodo.
	 * @param major El parent.
	 * @return El punto minimo del nodo.
	 */
	public static Point2D getPointAtGrid(Node node, Node major) {
		return getPoint(getRelativeBounds(node, major));
	}
	
}
