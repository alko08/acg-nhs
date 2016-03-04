package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Tyler on 3/4/2016.
 */
public class CamTest extends OpMode{
    //Declaration of servo variables
    Servo vCam,hCam;
    //Initial Values for servos
    double hCamTurnPosition = 0.5;
    double vCamTurnPosition = 0.2; //EXPERIMENTAL VALUE

    //Rate at which the servos move
    final double MoveRate = 0.005;
    public void init() {
        //mapping servos vCam is for vertical turning, hCam is horizontal turning
        vCam = hardwareMap.servo.get("vCam");
        hCam = hardwareMap.servo.get("hCam");

        telemetry.addData("Message 1", "Servos declared! All Systems go!");

    }

    public void loop() {
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
        telemetry.addData("VcamSpot", "Vertical Cameria Position: " + String.valueOf(vCamTurnPosition));
        telemetry.addData("hCamSpot", "Horizontal Camera Position: " + String.valueOf(hCamTurnPosition));
    }
}
