package algorithm.random;

import java.util.ArrayList;
import lp.display.EBalloon;

public class RandomEBalloon {
	
	public static ArrayList<Cell> getRandomBallons(ArrayList<EBalloon> balloons,float x,float y){
		int num = balloons.size();
		int up_num = num - num/2;
		int down_num = num/2;
		float x_len = x/up_num;
		float x_len_down = x/down_num;
		ArrayList<Cell>cells = new ArrayList<Cell>();
		for(int i = 0;i<up_num;i++){
			Cell c = new Cell(new Couple(i*x_len,0),new Couple((i+1)*x_len,y/2f));
			cells.add(c);
		}
		for(int i = 0;i<down_num+1;i++){
			Cell c = new Cell(new Couple(i*x_len_down,y/2f), new Couple((i+1)*x_len_down,y));
			cells.add(c);
		}
		return cells;
	}
	public static Couple getCenter(Cell c,float diameter){
		return getCenter(c.init,c.end,diameter);
	}
	public static Couple getCenter(Couple init,Couple end,float diameter){
		float r = diameter /2f;
		Couple c1 = new Couple(init.x+r,init.y+r);
		Couple c2 = new Couple(end.x-r,end.y-r);
		return getRandom(c1,c2);
	}
	public static Couple getRandom(Couple c1,Couple c2){
		float x1 = c1.x;
		float y1 = c1.y;
		float x2 = c2.x;
		float y2 = c2.y;
		float r = (float) Math.random();
		float x = x1+(x2-x1)*r;
		float y = y1+(y2-y1)*r;
		return new Couple(x,y);
	}
}
