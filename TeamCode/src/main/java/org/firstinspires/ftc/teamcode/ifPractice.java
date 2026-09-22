package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp
public class ifPractice extends OpMode {

    @Override
    public void init() {

    }

    @Override
    public void loop() {

        /*
        boolean aButton = gamepad1.a; // press TRUE, depress FALSE

        // true or false
        if(aButton) {
            telemetry.addData("A Button", "Pressed!");
        } else {
            telemetry.addData("A Button", "NOT Pressed");
        }

        telemetry.addData("A Button State", aButton);
        */

        /*
        double leftY = gamepad1.left_stick_y;

        if (leftY < 0.1 && leftY > -0.1) {
            telemetry.addData("Left Stick", "In Dead Zone");
        }

        telemetry.addData("Left Stick Value", leftY);
        */

        /*
        double leftY = gamepad1.left_stick_y;

        if (leftY < 0) {
            telemetry.addData("Left Stick", "Is Negative");
        } else if (leftY > 0.5){
            telemetry.addData("Left Stick", "Is >50%");
        } else if (leftY > 0) {
            telemetry.addData("Left Stick", "Is Positive");
        }  else {
            telemetry.addData("Left Stick", "Is Zero!");
        }

        telemetry.addData("Left Stick Value", leftY);
        */


        double motorSpeed = gamepad1.left_stick_y;

        if(!gamepad1.a) {
            motorSpeed *= 0.5;
        }
    }
}

/*

AND - && if (leftY < 0.5 && leftY > 0) {
OR - || if (leftY < 0 || rightY < 0) {
NOT - ! if (!ClawClosed) {
 */
