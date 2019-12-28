package com.snorlaxgames.magicboxes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.androidgames.framework.FileIO;
import com.badlogic.androidgames.framework.GameObject;

public class Settings {
	public static boolean soundEnabled = true;
	public static int level =12;
	public static Person person;
	public static GameObject wall;
	public static List<GameObject> subWalls = new ArrayList<GameObject>();
	public static List<Box> boxes = new ArrayList<Box>();
	public static List<GameObject> holes = new ArrayList<GameObject>();
	public static String s;
	public static List<String> strings = new ArrayList<String>();
	
	public final static String file = ".magicboxes";
	
	public static void load(FileIO files){
		strings.clear();
		subWalls.clear();
		boxes.clear();
		holes.clear();
		BufferedReader in = null;
			
		try{
			in = new BufferedReader(new InputStreamReader(files.readFile(file)));
			soundEnabled = Boolean.parseBoolean(in.readLine());
			level = Integer.parseInt(in.readLine());
			
			in = new BufferedReader(new InputStreamReader(files.readAsset("setting.txt")));
			s = in.readLine();
			int len = s.length();
			String str = new String();
			int j=0;
			for(int i=0;i<len;i++){
				if(s.charAt(i)==','|| i==len-1)
				{
					str = s.substring(j, i);
					if(strings.size()==0){
						if(Integer.parseInt(str)!=level)
						{
							s= in.readLine();
							if(s==null || s.matches("")){
								level=1;
								in =  new BufferedReader(new InputStreamReader(files.readAsset("setting.txt")));
								//in.reset();
								s=in.readLine();
							}
							len=s.length();
							i=-1;
							j=0;
							continue;
						}
					}
					j=i+1;
					strings.add(str);
				}
			}
			for(int i=1;i<strings.size();i++){
				str = strings.get(i);
				if(str.contains("person")){
					int d = Integer.parseInt(str.substring(7,8));
					float x = Float.parseFloat(str.substring(10, 14));
					float y = Float.parseFloat(str.substring(15, 19));
					person = new Person(x, y);
					person.direction = d;
					continue;
				}
				if(str.contains("wall")){
					float width = Float.parseFloat(str.substring(5,6));
					float height = Float.parseFloat(str.substring(7, 8));
					float x = Float.parseFloat(str.substring(14, 18));
					float y = Float.parseFloat(str.substring(19, 23));
					wall = new GameObject(x, y, width, height);
					continue;
				}
				if(str.contains("subWall")){
					float x = Float.parseFloat(str.substring(9, 13));
					float y = Float.parseFloat(str.substring(14, 18));
					GameObject subWall = new GameObject(x, y, 1, 1);
					subWalls.add(subWall);
					continue;
				}
				if(str.contains("box")){
					int s = Integer.parseInt(str.substring(4,5));
					float x = Float.parseFloat(str.substring(7, 11));
					float y = Float.parseFloat(str.substring(12, 16));
					Box box = new Box(x,y,1,1);
					box.state = s;
					boxes.add(box);
					continue;
				}
				if(str.contains("hole")){
					float x = Float.parseFloat(str.substring(6, 10));
					float y = Float.parseFloat(str.substring(11, 15));
					GameObject hole = new GameObject(x, y, 1, 1);
					holes.add(hole);
					continue;
				}
			}
		}catch(IOException e)
		{
			
		}catch (NumberFormatException e) {
			
		}finally{
			try{
				if(in!=null)
					in.close();
			}catch(IOException e){
				
			}
		}
	}

	public static void save(FileIO files){
		BufferedWriter out =null;
		try{
			out = new BufferedWriter(new OutputStreamWriter(files.writeFile(file)));
			
			StringBuilder s = new StringBuilder(Boolean.toString(soundEnabled));
			s.append("\n");
			s.append(level);
			out.write(s.toString());
		}catch(IOException e)
		{
			
		}finally{
			try{
				if(out != null)
				{
					out.close();
				}
			}catch(IOException e)
			{
				
			}
		}
	}
}
