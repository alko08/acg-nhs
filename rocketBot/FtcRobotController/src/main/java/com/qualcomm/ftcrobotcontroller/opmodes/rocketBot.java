
package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class rocketBot extends OpMode {


    DcMotor motorRight;
    DcMotor motorLeft;

    Servo Cam;

    final static double CAM_MIN_RANGE  = 0.10;
    final static double CAM_MAX_RANGE  = 0.90;

    double camPosition;
    double camLastPosition;

    double camDelta = 0.02;


    @Override
    public void init() {
        motorRight = hardwareMap.dcMotor.get("m1");
        motorLeft = hardwareMap.dcMotor.get("m2");
        Cam = hardwareMap.servo.get("s1");

        // James Says: These aren't necessary
        //motorLeft.setDirection(DcMotor.Direction.REVERSE);
        //motorRight.setDirection(DcMotor.Direction.REVERSE);


        camPosition = 0.5;
    }

    @Override
    public void loop() {

        // tank drive
        // note that if y equal -1 then joystick is pushed all of the way forward.
        float left = gamepad1.left_stick_y;
        float right = -gamepad1.right_stick_y;

        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        right = (float)scaleInput(right);
        left =  (float)scaleInput(left);
        // write the values to the motors

        motorRight.setPower(left);
        motorLeft.setPower(right);



        if (gamepad1.right_bumper) {
            //if right dpad is pressed, cam servo goes to the right
            Cam.setPosition(0.9);

        }
        else if (gamepad1.left_bumper) {
            // if left dpad is pressed, cam servo goes to the left
            Cam.setPosition(0.1);

        }
        else if(gamepad1.x){
            //if up dpad is pressed, cam servo goes straight ahead
            Cam.setPosition(0.5);

        }
        else {
            //if dpad is released, the servo will stay pointed at the last requested direction
            Cam.setPosition(0.5);
        }


        camPosition = Range.clip(camPosition, CAM_MIN_RANGE, CAM_MAX_RANGE);
        //Servo Controlling

        //Cam.setPower(1f);


        // update the position of the arm.

      /*
       * Send telemetry data back to driver station. Note that if we are using
       * a legacy NXT-compatible motor controller, then the getPower() method
       * will return a null value. The legacy NXT-compatible motor controllers
       * are currently write only.
       */

        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", left));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));
    }

    /*
     * Code to run when the Op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
    @Override
    public void stop() {

    }

    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        // James Says: This code works just fine, except you could also use "scaleArray.length"
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

}