package org.firstinspires.ftc.teamcode.OpModes;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Limelight;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@TeleOp
public class AprilTagTest extends OpMode {
    Limelight limelight;
    Drive drive;
    Follower follower;
    @Override
    public void init() {
        limelight = new Limelight(hardwareMap, follower);
        drive = new Drive(hardwareMap);
        follower = Constants.createFollower(hardwareMap);
        follower.setPose(new Pose(72, 72, 0)); // middle of field
    }

    @Override
    public void loop() {

        drive.periodic(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);


        telemetry.addData("ROBOT POSE", follower.getPose());
        telemetry.addData("ROBOT HEADING DEG", Math.toDegrees(follower.getPose().getHeading()));
//        telemetry.addData("ESTIMATED ROBOT POSE", limelight.getRobotPose());
        telemetry.update();
    }
}
