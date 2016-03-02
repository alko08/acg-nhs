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
    Servo Hcamera, Vcamera;

    // Constant
    final double WHEEL_SCALE_FACTOR = 1.6;
    double speedRight;
    double speedLeft;

    //Tracker
    double HcamTurnPosition = 0.5;
    double VcamTurnPosition = 0;
    double cameraMoveRate = 0.005;


    @Override
    public void init() {
        rightMotorFront = hardwareMap.dcMotor.get("m2");
        leftMotorFront = hardwareMap.dcMotor.get("m1");
        rightMotorBack = hardwareMap.dcMotor.get("m3");
        leftMotorBack = hardwareMap.dcMotor.get("m4");
        Hcamera = hardwareMap.servo.get("Hcamera");
        Vcamera = hardwareMap.servo.get("Vcamera");

        telemetry.addData("Message 1", "Motors and servos declared! All Systems go!");
    }

    @Override
    public void loop() {
        //Wheels
        speedRight = gamepad1.left_stick_y / WHEEL_SCALE_FACTOR;
        speedLeft = -gamepad1.right_stick_y / WHEEL_SCALE_FACTOR;
        leftMotorFront.setPower(speedLeft);
        rightMotorFront.setPower(speedRight);
        leftMotorBack.setPower(speedLeft);
        rightMotorBack.setPower(speedRight);

        //Horizontal Servo Control
        if (gamepad1.left_bumper) HcamTurnPosition = HcamTurnPosition + cameraMoveRate;
        if (gamepad1.right_bumper) HcamTurnPosition = HcamTurnPosition - cameraMoveRate;
        if (HcamTurnPosition > 1) HcamTurnPosition = 1;
        if (HcamTurnPosition < 0) HcamTurnPosition = 0;
        Hcamera.setPosition(HcamTurnPosition);

        //Vertical Servo Control
        if (gamepad1.left_trigger > 0.85) VcamTurnPosition = VcamTurnPosition + cameraMoveRate;
        if (gamepad1.right_trigger > 0.85) VcamTurnPosition = VcamTurnPosition - cameraMoveRate;
        if (VcamTurnPosition > 1) VcamTurnPosition = 1;
        if (VcamTurnPosition < 1) VcamTurnPosition = 0;
        Vcamera.setPosition(VcamTurnPosition);

        //values of sticks displayed on phone
        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", speedLeft));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", speedRight));
    }


}
