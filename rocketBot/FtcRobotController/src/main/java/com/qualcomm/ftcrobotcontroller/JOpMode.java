package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by James The Legend on 2/27/2016.
 */
public class JOpMode extends OpMode {

    // Motors
    DcMotor leftMotorFront, rightMotorFront, leftMotorBack, rightMotorBack;
    Servo camera;

    // Constant
    final double WHEEL_SCALE_FACTOR = 1.8;

    //Tracker
    double camTurnPosition = 0.5;
    double cameraMoveRate = 0.005;


    @Override
    public void init() {
        rightMotorFront = hardwareMap.dcMotor.get("m2");
        leftMotorFront = hardwareMap.dcMotor.get("m1");
        rightMotorBack = hardwareMap.dcMotor.get("m3");
        leftMotorBack = hardwareMap.dcMotor.get("m4");
        camera = hardwareMap.servo.get("camera");
        telemetry.addData("Message 1", "Motors and servo declared! All Systems go!");
    }

    @Override
    public void loop() {
        // Wheels
        leftMotorFront.setPower(gamepad1.left_stick_y / WHEEL_SCALE_FACTOR);
        rightMotorFront.setPower(-gamepad1.right_stick_y / WHEEL_SCALE_FACTOR);
        leftMotorBack.setPower(gamepad1.left_stick_y / WHEEL_SCALE_FACTOR);
        rightMotorBack.setPower(-gamepad1.right_stick_y / WHEEL_SCALE_FACTOR);

        //Servo
        if (gamepad1.left_bumper) camTurnPosition = camTurnPosition + cameraMoveRate;
        if (gamepad1.right_bumper) camTurnPosition = camTurnPosition - cameraMoveRate;
        if (camTurnPosition > 1) camTurnPosition = 1;
        if (camTurnPosition < 0) camTurnPosition = 0;
        camera.setPosition(camTurnPosition);

        //values of sticks displayed on phone
        //telemetry.addData("Text", "*** Robot Data***");
        //telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", left));
        //telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));
    }


}
