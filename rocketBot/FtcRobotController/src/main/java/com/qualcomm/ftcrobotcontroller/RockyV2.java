package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.ftcrobotcontroller.opmodes.ResQ_Library;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Tyler The Creator on 3/4/2016 Special Thanks to James for helping overhaul our code :).
 */
public class RockyV2 extends ResQ_Library{
    // Motors
    DcMotor leftMotorFront, rightMotorFront, leftMotorBack, rightMotorBack;
    //Servos
    Servo vCam,hCam;
    //Values for virtual gearing
    double WHEEL_SCALE_FACTOR = 1.6;
    final double gearRate = 0.4;
    //Initial Values for servos
    double hCamTurnPosition = 0.5;
    double vCamTurnPosition = 0.2; //EXPERIMENTAL VALUE
    //Rate at which the servos move
    final double MoveRate = 0.005;
    //Variables for 1 stick drive
    double X,Y,V,W,right,left;



    @Override
    public void init() {
        //Getting Motors
        rightMotorFront = hardwareMap.dcMotor.get("m2");
        leftMotorFront = hardwareMap.dcMotor.get("m1");
        rightMotorBack = hardwareMap.dcMotor.get("m3");
        leftMotorBack = hardwareMap.dcMotor.get("m4");
        //Getting Servos
        vCam = hardwareMap.servo.get("vCam");
        hCam = hardwareMap.servo.get("hCam");

        //Message to confirm that everything is declared
        telemetry.addData("Message 1", "Motors and Servos declared! All Systems go!");

    }
    public void loop() {
        //Virtual Gearing
        //Keep gear number within range
        if (gamepad1.dpad_down){
            WHEEL_SCALE_FACTOR += gearRate;
        }
        if (gamepad1.dpad_up){
            WHEEL_SCALE_FACTOR -= gearRate;
        }

        //Keep values of scale factor within range
        Range.clip(WHEEL_SCALE_FACTOR, 1, 3);

        //1 Stick Drive
        Y = -(gamepad1.left_stick_x / WHEEL_SCALE_FACTOR); //Y is inverted with the negative sign
        X = (gamepad1.left_stick_y / WHEEL_SCALE_FACTOR); //NOT inverted

        V = (100-Math.abs(X)) * (Y/100) + Y; // R+L
        W = (100-Math.abs(Y)) * (X/100) + X; // R-L

        right = (V+W)/2;
        left = (V-W)/2;

        right = Range.clip(right, -1f, 1f);
        left = Range.clip(left, -1f, 1f);

        //Apply values to motors
        leftMotorFront.setPower(left);
        rightMotorFront.setPower(right);
        leftMotorBack.setPower(left);
        rightMotorBack.setPower(right);

        //Servos
        //X axis controls hCam, Y axis controls vCam
        if (gamepad1.right_stick_y > 0.4) vCamTurnPosition = vCamTurnPosition + MoveRate;
        if (gamepad1.right_stick_y < -0.4) vCamTurnPosition = vCamTurnPosition - MoveRate;
        if (gamepad1.right_stick_x > 0.4) hCamTurnPosition = hCamTurnPosition + MoveRate;
        if (gamepad1.right_stick_x < -0.4) hCamTurnPosition = hCamTurnPosition - MoveRate;
        //Keep positions of servos between 1 and -1
        vCamTurnPosition = Range.clip(vCamTurnPosition, -1, 1);
        hCamTurnPosition = Range.clip(hCamTurnPosition, -1, 1);

        //Write the values to the servos

        vCam.setPosition(vCamTurnPosition);
        hCam.setPosition(hCamTurnPosition);

        //display positions of servos
        telemetry.addData("VcamSpot", "Vertical Camera Position: " + String.valueOf(vCamTurnPosition));
        telemetry.addData("hCamSpot", "Horizontal Camera Position: " + String.valueOf(hCamTurnPosition));

    }
}
