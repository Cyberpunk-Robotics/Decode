package org.firstinspires.ftc.teamcode.OpModes;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
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
@Autonomous(name = "SkyTechRosu" ,group = "auto")
public class SkyTechRosu extends CommandOpMode {
    private Follower follower;
    private Intake intake;
    private Shooter shooter;
    private Indexer indexer;
    private Limelight limelight;
    private final Pose startPose = new Pose(119.23916083916086, 130.24895104895103 , 36);
    private final Pose runAndGunEnd = new Pose(83.18881118881119, 112.33006993006994, 32);
    private final Pose bezierFirstPickupFirstPoint= new Pose(71.43636363636365, 82.40559440559441, 0);
    private final Pose bezierFirstPickupSecondPoint= new Pose(101.95314685314685, 82.74265734265734, 0);
    private final Pose bezierFirstPickupLastPoint  = new Pose(132.46993006993006, 83.07972027972028, 0);
    private final Pose shootPose  = new Pose(112.62097902097902, 102.62097902097905, 55);
    private final Pose bezierSecondPickupFirstPoint  = new Pose(83.74965034965035, 73.2167832167832, 0);
    private final Pose bezierSecondPickupSecondPoint  = new Pose(89.52447552447553, 59.67692307692309, 0);
    private final Pose bezierSecondPickupLastPoint  = new Pose(126.88531468531468, 58.790209790209786, 0);
    private final Pose bezierFirstGatePoint  = new Pose(91.76643356643353, 67.96083916083917, 55);
    private final Pose bezierSecondGatePoint  = new Pose(111.52307692307694, 57.32167832167832, 0);
    private final Pose bezierLastGatePoint  = new Pose(133.80279720279725, 59.794405594405575, 35);
    private final Pose bezierReturnToShootFirstPoint = new Pose(110.41678321678322, 66.48111888111892, 55);
    private final Pose bezierReturnToShootSecondPoint = new Pose(102.38741258741257, 85.7020979020979, 55);//last point is shootPose
    private final Pose bezierLastPickupFirstPoint  = new Pose(84.21538461538462, 60.87692307692308, 0);
    private final Pose bezierLastPickupSecondPoint  = new Pose(87.46013986013986, 41.07552447552448, 0);
    private final Pose bezierLastPickupThirdPoint  = new Pose(102.206993006993, 34.03636363636363, 0);
    private final Pose bezierLastPickupLastPoint  = new Pose(133.56503496503498, 35.43076923076923, 0);
    private final Pose parkPose  = new Pose(126.81958041958043, 93.41818181818181, 180);
    private PathChain runAndGun, pickupFirst, shoot1, secondPickup, shoot2, gate, shootGate,  lastPickup, shoot3, park;
    public void buildPaths(){
        runAndGun = follower.pathBuilder()
                .addPath(new BezierLine(startPose, runAndGunEnd))
                .setLinearHeadingInterpolation(startPose.getHeading(), runAndGunEnd.getHeading())
                .build();

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
                .setLinearHeadingInterpolation(shootPose.getHeading(), bezierLastPickupLastPoint.getHeading())
                .build();

        shoot2 = follower.pathBuilder()
                .addPath(new BezierCurve(bezierSecondPickupLastPoint,
                        bezierReturnToShootFirstPoint,
                        bezierReturnToShootSecondPoint,
                        shootPose))
                .setLinearHeadingInterpolation(bezierSecondPickupLastPoint.getHeading(), shootPose.getHeading())
                .build();

        gate = follower.pathBuilder()
                .addPath(new BezierCurve(
                        shootPose,
                        bezierFirstGatePoint,
                        bezierSecondGatePoint,
                        bezierLastGatePoint)
                )
                .setLinearHeadingInterpolation(shootPose.getHeading(), bezierLastGatePoint.getHeading())
                .build();

        shootGate = follower.pathBuilder()
                .addPath(new BezierCurve(
                        bezierLastGatePoint,
                        bezierReturnToShootFirstPoint,
                        bezierReturnToShootSecondPoint,
                        shootPose))
                .setLinearHeadingInterpolation(bezierLastGatePoint.getHeading(), shootPose.getHeading())
                .build();

        lastPickup = follower.pathBuilder()
                .addPath(new BezierCurve(
                        shootPose,
                        bezierLastPickupFirstPoint,
                        bezierLastPickupSecondPoint,
                        bezierLastPickupThirdPoint,
                        bezierLastPickupLastPoint))
                .setLinearHeadingInterpolation(shootPose.getHeading(), bezierLastPickupLastPoint.getHeading())
                .build();

        shoot3 = follower.pathBuilder()
                .addPath(new BezierCurve(
                        bezierLastPickupLastPoint,
                        bezierReturnToShootFirstPoint,
                        bezierReturnToShootSecondPoint,
                        shootPose))
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
        limelight = new Limelight(hardwareMap, follower);
        indexer = new Indexer(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, limelight);
        buildPaths();

        schedule(
                new RunCommand(() -> follower.update()),
                //shoot preload
                new ParallelCommandGroup(
                        new FollowPathCommand(follower, runAndGun, false),
                        new FullShooterCommand(shooter, intake, indexer)),

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
    }
    @Override
    public void run(){
        super.run();
        telemetry.addData("X: ", follower.getPose().getX());
        telemetry.addData("Y: ", follower.getPose().getY());
        telemetry.addData("Heading: ", follower.getPose().getHeading());

    }
}
