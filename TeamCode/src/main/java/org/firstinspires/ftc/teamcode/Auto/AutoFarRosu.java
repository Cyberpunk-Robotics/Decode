package org.firstinspires.ftc.teamcode.Auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Indexer;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Limelight;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "AutoFarRosu", group = "Auto")
public class AutoFarRosu extends OpMode {
    private Follower follower;
    private Intake intake;
    private Shooter shooter;
    private Indexer indexer;
    private Turret turret;
    private Drive drive;
    private Timer opModeTimer;
    private Timer actionTimer;
    private int step = 0;
    private Limelight limelight;

    public enum PathState {
        SHOOT_PRELOAD,
        PARK,
        DONE
    }

    private PathState pathState;
    private final Pose startPose = new Pose(87.82097902097902, 7.798601398601402, Math.toRadians(90));
    private final Pose shootPose = new Pose(82.78601398601398, 16.262937062937052, Math.toRadians(64));
    private final Pose parkPose = new Pose(111.17342657342658, 7.998601398601402, Math.toRadians(90));
    private PathChain shootPreload, park;

    public void buildPaths() {
        shootPreload = follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                .build();
        park = follower.pathBuilder()
                .addPath(new BezierLine(shootPose, parkPose))
                .setLinearHeadingInterpolation(shootPose.getHeading(), parkPose.getHeading())
                .build();
    }

    public double actionTime() {
        return actionTimer.getElapsedTimeSeconds();
    }

    public void setPathState(PathState newState) {
        if (pathState != newState) {
            pathState = newState;
            step = 0;
        }
    }

    public void statePathUpdate() {
        switch (pathState) {
            case SHOOT_PRELOAD:
                shooter.lowRPM();
                if (step == 0) { // Initial 3s spin up
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 1 && actionTime() > 3.0) {
                    follower.followPath(shootPreload, true);
                    step++;
                } else if (step == 2 && !follower.isBusy()) { // At shoot pose, 3s more spin up
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 3 && actionTime() > 3.0) {
                    // Shot 1
                    intake.on();
                    indexer.run();
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 4 && actionTime() > 0.24) { // Shoot time
                    intake.off();
                    indexer.off(); // Pause
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 5 && actionTime() > 1.5) { // Pause time
                    // Shot 2
                    intake.on();
                    indexer.run();
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 6 && actionTime() > 0.3) { // Shoot time
                    intake.off();
                    indexer.off(); // Pause
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 7 && actionTime() > 1.5) { // Pause time
                    // Shot 3
                    intake.on();
                    indexer.run();
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 8 && actionTime() > 0.6) { // Shoot time
                    intake.off();
                    indexer.off();
                    setPathState(PathState.PARK);
                }
                break;
            case PARK:
                shooter.off();
                intake.off();
                indexer.off();
                if (step == 0) {
                    follower.followPath(park, true);
                    step++;
                } else if (step == 1 && !follower.isBusy()) {
                    setPathState(PathState.DONE);
                }
                break;
            case DONE:
                shooter.off();
                intake.off();
                indexer.off();
                break;
        }
    }


    @Override
    public void init() {
        opModeTimer = new Timer();
        actionTimer = new Timer();

        follower = Constants.createFollower(hardwareMap);
        follower.setPose(startPose);

        limelight = new Limelight(hardwareMap, follower);
        shooter = new Shooter(hardwareMap, limelight);
        intake = new Intake(hardwareMap);
        indexer = new Indexer(hardwareMap);
        turret = new Turret(hardwareMap, limelight);

        buildPaths();
    }

    @Override
    public void start() {
        opModeTimer.resetTimer();
        setPathState(PathState.SHOOT_PRELOAD);
    }


    @Override
    public void loop() {
        follower.update();
        limelight.periodic();
        turret.periodic();
        statePathUpdate();

        telemetry.addData("pathState", pathState.toString());
        telemetry.addData("step", step);
        telemetry.addData("opModeTimer", opModeTimer.getElapsedTimeSeconds());
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("Heading", Math.toDegrees(follower.getPose().getHeading()));
        telemetry.addData("Ta", limelight.getTa());
        telemetry.addData("Ty", limelight.getTy());
        telemetry.addData("Tx", limelight.getTx());
        telemetry.addData("RPM", shooter.getRPM());
        telemetry.update();
    }
}
