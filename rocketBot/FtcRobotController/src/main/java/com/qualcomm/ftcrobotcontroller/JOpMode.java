package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by James on 2/27/2016.
 */
public class JOpMode extends OpMode {

    // Motors
    DcMotor leftMotor, rightMotor;
    Servo camera;

    // Constant
    final double WHEEL_SCALE_FACTOR = 1.8;

    //Tracker
    double camTurnPosition = 0.5;
    double cameraMoveRate = 0.005;


    @Override
    public void init() {
        rightMotor = hardwareMap.dcMotor.get("m2");
        leftMotor = hardwareMap.dcMotor.get("m1");
        camera = hardwareMap.servo.get("camera");
        telemetry.addData("Message 1", "Ready for loop...");
    }

    @Override
    public void loop() {
        // Wheels
        leftMotor.setPower(gamepad1.left_stick_y / WHEEL_SCALE_FACTOR);
        rightMotor.setPower(-gamepad1.right_stick_y / WHEEL_SCALE_FACTOR);

        //Servo
        if (gamepad1.left_bumper) camTurnPosition = camTurnPosition + cameraMoveRate;
        if (gamepad1.right_bumper) camTurnPosition = camTurnPosition - cameraMoveRate;
        if (camTurnPosition > 1) camTurnPosition = 1;
        if (camTurnPosition < 0) camTurnPosition = 0;
        camera.setPosition(camTurnPosition);
    }


}
