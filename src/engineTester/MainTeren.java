package engineTester;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;

public class MainTeren {
	
	public static void main(String[] args){
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		
				
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture,gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		List<Terrain> terrains=new ArrayList<Terrain>();
		
		Terrain terrain=new Terrain(0,0,loader, texturePack, blendMap,"heightmap4");
		Terrain terrain2=new Terrain(-1,0,loader, texturePack, blendMap,"heightmap4");
		terrains.add(terrain);
		terrains.add(terrain2);
		
		Light light = new Light(new Vector3f(20000, 40000, 20000), new Vector3f(0.7f,0.7f,0.7f));
		List<Light> lights=new ArrayList<Light>();
		lights.add(light);		
		lights.add(new Light(new Vector3f(110,terrain.getHeightOfTerrain(110, -400)+15,-400), new Vector3f(2,0,0), new Vector3f(1, 0.0f, 0.002f)));
		Entity light1 = GenEntities.genOBJ(110, terrain.getHeightOfTerrain(110, -400), -400, 0, 0, 0, 1, "lamp", "lamp", loader);
		
		List<Entity> entities= GenEntities.genField(loader, terrains);
		entities.add(light1);
		light1.getModel().getTexture().setUseFakeLighting(true);
		MasterRenderer renderer =new MasterRenderer(loader);
		
		Entity e = GenEntities.genOBJ(0, 0, 0, 0, 0, 0, 1, "person", "playerTexture", loader);
		Player player= new Player(e.getModel(), new Vector3f(75,-75,-0), 0, 135, 0, 0.75f);
		entities.add(player);
		Camera camera = new Camera(player);
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrains);
		
		while(!Display.isCloseRequested()){
	
			int te=player.isOnTerrain();
			player.move(terrains.get(te));
			camera.move();
			
			picker.update();
			Vector3f terrainsPoint=picker.getCurrentTerrainPoint();
			if(terrainsPoint!=null){
				light1.setPosition(terrainsPoint);
				lights.get(1).setPosition(terrainsPoint.x, terrainsPoint.y+15f,terrainsPoint.z);
			}
			renderer.renderScene(entities, terrains, lights, camera,new Vector4f(0,-1,0,1000000));
						
			//guiRenderer.render(guis);
			DisplayManager.updateDisplay();
		}
		guiRenderer.CleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}

