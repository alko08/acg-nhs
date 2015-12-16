#include "JoystickDriver.c"
#include "C:\rdpartyrobotcdr-3.3.1\drivers\hitechnic-irseeker-v2.h"

//The amount of time one needs to turn to make a right angle.
#define RIGHT_ANGLE_TURN_TIME 1000
//The value for a dark right angle.
#define LIGHT_SENSOR_DARK 25
//The value for a light read the name
#define LIGHT_SENSOR_LIGHT 33
//The movespeed for autonomous
#define MOVESPEED 100
//A slower movespeed for autonomous
#define MOVESPEED_SLOW 10
#define MOVESPEED_MEDIUM 30
//The deadzone for the joystick
#define JOYSTICK_DEAD_ZONE 25

//Variables we need to stuff
bool gyroCalibrated = false; //Autonomous lolz
bool teleop; //To work with the drive processes

//*****************************************Normalization/Internal Number Manipulation**************************************************************************
//random comment commenting
int roundMotorValues(int power) {
	if (power > 100) {
		return 100;
		} else if (power < -100){
		return -100;
	}
	return power;
}

//Changes a joystick value (-127 to 128) to a motor value (-100 to 100)
int normalizeJoyForMotor(int power) {
	power = (int)((float) power * (float) 100 / (float) 127);
	if (power <= JOYSTICK_DEAD_ZONE && power >= -1 * JOYSTICK_DEAD_ZONE) {
		return 0;
	}
	roundMotorValues(power);
	return power;
}

bool clampSlideUp = true;
bool noOverwrite2 = false;

void doorServo(int door) {
	if(door) { //cover
		servo[ballCoverSrvo]=250;
	}else { //uncover
		servo[ballCoverSrvo]=100;
	}
}

void clampSlide(int mode) {
	if(mode != 1 || noOverwrite2 == true) {
			return;
		}
		noOverwrite2 = true;

		if(clampSlideUp) {
			servo[clampSlideSrvo] = 256;
		} else if(!clampSlideUp) {
			servo[clampSlideSrvo] = 0;
		}

		clampSlideUp = !clampSlideUp;
		wait10Msec(130);
		servo[clampSlideSrvo] = 127;
		noOverwrite2 = false;
}

void emergencyBrake () { //This is used to completely stop the robot if using the singleStickDrive function (which doesn't stop on it's own)
	motor[frontLeft] = 0; motor[backLeft] = 0;
	motor[frontRight] = 0; motor[backRight] = 0;
}

void emergencySubBrake() {
	motor[nxtHandMtr] = 0;
	motor[armBranchTurner] = 0;
}

//*****************************************4 Wheel Drive Functions**************************************************************************
void FrontRearDrive (int Power) { //this is forward-backward drive
	Power = roundMotorValues(Power); //prevents input from being over 100 and under -100

	motor[frontLeft] = Power; motor[backLeft] = Power;
	motor[frontRight] = Power; motor[backRight] = Power;
}

void CyclicDrive (int Power) { //this rotates the robot
	Power = roundMotorValues(Power); //prevents input from being over 100 and under -100

	motor[frontLeft] = -Power; motor[backLeft] = -Power;
	motor[frontRight] = Power; motor[backRight] = Power;
}

//A basic drive system that drives the left and right sides at the specified power.
void drive(int leftPower, int rightPower) {
	motor[frontLeft] = -leftPower; motor[backLeft] = -leftPower;
	motor[frontRight] = -rightPower; motor[backRight] = -rightPower;
}

//Turns left in place at the given power.
void driveTurnLeftInPlace(int power) {
	drive(-power, power);
}

//Turns right in place at the given power.
void driveTurnRightInPlace(int power) {
	drive(power, -power);
}

//Drives forward in place at the given power
void drive(int power){ //Drives forward with equal power to each side
	drive(power, power);
}

//Drives only the left side at the given power.
void driveLeftSide(int power){
	drive(power, 0);
}

//Drives only the right side at the given power.
void driveRightSide(int power){
	drive(0, power);
}

void singleStickDrive(int x, int y){
	x = normalizeJoyForMotor(x);
	y = normalizeJoyForMotor(y);

	if(y/x>1 || y/x<-1){
		FrontRearDrive(-y);
  }else{
  	CyclicDrive(x);
	}
}

/* The 90 degree turnings that use gyro (not done yet)
//Turns 90 degrees left at the given power.
void driveTurnLeft90(int power){
	float degrees = 0;
	writeDebugStream("TURNING...");
	if (!gyroCalibrated) {
		HTGYROstartCal(HTGYRO);
		gyroCalibrated = true;
	}
	writeDebugStreamLine("Calibration done");
	ClearTimer(T1);
	wait10Msec(1);
	writeDebugStreamLine("Degrees: %d", degrees);
	driveTurnLeftInPlace(power);
	while(abs(degrees) / 91.0 < 90)
	{
		if(time1[T1] % 10 == 0)
		{
			degrees += getGyroSensor();
		}
	}
	drive(0);
}

//Turns 90 degrees to the right at the given power.
void driveTurnRight90(int power){
	float degrees = 0;
	writeDebugStream("TURNING...");
	if (!gyroCalibrated) {
		HTGYROstartCal(HTGYRO);
		gyroCalibrated = true;
	}
	writeDebugStreamLine("Calibration done");
	ClearTimer(T1);
	wait10Msec(1);
	writeDebugStreamLine("Degrees: %d", degrees);
	driveTurnRightInPlace(power);
	while(abs(degrees) / 91.0 < 90)
	{
		if(time1[T1] % 10 == 0)
		{
			degrees += getGyroSensor();
		}
	}
	drive(0);
}*/

//
// Code for ball collector and goal clamp
//

void ToggleBallCollecter (int mode) {
	if (mode == 1) {
		motor[collectorMtr] = -100; //normal procedure
	}
	else if (mode == 2) {
		motor[collectorMtr] = 0; 	//stop motor
	}
	else if (mode == 3) {
		motor[collectorMtr] = 100; 	//reversed procedure
	}
}

//------------------------------------------
bool clampUp = true;
bool noOverwrite = false;

void clampRotate(int mode) {
	if(mode != 1 || noOverwrite == true) {
		return;
	}
	noOverwrite = true;

	if(clampUp) { //Clamp down
		servo[clampSrvo] = 63;//unlike motors this sets the servo's POSITION
	} else if(!clampUp) { //Clamp up
		servo[clampSrvo] = 200;
	}

	clampUp = !clampUp;
	wait10Msec(50);
	noOverwrite = false;
}

int armPower = 50; //default and max power. Should be a multiple of 5 for niceness

void joyButtonArmMov(int button1Down, int button2Down) { //takes in joystick controls for arm movement
	int x = button1Down;
	int y = button2Down;

	if(x == 1) {
		motor[armBranchTurner] = armPower;
	} else if(y == 1) {
		motor[armBranchTurner] = armPower*-1;
	} else {
		motor[armBranchTurner] = 0;
	}
}

//------------------------------------------
//see http://www.usfirst.org/sites/default/files/uploadedFiles/Robotics_Programs/FTC/FTC_Documents/Using_the_IR_Seeker.pdf for an ir sensor tutorial
//IF THIS BREAKS YOUR CODE YOUR CODE IS WRONG!
int getIRSensorDirection() {
	//return SensorValue[IRSensor];
	int direction1, direction3, direction5, direction7, direction9 = 0;
	HTIRS2readAllACStrength(irSensor, direction1, direction3, direction5, direction7, direction9); //magically sets directions to ir values 0-?

	//writeDebugStreamLine("%d %d %d %d %d", direction1, direction3, direction5, direction7, direction9);
	if(direction1 < 3 && direction3 < 3 && direction5 < 3 && direction7 < 3 && direction9 < 3) return 0;
	if(direction1 > 0 && direction3 == 0) return 1;
	if(direction1 > 0 && direction3 > 0) return 2;
	if(direction1 == 0 && direction3 > 0 && direction5 == 0) return 3;
	if(direction1 == 0 && direction3 > 0 && direction5 > 0 && direction7 == 0) return 4;
	if(direction3 == 0 && direction5 > 0 && direction7 == 0) return 5;
	if(direction3 == 0 && direction5 > 0 && direction7 > 0 && direction9 == 0) return 6;
	if(direction5 == 0 && direction7 > 0 && direction9 == 0) return 7;
	if(direction7 > 0 && direction9 > 0) return 8;
	if(direction7 == 0 && direction9 > 0 && direction7 == 0) return 9;
	return 0;
}

//returns the signal strength for the forward direction.
int getIRSensorStrength() {
	int direction1, direction3, direction5, direction7, direction9 = 0;
	HTIRS2readAllACStrength(irSensor, direction1, direction3, direction5, direction7, direction9);
	return direction5;
}

//Autonomous One

void normalize(){ //Resets all driving tasks
	teleop = false;
}

void goForward(int time, int direction){ //Go forward for time * 10ms
	FrontRearDrive(-100 * direction);
	wait10Msec(time);
	FrontRearDrive(0);
}

void turn(int deg){ //Weird turning thing that turns degrees
	int power;
	if(deg < 0){ //if postive (left)
		power = -100;
	}
	else { // if negative (right)
		power = 100;
	}
	deg = abs(deg);
	for(int i=0; i<deg; i++){
		CyclicDrive(power);
		wait10Msec(2.5);
	}
	CyclicDrive(0);
}
