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

package com.toxicnether.dotsx.console.writer.timelines.game;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import java.util.Timer;
import java.util.TimerTask;

import com.toxicnether.dotsx.console.ConsoleAdapter;
import com.toxicnether.dotsx.console.bundle.TimeFormatter;
import com.toxicnether.dotsx.console.bundle.ansi.AnsiConverter;
import com.toxicnether.dotsx.console.reader.component.KeyAction;
import com.toxicnether.dotsx.console.writer.ConsoleTimeline;
import com.toxicnether.dotsx.console.writer.ConsoleWriter;
import com.toxicnether.dotsx.console.writer.component.Variable;
import com.toxicnether.dotsx.console.writer.layer.group.AnimationLayer;
import com.toxicnether.dotsx.console.writer.layer.group.AssemblerLayer;
import com.toxicnether.dotsx.console.writer.layer.group.BreakLayer;
import com.toxicnether.dotsx.console.writer.layer.group.VariableLayer;
import com.toxicnether.dotsx.console.writer.layer.model.LayerEvent;
import com.toxicnether.dotsx.console.writer.layer.model.LayerModifier;
import com.toxicnether.dotsx.console.writer.timelines.GameTimeline;
import com.toxicnether.dotsx.console.writer.timelines.WinTimeline;
import com.toxicnether.dotsx.core.color.DotColorType;
import com.toxicnether.dotsx.core.config.Configuration;
import com.toxicnether.dotsx.core.game.GameType;
import com.toxicnether.dotsx.core.sound.playlist.Playlist;
import com.toxicnether.dotsx.core.sound.type.MusicType;
import com.toxicnether.dotsx.core.sound.type.SoundType;

public class BomberTimeline extends GameTimeline implements ConsoleTimeline, Serializable {
	
	private static final long serialVersionUID = -3034521677046714162L;

	private static transient BomberTimeline instance;
	
	private transient VariableLayer board;
	private transient VariableLayer game;
	
	protected Timer timer;
	protected Thread stirThread;
	
	protected Variable selectedDot;
	protected Playlist music = new Playlist(MusicType.CONSOLE_BOMBER_MUSIC1, MusicType.CONSOLE_BOMBER_MUSIC2, MusicType.CONSOLE_BOMBER_MUSIC3);
	
	protected boolean selection = false;
	protected DotColorType selectionColor = DotColorType.UNKNOW;
	
	protected LinkedList<Variable> explosionDots = new LinkedList<Variable>();
	protected LinkedList<Variable> selectionDots = new LinkedList<Variable>();

	protected int scoreint = 0;
	protected int starsint = 0;
	
	protected Variable combo = new Variable("%combo", "&k");
	protected Variable score = new Variable("%score", "000000");
	protected Variable stars = new Variable("%stars", "000000");
	
	protected Variable moves = new Variable("%mov", "0000");
	protected Variable maxmoves = new Variable("%max", "0030");
	
	protected Variable border = new Variable("%border", "&k");
	
	protected List<Variable> dotsdesigns = new ArrayList<Variable>();
	protected List<Variable> dots = new ArrayList<Variable>();
	
	protected List<Variable> variables = new ArrayList<Variable>();
	protected LinkedList<Variable> hotkeys = Configuration.getHotkeys();
	
	public BomberTimeline() {}
	
	private BomberTimeline(LinkedList<String> data) {
		
		instance = this;
		timer = new Timer();
		
		String listKey = "none";
		
		for(String line : data) {
			
			if(line.startsWith("#") || line.isEmpty())
				continue;
			
			if(listKey != "none") {
				
				if(!line.startsWith("%"))
					if(listKey == "DOTS") {
						
						listKey = "SELECTED";
						continue;
						
					}
				
				if(listKey == "DOTS") {
					
					Variable var = Variable.fromString(line);
					
					dots.add(var);
					dotsdesigns.add(new Variable(var.getKey().replaceAll("dot", ""), var.getValue().equals("&k") ? "¤" : "■"));
					
				}
				
				if(listKey == "SELECTED")
					for(Variable dot : dots)
						if(dot.getKey().equals(Variable.fromString(line).getKey()))
							selectionDots.add(dot);
				
				continue;
				
			} else if(line.startsWith("DOTS:")) {
				
				listKey = "DOTS";
				continue;
				
			}
			
			if(line.startsWith("SCORE: ")) {
				
				scoreint = Integer.parseInt(line.replaceFirst("SCORE: ", ""));
				continue;
				
			}
			
			if(line.startsWith("STARS: ")) {
				
				starsint = Integer.parseInt(line.replaceFirst("STARS: ", ""));
				continue;
				
			}
			
			if(line.startsWith("SELECTION: ")) {
				
				selection = Boolean.parseBoolean(line.replaceFirst("SELECTION: ", ""));
				continue;
				
			}
			
			if(line.startsWith("SELECTION_COLOR: ")) {
				
				selectionColor = DotColorType.valueOf(line.replaceFirst("SELECTION_COLOR: ", ""));
				continue;
				
			}
			
			if(line.startsWith("SELECTED_DOT: ")) {
				
				selectedDot = Variable.fromString(line.replaceFirst("SELECTED_DOT: ", ""));
				continue;
				
			}
			
		}
		
		for(Variable dot : dots)
			if(selectedDot.getKey().equals(dot.getKey())) {
				
				selectedDot = dot;
				break;
				
			}
		
		combo = new Variable("%combo", selectedDot.getValue());
		score = new Variable("%score", String.format("%06d", scoreint));
		stars = new Variable("%stars", String.format("%06d", starsint));
		
		variables.add(border);
		variables.addAll(dotsdesigns);
		variables.addAll(selectionDots);
		variables.addAll(dots);
		
	}
	
	@Override
	public void start() {
		
		instance = this;
		timer = new Timer();
		
		for(char i = 'A'; i <= 'Y'; i++) {
			
			boolean bomb = ThreadLocalRandom.current().nextInt(0, 100) > 95 ? true : false;
			
			dotsdesigns.add(new Variable("%" + i, bomb ? "¤" : "■"));
			dots.add(new Variable("%dot" + i, bomb ? "&k" : AnsiConverter.random()));
			
		}
		
		selectedDot = dots.get(0);
		
		variables.add(border);
		variables.addAll(dotsdesigns);
		variables.addAll(dots);
		
		ConsoleAdapter.getAdapter().setWriter(new ConsoleWriter((short) 50));
		
		if(Configuration.get("animations").equals("false")) {
			
			show();
			return;
			
		}
		
		ConsoleAdapter.getAdapter().getWriter().addLayer(new BreakLayer("Divisor", 9));
		ConsoleAdapter.getAdapter().getWriter().addLayer(
				new AssemblerLayer("GameAssembler", "assets/console/layers/games/BOMBER_GAME_NON")
					.autoFrameUp(50)
					.registerEvent(new LayerEvent() {

						@Override
						public void onFinish() {
							
							countdown();
							return;
							
						}

						@Override
						public void onFrame() {
							
							SoundType.CONSOLE_GAME_CRAFT.getPlay().play();
							return;
							
						}
						
					})
				);
		ConsoleAdapter.getAdapter().getWriter().addLayer(new BreakLayer("Divisor", 1));
		
	}

	public void countdown() {
		
		ConsoleAdapter.getAdapter().getWriter().setLayer(
				new AnimationLayer("GameCountdown", "assets/console/animations/gamestart/original/", 4, false)
				.autoFrameUp(450).registerEvent(new LayerEvent() {

					boolean go = false;
					int index = 0;
					
					@Override
					public void onFinish() {
						
						show();
						return;
						
					}

					@Override
					public void onFrame() {
						
						if(go)
							return;
						
						index++;
						
						if(index > 3) {
							
							go = true;
							
							SoundType.CONSOLE_GAME_GO.getPlay().play();
							return;
							
						}
						
						SoundType.CONSOLE_GAME_COUNTDOWN.getPlay().play();
						
					}
					
				}), 1);
		
	}
	
	@Override
	public void show() {
		
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				
				game = new VariableLayer("Game", "assets/console/layers/games/BOMBER_GAME")
						.attach(variables)
						.addModifier(new LayerModifier() {
							
							@Override
							public void apply(LinkedList<String> content) {
								
								int index = 0;
								LinkedHashSet<Entry<Integer, Integer>> corner = getCorner(dots.indexOf(selectedDot));
								
								Iterator<Entry<Integer, Integer>> iterator = corner.iterator();
								
								while(iterator.hasNext()) {
									
									Entry<Integer, Integer> next = iterator.next();
									
									if(next.getValue() >= content.get(next.getKey()).length())
										continue;
										
									StringBuilder builder = new StringBuilder(content.get(next.getKey()));
									builder.setCharAt(next.getValue(), (index >= 0 && index < 4) ? (selection) ? '░' : '║' : getCornerChar(index));
									
									index++;
									content.set(next.getKey(), builder.toString());
									
								}
								
								selection(content);
								
							}
							
							public void selection(LinkedList<String> content) {
								
								for(Variable dot : selectionDots) {
									
									int index = 0;
									
									LinkedHashSet<Entry<Integer, Integer>> corner = getCorner(dots.indexOf(dot));
									Iterator<Entry<Integer, Integer>> iterator = corner.iterator();
									
									while(iterator.hasNext()) {
										
										Entry<Integer, Integer> next = iterator.next();
										
										if(next.getValue() >= content.get(next.getKey()).length())
											continue;
											
										StringBuilder builder = new StringBuilder(content.get(next.getKey()));
										builder.setCharAt(next.getValue(), (index >= 0 && index < 4) ? (selection) ? '░' : '║' : getCornerChar(index));
										
										index++;
										content.set(next.getKey(), builder.toString());
										
									}
									
								}
								
								for(Variable dot : explosionDots) {
									
									LinkedHashSet<Entry<Integer, Integer>> corner = getCorner(dots.indexOf(dot));
									Iterator<Entry<Integer, Integer>> iterator = corner.iterator();
									
									while(iterator.hasNext()) {
										
										Entry<Integer, Integer> next = iterator.next();
										
										if(next.getValue() >= content.get(next.getKey()).length())
											continue;
											
										StringBuilder builder = new StringBuilder(content.get(next.getKey()));
										builder.setCharAt(next.getValue(), '▒');
										
										content.set(next.getKey(), builder.toString());
										
									}
									
								}
								
							}
							
							public char getCornerChar(int index) {
								
								switch(index) {
								
								case 4: return '╔';
								case 8: return '╗';
								
								case 9: return '╚';
								case 13: return '╝';
								
								default: return (selection) ? '░' : '═';
								
								}
								
							}
							
						});
				
				ConsoleAdapter.getAdapter().setWriter(new ConsoleWriter((short) 50));
				
				ConsoleAdapter.getAdapter().getReader().addAction(new KeyAction() {
					
					@Override
					public boolean action(String input) {
						
						int index = dots.indexOf(selectedDot);
						
						if(input.startsWith(Configuration.get("hotkey_w"))) {
							
							if(index - 5 < 0) {
								
								SoundType.CONSOLE_INVALID_MOVE.getPlay().play();
								
							} else if(selection) {
								
								Variable cache = dots.get(index - 5);
								
								if(getDotColor(cache) == selectionColor && !selectionDots.contains(cache)) {
									
									selectionDots.addLast(cache);
									
									selectedDot = dots.get(index - 5);
									SoundType.CONSOLE_MOVE.getPlay().play();
									
								} else if(selectionDots.size() > 1 && selectionDots.get(selectionDots.indexOf(selectedDot) - 1).equals(cache)) {
									
									selectionDots.removeLast();
									selectedDot = cache;
									
									SoundType.CONSOLE_MOVE.getPlay().play();
									
								} else if(selectionDots.contains(cache)){
									
									superP();
									
								} else if(getDotColor(cache) == DotColorType.CUSTOM){
									
									explodeBomb(cache);
									
								} else {
									
									SoundType.CONSOLE_INVALID_MOVE.getPlay().play();
									
								}
								
							} else {
								
								selectedDot = dots.get(index - 5);
								SoundType.CONSOLE_MOVE.getPlay().play();
								
							}
							
						}
						
						if(input.startsWith(Configuration.get("hotkey_a"))) {
							
							if(index - 1 < 0 || index % 5 == 0) {
								
								SoundType.CONSOLE_INVALID_MOVE.getPlay().play();
								
							} else if(selection) {
								
								Variable cache = dots.get(index - 1);
								
								if(getDotColor(cache) == selectionColor && !selectionDots.contains(cache)) {
									
									selectionDots.addLast(cache);
									
									selectedDot = dots.get(index - 1);
									SoundType.CONSOLE_MOVE.getPlay().play();
									
								} else if(selectionDots.size() > 1 && selectionDots.get(selectionDots.indexOf(selectedDot) - 1).equals(cache)) {
									
									selectionDots.removeLast();
									selectedDot = cache;
									
									SoundType.CONSOLE_MOVE.getPlay().play();
									
								} else if(selectionDots.contains(cache)){
									
									superP();
									
								} else if(getDotColor(cache) == DotColorType.CUSTOM){
									
									explodeBomb(cache);
									
								} else {
									
									SoundType.CONSOLE_INVALID_MOVE.getPlay().play();
									
								}
								
							} else {
								
								selectedDot = dots.get(index - 1);
								SoundType.CONSOLE_MOVE.getPlay().play();
								
							}
							
						}
						
						if(input.startsWith(Configuration.get("hotkey_s"))) {
							
							if(index + 5 >= dots.size()) {
								
								SoundType.CONSOLE_INVALID_MOVE.getPlay().play();
								
							} else if(selection) {
								
								Variable cache = dots.get(index + 5);
								
								if(getDotColor(cache) == selectionColor && !selectionDots.contains(cache)) {
									
									selectionDots.addLast(cache);
									
									selectedDot = dots.get(index + 5);
									SoundType.CONSOLE_MOVE.getPlay().play();
									
								} else if(selectionDots.size() > 1 && selectionDots.get(selectionDots.indexOf(selectedDot) - 1).equals(cache)) {
									
									selectionDots.removeLast();
									selectedDot = cache;
									
									SoundType.CONSOLE_MOVE.getPlay().play();
									
								} else if(selectionDots.contains(cache)){
									
									superP();
									
								} else if(getDotColor(cache) == DotColorType.CUSTOM){
									
									explodeBomb(cache);
									
								} else {
									
									SoundType.CONSOLE_INVALID_MOVE.getPlay().play();
									
								}
								
							} else {
								
								selectedDot = dots.get(index + 5);
								SoundType.CONSOLE_MOVE.getPlay().play();
								
							}
							
						}
						
						if(input.startsWith(Configuration.get("hotkey_d"))) {
							
							if(index + 1 >= dots.size() ||  (index + 1) % 5 == 0) {
								
								SoundType.CONSOLE_INVALID_MOVE.getPlay().play();
								
							} else if(selection) {
								
								Variable cache = dots.get(index + 1);
								
								if(getDotColor(cache) == selectionColor && !selectionDots.contains(cache)) {
									
									selectionDots.addLast(cache);
									
									selectedDot = dots.get(index + 1);
									SoundType.CONSOLE_MOVE.getPlay().play();
									
								} else if(selectionDots.size() > 1 && selectionDots.get(selectionDots.indexOf(selectedDot) - 1).equals(cache)) {
									
									selectionDots.removeLast();
									selectedDot = cache;
									
									SoundType.CONSOLE_MOVE.getPlay().play();
									
								} else if(selectionDots.contains(cache)){
									
									superP();
									
								} else if(getDotColor(cache) == DotColorType.CUSTOM){
									
									explodeBomb(cache);
									
								} else {
									
									SoundType.CONSOLE_INVALID_MOVE.getPlay().play();
									
								}
								
							} else {
								
								selectedDot = dots.get(index + 1);
								SoundType.CONSOLE_MOVE.getPlay().play();
								
							}
							
						}
						
						if(input.startsWith(Configuration.get("hotkey_p"))) {
							
							if(selection == false) {
								
								selection = true;
								selectionColor = getDotColor(selectedDot);
								
								selectionDots.add(selectedDot);
								combo.change(selectedDot.getValue());
								
								SoundType.CONSOLE_GAME_PRESS_START.getPlay().play();
								
							} else {
								
								selection = false;
								combo.change("&k");
								
								if(selectionDots.size() > 1) {
									
									for(Variable dot : selectionDots)
										dot.silentChange("&x");
									
									scoreint += selectionDots.size() * 100;
									score.silentChange(String.format("%06d", scoreint));
									
									accomodate();
									SoundType.CONSOLE_GAME_REMOVE.getPlay().play();
									
									update();
									game.sleep(100);
									replace();
									
									update();
									game.sleep(100);
									
									selectionDots.clear();
									selectionColor = DotColorType.UNKNOW;
									
								} else {
									
									selectionDots.clear();
									selectionColor = DotColorType.UNKNOW;
									
									SoundType.CONSOLE_GAME_UNSELECT.getPlay().play();
									
								}
								
							}
							
						}
						
						if(input.startsWith("1")) {
							
							String state = toData();
							Configuration.setState(Configuration.CONSOLE_STATE_FILE, state);
							
							System.exit(0);
							return false;
							
						}
						
						if(input.startsWith("2")) {
							
							music.stop();
							finish();
							return false;
							
						}
						
						game.update();
						board.update();
						ConsoleAdapter.getAdapter().getWriter().update();
						return false;
						
					}
					
					public void superP() {
						
						selectColor(selectionColor);
						update();
						
						scoreint += selectionDots.size() * 100;
						score.silentChange(String.format("%06d", scoreint));
						
						starsint += 1;
						stars.silentChange(String.format("%06d", starsint));
						
						deleteSelect();
						update();
						
						selection = false;
						
						selectionDots.clear();
						selectionColor = DotColorType.UNKNOW;
						
						SoundType.CONSOLE_GAME_SUPER.getPlay().play();
						
						accomodate();
						update();
						
						game.sleep(80);
						replace();
						
					}
					
					public void explodeBomb(Variable cache) {
						
						int index = dots.indexOf(cache);
						
						LinkedHashSet<Variable> range = new LinkedHashSet<Variable>();
						
						range.add(cache);
						range.addAll(explodeBombList(index));
						
						for(Variable dot : range)
							dot.silentChange(dotsdesigns.get(dots.indexOf(dot)).getValue().equals("¤") ? "&k" : dot.getValue());
						
						SoundType.CONSOLE_GAME_BOMBER_BOMB2.getPlay().play();
						game.sleep(50);
						
						for(Variable dot : range) {
							
							explosionDots.add(dot);
							
							dot.silentChange("&t");
							dotsdesigns.get(dots.indexOf(dot)).silentChange("▒");
							
							SoundType.CONSOLE_GAME_BOMBER_PREBOMB.getPlay().play();
							game.sleep(30);
							update();
							
						}
						
						game.sleep(100);
						SoundType.CONSOLE_GAME_BOMBER_BOMB.getPlay().play();
						SoundType.CONSOLE_GAME_BOMBER_BOMB2.getPlay().play();
						
						scoreint += explosionDots.size() * 30;
						score.silentChange(String.format("%06d", scoreint));
						
						for(Variable dot : explosionDots) {
							
							dot.silentChange("&x");
							dotsdesigns.get(dots.indexOf(dot)).silentChange("■");
							
						}
						
						update();
						game.sleep(300);
						
						selection = false;
						
						selectionDots.clear();
						explosionDots.clear();
						accomodate();
						update();
						
						replace();
						update();
						return;
						
					}
					
					@SuppressWarnings("unchecked")
					public LinkedHashSet<Variable> explodeBombList(int index) {
						
						LinkedHashSet<Variable> range = new LinkedHashSet<Variable>();
						
						int copy = index;
						dots.get(copy).silentChange("&t");
						
						while(!(index - 1 < 0 || index % 5 == 0))
							range.add(dots.get(--index));
						
						index = copy;
						
						while(!(index + 1 >= dots.size() ||  (index + 1) % 5 == 0))
							range.add(dots.get(++index));
						
						index = copy;
						
						while(!(index + 5 >= dots.size()))
							range.add(dots.get(index += 5));
						
						index = copy;
						
						while(!(index - 5 < 0))
							range.add(dots.get(index -= 5));
						
						index = copy;
						
						for(Variable dot : (LinkedHashSet<Variable>) range.clone())
							if(getDotColor(dot) == DotColorType.CUSTOM)
								range.addAll(explodeBombList(dots.indexOf(dot)));
						
						return range;
						
					}
					
				});
				
				List<Variable> boardVars = new ArrayList<Variable>();
				
				boardVars.add(combo);
				boardVars.add(score);
				boardVars.add(stars);
				boardVars.add(moves);
				boardVars.add(maxmoves);
				
				boardVars.addAll(hotkeys);
				
				board = new VariableLayer("Score", "assets/console/layers/games/BOMBER_GAME_SCORE")
							.addModifier(new LayerModifier() {
								
								@Override
								public void apply(LinkedList<String> content) {
									
									if(selectionDots.size() == 0)
										return;
									
									String comboLine = content.get(3);
									final int index = comboLine.indexOf("%combo") + 6;
									
									StringBuilder builder = new StringBuilder(comboLine);
									int selections = selectionDots.size();
									
									for(int i = 0; i < 16; i += 2) {
										
										if(selections == 0)
											break;
										
										selections--;
										builder.setCharAt(index + i, '█');
										
									}
									
									content.set(3, builder.toString());
									
								}
							
							}).attach(boardVars);
				
				ConsoleAdapter.getAdapter().getWriter().addLayer(board);
				ConsoleAdapter.getAdapter().getWriter().addLayer(game);
				ConsoleAdapter.getAdapter().getWriter().addLayer(new BreakLayer("Divisor", 1));
				
				game.update();
				board.update();
				ConsoleAdapter.getAdapter().getWriter().update();
				
				if(Configuration.get("music").equals("true"))
					music.play(true);
				
				stirThread = new Thread() {
					
					@Override
					public void run() {
						
						A : while(true) {
							
							try { TimeUnit.MILLISECONDS.sleep(3000);
							} catch (InterruptedException e) {}
							
							for(Variable dot : dots)
								if(getAroundColors(dot).contains(getDotColor(dot)))
									continue A;
							
							for(Variable dot : dots)
								dot.silentChange("&x");
							
							try { TimeUnit.MILLISECONDS.sleep(150);
							} catch (InterruptedException e) {}
							
							replace();
							update();
							
							accomodate();
							update();
							
						}
						
					}
					
					public List<DotColorType> getAroundColors(Variable dot) {
						
						List<DotColorType> result = new ArrayList<DotColorType>();
						
						int index = dots.indexOf(dot);
						
						if(!(index - 1 < 0 || index % 5 == 0))
							result.add(getDotColor(dots.get(index - 1)));
						
						if(!(index + 1 >= dots.size() ||  (index + 1) % 5 == 0))
							result.add(getDotColor(dots.get(index + 1)));
						
						if(!(index + 5 >= dots.size()))
							result.add(getDotColor(dots.get(index + 5)));
						
						if(!(index - 5 < 0))
							result.add(getDotColor(dots.get(index - 5)));
						
						return result;
						
					}
					
				};
				
				stirThread.start();
				return;
				
			}
			
		}, 70);
		
	}
	
	public void accomodate() {
		
		boolean reaccomodate = false;
		
		for(Variable dot : dots) {
			
			if(dot.getValue().equals("&x"))
				continue;
			
			int index = dots.indexOf(dot);
			
			if(index + 5 < dots.size())
				if(dots.get(index + 5).getValue().equals("&x")) {
					
					dotsdesigns.get(index + 5).silentChange(dotsdesigns.get(index).getValue());
					dots.get(index + 5).silentChange(dot.getValue());
					
					dotsdesigns.get(index).silentChange("■");
					dot.silentChange("&x");
					
					reaccomodate = true;
					
				}	
			
		}
		
		if(reaccomodate == true)
			accomodate();
		
	}
	
	public void replace() {
		
		game.sleep(80);
		SoundType.CONSOLE_GAME_REPLACE.getPlay().play();
		
		for(Variable dot : dots)
			if(dot.getValue().equals("&x")) {
				
				boolean bomb = ThreadLocalRandom.current().nextInt(0, 100) > 95 ? true : false;
				
				dotsdesigns.get(dots.indexOf(dot)).silentChange(bomb ? "¤" : "■");
				dot.silentChange(bomb ? "&k" : AnsiConverter.random());
				
			}
		
	}
	
	public void selectColor(DotColorType color) {
		
		game.sleep(100);
		SoundType.CONSOLE_MOVE.getPlay().play();
		
		for(Variable dot : dots)
			if(dot.getValue().equals(AnsiConverter.COLOR_CODES.get(color)))
				if(!selectionDots.contains(dot))
					selectionDots.add(dot);
		
	}
	
	public void deleteSelect() {
		
		game.sleep(100);
		
		for(Variable dot : selectionDots)
			dot.silentChange("&x");
		
	}
	
	public void deleteColor(DotColorType color) {
		
		game.sleep(100);
		
		for(Variable dot : dots)
			if(dot.getValue().equals(AnsiConverter.COLOR_CODES.get(color)))
				dot.silentChange("&x");
		
	}
	
	public void update() {
		
		game.update();
		board.update();
		
		ConsoleAdapter.getAdapter().getWriter().update();
		
	}
	
	public LinkedHashSet<Entry<Integer, Integer>> getCorner(int dotid) {
		
		LinkedHashSet<Entry<Integer, Integer>> corner = new LinkedHashSet<Entry<Integer, Integer>>();
		
		Entry<Integer, Integer> index = game.getPos(dots.get(dotid));
		int updown = (dotid % 5);
		corner.add(index);
		
		corner.add(getCornerPos(index.getKey() + 1, index.getValue()));
		corner.add(getCornerPos(index.getKey(), (index.getValue() + 13)));
		corner.add(getCornerPos(index.getKey() + 1, (index.getValue() + 13)));
		
		for(int i = 1; i <= 5; i++)
			corner.add(getCornerPos(index.getKey() - 1, 39 + (7 * updown) + i));
		
		for(int i = 1; i <= 5; i++)
			corner.add(getCornerPos(index.getKey() + 2, 39 + (7 * updown) + i));
		
		return corner;
		
	}
	
	public Entry<Integer, Integer> getCornerPos(int i, int j){
		return new AbstractMap.SimpleEntry<Integer, Integer>(i, j);
	}
	
	public DotColorType getDotColor(Variable dot) {
		return AnsiConverter.getColorType(dot.getValue());
	}
	
	@Override
	public void pause() {
		
	}

	@Override
	public void quit() {
		finish();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void finish() {
		
		music.stop();
		stirThread.stop();
		
		ConsoleAdapter.getAdapter().getWriter().stop();
		ConsoleAdapter.getAdapter().getWriter().clear();
		ConsoleAdapter.getAdapter().getReader().clear();
		
		WinTimeline.getModeInstance().initDefault("ORIGINAL", scoreint, starsint);
		return;
		
	}
	
	@Override
	public GameType getType() {
		return GameType.ORIGINAL;
	}
	
	public static ConsoleTimeline getInstance() {
		return new BomberTimeline();
	}
	
	public static BomberTimeline getModeInstance() {
		return instance;
	}
	
	public static BomberTimeline continueGame(LinkedList<String> data) {
		return new BomberTimeline(data);
	}
	
	public String toData() {
		
		StringBuilder data = new StringBuilder();
		
		data.append("#----------------------------#\n")
			.append("# Partida guardada           #\n")
			.append("# Fecha: " + TimeFormatter.getAdvancedCurrentTime() + " #\n")
			.append("#----------------------------#\n")
			.append("MODO: BOMBER\n")
			.append("FECHA: " + TimeFormatter.getAdvancedCurrentTime() + "\n")
			.append("SCORE: " + scoreint + "\n")
			.append("STARS: " + starsint + "\n")
			.append("SELECTION: " + selection + "\n")
			.append("SELECTION_COLOR: " + selectionColor + "\n");
		
		data.append("SELECTED_DOT: " + selectedDot.toString() + "\n");
		data.append("DOTS:\n");
		
		for(Variable dot : dots)
			data.append(dot.toString() + "\n");
		
		data.append("SELECTED: \n");
		
		for(Variable dot : selectionDots)
			data.append(dot.toString() + "\n");
		
		return data.toString();
		
	}
	
}
