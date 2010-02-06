package demoman;
import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.*;
/*
*	Team 3181 Robotics
*		Project:	Breakaway
*		Codename:	Demoman
*						(The Chargin Scottsman)
*		Filename:	DriveSystem.java
*/
/**		
*		This class defines the drive system.  Functions for driving and the like.
*		
*		@author eric
*		@author ben													  
*
*/

public class DriveSystem extends RobotDrive {

	// Maintain ramping state:
	double lastLeftSpeed = 0.0;
	double lastRightSpeed = 0.0;
	double RAMPING_CONSTANT_1 = 0.0001; //change this for different speeds
	double RAMPING_CONSTANT_2 = 0.004; //not very useful
	double MAX_INCREASE = 0.1;
	Timer leftTimer = new Timer();
	Timer rightTimer = new Timer();
	int counter = 0;

	/**
	*	Constructor for the DriveSystem, does nothing but call the RobotDrive constructor.
	*
	*/
	DriveSystem (int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
	}

	// --- EXPONENTIAL RAMPING
	// Exponential ramping
	// Ramp to a given speed
	// Eric thinks he can do calculus, but he really can't
	// Ignore the next line
	// Approximate the equation ds/dt = ln 2 * 2^t
	// Increase/decrease speed by a constant times 2^t
	// @benhazawesome

	public void driveAtSpeed(double leftTarget, double rightTarget)	{

		double leftDelta = leftTarget - lastLeftSpeed;
		if (Math.abs(leftDelta)>MAX_INCREASE) {
			leftDelta = ((leftDelta < 0) ? -1 : 1) * RAMPING_CONSTANT_1 * MathUtils.pow(2.0, RAMPING_CONSTANT_2 * leftTimer.get());
		}else{
			leftTimer.reset();
		}
		lastLeftSpeed += leftDelta;
		lastLeftSpeed = Math.min(Math.max(lastLeftSpeed,-1.0),1.0); // make sure that speed is in between -1 and 1
		
		double rightDelta = rightTarget - lastRightSpeed;
		if (Math.abs(rightDelta)>MAX_INCREASE) {
			rightDelta = ((rightDelta < 0) ? -1 : 1) * RAMPING_CONSTANT_1 * MathUtils.pow(2, RAMPING_CONSTANT_2 * rightTimer.get());
		}else{
			rightTimer.reset();
		}
		lastRightSpeed += rightDelta;
		lastRightSpeed = Math.min(Math.max(lastRightSpeed,-1.0),1.0); // make sure that speed is in between -1 and 1

		setLeftRightMotorSpeeds(lastLeftSpeed,lastRightSpeed);
		
	}

	// --- END EXPONENTIAL RAMPING ---

	/*
	//  --- LINEAR RAMPING ---

	// Ramp to a given speed
	public void driveAtSpeed(double leftTarget, double rightTarget) {
	
		// Left
		double leftDelta = leftTarget - lastLeftSpeed;
		if (Math.abs(leftDelta) > RAMPING_CONSTANT) {
			leftDelta = ((leftDelta < 0) ? -1 : 1) * RAMPING_CONSTANT;
		}
		lastLeftSpeed += leftDelta;
		
		// Right
		double rightDelta = rightTarget - lastRightSpeed;
		if (Math.abs(rightDelta) > RAMPING_CONSTANT) {
			rightDelta = ((rightDelta < 0) ? -1 : 1) * RAMPING_CONSTANT;
		}
		lastRightSpeed += rightDelta;
	
		setLeftRightMotorSpeeds(lastLeftSpeed, lastRightSpeed);
	}
	//	--- END LINEAR RAMPING ---
	*/
	
	// Stop EVERYTHING.
	public void stop() {
		setLeftRightMotorSpeeds(0.0, 0.0);
		resetDefaults();
	}

	// It might get confused when we restart
	public void resetDefaults() {
		lastLeftSpeed = 0.0;
		lastRightSpeed = 0.0;
	}

}