package com.badlogic.androidgames.framework.gl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.impl.GLGraphics;

public class Vertices {
	final GLGraphics glGraphics;
	final boolean hasColor;
	final boolean hasTexCoords;
	final int verticesSize;
	final IntBuffer vertices;
	final ShortBuffer indices;
	final int[] tmpBuffer;
	
	public Vertices(GLGraphics glGraphics, int maxVertices, int maxIndices, boolean hasColor, boolean hasTexCoords){
		this.glGraphics = glGraphics;
		this.hasColor = hasColor;
		this.hasTexCoords = hasTexCoords;
		
		this.verticesSize = (2 + (hasColor?4:0) + (hasTexCoords?2:0))*4;
		this.tmpBuffer = new int[maxVertices*verticesSize/4];
		
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(maxVertices * verticesSize);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertices = byteBuffer.asIntBuffer();
		
		if(maxIndices>0)
		{
			byteBuffer = ByteBuffer.allocateDirect(maxIndices * Short.SIZE/8);
			byteBuffer.order(ByteOrder.nativeOrder());
			indices = byteBuffer.asShortBuffer();
		}else {
			indices = null;
		}
	}
	
	public void setVertices(float[] vertices, int offset, int length){
		this.vertices.clear();
		int len = offset + length;
		for(int i=offset,j=0;i<len;i++,j++){
			tmpBuffer[j] = Float.floatToRawIntBits(vertices[i]);		
		}
		this.vertices.put(tmpBuffer, offset, length);
		this.vertices.flip();
	}
	
	public void setIndices(short[] indices, int offset, int length){
		this.indices.clear();
		this.indices.put(indices,offset, length);
		this.indices.flip();
	}
	
	public void bind(){
		GL10 gl = glGraphics.getGL();
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		vertices.position(0);
		gl.glVertexPointer(2, GL10.GL_FLOAT, verticesSize, vertices);
		if(hasColor){
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			vertices.position(2);
			gl.glColorPointer(4, GL10.GL_FLOAT, verticesSize, vertices);
		}
		if(hasTexCoords){
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			vertices.position(hasColor?6:2);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, verticesSize, vertices);
		}
	}
	
	public void draw(int primitiveType, int offset, int numVertices){
		
		GL10 gl = glGraphics.getGL();
		if(indices != null){
			indices.position(offset);
			gl.glDrawElements(primitiveType, numVertices, GL10.GL_UNSIGNED_SHORT, indices);
		}else{
			gl.glDrawArrays(primitiveType, offset, numVertices);
		}
		
	}
	
	public void unBind(){
		GL10 gl = glGraphics.getGL();
		if(hasColor)
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		if(hasTexCoords)
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}
}