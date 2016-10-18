package org.usfirst.frcpackage org.usfirst.frc.team4992.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.Jaguar;
//import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;


public class Robot extends IterativeRobot {
	//Construct speed controllers
	TalonSRX rightFront=new TalonSRX(3);	
	Jaguar rightBack   = new Jaguar(2);
	TalonSRX leftFront = new TalonSRX(4);
	Jaguar leftBack    = new Jaguar(1);
	RobotDrive bot = new RobotDrive (leftFront,leftBack,rightFront,rightBack);	
    private Solenoid s1,s2; 
    private Compressor myComp;
	//Define Joystick Variables
	private Joystick controller;
	private JoystickButton AButton;
	private JoystickButton BButton;
	double stickX;
	double stickY;
	double leftTrigger;
	double rightTrigger;
	double triggerValue;
	//Define Pneumatics
	

    

    @Override
	public void robotInit() {
        s1 = new Solenoid(0); //Construct Solenoid
        s2 = new Solenoid(1); //Construct Solenoid
        myComp = new Compressor(1); //Construct Compressor
        controller = new Joystick (0);//sets the joystick port/create joystick object for driving
        AButton = new JoystickButton(controller,1); //Construct "A" Button on controller
        BButton = new JoystickButton(controller,2); //Construct "A" Button on controller
    }
  
   
   @Override
public void teleopInit () {
	   bot.drive(0.0, 0.0); //Stop Robot
	   turnSolenoidsOff();
	   myComp.setClosedLoopControl(true);
   }
   
    @Override
	public void teleopPeriodic() {
		stickX = controller.getRawAxis(0);//update X value
		stickY = controller.getRawAxis(1);//update Y value
		leftTrigger = controller.getRawAxis(2);//triggers for driving
		rightTrigger = (controller.getRawAxis(3))*-1;//MAde negative so adds up
		triggerValue = leftTrigger+rightTrigger;
    	//bot.arcadeDrive(triggerValue, stickX);
    	//rightBack.set(rightBack.get()*-1);
    //	leftBack.set(leftBack.get()*-1);   
        if (AButton.get()){//If Button pressed call toggle function
        	extend();
    	}
        else if (BButton.get()) {
        	retract();
        }
        
        
    }
    public void toggleLifter() {
    	
    	s1.set(!s1.get());
    	s2.set(!s2.get());
   }
    
    public void retract (){
    	s2.set(true);
    	s1.set(false);
    }
    public void extend (){
    	s1.set(true);
    	s2.set(false);
    }

   
   public void fillTank() throws InterruptedException  {
       myComp.start(); //Start Compressor
      // compressorFull=false;
       long startTime=System.currentTimeMillis();
      while(myComp.getPressureSwitchValue() || System.currentTimeMillis()-startTime > 20000){
   	   	Thread.sleep(1);
      }
      myComp.stop();
     // compressorFull=true;
      
	  //Stop Compressor
	   
   }
   	
   //This function will turn bothe solenoids off
   
   public void turnSolenoidsOff(){
	   System.out.println("Turning Both Solenoids Off");
	   s1.set(false);
	   s2.set(false);
   }
 

}