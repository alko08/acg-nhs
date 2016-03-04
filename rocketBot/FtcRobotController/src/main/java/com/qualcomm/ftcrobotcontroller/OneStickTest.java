package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.ftcrobotcontroller.opmodes.ResQ_Library;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by T-Oz on 3/3/2016.
 */
public class OneStickTest extends ResQ_Library {
    // Motors
    DcMotor leftMotorFront, rightMotorFront, leftMotorBack, rightMotorBack;
    // Constant
    double WHEEL_SCALE_FACTOR = 1.6;
    double X,Y,V,W,right,left;



    @Override
    public void init() {
        rightMotorFront = hardwareMap.dcMotor.get("m2");
        leftMotorFront = hardwareMap.dcMotor.get("m1");
        rightMotorBack = hardwareMap.dcMotor.get("m3");
        leftMotorBack = hardwareMap.dcMotor.get("m4");
        telemetry.addData("Message 1", "Motors declared! All Systems go!");

    }
    public void loop() {
        if (gamepad1.dpad_down){
            WHEEL_SCALE_FACTOR = 3.0;
        }
        if (gamepad1.dpad_up){
            WHEEL_SCALE_FACTOR = 1.6;
        }
        //1 Stick Drive Code from Resq_Teleop.java
        Y = -(gamepad1.left_stick_x / WHEEL_SCALE_FACTOR); //Y is inverted with the negative sign
        X = (gamepad1.left_stick_y / WHEEL_SCALE_FACTOR); //NOT inverted

        V = (100-Math.abs(X)) * (Y/100) + Y; // R+L
        W = (100-Math.abs(Y)) * (X/100) + X; // R-L

        right = (V+W)/2;
        left = (V-W)/2;

        right = Range.clip(right, -1f, 1f);
        left = Range.clip(left, -1f, 1f);
        //End of Snippet

        //Apply values to motors
        leftMotorFront.setPower(left);
        rightMotorFront.setPower(right);
        leftMotorBack.setPower(left);
        rightMotorBack.setPower(right);


    }
}
