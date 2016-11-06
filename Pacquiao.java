import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Pacquiao{
	
	private String name;
	private int hp;
	private int xPos;
	private int yPos;
	private boolean isAlive;

	public Pacquiao(){
		this.hp = 3;
		this.name = "Manny Pacquiao";
		this.isAlive = true;
	}

	/*----- getter methods -----*/
	public int getHp(){
		return this.hp;
	}

	public String getName(){
		return this.name;
	}

	public int getXPos(){
		return this.xPos;
	}

	public int getYPos(){
		return this.yPos;
	}

	public boolean getIsAlive(){
		if(this.hp > 0)
			return true;
		else
			return false;
	}

	/*----- setter methods -----*/
	public void setXPos(int xPos){
		this.xPos = xPos;
	}

	public void setYPos(int yPos){
		this.yPos = yPos;
	}

	public void setIsAlive(){
		
		this.isAlive = false;
		this.hp-=1;
		Playable.plifeGrid.remove(Playable.pheart[hp]); // removes 'pheart' label on 'game' panel every time pacquiao loses hp
		Sound.dead.play();
	}
}