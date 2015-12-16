#pragma config(Hubs,  S1, HTMotor,  HTMotor,  HTMotor,  HTServo)
#pragma config(Sensor, S2,     gyroSensor,     sensorI2CHiTechnicGyro)
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
// This is to drive from the ramp and kick the kickstand down from there
// The robot will be facing FORWARD from the RAMP
// MOSTLY FINISHED (You can work with it without the IR sensor)
// HARDWARE ISSUE
//
////////////////////////////////////////

void initializeRobot() //Start
{
	normalize(); //Make stuff normal
  return;
}

void findAndMoveToIr() {
	if(getIRSensorDirection() == 5) {
		goForward(100, 1);
	} else if(getIRSensorDirection() == 6) {
		writeDebugStreamLine("Nope");
	} else if (getIRSensorDirection() == 7) {
		//motor[frontLeft] = -100; motor[backLeft] = -100;
		//motor[frontRight] = -100; motor[backRight] = -100;
	}
}

task main()
{
  initializeRobot();

  waitForStart(); // Wait for the beginning of autonomous phase.

	goForward(420, 1); //Go forward 2.1 sec
	turn(80); // Turn 90 degs left
	goForward(350, 1);
	turn(-90);

	//findAndMoveToIr();

  while (true)
  {}
}
