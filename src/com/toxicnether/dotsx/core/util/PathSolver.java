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

package com.toxicnether.dotsx.core.util;

import java.util.Arrays;
import java.util.LinkedList;

import com.toxicnether.dotsx.desktop.nodes.component.game.GameDot;

import javafx.scene.layout.HBox;

public class PathSolver {

	private char[][] maze;
	public String currPath;
	private int currX;
	private int currY;
	
	public boolean unsolvable;

	public PathSolver(LinkedList<HBox> boxes, GameDot start, GameDot flag, String currentPath, boolean noSolution) {
		
		int size = boxes.getFirst().getChildren().size();
		maze = new char[size][size];
		
		for(int i = 0; i < size; i++)
			for(int j = 0; j < size; j++) {
				
				GameDot dot = (GameDot) boxes.get(i).getChildren().get(j);
				maze[i][j] = dot.equals(start) ? 'S' : dot.equals(flag) ? 'E' : dot.getColorType().equals(start.getColorType()) ? 'O' : 'X';
				
				if(dot.equals(start)) {
					
					currX = i;
					currY = j;
					
				}
				
			}
		
		currPath = currentPath;
		unsolvable = noSolution;
	}

	public void placePlus() {
		maze[currX][currY] = '+';
	}

	void placeMinus() {
		maze[currX][currY] = '-';
	}

	public void solveMaze() {
		
		if (checkForWin()) {
			return;
		}
		
		if (currY + 1 < maze[currX].length && checkForOpen(currX, currY + 1)) {
			currY++;
			
			placePlus();
			currPath += "E";
			
			solveMaze();
			// check west
		} else if (currY - 1 >= 0 && checkForOpen(currX, currY - 1)) {
			currY--;
			placePlus();
			currPath += "W";
			solveMaze();
			// check south
		} else if (currX + 1 < maze.length && checkForOpen(currX + 1, currY)) {
			currX++;
			placePlus();
			currPath += "S";
			solveMaze();
			// check north
		} else if (currX - 1 >= 0 && checkForOpen(currX - 1, currY)) {
			currX--;
			placePlus();
			currPath += "N";
			solveMaze();
		} else { // we've hit a dead end, we need to backtrack
			if (currPath.length() == 0) {
				// we're back at the starting point, the maze is unsolvable
				unsolvable = true;
				return;
			} else {
				// we've reached a dead end, lets backtrack
				placeMinus();
				backTrack();
			}
		}
	}

	// see if the spot at a give x, y is open
	boolean checkForOpen(int x, int y) {
		return maze[x][y] == 'O';
	}

	// see if any of the surrounding spots are the exit
	boolean checkForWin() {
		// make sure to protect against out of bounds as well
		return ((currY + 1 < maze[currX].length && maze[currX][currY + 1] == 'X') || (currY - 1 >= 0 && maze[currX][currY - 1] == 'X') || (currX + 1 < maze[currX].length && maze[currX + 1][currY] == 'X') || (currX - 1 >= 0 && maze[currX - 1][currY] == 'X'));
	}

	void backTrack() {
		// sanity chek currPath.length() should always be > 0 when we call backTrack
		if (currPath.length() > 0) {
			placeMinus();
			switch (currPath.charAt(currPath.length() - 1)) {
			case 'E':
				currY--;
				break;
			case 'W':
				currY++;
				break;
			case 'S':
				currX--;
				break;
			case 'N':
				currX++;
				break;
			}
			currPath = currPath.substring(0, currPath.length() - 1);
			solveMaze();
		}
	}

	public void printMaze() {
		for (int i = 0; i < maze.length; i++) {
			System.out.println(Arrays.toString(maze[i]));
		}
	}

}