package org.firstinspires.ftc.teamcode.OpModes;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Subsystems.Indexer;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Limelight;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "AutoAlbastru", group = "Auto")
public class AutoAlbastru extends OpMode {
    private Follower follower;
    private Intake intake;
    private Shooter shooter;
    private Indexer indexer;
    private Turret turret;
    private Timer opModeTimer; // For total time telemetry
    private Timer actionTimer; // For timed actions like shooting
    private Limelight limelight;

    public enum PathState {
        RUN_AND_GUN,
        FIRST_PICKUP,
        SHOOT1,
        SECOND_PICKUP,
        SHOOT2,
        THIRD_PICKUP,
        SHOOT3,
        PARK,
        DONE
    }

    private PathState pathState;
    private int step = 0; // Step for multi-stage states

    // Poses
    private final Pose startPose = new Pose(24.394405594405583, 130.4433566433566, Math.toRadians(144));
    private final Pose runAndGunEnd = new Pose(64.41678321678323, 102.05174825174825, Math.toRadians(138));
    private final Pose FirstPickupStart = new Pose(50.34965034965035, 85.59160839160845, Math.toRadians(180));
    private final Pose FirstPickupEnd = new Pose(20.626573426573422, 85.58741258741258, Math.toRadians(180));
    private final Pose shootPose = new Pose(58.539860139860146, 101.03916083916086, Math.toRadians(132));
    private final Pose SecondPickupStart = new Pose(62.374825174825176, 62.14965034965037, Math.toRadians(180));
    private final Pose SecondPickupEnd = new Pose(18.712587412587414, 62.39720279720278, Math.toRadians(180));
    private final Pose ThirdPickupStart = new Pose(61.97342657342658, 37.83916083916083, Math.toRadians(180));
    private final Pose ThirdPickupEnd = new Pose(15.447552447552443, 37.776223776223766, Math.toRadians(180));
    private final Pose parkPose = new Pose(20.82937062937063, 88.58461538461539, Math.toRadians(90));
//    private final Pose startPose = new Pose(119.0265734265734, 130.7076923076923, Math.toRadians(36));
//    private final Pose runAndGunEnd = new Pose(84.5986013986014, 102.66293706293706, Math.toRadians(38));
//    private final Pose FirstPickupStart = new Pose(82.99440559440558, 89.59300699300701, Math.toRadians(0));
//    private final Pose FirstPickupEnd = new Pose(120.06433566433564, 89.40699300699303, Math.toRadians(0));
//    private final Pose shootPose = new Pose(84.5986013986014, 102.66293706293706, Math.toRadians(38));
//    private final Pose SecondPickupStart = new Pose(90.21678321678321, 66.13986013986015, Math.toRadians(0));
//    private final Pose SecondPickupEnd = new Pose(121.36223776223778, 66.29090909090908, Math.toRadians(0));
//    private final Pose ThirdPickupStart = new Pose(93.45034965034964, 42.258741258741246, Math.toRadians(0));
//    private final Pose ThirdPickupEnd = new Pose(127.96923076923076, 42.43076923076923, Math.toRadians(0));
//    private final Pose parkPose = new Pose(126.07552447552446, 90.83076923076923, Math.toRadians(90));
    // Paths
    private PathChain runAndGun, pickupFirstStart, pickupFirstEnd, shoot1, secondPickupStart,
            secondPickupEnd, shoot2, thirdPickupStart, thirdPickupEnd, shoot3, park;

    public void buildPaths() {
        runAndGun = follower.pathBuilder()
                .addPath(new BezierLine(startPose, runAndGunEnd))
                .setLinearHeadingInterpolation(startPose.getHeading(), runAndGunEnd.getHeading())
                .build();

        pickupFirstStart = follower.pathBuilder()
                .addPath(new BezierLine(runAndGunEnd, FirstPickupStart))
                .setLinearHeadingInterpolation(runAndGunEnd.getHeading(), FirstPickupStart.getHeading())
                .build();

        pickupFirstEnd = follower.pathBuilder()
                .addPath(new BezierLine(FirstPickupStart, FirstPickupEnd))
                .setLinearHeadingInterpolation(FirstPickupStart.getHeading(), FirstPickupEnd.getHeading())
                .build();
        shoot1 = follower.pathBuilder()
                .addPath(new BezierLine(FirstPickupEnd, shootPose))
                .setLinearHeadingInterpolation(FirstPickupEnd.getHeading(), shootPose.getHeading())
                .build();

        secondPickupStart = follower.pathBuilder()
                .addPath(new BezierLine(shootPose, SecondPickupStart))
                .setLinearHeadingInterpolation(shootPose.getHeading(), SecondPickupStart.getHeading())
                .build();

        secondPickupEnd = follower.pathBuilder()
                .addPath(new BezierLine(SecondPickupStart, SecondPickupEnd))
                .setLinearHeadingInterpolation(SecondPickupStart.getHeading(), SecondPickupEnd.getHeading())
                .build();

        shoot2 = follower.pathBuilder()
                .addPath(new BezierLine(SecondPickupEnd, shootPose))
                .setLinearHeadingInterpolation(SecondPickupEnd.getHeading(), shootPose.getHeading())
                .build();

        thirdPickupStart = follower.pathBuilder()
                .addPath(new BezierLine(shootPose, ThirdPickupStart))
                .setLinearHeadingInterpolation(shootPose.getHeading(), ThirdPickupStart.getHeading())
                .build();
        thirdPickupEnd = follower.pathBuilder()
                .addPath(new BezierLine(ThirdPickupStart, ThirdPickupEnd))
                .setLinearHeadingInterpolation(ThirdPickupStart.getHeading(), ThirdPickupEnd.getHeading())
                .build();

        shoot3 = follower.pathBuilder()
                .addPath(new BezierLine(ThirdPickupEnd, shootPose))
                .setLinearHeadingInterpolation(ThirdPickupEnd.getHeading(), shootPose.getHeading())
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
            step = 0; // Reset step on state change
        }
    }

    public void statePathUpdate() {
        switch (pathState) {
            case RUN_AND_GUN:
                shooter.highRPM();
                if (step == 0) {
                    follower.followPath(runAndGun, false);
                    step++;
                } else if (step == 1 && !follower.isBusy()) {
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 2 && actionTime() > 2.0) { // Stabilize shooter
                    intake.on();
                    // Shot 1
                    indexer.run();
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 3 && actionTime() > 0.4) { // Shoot time
                    indexer.off(); // Pause
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 4 && actionTime() > 0.6) { // Pause time
                    // Shot 2
                    indexer.run();
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 5 && actionTime() > 0.4) { // Shoot time
                    indexer.off(); // Pause
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 6 && actionTime() > 0.6) { // Pause time
                    // Shot 3
                    indexer.run();
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 7 && actionTime() > 0.4) { // Shoot time
                    intake.off();
                    indexer.off();
                    setPathState(PathState.FIRST_PICKUP);
                }
                break;

            case FIRST_PICKUP:
                if (step == 0) {
                    shooter.off();
                    indexer.reverse();
                    intake.on();
                    follower.followPath(pickupFirstStart, false);
                    step++;
                } else if (step == 1 && !follower.isBusy()) {
                    follower.followPath(pickupFirstEnd, false);
                    step++;
                } else if (step == 2 && !follower.isBusy()) {
                    intake.off();
                    indexer.off();
                    setPathState(PathState.SHOOT1);
                }
                break;

            case SHOOT1:
                shooter.highRPM();
                if (step == 0) {
                    follower.followPath(shoot1, true);
                    step++;
                } else if (step == 1 && !follower.isBusy()) {
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 2 && actionTime() > 2.0) { // Stabilize shooter
                    intake.on();
                    // Shot 1
                    indexer.run();
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 3 && actionTime() > 0.4) { // Shoot time
                    indexer.off(); // Pause
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 4 && actionTime() > 0.6) { // Pause time
                    // Shot 2
                    indexer.run();
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 5 && actionTime() > 0.4) { // Shoot time
                    indexer.off(); // Pause
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 6 && actionTime() > 0.6) { // Pause time
                    // Shot 3
                    indexer.run();
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 7 && actionTime() > 0.4) { // Shoot time
                    intake.off();
                    indexer.off();
                    setPathState(PathState.SECOND_PICKUP);
                }
                break;

            case SECOND_PICKUP:
                if (step == 0) {
                    shooter.off();
                    indexer.reverse();
                    intake.on();
                    follower.followPath(secondPickupStart, false);
                    step++;
                } else if (step == 1 && !follower.isBusy()) {
                    follower.followPath(secondPickupEnd, false);
                    step++;
                } else if (step == 2 && !follower.isBusy()) {
                    intake.off();
                    indexer.off();
                    setPathState(PathState.SHOOT2);
                }
                break;

            case SHOOT2:
                shooter.highRPM();
                if (step == 0) {
                    follower.followPath(shoot2, true);
                    step++;
                } else if (step == 1 && !follower.isBusy()) {
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 2 && actionTime() > 2.0) { // Stabilize shooter
                    intake.on();
                    // Shot 1
                    indexer.run();
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 3 && actionTime() > 0.4) { // Shoot time
                    indexer.off(); // Pause
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 4 && actionTime() > 0.6) { // Pause time
                    // Shot 2
                    indexer.run();
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 5 && actionTime() > 0.4) { // Shoot time
                    indexer.off(); // Pause
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 6 && actionTime() > 0.6) { // Pause time
                    // Shot 3
                    indexer.run();
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 7 && actionTime() > 0.4) { // Shoot time
                    intake.off();
                    indexer.off();
                    setPathState(PathState.THIRD_PICKUP);
                }
                break;

            case THIRD_PICKUP:
                if (step == 0) {
                    shooter.off();
                    indexer.reverse();
                    intake.on();
                    follower.followPath(thirdPickupStart, false);
                    step++;
                } else if (step == 1 && !follower.isBusy()) {
                    follower.followPath(thirdPickupEnd, false);
                    step++;
                } else if (step == 2 && !follower.isBusy()) {
                    intake.off();
                    indexer.off();
                    setPathState(PathState.SHOOT3);
                }
                break;

            case SHOOT3:
                shooter.highRPM();
                if (step == 0) {
                    follower.followPath(shoot3, true);
                    step++;
                } else if (step == 1 && !follower.isBusy()) {
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 2 && actionTime() > 2.0) { // Stabilize shooter
                    intake.on();
                    // Shot 1
                    indexer.run();
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 3 && actionTime() > 0.4) { // Shoot time
                    indexer.off(); // Pause
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 4 && actionTime() > 0.6) { // Pause time
                    // Shot 2
                    indexer.run();
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 5 && actionTime() > 0.4) { // Shoot time
                    indexer.off(); // Pause
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 6 && actionTime() > 0.6) { // Pause time
                    // Shot 3
                    indexer.run();
                    actionTimer.resetTimer();
                    step++;
                } else if (step == 7 && actionTime() > 0.4) { // Shoot time
                    intake.off();
                    indexer.off();
                    setPathState(PathState.PARK);
                }
                break;

            case PARK:
                if (step == 0) {
                    shooter.off();
                    intake.off();
                    indexer.off();
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

        limelight = new Limelight(hardwareMap);
        shooter = new Shooter(hardwareMap, limelight);
        intake = new Intake(hardwareMap);
        indexer = new Indexer(hardwareMap);
        buildPaths();
    }

    @Override
    public void start() {
        opModeTimer.resetTimer();
        setPathState(PathState.RUN_AND_GUN);
    }

    @Override
    public void loop() {
        follower.update();
        limelight.periodic();
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
