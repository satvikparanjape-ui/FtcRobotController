package org.firstinspires.ftc.teamcode;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "Shoot Balls")
public class ShooterAutonomous extends OpMode {
    private final RobotHardware robot = new RobotHardware();

    public final int LAUNCHER_TARGET_VELOCITY = 1250; //2678 RPM
    public final int LAUNCHER_MIN_VELOCITY = 800; //2571 RPM //1200
    public double intakePower = 1.0;

    @Override
    public void init() {
        robot.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void start() {

        robot.setDrivePower(1.0, 1.0, 1.0, 1.0);
        sleep(500);
        robot.setDrivePower(0, 0, 0, 0);
        sleep(500);

        robot.setLauncherVelocity(LAUNCHER_TARGET_VELOCITY);
        robot.setWindmillPower(1);
        sleep(10000);

        robot.setDrivePower(-1.0, -1.0, -1.0, -1.0);
        sleep(700);
        robot.setDrivePower(0, 0, 0, 0);

        robot.setLauncherVelocity(0);
        robot.setWindmillPower(0);

    }

    @Override
    public void loop() {
        robot.setIntakePower(intakePower);
    }
}
