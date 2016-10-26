package org.usfirst.frc.team4992.robot;

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
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Jaguar;
//import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;


public class Robot extends IterativeRobot {
	
    private final static double turnSpeed = 0.2;
    private final static double driveSpeed = 0.8;

    private final static int ac_gyro = 0;
    private final static int ac_ranger = 3;
    private final static int c_rFront =3;
    private final static int c_lFront = 4;
    private final static int c_rBack = 2;
    private final static int c_lBack = 1;

    
    
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
	
	//Construct speed controllers
	TalonSRX rightFront=new TalonSRX(c_rFront);
	Jaguar rightBack   = new Jaguar(c_rBack);
	TalonSRX leftFront = new TalonSRX(c_lFront);
	Jaguar leftBack    = new Jaguar(c_rFront);
	
	RobotDrive bot = new RobotDrive (leftFront,leftBack,rightFront,rightBack);	

	AnalogGyro gyro;
	Ultrasonic ranger; 
	
    Timer driveTimer;

    //for the driver station

    String autoSelected;
    SendableChooser chooser;
    
    @Override
	public void robotInit() {
    	gyro = new AnalogGyro(ac_gyro);
    	
  //      ranger = new AnalogInput(ac_ranger);
        driveTimer = new Timer();
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", chooser);
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    	autoSelected = (String) chooser.getSelected();
//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
		//get an absolute reference system
		gyro.reset();
		gyro.calibrate();
    }
    

    private boolean rotateRight(int angle) {
		// TODO Auto-generated method stub
 	   rightBack.set(turnSpeed);
 	   leftBack.set(turnSpeed);
 	   rightBack.set(turnSpeed);
 	   leftBack.set(turnSpeed);
 	   return gyro.getAngle()>=angle;
	}
    private boolean rotateLeft(int angle) {
  	   rightBack.set(-turnSpeed);
  	   leftBack.set(-turnSpeed);
  	   rightBack.set(-turnSpeed);
  	   leftBack.set(-turnSpeed);
 	   return gyro.getAngle()>=angle;
	}
    
	/**
     * This function is called periodically during autonomous
     */
    private int step = 0;// this is the variable which is the current operating code in autonomous
    
    public void autonomousPeriodic() {
    	
    	switch(autoSelected) {
    	case customAuto:
        //Put custom auto code here   
            break;
    	case defaultAuto:
    	default:
    	//Put default auto code here
    		switch(step){
    		case 0:
    			step ++;
    			break;
    		case 1:
    			drive(0.3);
    			if (driveTimer.hasPeriodPassed(5000)){
    				step++;
    				driveTimer.stop();
    			}
    			break;
    		case 2:
    			if (rotateRight(180)){
    				step++;
    			}
    			break;
    		
    		case 3:
    			
    			drive(0.6);
    			if (driveTimer.hasPeriodPassed(2500)){
    				step++;
    				driveTimer.stop();
    			}
            break;
    		}
    	}
    }
   
    
    private void timerMark(Timer t){
    	if (t.get()==0){
    		t.reset();
    		t.start();
    	}   	
    }
    
   public void drive(double speed){
	   timerMark(driveTimer);
	   rightBack.set(-speed);
	   rightFront.set(-speed);
	   leftBack.set(speed);
	   leftFront.set(speed);
   }
   
   @Override
public void teleopInit () {
	   bot.drive(0.0, 0.0); //Stop Robot
//	   turnSolenoidsOff();
//	   myComp.setClosedLoopControl(true);
//   }
   }
    @Override
	public void teleopPeriodic() {
//		stickX = controller.getRawAxis(0);//update X value
//		stickY = controller.getRawAxis(1);//update Y value
//		leftTrigger = controller.getRawAxis(2);//triggers for driving
//		rightTrigger = (controller.getRawAxis(3))*-1;//MAde negative so adds up
//		triggerValue = leftTrigger+rightTrigger;
//    	//bot.arcadeDrive(triggerValue, stickX);
//    	//rightBack.set(rightBack.get()*-1);
//    //	leftBack.set(leftBack.get()*-1);   
//        if (AButton.get()){//If Button pressed call toggle function
//        	extend();
//    	}
//        else if (BButton.get()) {
//        	retract();
//        }
        
        
    }
//    public void toggleLifter() {
////    	
////    	s1.set(!s1.get());
////    	s2.set(!s2.get());
//   }
//    
//    public void retract (){
//    	s2.set(true);
//    	s1.set(false);
//    }
//    public void extend (){
//    	s1.set(true);
//    	s2.set(false);
//    }
//
//   
//   public void fillTank() throws InterruptedException  {
//       myComp.start(); //Start Compressor
//      // compressorFull=false;
//       long startTime=System.currentTimeMillis();
//      while(myComp.getPressureSwitchValue() || System.currentTimeMillis()-startTime > 20000){
//   	   	Thread.sleep(1);
//      }
//      myComp.stop();
//     // compressorFull=true;
//      
//	  //Stop Compressor
//	   
//   }
//   	
//   //This function will turn bothe solenoids off
//   
//   public void turnSolenoidsOff(){
//	   System.out.println("Turning Both Solenoids Off");
//	   s1.set(false);
//	   s2.set(false);
//   }
// 

}