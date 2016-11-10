//package lejos.nxt;

import lejos.nxt.*;


/**
 * LServo, Lattebox Servo, is a abstraction to model any Servo connected to
 * LSC, Lattebox Servo Controller. 
 * 
 * @author Juan Antonio Brenha Moral
 */
public class LServo extends I2CSensor{
	private String servoName = "";//String to describe any Servo connected to LSC
	private int angle;//Angle
	private int speed;//Not implemented yet.
	private int min_angle = 0;
	private int max_angle = 2000;
	private int LSC_position; //Position where Servo has been pluged
	
	//Servo ID
	private SensorPort portConnected;//What
	private byte SPI_PORT;//What SPI Port is connected LSC
	private int servo;
	
	//I2C
	int I2C_Response;
	private byte[] bufReadResponse;
	private byte h_byte;
	private byte l_byte;
	
	public static final int arrServoUnload[] = {(int)0x01,(int)0x02,(int)0x04,(int)0x08,(int)0x20,(int)0x40,(int)0x80,(int)0x100,(int)0x200};
	public static final int arrServoLoad[] = {(int)0x3FE,(int)0x3FD,(int)0x3FB,(int)0x3F7,(int)0x3EF,(int)0x3DF,(int)0x3BF,(int)0x37F,(int)0x2FF,(int)0x1FF};
	
	/**
	 * Constructor
	 * 
	 * @param port
	 * @param location
	 * @param servoName
	 * @param SPI_PORT
	 *  
	 */
	public LServo(SensorPort port, int location, String servoName, byte SPI_PORT){
		super(port);
		this.servoName = servoName;
		this.LSC_position = location;
		
		this.SPI_PORT = SPI_PORT;
		
		bufReadResponse = new byte[8];		
		this.setAddress((int) NXTe.NXTE_ADDRESS);
	}
	
	/**
	 * Method to setAngle in Servo. 
	 * 
	 * Note:
	 * In next version, I will delete servo parameter, 
	 * because the object should know servo id.
	 * 
	 * @param angle
	 * 
	 */
	public void setAngle(int angle){
		int servo = LSC_position;
		h_byte = (byte)(0x80 | ((servo<<3) | (angle >>8)));
	    l_byte = (byte)angle;
		
	    //High Byte Write
		I2C_Response = this.sendData((int)this.SPI_PORT, h_byte);

	    //Low Byte Write
		I2C_Response = this.sendData((int)this.SPI_PORT, l_byte);
	}
	
	/**
	 * 
	 * Method to know the angle
	 * 
	 * Note:
	 * In next version, I will delete servo parameter, 
	 * because the object should know servo id.
	 *
	 * @return
	 * 
	 */
	public int getAngle(){
		int servo = LSC_position;
	    //Write OP Code
	    h_byte  = (byte)(servo << 3);
		I2C_Response = this.sendData((int)this.SPI_PORT, h_byte);
		
	    //Read High Byte
	    //I2CBytes(IN_3, bufReadValue, buflen, bufReadResponse);
		I2C_Response = this.sendData((int)this.SPI_PORT, (byte)0x00);		
		I2C_Response = this.getData((int)this.SPI_PORT, bufReadResponse, 1);
		
	    h_byte = bufReadResponse[0];
	    
	    //Read Low Byte
		I2C_Response = this.sendData((int)this.SPI_PORT, (byte)0x00);
		I2C_Response = this.getData((int)this.SPI_PORT, bufReadResponse, 1);
	    l_byte = bufReadResponse[0];
	    
	    return  ((h_byte & 0x07 ) << 8) +  (l_byte & 0x00000000FF);
	}
	
	
	/**
	 * 
	 * public method to know internal information about 
	 * if the servo is moving
	 * 
	 * @return
	 * 
	 */
	public int readMotion(){
		int motion = -1;
		
		//Write OP Code
		I2C_Response = this.sendData((int)this.SPI_PORT, (byte)0x68);
		
		//Read High Byte
		I2C_Response = this.sendData((int)this.SPI_PORT, (byte)0x00);	
		I2C_Response = this.getData((int)this.SPI_PORT, bufReadResponse, 1);
		h_byte = bufReadResponse[0];

		//Read Low Byte
		I2C_Response = this.sendData((int)this.SPI_PORT, (byte)0x00);	
		I2C_Response = this.getData((int)this.SPI_PORT, bufReadResponse, 1);
		l_byte = bufReadResponse[0];
	
		if(l_byte == 0xFF){
			motion =  ((h_byte & 0x07 ) << 8) + 255;
		}else{
			motion = ((h_byte & 0x07 ) << 8)|(l_byte&0xFF);
		}
		return motion;
	}
	
	/**
	 * Method to know if Servo is moving to a determinated angle
	 * 
	 * @return
	 */
	public boolean isMoving(){
		boolean flag = false;
		if(readMotion() != 0){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * Set a delay in Servo
	 * 
	 * Note:
	 * In next version, I will delete servo parameter, 
	 * because the object should know servo id.
	 * 
	 * @param Servo
	 * @param delay
	 */
	public void setDelay(int delay){
		int servo = LSC_position;
		h_byte = (byte)0xF0;
		l_byte = (byte)(((servo)<<4) + delay);
	     
	     I2C_Response = this.sendData((int)this.SPI_PORT, (byte)h_byte);
	     I2C_Response = this.sendData((int)this.SPI_PORT, (byte)l_byte);
	}
	
	/**
	 * Set Minimal angle. Useful method to calibrate a Servo
	 * 
	 * @param minAngle
	 * 
	 */
	public void setMinAngle(int minAngle){
		this.min_angle = minAngle;
	}

	/**
	 * Set Maximum angle. Useful method to calibrate a Servo
	 * 
	 * @param maxAngle
	 * 
	 */	
	public void setMaxAngle(int maxAngle){
		this.max_angle = maxAngle;
	}	

	/**
	 * Method to set minimal angle
	 *  
	 */	
	public void goToMinAngle(){
		this.setAngle(this.min_angle);
	}

	/**
	 * Method to set maximum angle
	 * 
	 */	
	public void goToMaxAngle(){
		this.setAngle(this.max_angle);		
	}

	/**
	 * Method to set medium angle
	 * 
	 */		
	public void goToMiddleAngle(){
		float middle = (this.min_angle + this.max_angle) / 2;
		
		this.setAngle(Math.round(middle));		
	}

	/**
	 * Classic forward method
	 * 
	 */
	public void forward(){
		this.setAngle(0);
	}

	/**
	 * Classic backward method
	 * 
	 */
	public void backward(){
		this.setAngle(2000);
	}
	
	/**
	 * Get servo name
	 * 
	 */	
	public String getName(){
		return this.servoName;
	}
	
	public void unload(){
		int channel = (int)0x00;
		channel = (int) arrServoUnload[LSC_position];
		
		h_byte = (byte)0xe0; //0xe0 | (0x00 >>(byte)8); //?? 
		l_byte = (byte)channel;
	     
	    //High Byte Write
		I2C_Response = this.sendData((int)this.SPI_PORT, h_byte);

	    //Low Byte Write
		I2C_Response = this.sendData((int)this.SPI_PORT, l_byte);		
	}	
	
	/**
	 * Load Servo located in a position X
	 * 
	 * @param location
	 */
	public void load(){
		int channel = (int)0x00;
		channel = (int) arrServoLoad[LSC_position];
		
		h_byte = (byte)0xe0; //0xe0 | (0x00 >>(byte)8); //?? 
		l_byte = (byte)channel;
	     
	    //High Byte Write
		I2C_Response = this.sendData((int)this.SPI_PORT, h_byte);

	    //Low Byte Write
		I2C_Response = this.sendData((int)this.SPI_PORT, l_byte);		
	}	
}