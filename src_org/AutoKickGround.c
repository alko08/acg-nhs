#pragma config(Hubs,  S1, HTMotor,  HTMotor,  HTMotor,  HTServo)
#pragma config(Sensor, S2,     gyroSensor,     sensorI2CHiTechnicGyro)
#pragma config(Sensor, S3,     ultraSonic,     sensorSONAR)
#pragma config(Sensor, S4,     irSensor,       sensorI2CCustom)
#pragma config(Motor,  motorA,          nxtHandMtr,    tmotorNXT, PIDControl, encoder)
#pragma config(Motor,  mtr_S1_C1_1,     frontLeft,     tmotorTetrix, openLoop, reversed)
#pragma config(Motor,  mtr_S1_C1_2,     frontRight,    tmotorTetrix, openLoop)
#pragma config(Motor,  mtr_S1_C2_1,     backRight,     tmotorTetrix, openLoop)
#pragma config(Motor,  mtr_S1_C2_2,     backLeft,      tmotorTetrix, openLoop, reversed)
#pragma config(Motor,  mtr_S1_C3_1,     armBranchTurner, tmotorTetrix, openLoop)
#pragma config(Motor,  mtr_S1_C3_2,     collectorMtr,  tmotorTetrix, openLoop)
#pragma config(Servo,  srvo_S1_C4_1,    clampSrvo,            tServoStandard)
#pragma config(Servo,  srvo_S1_C4_2,    ballCoverSrvo,        tServoStandard)
#pragma config(Servo,  srvo_S1_C4_3,    clampSlideSrvo,       tServoContinuousRotation)
#pragma config(Servo,  srvo_S1_C4_4,    servo4,               tServoNone)
#pragma config(Servo,  srvo_S1_C4_5,    servo5,               tServoNone)
#pragma config(Servo,  srvo_S1_C4_6,    servo6,               tServoNone)
//*!!Code automatically generated by 'ROBOTC' configuration wizard               !!*//

#include "CascadeLibrary.h"  //Include file to "handle" the Bluetooth messages.

////////////////////////////////////////
//
// This is to drive from the ground and knock over the kickstand
// The robot will be facing FORWARD from the GROUND
// NOT FINISHED
// HARDWARE ISSUE
//
////////////////////////////////////////

void initializeRobot() //Start
{
	normalize(); //Make stuff normal
  return;
}

int centerGoalPos() {
	while(true) {
		writeDebugStreamLine("%d", getIRSensorDirection());
	}

	return 0;
}

void findAndMoveToIr () {
	if (getIRSensorDirection() == 5 || getIRSensorDirection() == 4 || getIRSensorDirection() == 6) { //pos 1

		goForward(80, 1);
		driveTurnRightInPlace(100);
		wait10Msec(150);
		driveTurnRightInPlace(0);
		goForward(200, 1);
	}
	else if(getIRSensorDirection() == 7) { //pos 3 or 2
		driveTurnRightInPlace(100);
		wait10Msec(100);
		driveTurnRightInPlace(0);

		if(SensorValue(ultraSonic) > 50) { //pos 3
			driveTurnRightInPlace(100);
			wait10Msec(100);
			driveTurnRightInPlace(0);
			goForward(240, 1);
			driveTurnLeftInPlace(100);
			wait10Msec(180);
			driveTurnLeftInPlace(0);
			goForward(300, 1);
		} else { //pos 2
			goForward(300, 1);
		}
	}
}

//_______________________________________________________________________________
//IMPORTANT! robot should be placed with left wheel in the middle of the red tape|
//--------------------------------------------------------------------------------

task main() {
  initializeRobot();
  waitForStart(); // Wait for the beginning of autonomous phase.
  goForward(220, 1);
	findAndMoveToIr();

  while (true)
  {}
}