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

// Defined automatically by ROBOTC

#include "CascadeLibrary.h" //Includes the library, which has the driving functions etc.

void findAndMoveToIr() {
		while(true) {
			if(getIRSensorDirection() > 5) { //orients robot so it faces ir...hopefully
				driveTurnLeftInPlace(100); //turn right
			}
			else if(getIRSensorDirection() < 5) { //orients robot so it faces ir...hopefully
				driveTurnRightInPlace(100); //turn left
			}
			else if (getIRSensorDirection() == 5) {
				motor[frontLeft] = -100; motor[backLeft] = -100;
				motor[frontRight] = -100; motor[backRight] = -100;
				//Keeps it always correcting itself, and doesn't break from loop
			}
		}
}

task main() {
	//waitForStart();
	normalize();
	findAndMoveToIr();
	while(true){}
}