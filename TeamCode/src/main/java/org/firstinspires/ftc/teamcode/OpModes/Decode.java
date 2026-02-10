package org.firstinspires.ftc.teamcode.OpModes;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Garbage.AutoCommand;
import org.firstinspires.ftc.teamcode.Subsystems.Indexer;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Limelight;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "Decode", group = "Auto")
public class Decode extends OpMode {
    private Shooter shooter;
    private Follower follower;
    private Intake intake;
    private Indexer indexer;
    private AutoCommand autoCommand;
    private Limelight limelight;
    private Timer pathTimer, opModeTimer;

    public enum PathState {
        DRIVE_START_POS,
        FIRST_PICKUP,
        SHOOT_PRELOAD
    }

    PathState pathState, firstPath;
    private final Pose startPos = new Pose(25.588811188811178, 130.6517482517483, Math.toRadians(-36));
    private final Pose firstShoot = new Pose(29.012587412587404, 127.63636363636364, Math.toRadians(-36));
    private final Pose firstPickup = new Pose(46.6006993006993, 83.81118881118881, Math.toRadians(180));
    private final Pose firstPickupEnd = new Pose(21.895104895104893, 83.4881118881119, Math.toRadians(180));
    private final Pose secondShoot = new Pose(30.503496503496503, 105.37902097902098, Math.toRadians(-60));
    private PathChain driveStartFirstPickup, firstPickupSequence, firstShootSeq, pickupEnd,
            secondShootSequence;

    public void buildPaths() {
        driveStartFirstPickup = follower.pathBuilder()
                .addPath(new BezierLine(startPos, firstShoot))
                .setLinearHeadingInterpolation(startPos.getHeading(), firstShoot.getHeading())
                .build();
        firstPickupSequence = follower.pathBuilder()
                .addPath(new BezierLine(firstShoot, firstPickup))
                .setLinearHeadingInterpolation(firstShoot.getHeading(), firstPickup.getHeading())
                .build();
        pickupEnd = follower.pathBuilder()
                .addPath(new BezierLine(firstPickup, firstPickupEnd))
                .setLinearHeadingInterpolation(firstPickup.getHeading(), firstPickupEnd.getHeading())
                .build();
        firstShootSeq = follower.pathBuilder()
                .addPath(new BezierLine(firstPickupEnd, secondShoot))
                .setLinearHeadingInterpolation(firstPickupEnd.getHeading(), secondShoot.getHeading())
                .build();
    }

    public void statePathUpdate() {
        switch (pathState) {
            case DRIVE_START_POS:
                    follower.followPath(driveStartFirstPickup, true);
                    shooter.periodic();
                pathState = PathState.SHOOT_PRELOAD;
                break;
            case SHOOT_PRELOAD:
                if (opModeTimer.getElapsedTimeSeconds() > 2) {
                    shooter.periodic();
                    intake.on();
                    indexer.run();
                }
                // Only execute the shooting sequence after the robot has arrived.
                if (!follower.isBusy()) {
                    if (opModeTimer.getElapsedTimeSeconds() > 6) {
                        follower.followPath(firstPickupSequence, true);
                        shooter.off();
                        intake.off();
                        indexer.off();
                    }
                    if (opModeTimer.getElapsedTimeSeconds() > 6.8){
                        follower.followPath(pickupEnd, true);
                    }
                }
                break;
            case FIRST_PICKUP:
                if (!follower.isBusy() && opModeTimer.getElapsedTimeSeconds() > 8) {
                    follower.followPath(firstShootSeq, true);
                }
                break;
            default:
                telemetry.addLine("done");
                break;
        }
    }

    public void setPathState(PathState newState) {
        pathState = newState;
        pathTimer.resetTimer();
    }

    @Override
    public void init() {
        pathState = PathState.DRIVE_START_POS;
        firstPath = PathState.FIRST_PICKUP;
        pathTimer = new Timer();
        opModeTimer = new Timer();
        opModeTimer.resetTimer();
        follower = Constants.createFollower(hardwareMap);
        limelight = new Limelight(hardwareMap);
        shooter = new Shooter(hardwareMap, limelight);
        intake = new Intake(hardwareMap);
        indexer = new Indexer(hardwareMap);

        buildPaths();
        follower.setPose(startPos);
        //add in any other init mechanisms


    }

    public void start() {
        opModeTimer.resetTimer();
        setPathState(pathState);
    }

    @Override
    public void loop() {
        follower.update();
        statePathUpdate();

        telemetry.addData("pathState", pathState.toString());
        telemetry.addData("pathTimer", pathTimer.getElapsedTime());
        telemetry.addData("opModeTimer", opModeTimer.getElapsedTime());
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("Heading", follower.getPose().getHeading());
        telemetry.update();

    }
}
