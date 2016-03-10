package engineTester;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;

public class MainGameLoop {
	
	public static void main(String[] args){
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		
				
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture,gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMapWater"));
		
		List<Terrain> terrains=new ArrayList<Terrain>();
		
		Terrain terrain=new Terrain(0,0,loader, texturePack, blendMap,"heightWater");
		terrains.add(terrain);
		
		Light sun = new Light(new Vector3f(10000, 10000, -10000+150), new Vector3f(1.3f,1.3f,1.3f));
		List<Light> lights=new ArrayList<Light>();
		lights.add(sun);		
		lights.add(new Light(new Vector3f(0, -10000, +150), new Vector3f(0.8f,0.8f,0.8f)));
		
		List<Entity> entities = new ArrayList<Entity>();
		MasterRenderer renderer =new MasterRenderer(loader);
		
		Entity e = GenEntities.genOBJ(0, 0, 0, 0, 0, 0, 1, "person", "playerTexture", loader);
		Player player= new Player(e.getModel(), new Vector3f(75,5,-75 ), 0, 100, 0, 0.75f);
		//entities.add(player);
		Entity rocks=GenEntities.genOBJ(75, 4.6f, -75, 0, 0, 0, 75, "rocks", "rocks", loader);
	//	Entity rocks2=GenEntitie 0s.genOBJ(96.19921f, -13.140144f-5, -52.95221f, 0, 0, 180, 20, "rocks", "rocks", loader);
		entities.add(rocks)  ;
		//entities.add(rocks2);
		
		entities.add(GenEntities.genOBJ(138.54913f, 3.568573f, -121.50568f, 0, 0, 0,1, "pine", "pine", loader));
		entities.add(GenEntities.genOBJ(109.299515f, 2.1587982f, -138.63922f, 0, 0, 0,1, "pine", "pine", loader));
		entities.add(GenEntities.genOBJ(119.26012f, 5.1535034f, -100.275986f, 0, 0, 0,1, "pine", "pine", loader));
		entities.add(GenEntities.genOBJ(22.351414f, 5.8276978f, -91.86105f, 0, 0, 0,1, "pine", "pine", loader));
		entities.add(GenEntities.genOBJ(42.97426f, 4.862747f, -120.51587f, 0, 0, 0,1, "pine", "pine", loader));
		entities.add(GenEntities.genOBJ(16.65224f, 3.6388779f, -131.4612f, 0, 0, 0,1, "pine", "pine", loader));
		entities.add(GenEntities.genOBJ(133.38937f, 5.9806366f, -16.315125f, 0, 0, 0,1, "pine", "pine", loader));
		entities.add(GenEntities.genOBJ(133.65247f, 5.5906677f, -41.71689f, 0, 0, 0,1, "pine", "pine", loader));
		entities.add(GenEntities.genOBJ(139.40186f, 5.4098816f, -25.010178f, 0, 0, 0,1, "pine", "pine", loader));
		entities.add(GenEntities.genOBJ(15.215134f, 4.3938065f, -60.832977f, 0, 0, 0,1, "pine", "pine", loader));
		entities.add(GenEntities.genOBJ(44.956066f, 7.039116f, -23.677536f, 0, 0, 0,1, "pine", "pine", loader));
		entities.add(GenEntities.genOBJ(40.408657f, 7.3725433f, -34.29663f, 0, 0, 0,1, "pine", "pine", loader));
		entities.add(GenEntities.genOBJ(24.343212f, 4.719902f, -113.47754f, 0, 0, 0,1, "pine", "pine", loader));
		entities.add(GenEntities.genOBJ(26.853004f, 6.598198f, -125.319244f, 0, 0, 0,1, "pine", "pine", loader));
		entities.add(GenEntities.genOBJ(106.22244f, 2.6666565f, -125.94525f, 0, 0, 0,1, "pine", "pine", loader));

		rocks.getModel().getTexture().setReflectivity(0.6f);
		rocks.getModel().getTexture().setShineDamper(10);
		Camera camera = new Camera(player);
		//List<Entity> allCubes = GenCubes.genCubes(1, loader);
				
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrains);
		//lights.get(0).moveSun(20);
		lights.add(new Light(new Vector3f(293, 7, -305+150), new Vector3f(2,2,2), new Vector3f(1,0.0f,0.002f)));
		
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		GuiRenderer guiRenderer = new GuiRenderer(loader);

		
		WaterFrameBuffers fbos = new WaterFrameBuffers();
		
		WaterShader waterShader= new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader,waterShader,renderer.getProjectionMatrix(),fbos);
		List<WaterTile> waters = new ArrayList<WaterTile>();
		WaterTile water =new WaterTile(75,-75, 0);
		waters.add(water);
		
		
		/*GuiTexture refraction=new GuiTexture(fbos.getRefractionTexture(),new Vector2f(0.5f,0.5f),new Vector2f(0.5f,0.5f));
		GuiTexture reflection=new GuiTexture(fbos.getReflectionTexture(),new Vector2f(-0.5f,0.5f),new Vector2f(0.5f,0.5f));
		guis.add(refraction);
		guis.add(reflection);*/
		// Entity e1 =(GenEntities.genQuad(75, 10, -75, 90, 0, 0, 10, loader, "white"));
		// entities.add(e1);
		
		while(!Display.isCloseRequested()){
			//entity.increasePosition(0, 0, -0.002f);
			//cube.increaseRotation(0, 0.5f, 0.5f);
			//cube.increasePositionOnMobius();
			//int te=player.isOnTerrain();
			player.moveFree();
			camera.move();
			
			//picker.update();
		/*	Vector3f point=picker.getCurrentTerrainPoint();
			if(Mouse.isButtonDown(0))System.out.println(
					"entities.add(GenEntities.genOBJ("+point.x+"f, "+point.y+"f, "+point.z+"f, 0, 0, 0,1, \"pine\", \"pine\", loader));");
			Vector3f terrainsPoint=picker.getCurrentTerrainPoint();
			if(terrainsPoint!=null){
				rocks2.setPosition(terrainsPoint);
				//lights.get(1).setPosition(terrainsPoint.x, terrainsPoint.y+15f,terrainsPoint.z);
			}*/
			
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			
			fbos.bindReflectionFrameBuffer();
			float distance = 2* (camera.getPosition().y-water.getHeight());
			camera.getPosition().y-=distance;
			camera.invertPitch();
			renderer.renderScene(entities, terrains, lights, camera,new Vector4f(0f,1f,0f,-water.getHeight()+0.0f));
			camera.getPosition().y+=distance;
			camera.invertPitch();
			
			fbos.bindRefractionFrameBuffer();
			renderer.renderScene(entities, terrains, lights, camera,new Vector4f(0f,-1f,0f,water.getHeight()));
			
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			fbos.unbindCurrentFrameBuffer();
			
			renderer.renderScene(entities, terrains, lights, camera,new Vector4f(0,-1,0,1000000));
			waterRenderer.render(waters, camera, sun);
			
			guiRenderer.render(guis);
			DisplayManager.updateDisplay();
		}
		fbos.cleanUp();
		waterShader.cleanUp();
		guiRenderer.CleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}

/*vertex/terrainvertex je visibility na 1, megla izkljuèena
 * v skybox je izkljuèena megla, v fragmentshader je clamp med 1 in 1
 *player je freemove
 *MousePicker vedno vraèa teren 0;
*/