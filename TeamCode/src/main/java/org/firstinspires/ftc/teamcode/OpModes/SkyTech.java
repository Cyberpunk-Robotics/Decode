package org.firstinspires.ftc.teamcode.OpModes;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.ParallelRaceGroup;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.AutoCommands.ShooterAutoCommands.FullShooterCommand;
import org.firstinspires.ftc.teamcode.Commands.IndexerCommands.IndexerForwardCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.ShooterCommands.ShooterCommand;
import org.firstinspires.ftc.teamcode.Subsystems.Indexer;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Limelight;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
@Autonomous(name= "SkyTech")
public class SkyTech extends CommandOpMode {
    private Follower follower;
    private Intake intake;
    private Shooter shooter;
    private Indexer indexer;
    private Limelight limelight;
    private final Pose startPose = new Pose(25.58881118881119, 130.45034965034964, Math.toRadians(144));
    private final Pose runAndGunEnd = new Pose(63.04895104895106, 95.61398601398604, Math.toRadians(136));
    private final Pose bezierFirstPickupFirstPoint= new Pose(52.75664335664337, 93.92727272727272, Math.toRadians(180));
    private final Pose bezierFirstPickupSecondPoint= new Pose(36.30629370629371, 83.62517482517482, Math.toRadians(180));
    private final Pose bezierFirstPickupLastPoint  = new Pose(14.455944055944055, 83.47132867132869, Math.toRadians(180));
    private final Pose shootPose  = new Pose(29.760839160839165, 100.94545454545455, Math.toRadians(180));
    private final Pose bezierSecondPickupFirstPoint  = new Pose(53.22237762237763, 77.95244755244754, Math.toRadians(180));
    private final Pose bezierSecondPickupSecondPoint  = new Pose(45.07272727272726, 60.42797202797202, Math.toRadians(180));
    private final Pose bezierSecondPickupLastPoint  = new Pose(20.52867132867133, 60.21118881118881, Math.toRadians(180));
    private final Pose bezierFirstGatePoint  = new Pose(25.58041958041957, 62.05594405594403, Math.toRadians(145));
    private final Pose bezierLastGatePoint  = new Pose(11.236363636363638, 60.26993006993007, Math.toRadians(145));
    private final Pose bezierReturnToShootPoint = new Pose(35.697902097902094, 75.38881118881119, Math.toRadians(145));//last point is shootPose
    private final Pose bezierLastPickupFirstPoint  = new Pose(66.24755244755244, 46.95804195804197, Math.toRadians(180));
    private final Pose bezierLastPickupSecondPoint  = new Pose(51.16223776223778, 33.26713286713287, Math.toRadians(180));
    private final Pose bezierLastPickupLastPoint  = new Pose(14.35524475524476, 35.562237762237764, Math.toRadians(180));
    private final Pose parkPose  = new Pose(17.074125874125883, 87.43496503496502, Math.toRadians(180));
    private PathChain  pickupFirst, shoot1, secondPickup, shoot2, gate, shootGate,  lastPickup, shoot3, park;
    private Path runAndGun;
    public void buildPaths(){

        pickupFirst = follower.pathBuilder()
                .addPath(new BezierCurve(
                        runAndGunEnd,
                        bezierFirstPickupFirstPoint,
                        bezierFirstPickupSecondPoint,
                        bezierFirstPickupLastPoint)
                )
                .setLinearHeadingInterpolation(runAndGunEnd.getHeading(), bezierLastPickupLastPoint.getHeading())
                .build();

        shoot1 = follower.pathBuilder()
                .addPath(new BezierLine(bezierFirstPickupLastPoint, shootPose))
                .setLinearHeadingInterpolation(bezierFirstPickupLastPoint.getHeading(), shootPose.getHeading())
                .build();

        secondPickup = follower.pathBuilder()
                .addPath(new BezierCurve(
                        shootPose,
                        bezierSecondPickupFirstPoint,
                        bezierSecondPickupSecondPoint,
                        bezierSecondPickupLastPoint)
                )
                .setLinearHeadingInterpolation(shootPose.getHeading(), bezierSecondPickupLastPoint.getHeading())
                .build();

        shoot2 = follower.pathBuilder()
                .addPath(new BezierCurve(bezierSecondPickupLastPoint,
                        bezierReturnToShootPoint,
                        shootPose))
                .setLinearHeadingInterpolation(bezierSecondPickupLastPoint.getHeading(), shootPose.getHeading())
                .build();

        gate = follower.pathBuilder()
                .addPath(new BezierCurve(
                        shootPose,
                        bezierFirstGatePoint,
                        bezierLastGatePoint)
                )
                .setLinearHeadingInterpolation(shootPose.getHeading(), bezierLastGatePoint.getHeading())
                .build();

        shootGate = follower.pathBuilder()
                .addPath(new BezierCurve(bezierLastGatePoint, bezierReturnToShootPoint, shootPose))
                .setLinearHeadingInterpolation(bezierLastGatePoint.getHeading(), shootPose.getHeading())
                .build();

        lastPickup = follower.pathBuilder()
                .addPath(new BezierCurve(
                        shootPose,
                        bezierLastPickupFirstPoint,
                        bezierLastPickupSecondPoint,
                        bezierLastPickupLastPoint))
                .setLinearHeadingInterpolation(shootPose.getHeading(), bezierLastPickupLastPoint.getHeading())
                .build();

        shoot3 = follower.pathBuilder()
                .addPath(new BezierCurve(bezierLastPickupLastPoint, bezierReturnToShootPoint, shootPose))
                .setLinearHeadingInterpolation(bezierLastPickupLastPoint.getHeading(), shootPose.getHeading())
                .build();

        park = follower.pathBuilder()
                .addPath(new BezierLine(shootPose, parkPose))
                .setLinearHeadingInterpolation(shootPose.getHeading(), parkPose.getHeading())
                .build();
    }

    @Override
    public void initialize() {
        super.reset();
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);
        indexer = new Indexer(hardwareMap);
        limelight = new Limelight(hardwareMap,follower);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, limelight);
        buildPaths();
        SequentialCommandGroup autonomousSequence = new SequentialCommandGroup(

                        new ShooterCommand(shooter),
                        new WaitCommand(500),
                        new FollowPathCommand(follower, runAndGun, true),
                        new FullShooterCommand(shooter, intake, indexer),

                new WaitCommand(1000),
                new ParallelRaceGroup(
                        new FollowPathCommand(follower, pickupFirst),
                        new IntakeCommand(intake)
                        ),

                new SequentialCommandGroup(
                        new ParallelRaceGroup(
                                new FollowPathCommand(follower, shoot1, true),
                                new ShooterCommand(shooter)
                        ),
                        new ParallelRaceGroup(
                                new ShooterCommand(shooter),
                                new IntakeCommand(intake),
                                new IndexerForwardCommand(indexer),
                                new WaitCommand(1500)
                        )
                ),
                new ParallelRaceGroup(
                        new FollowPathCommand(follower, secondPickup, false),
                        new IntakeCommand(intake)
                        ),
                new SequentialCommandGroup(
                        new ParallelRaceGroup(
                                new FollowPathCommand(follower, shoot2, true),
                                new ShooterCommand(shooter)
                        ),
                        new ParallelRaceGroup(
                                new ShooterCommand(shooter),
                                new IntakeCommand(intake),
                                new IndexerForwardCommand(indexer),
                                new WaitCommand(1500)
                        )
                ),
                new ParallelRaceGroup(
                        new FollowPathCommand(follower, gate),
                        new IntakeCommand(intake),
                        new WaitCommand(2000)
                        ),
                new SequentialCommandGroup(
                        new ParallelRaceGroup(
                                new FollowPathCommand(follower, shootGate, true),
                                new ShooterCommand(shooter)
                                ),
                        new ShooterCommand(shooter),
                        new IntakeCommand(intake),
                        new IndexerForwardCommand(indexer)
                ),
                new ParallelRaceGroup(
                        new FollowPathCommand(follower, gate),
                        new IntakeCommand(intake),
                        new WaitCommand(2000)
                ),
                new SequentialCommandGroup(
                        new ParallelRaceGroup(
                                new FollowPathCommand(follower, shootGate, true),
                                new ShooterCommand(shooter)
                        ),
                        new ShooterCommand(shooter),
                        new IntakeCommand(intake),
                        new IndexerForwardCommand(indexer)
                ),
                new ParallelRaceGroup(
                        new FollowPathCommand(follower, lastPickup),
                        new IntakeCommand(intake)
                ),
                new SequentialCommandGroup(
                        new ParallelRaceGroup(
                                new FollowPathCommand(follower, shoot3, true),
                                new ShooterCommand(shooter)
                        ),
                        new ParallelRaceGroup(
                                new ShooterCommand(shooter),
                                new IntakeCommand(intake),
                                new IndexerForwardCommand(indexer),
                                new WaitCommand(1500)
                        )
                ),
                new FollowPathCommand(follower, park)

        );
        schedule(autonomousSequence);
    }
    @Override
    public void run(){
        super.run();
        follower.update();
        telemetry.addData("X: ", follower.getPose().getX());
        telemetry.addData("Y: ", follower.getPose().getY());
        telemetry.addData("Heading: ", follower.getPose().getHeading());
        telemetry.update();

    }
}
