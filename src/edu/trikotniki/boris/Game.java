package edu.trikotniki.boris;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;




public class Game implements ApplicationListener {


	 private Mesh[] stranice;
	 private PerspectiveCamera camera;
	 //private OrthographicCamera camera;
	 protected int lastTouchX;
	 protected int lastTouchY;
	 Music music;
	@Override
	public void create() {
		// TODO Auto-generated method stub
		
		
		music = Gdx.audio.newMusic(Gdx.files.internal("background_music.mp3"));
		music.setLooping(true);
		music.play();
		
		if (stranice == null) {
		      stranice = new Mesh[6];
		 
		      for (int i = 0; i < 6; i++) {
		        stranice[i] = new Mesh(true, 4, 4,
		            new VertexAttribute(Usage.Position, 3, "a_position"),
		            new VertexAttribute(Usage.ColorPacked, 4, "a_color"));
		 
		        stranice[i].setIndices(new short[] { 0, 1, 2, 3 });
		      }
		 
		      stranice[0].setVertices(new float[] {
		          0.25f, 0.25f, 0.25f, Color.toFloatBits(96, 0, 0, 255),
		          -0.25f, 0.25f, 0.25f, Color.toFloatBits(96, 0, 0, 255),
		          0.25f, -0.25f, 0.25f, Color.toFloatBits(96, 0, 0, 255),
		          -0.25f, -0.25f, 0.25f, Color.toFloatBits(96, 0, 0, 255) });
		 
		      stranice[1].setVertices(new float[] {
		          0.25f, 0.25f, -0.25f, Color.toFloatBits(255, 0, 0, 255),
		          -0.25f, 0.25f, -0.25f, Color.toFloatBits(255, 0, 0, 255),
		          0.25f, -0.25f, -0.25f,  Color.toFloatBits(255, 0, 0, 255),
		          -0.25f, -0.25f, -0.25f, Color.toFloatBits(255, 0, 0, 255) });
		 
		      stranice[2].setVertices(new float[] {
		          0.25f, 0.25f, -0.25f, Color.toFloatBits(0, 255, 0, 255),
		          -0.25f, 0.25f, -0.25f, Color.toFloatBits(0, 255, 0, 255),
		          0.25f, 0.25f, 0.25f, Color.toFloatBits(0, 255, 0, 255),
		          -0.25f, 0.25f, 0.25f, Color.toFloatBits(0, 255, 0, 255) });
		 
		      stranice[3].setVertices(new float[] {
		          0.25f, -0.25f, -0.25f, Color.toFloatBits(0, 96, 0, 255),
		          -0.25f, -0.25f, -0.25f, Color.toFloatBits(0, 96, 0, 255),
		          0.25f, -0.25f, 0.25f, Color.toFloatBits(0, 96, 0, 255),
		          -0.25f, -0.25f, 0.25f,  Color.toFloatBits(0, 96, 0, 255) });
		 
		      stranice[4].setVertices(new float[] {
		          0.25f, 0.25f, 0.25f, Color.toFloatBits(0, 0, 255, 255),
		          0.25f, -0.25f, 0.25f, Color.toFloatBits(0, 0, 255, 255),
		          0.25f, 0.25f, -0.25f, Color.toFloatBits(0, 0, 255, 255),
		          0.25f, -0.25f, -0.25f, Color.toFloatBits(0, 0, 255, 255) });
		 
		      stranice[5].setVertices(new float[] {
		          -0.25f, 0.25f, 0.25f, Color.toFloatBits(0, 0, 96, 255),
		          -0.25f, -0.25f, 0.25f, Color.toFloatBits(0, 0, 96, 255),
		          -0.25f, 0.25f, -0.25f, Color.toFloatBits(0, 0, 96, 255),
		          -0.25f, -0.25f, -0.25f, Color.toFloatBits(0, 0, 96, 255) });
		    }
		 
		    Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
		   


	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		float aspectRatio = (float) width / (float) height;
	    camera = new PerspectiveCamera(66, 1f * aspectRatio, 1f);
	    //camera.near = 0.1f;
	    
	    
	    camera.position.x = 1;
		camera.position.y = 0.5f;
		camera.position.z= 1;
		camera.lookAt(0, 0, 0);
	}
	
	

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
		
		//za predvajanje glasbe
		if(Gdx.input.isKeyPressed(Input.Keys.P)) {
			music.play();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			music.stop();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.O)) {
			music.pause();
		}
		
		//zoomiranje
		if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
			camera.position.z=camera.position.z-1;
		    camera.lookAt(0, 0, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			camera.position.z=camera.position.z+1;
		    camera.lookAt(0, 0, 0);
		}
				
		//interaktivno premikanje
		if (Gdx.input.justTouched()) {
		      lastTouchX = Gdx.input.getX();
		      lastTouchY = Gdx.input.getY();
		}
		else if (Gdx.input.isTouched()) {
		    	if(lastTouchY>Gdx.input.getY())
		    	{
		    		camera.position.y=camera.position.y-0.15f;
		    		camera.lookAt(0, 0, 0);
		    	}else if(lastTouchY<Gdx.input.getY())
		    	{
		    		camera.position.y=camera.position.y+0.15f;
		    		camera.lookAt(0, 0, 0);
		    	}
		    	if(lastTouchX>Gdx.input.getX())
		    	{
					camera.position.x=camera.position.x-0.15f;
				    camera.lookAt(0, 0, 0);
				    
		    	}else if(lastTouchX<Gdx.input.getX())
		    	{
		    		camera.position.x=camera.position.x+0.15f;
				    camera.lookAt(0, 0, 0);
		    	}
				    		
		    	lastTouchX = Gdx.input.getX();
			    lastTouchY = Gdx.input.getY();
		    }
		
			if(Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT))
			{
	       //camera.position.x=1;
	       //camera.position.y=1;
	       //camera.position.z=1;
	       //camera.lookAt(0, 0, 0);
				orbitLookAt(-20);
				camera.lookAt(0, 0, 0); 
				camera.position.z-=0.3f;
			}
		      if(Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT))
		      {
		       //camera.position.x=1f;
		       //camera.position.y=0.5f;
		       //camera.position.z=1f;
		       //camera.lookAt(0, 0, 0); 
		    	  orbitLookAt(20);
		    	  camera.lookAt(0, 0, 0); 
		    	  camera.position.z-=0.3f;
		      }
		      if(Gdx.input.isKeyPressed(Input.Keys.DPAD_UP))
		      {
		    	  	camera.position.x=-1;
		       		camera.position.y=-1;
		       		camera.position.z=-1;
		       		camera.lookAt(0, 0, 0);
		      }
		      if(Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN))
		      {
		    	  	camera.position.x=-1;
		       		camera.position.y=1;
		       		camera.position.z=-1;
		       		camera.lookAt(0, 0, 0);
		      }

			    camera.update();
			    camera.apply(Gdx.gl10);
			 
			    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
			 
			    for (Mesh face : stranice) {
			      face.render(GL10.GL_TRIANGLE_STRIP, 0, 4);
			    }
			    
			    try {
				      Thread.sleep(16); // ~60FPS
				    } catch (InterruptedException e) {
				    }
		}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		music.dispose();
	}

	

	 public void orbitLookAt(float angle)
	 {
		 Ray camViewRay= new Ray(new Vector3(), new Vector3());
		 Plane xzPlane = new Plane(new Vector3(0,0,0), 10);
		 Vector3 lookAtPoint= new Vector3(camera.direction);
		 Vector3 cameraPosition= new Vector3(camera.position);
		 float orbitRadius=0;
		 Vector3 orbitReturnVector = new Vector3();
		 
	        // (1) get intersection point for
	        //     camera viewing direction and xz-plane
	        camViewRay.set(camera.position, camera.direction);
	        Intersector.intersectRayPlane(camViewRay, xzPlane, lookAtPoint);
	 

	      // (2) calculate radius between
	        //     camera position projected on xz-plane
	        //     and the intersection point from (1)
	        orbitRadius = lookAtPoint.dst(cameraPosition.set(camera.position));
	 

	      // (3) move camera to intersection point from (1)
	        camera.position.set(lookAtPoint);
	               
	        // (4) rotate camera by 1° around y-axis
	        //     according to winding clockwise/counter-clockwise
	        camera.rotate(angle, 0, 1, 0);
	               
	        // (5) move camera back by radius
	        orbitReturnVector.set(camera.direction.tmp().mul(-orbitRadius));
	        camera.translate(orbitReturnVector.x, orbitReturnVector.y, orbitReturnVector.z);
	 }
	
	
	

}
