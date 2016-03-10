package entities;

import org.lwjgl.util.vector.Vector3f;

public class Light {
	
	private double distance;
	
	private Vector3f position;
	private Vector3f color;
	private Vector3f attenuation = new Vector3f(1,0,0);
	
	public Light(Vector3f position, Vector3f color) {
		this.position = position;
		this.color = color;
		distance = position.length();
	}
	
	public Light(Vector3f position, Vector3f color, Vector3f attenuation) {
		this.position = position;
		this.color = color;
		this.attenuation=attenuation;
	}
	
	public Vector3f getAttenuation(){
		return attenuation;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public void setPosition(float x, float y, float z) {
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
	}

	public Vector3f getColor() {
		return color;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}
	
	private float fi=0.0f;
	
	public void moveSun(){
		position.x=(float) (distance*Math.cos(fi));
		position.y=(float) (distance*Math.sin(fi));
		fi+=0.002f;
		if(fi>90)fi=0;
		
	}
	
	public void moveSun(int fi){
		position.x=(float) (distance*Math.cos(fi));
		position.y=(float) (distance*Math.sin(fi));		
	}
}
