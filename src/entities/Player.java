package entities;

import models.TexturedModel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import terrains.Terrain;

public class Player extends Entity{
	
	private static final float RUN_SPEED = 30;
	private static final float TURN_SPEED = 160;
	private static final float GRAVITY = -50;
	private static final float JUMPPOWER = 30;
		
	private float currentSpeed=0;
	private float currentUpSpeed=0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	
	private boolean isInAir = false;

	public Player(TexturedModel model, Vector3f position, float rotX,
			float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	public void move(Terrain terrain){
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed*DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx=(float) (distance*Math.sin(Math.toRadians(super.getRotY())));
		float dz=(float) (distance*Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed* DisplayManager.getFrameTimeSeconds(), 0);
		float terrainHeight=terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		if(super.getPosition().y<terrainHeight){
			upwardsSpeed=0;
			isInAir=false;
			super.getPosition().y = terrainHeight;
		}
	}
	
	public void moveFree(){
		checkInputs();
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			this.currentUpSpeed=RUN_SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			this.currentUpSpeed=-RUN_SPEED;
		}else{
			this.currentUpSpeed=0;
		}
		super.increaseRotation(0, currentTurnSpeed*DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx=(float) (distance*Math.sin(Math.toRadians(super.getRotY())));
		float dz=(float) (distance*Math.cos(Math.toRadians(super.getRotY())));
		float dy = currentUpSpeed * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(dx, dy, dz);	
	}
	
	private void jump(){
		if(!isInAir){
			this.upwardsSpeed = JUMPPOWER;
		}
	}
	
	private void checkInputs(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			this.currentSpeed=RUN_SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			this.currentSpeed=-RUN_SPEED;
		}else{
			this.currentSpeed=0;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			this.currentTurnSpeed=-TURN_SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			this.currentTurnSpeed=TURN_SPEED;
		}else{
			currentTurnSpeed=0;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			jump();
			isInAir=true;
		}
	}
}