import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Bomb extends Thread{
	private int x;
	private int y;
	private Map map;

	BombAnimation bombB = new BombAnimation(1);
	BombAnimation bombF = new BombAnimation(2);
	BombAnimation tipUp = new BombAnimation(3);
	BombAnimation tipDown = new BombAnimation(4);
	BombAnimation tipLeft = new BombAnimation(5);
	BombAnimation tipRight = new BombAnimation(6);
	BombAnimation [] vert = new BombAnimation[4];
	BombAnimation [] hori = new BombAnimation[4];
	static boolean bombSet = false;	//bomb checker
	static boolean pacdead = false;	//pacman dead checker
	static boolean maywdead = false; //mayweather dead checker

	private Thread b;

	public Bomb(int x,int y, Map map){
		super();
		b = new Thread(this);
		this.map = map;
		this.x=x;
		this.y=y;
		
		this.start();

	}
	
	
	public void  run(){
		this.bombSet = true;
		map.p[x][y].add(bombB); //drops bomb
		
		try{
			b.sleep(3000);
		} catch(Exception e){}
		finally{
			explode(); //bomb explodes
			try{
				b.sleep(1500);
			}catch(Exception e){}		
			clear(); //clears field
			checkPlayers();	//checks if players were hit
			this.bombSet = false;
			Playable.i=0; //resets bomb positions in Playable
			Playable.j=0;
			Playable.k=0;
			Playable.l=0;
		}
		
	}

	public void explode(){ //explodes bomb in corresponding direction
		map.p[x][y].removeAll();		
		map.p[x][y].add(bombF);
		map.p[x][y].revalidate();
		map.p[x][y].repaint();

		Sound.explosion.play(); // plays explosion sfx

		map.p[x][y].setBackground(Color.GREEN);	

		for(int i=1; i<5; i+=1){ //upward explosion
			if(x-i>0){
				if(!map.p[x-i][y].getBackground().equals(Color.GRAY)){
					if (map.p[x-i][y].getBackground().equals(Color.LIGHT_GRAY)){
						map.p[x-i][y].remove(map.getWall(x-i, y));
						map.p[x-i][y].add(tipUp);
						map.p[x-i][y].setBackground(Color.GREEN);
						break;
					}
					if(x-i-1 > 0 && map.p[x-i-1][y].getBackground().equals(Color.GRAY)){
						map.p[x-i][y].add(tipUp);
					}
					if(i==4){
						map.p[x-i][y].add(tipUp);
					} else{
						vert[i] = new BombAnimation(7);
						map.p[x-i][y].add(vert[i]);	
					}
					
					map.p[x-i][y].setBackground(Color.GREEN);
				} else break;		
			} else break;
				
		}

		for(int i=1; i<5; i+=1){ //downward explosion
			if(x+i<17){
				if(!map.p[x+i][y].getBackground().equals(Color.GRAY)){
					if (map.p[x+i][y].getBackground().equals(Color.LIGHT_GRAY)){
						map.p[x+i][y].remove(map.getWall(x+i, y));
						map.p[x+i][y].add(tipDown);
						map.p[x+i][y].setBackground(Color.GREEN);
						break;
					}
					if(x+i+1 < 17 && map.p[x+i+1][y].getBackground().equals(Color.GRAY)){
						map.p[x+i][y].add(tipDown);
					}
					if(i==4){
						map.p[x+i][y].add(tipDown);
					} else{
						vert[i] = new BombAnimation(7);
						map.p[x+i][y].add(vert[i]);	
					}
				
					map.p[x+i][y].setBackground(Color.GREEN);
				} else break;
			} else break;
		}

		for(int i=1; i<5; i+=1){ //left explosion
			if(y-i>0){
				if(!map.p[x][y-i].getBackground().equals(Color.GRAY)){
					if (map.p[x][y-i].getBackground().equals(Color.LIGHT_GRAY)){
						map.p[x][y-i].remove(map.getWall(x, y-i));
						map.p[x][y-i].add(tipLeft);
						map.p[x][y-i].setBackground(Color.GREEN);
						break;
					}
					if(y-i-1 > 0 && map.p[x][y-i-1].getBackground().equals(Color.GRAY)){
						map.p[x][y-i-1].add(tipLeft);
					}
					if(i==4){
						map.p[x][y-i].add(tipLeft);
					} else{
						hori[i] = new BombAnimation(8);
						map.p[x][y-i].add(hori[i]);	
					}
				
					map.p[x][y-i].setBackground(Color.GREEN);
				} else break;
			} else break;
		}

		for(int i=1; i<5; i+=1){ //right explosion
			if(y+i<17){
				if(!map.p[x][y+i].getBackground().equals(Color.GRAY)){
					if (map.p[x][y+i].getBackground().equals(Color.LIGHT_GRAY)){
						map.p[x][y+i].remove(map.getWall(x, y+i));
						map.p[x][y+i].add(tipRight);
						map.p[x][y+i].setBackground(Color.GREEN);
						break;
					}
					if(y+i+1 < 17 && map.p[x][y+i+1].getBackground().equals(Color.GRAY)){
						map.p[x][y+i].add(tipRight);
					}
					if(i==4){
						map.p[x][y+i].add(tipRight);
					} else{
						hori[i] = new BombAnimation(8);
						map.p[x][y+i].add(hori[i]);	
					}
				
					map.p[x][y+i].setBackground(Color.GREEN);
				} else break;	
			}
		}
		
		map.p[x][y].setBackground(Color.GREEN);

		
		Playable.pacbomb=0;	//resets pacman's available bomb
		Playable.mayweatherbomb=0; //resets mayweather's available bomb
	}

	public void clear(){ //clears field
		map.p[x][y].removeAll();
		map.p[x][y].revalidate();
		map.p[x][y].repaint();

		for(int i=1; i<5; i+=1){
			if(x-i>0){
				if(map.p[x-i][y].getBackground().equals(Color.GREEN)){
					map.p[x-i][y].removeAll();
					map.p[x-i][y].revalidate();
					map.p[x-i][y].repaint();
				} else break;
			}
		}
		for(int i=1; i<5; i+=1){
			if(x+i<17){
				if(map.p[x+i][y].getBackground().equals(Color.GREEN)){
					map.p[x+i][y].removeAll();
					map.p[x+i][y].revalidate();
					map.p[x+i][y].repaint();
				} else break;
			}
		}
		for(int i=1; i<5; i+=1){
			if(y-i>0){
				if(map.p[x][y-i].getBackground().equals(Color.GREEN)){
					map.p[x][y-i].removeAll();
					map.p[x][y-i].revalidate();
					map.p[x][y-i].repaint();
				} else break;
			}
		}
		for(int i=1; i<5; i+=1){
			if(y+i<17){
				if(map.p[x][y+i].getBackground().equals(Color.GREEN)){
					map.p[x][y+i].removeAll();
					map.p[x][y+i].revalidate();
					map.p[x][y+i].repaint();
				} else break;
			}
		}	
			
		
	}

	public void checkPlayers(){ //checks if players got hit
		if(!pacdead && map.p[map.getPacman().getXPos()][map.getPacman().getYPos()].getBackground().equals(Color.GREEN)){
			map.p[x][y].remove(map.getPLabel());		
			map.getPacman().setIsAlive();
			pacdead = true;
			try{
				b.sleep(2000);
			} catch(Exception e){}
			map.positionPacman();
			pacdead = false;
		}
		if(!maywdead && map.p[map.getMayweather().getXPos()][map.getMayweather().getYPos()].getBackground().equals(Color.GREEN)){
			map.p[x][y].remove(map.getMLabel());						
			map.getMayweather().setIsAlive();
			maywdead = true;
			try{
				b.sleep(2000);
			} catch(Exception e){}
			map.positionMayweather();
			maywdead = false;
		}
	}
}
