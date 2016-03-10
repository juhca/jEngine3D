package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;

import org.lwjgl.util.vector.Vector3f;

import renderEngine.Loader;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import entities.Entity;

public class GenEntities {
	
	private static float[] verticies = {			
			-0.5f,0.5f,-0.5f,	
			-0.5f,-0.5f,-0.5f,	
			0.5f,-0.5f,-0.5f,	
			0.5f,0.5f,-0.5f,		
			
			-0.5f,0.5f,0.5f,	
			-0.5f,-0.5f,0.5f,	
			0.5f,-0.5f,0.5f,	
			0.5f,0.5f,0.5f,
			
			0.5f,0.5f,-0.5f,	
			0.5f,-0.5f,-0.5f,	
			0.5f,-0.5f,0.5f,	
			0.5f,0.5f,0.5f,
			
			-0.5f,0.5f,-0.5f,	
			-0.5f,-0.5f,-0.5f,	
			-0.5f,-0.5f,0.5f,	
			-0.5f,0.5f,0.5f,
			
			-0.5f,0.5f,0.5f,
			-0.5f,0.5f,-0.5f,
			0.5f,0.5f,-0.5f,
			0.5f,0.5f,0.5f,
			
			-0.5f,-0.5f,0.5f,
			-0.5f,-0.5f,-0.5f,
			0.5f,-0.5f,-0.5f,
			0.5f,-0.5f,0.5f
			
	};
	
	private static float[] normals = {
			0,0,-1,
			0,0,-1,
			0,0,-1,
			0,0,-1,
			
			0,0,1,
			0,0,1,
			0,0,1,
			0,0,1,
			
			1,0,0,
			1,0,0,
			1,0,0,
			1,0,0,
			
			-1,0,0,
			-1,0,0,
			-1,0,0,
			-1,0,0,
			
			0,1,0,
			0,1,0,
			0,1,0,
			0,1,0,
			
			0,-1,0,
			0,-1,0,
			0,-1,0,
			0,-1,0
	};
	
	private static float[] textureCoords = {
			
			0,0,
			0,0.5f,
			0.3333f,0.5f,
			0.3333f,0,	
			
			0,0.5f,
			0,1,
			0.3333f,1,
			0.3333f,0.5f,
			
			0.3333f,0,
			0.3333f,0.5f,
			0.6666f,0.5f,
			0.6666f,0,	
			
			0.3333f,0.5f,
			0.3333f,1,
			0.6666f,1,
			0.6666f,0.5f,
			
			0.6666f,0,
			0.6666f,0.5f,
			1,0.5f,
			1,0,	
			
			0.6666f,0.5f,
			0.6666f,1,
			1,1,
			1,0.5f

			
	};
	
	private static int[] indices = {
			0,1,3,	
			3,1,2,	
			4,5,7,
			7,6,5,
			8,9,11,
			11,9,10,
			12,13,15,
			15,13,14,	
			16,17,19,
			19,17,18,
			20,21,23,
			23,21,22

	};
	
	public static Entity genCube(float x, float y, float z, float rotX, float rotY, float rotZ, float scale, Loader loader){
		RawModel model =OBJLoader.loadObjModel("person", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("white"));
		texture.setShineDamper(12);
		texture.setReflectivity(0.8f);
		TexturedModel staticModel = new TexturedModel(model, texture);
		return new Entity(staticModel, new Vector3f(x,y,z),rotX,rotY,rotZ,scale);
		
	}
	
	public static Entity genOBJ(float x, float y, float z,
			float rotX, float rotY, float rotZ, float scale, String fileName, String textureName, Loader loader){
		ModelData data=OBJFileLoader.loadOBJ(fileName);
		RawModel model =loader.loadToVAO(data.getVertices(), data.getTextureCoords(),
				data.getNormals(), data.getIndices());
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture(textureName)));
		return new Entity(staticModel, new Vector3f(x,y,z),rotX,rotY,rotZ,scale);

	}
	
	public static ArrayList<Entity> genField(Loader loader, List<Terrain> terrains){
		ModelData data=OBJFileLoader.loadOBJ("pine");
		RawModel model =loader.loadToVAO(data.getVertices(), data.getTextureCoords(),
				data.getNormals(), data.getIndices());
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("pine")));
		staticModel.getTexture().setShineDamper(15);
		staticModel.getTexture().setReflectivity(0.2f);
		
		ModelData data2=OBJFileLoader.loadOBJ("grassModel");
		RawModel model2 =loader.loadToVAO(data2.getVertices(), data2.getTextureCoords(),
				data2.getNormals(), data2.getIndices());
		TexturedModel grass = new TexturedModel(model2, new ModelTexture(loader.loadTexture("grassTexture")));
		grass.getTexture().setTransparent(true);
		grass.getTexture().setUseFakeLighting(true);
		
		ModelData data3=OBJFileLoader.loadOBJ("fern");
		RawModel model3 =loader.loadToVAO(data3.getVertices(), data3.getTextureCoords(),
				data3.getNormals(), data3.getIndices());
		TexturedModel fern = new TexturedModel(model3,new ModelTexture(loader.loadTexture("fernAtlas")));
		fern.getTexture().setNumberOfRows(2);
		fern.getTexture().setTransparent(true);
		fern.getTexture().setUseFakeLighting(true);
		
		ArrayList<Entity> entities = new ArrayList<Entity>();
		Random random=new Random();
		
		for(int i=0;i<300;i++){
			float x =(random.nextFloat()*800-400);
			float z=(random.nextFloat()*800-600);
			int terrain=isOnTerrain(x,z);
			entities.add(new Entity(staticModel, new Vector3f(x,terrains.get(terrain).getHeightOfTerrain(x, z), z),0,0,0,1));
			x =(random.nextFloat()*800-400);
			z=(random.nextFloat()*800-600);
			terrain=isOnTerrain(x,z);
			entities.add(new Entity(grass, new Vector3f(x,terrains.get(terrain).getHeightOfTerrain(x, z), z),0,0,0,1));
			x =(random.nextFloat()*800-400);
			z=(random.nextFloat()*800-600);
			terrain=isOnTerrain(x,z);
			entities.add(new Entity(fern, random.nextInt(4),new Vector3f(x,terrains.get(terrain).getHeightOfTerrain(x, z), z),0,0,0,0.6f));
		}
		return entities;
	}
	
	public static int isOnTerrain(float x, float z){
		if(x>=0&&z<=0) return 0;
		if(x<0&&z<0) return 1;
		return 0;
	}
	
	public static List<Entity> genCubes(int sum, Loader loader){
		
		RawModel model =loader.loadToVAO(verticies, textureCoords,normals, indices);
		ModelTexture texture = new ModelTexture(loader.loadTexture("kocka"));
		texture.setShineDamper(12);
		texture.setReflectivity(0.8f);
		TexturedModel staticModel = new TexturedModel(model, texture);
		
		
		List<Entity> allCubes = new ArrayList<Entity>();
		Random random = new Random();
		
		for(int i=0;i<sum;i++){
			float x = random.nextFloat() * 100 - 50;
			float y = random.nextFloat() * 100 - 50;
			float z = random.nextFloat() * -300;
			allCubes.add(new Entity(staticModel, new Vector3f(x,y,z),random.nextFloat()*180f,random.nextFloat()*180f,0f,3f));
		}
		
		return allCubes;
	}
	
	public static Entity genQuad(float x, float y, float z, float rotX, float rotY, float rotZ, float scale,  Loader loader, String textureName){
		
		float[] vertices = { -0.5f,0.5f,0,-0.5f,-0.5f,0,0.5f,-0.5f,0,0.5f,0.5f,0};
        int[] indicies ={1,0,3,3,2,1};
        float[] textureCoords={0,0,0,1,1,1,1,0};
        float[] normals = {0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0};
		RawModel model = loader.loadToVAO(vertices, textureCoords, normals, indicies);
        ModelTexture texture = new ModelTexture(loader.loadTexture(textureName));
        texture.setReflectivity(1);
        texture.setShineDamper(10);
        TexturedModel staticModel = new TexturedModel(model, texture);
        Entity entity = new Entity(staticModel, new Vector3f(x,y,z), rotX, rotY, rotZ, scale);
        return entity;
	}

}
