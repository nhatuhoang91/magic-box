package com.badlogic.androidgames.framework.gl;

import java.nio.ByteBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.util.FloatMath;

import com.badlogic.androidgames.framework.impl.GLGraphics;
import com.badlogic.androidgames.framework.math.Vector2;

public class SpriteBatcher {
	final float[] verticesBuffer;
	int bufferIndex;
	final Vertices vertices;
	int numSprites;
	
	public SpriteBatcher(GLGraphics glGraphics, int maxSprites){
		this.verticesBuffer = new float[maxSprites*4*4]; //each sprite have 4 vertice, each vertice have 4 float 
		bufferIndex = 0;
		numSprites = 0;
		vertices = new Vertices(glGraphics, maxSprites*4, maxSprites*6, false, true);
		
		short[] indices = new short[maxSprites*6];
		int len = indices.length;
		int j=0;
		for(int i=0;i<len;i+=6, j+=4){
			indices[i+0] = (short)(j+0);
			indices[i+1] = (short)(j+1);
			indices[i+2] = (short)(j+2);
			indices[i+3] = (short)(j+2);
			indices[i+4] = (short)(j+3);
			indices[i+5] = (short)(j+0);
		}
		vertices.setIndices(indices, 0, indices.length);
	}
	
	public void beginBatch(Texture texture){
		texture.bind();
		numSprites=0;
		bufferIndex=0;
	}
	
	public void endBatch(){
		vertices.setVertices(verticesBuffer, 0, bufferIndex);
		vertices.bind();
		vertices.draw(GL10.GL_TRIANGLES, 0, numSprites*6);
	}
	
	public void drawSprite(float x, float y, float width, float height, TextureRegion textureRegion){
		float halftWidth = width/2;
		float halftHeight = height/2;
		float x1 = x - halftWidth;
		float x2 = x + halftWidth;
		float y1 = y - halftHeight;
		float y2 = y + halftHeight;
		
		verticesBuffer[bufferIndex++] = x1;
		verticesBuffer[bufferIndex++] = y1;
		verticesBuffer[bufferIndex++] = textureRegion.u1;
		verticesBuffer[bufferIndex++] = textureRegion.v2;
		
		verticesBuffer[bufferIndex++] = x2;
		verticesBuffer[bufferIndex++] = y1;
		verticesBuffer[bufferIndex++] = textureRegion.u2;
		verticesBuffer[bufferIndex++] = textureRegion.v2;
		
		verticesBuffer[bufferIndex++] = x2;
		verticesBuffer[bufferIndex++] = y2;
		verticesBuffer[bufferIndex++] = textureRegion.u2;
		verticesBuffer[bufferIndex++] = textureRegion.v1;
		
		verticesBuffer[bufferIndex++] = x1;
		verticesBuffer[bufferIndex++] = y2;
		verticesBuffer[bufferIndex++] = textureRegion.u1;
		verticesBuffer[bufferIndex++] = textureRegion.v1;
	
		numSprites++;
	}
	
	public void drawSprite(float x, float y, float width, float height, float angle, TextureRegion textureRegion){
		float halftWidth = width/2;
		float halftHeight = height/2;
		
		float rad = angle * Vector2.TO_RADIAN;
		float cos = FloatMath.cos(rad);
		float sin = FloatMath.sin(rad);
			
	
		float x1 = -halftWidth*cos - (-halftHeight)*sin; //(x1,y1)=(-hw,-hh); 
		float y1 = -halftWidth*sin + (-halftHeight)*cos;
		
		float x2 = halftWidth*cos - (-halftHeight)*sin;  //(x2,y2)=(hw,-hh);
		float y2 = halftWidth*sin + (-halftHeight)*cos;
		
		float x3 = halftWidth*cos - halftHeight*sin;     //(x3,y3)=(hw,hh);
		float y3 = halftWidth*sin + halftHeight*cos;
		
		float x4 = -halftWidth*cos - halftHeight*sin; 	//(x4,y4)=(-hw, hh);
		float y4 = -halftWidth*sin + halftHeight*cos;
		
		x1+=x;
		y1+=y;
		x2+=x;
		y2+=y;
		x3+=x;
		y3+=y;
		x4+=x;
		y4+=y;
		
		verticesBuffer[bufferIndex++]=x1;
		verticesBuffer[bufferIndex++]=y1;
		verticesBuffer[bufferIndex++]=textureRegion.u1;
		verticesBuffer[bufferIndex++]=textureRegion.v2;
		
		verticesBuffer[bufferIndex++]=x2;
		verticesBuffer[bufferIndex++]=y2;
		verticesBuffer[bufferIndex++]=textureRegion.u2;
		verticesBuffer[bufferIndex++]=textureRegion.v2;
		
		verticesBuffer[bufferIndex++]=x3;
		verticesBuffer[bufferIndex++]=y3;
		verticesBuffer[bufferIndex++]=textureRegion.u2;
		verticesBuffer[bufferIndex++]=textureRegion.v1;
		
		verticesBuffer[bufferIndex++]=x4;
		verticesBuffer[bufferIndex++]=y4;
		verticesBuffer[bufferIndex++]=textureRegion.u1;
		verticesBuffer[bufferIndex++]=textureRegion.v1;
		
		numSprites++;
	}
}









