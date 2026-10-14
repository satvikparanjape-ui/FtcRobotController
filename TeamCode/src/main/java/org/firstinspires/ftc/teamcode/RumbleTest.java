package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class RumbleTest extends OpMode{
    /*
    boolean wasA, isA;
     */
    double endGameStart;
    boolean isEndGame;

    @Override
    public void init() {

    }

    public void start() {
        endGameStart = getRuntime() + 90;
    }

    @Override
    public void loop() {
        /* *
        isA = gamepad1.a;
        if (isA && !wasA) {
            //gamepad1.rumble(1.0, 0, 100); //(left joystick, right joystick, duration)
            gamepad1.rumbleBlips(3);
        }
        wasA = isA;
         */

        //end game check
        if (endGameStart >= getRuntime() && !isEndGame) {
            gamepad1.rumbleBlips(3);
            isEndGame = true;
        }
    }
}
