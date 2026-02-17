package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Limelight;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@TeleOp
@Config
@Configurable
public class TurretMovementTest extends OpMode {
    public Turret turret;
    private Follower follower;
//    Follower follower;

    public static double turretAngleDeg = 0;
//    public static boolean lockToPosition = false;

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
//        turret = new Turret(hardwareMap, limelight, new Limelight(hardwareMap, follower));
//        follower = Constants.createFollower(hardwareMap);
    }

    @Override
    public void loop() {

//        follower.update();
        turret.periodic();

//        if(lockToPosition) turret.setTargetAngle(turretAngleDeg - follower.getHeading());
//        turret.setTargetAngle(turretAngleDeg);

        telemetry.addData("TARGET ANGLE", turretAngleDeg);
        telemetry.addData("POSITION ERROR", turretAngleDeg );
//        telemetry.addData("ROBOT HEADING", follower.getHeading());
        telemetry.update();
    }
}
