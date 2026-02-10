package org.firstinspires.ftc.teamcode.OpModes;

import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.AutoCommands.ShooterAutoCommands.ShootingSequence;
import org.firstinspires.ftc.teamcode.Commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Commands.DriveNormalizedCommand;
import org.firstinspires.ftc.teamcode.Commands.FieldCentricCommand;
import org.firstinspires.ftc.teamcode.Commands.IndexerCommands.IndexerForwardCommand;
import org.firstinspires.ftc.teamcode.Commands.IndexerCommands.IndexerLowReverseCommand;
import org.firstinspires.ftc.teamcode.Commands.IndexerCommands.IndexerReverseCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommands.IntakeStopCommand;
import org.firstinspires.ftc.teamcode.Commands.LLPauseCommand;
import org.firstinspires.ftc.teamcode.Commands.LimelightCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommands.ReverseIntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.ShooterCommands.ShooterCommand;
import org.firstinspires.ftc.teamcode.Commands.ShooterCommands.ShooterOffCommand;
import org.firstinspires.ftc.teamcode.Commands.TurretCommand;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Indexer;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Limelight;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;

import java.util.List;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "TeleOp")
public class TeleOp extends OpMode {
    private Drive drive;
    private Limelight limelight;
    private Shooter shooter;
    private Intake intake;
    private Indexer indexer;
    private IntakeCommand intakeCommand;
    private ReverseIntakeCommand reverseIntakeCommand;
    private DriveCommand driveCommand;
    private DriveNormalizedCommand driveNormalizedCommand;
    private ShooterCommand shooterLowCommand;
    private ShootingSequence shootingSequence;
    private LimelightCommand limelightCommand;
    private LLPauseCommand llPauseCommand;
    private ShooterCommand shooterCommand;
    private ShooterOffCommand shooterOffCommand;
    private IndexerForwardCommand indexerForwardCommand;
    private IndexerReverseCommand indexerReverseCommand;
    private IntakeStopCommand intakeStopCommand;
    private IndexerLowReverseCommand indexerLowReverseCommand;
    private Turret turret;
    private TurretCommand turretCommand;
    
    private GamepadEx gamepadA, gamepadB;

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        CommandScheduler.getInstance().enable();
        CommandScheduler.getInstance().setBulkReading(hardwareMap, LynxModule.BulkCachingMode.AUTO);
        drive = new Drive(hardwareMap);
        limelight = new Limelight(hardwareMap);
        shooter = new Shooter(hardwareMap, limelight);
        intake = new Intake(hardwareMap);
        indexer = new Indexer(hardwareMap);
        turret = new Turret(hardwareMap, limelight);
        List<LynxModule> hubs = hardwareMap.getAll(LynxModule.class);
        hubs.forEach(hub -> hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL));

        gamepadA = new GamepadEx(gamepad1);
        gamepadB = new GamepadEx(gamepad2);
        CommandScheduler.getInstance().registerSubsystem(drive);
        // Pass joystick values as DoubleSuppliers (lambdas)
        driveCommand = new DriveCommand(drive,
                () -> gamepadA.getLeftX(), 
                () -> gamepadA.getLeftY(), 
                () -> gamepadA.getRightX()
        );
        driveNormalizedCommand = new DriveNormalizedCommand(drive,
                () -> gamepadA.getLeftX(),
                () -> gamepadA.getLeftY(),
                () -> gamepadA.getRightX());
        shooterCommand = new ShooterCommand(shooter);
        shooterLowCommand = new ShooterCommand(shooter);
        limelightCommand = new LimelightCommand(limelight);
        intakeCommand = new IntakeCommand(intake);
        reverseIntakeCommand = new ReverseIntakeCommand(intake);
        llPauseCommand = new LLPauseCommand(limelight);
        indexerForwardCommand = new IndexerForwardCommand(indexer);
        indexerReverseCommand = new IndexerReverseCommand(indexer);
        intakeStopCommand = new IntakeStopCommand(intake);
        turretCommand = new TurretCommand(turret);
        shooterOffCommand = new ShooterOffCommand(shooter);
        shootingSequence = new ShootingSequence(shooter, intake, indexer);
        indexerLowReverseCommand = new IndexerLowReverseCommand(indexer);



        // Set default commands
        CommandScheduler.getInstance().setDefaultCommand(drive, driveCommand);
        CommandScheduler.getInstance().setDefaultCommand(limelight, limelightCommand);
        CommandScheduler.getInstance().setDefaultCommand(intake, intakeStopCommand);
        CommandScheduler.getInstance().setDefaultCommand(turret, turretCommand);

        // Bindings should be in init()
        new GamepadButton(gamepadB, GamepadKeys.Button.RIGHT_BUMPER)
                .toggleWhenPressed(shooterCommand);
        new GamepadButton(gamepadA, GamepadKeys.Button.RIGHT_BUMPER)
                .whenHeld(intakeCommand);
        new GamepadButton(gamepadB, GamepadKeys.Button.LEFT_BUMPER)
                .whenHeld(reverseIntakeCommand);
        new GamepadButton(gamepadB, GamepadKeys.Button.TRIANGLE)
                .whenHeld(indexerForwardCommand);
        new GamepadButton(gamepadB, GamepadKeys.Button.CIRCLE)
                .whenHeld(indexerReverseCommand);
        new GamepadButton(gamepadA, GamepadKeys.Button.DPAD_UP)
                .whenHeld(driveNormalizedCommand);
        new GamepadButton(gamepadA,GamepadKeys.Button.LEFT_BUMPER)
                .whenHeld(reverseIntakeCommand);

    }


    @Override
    public void loop() {
        gamepadA.readButtons();
        gamepadB.readButtons();
        CommandScheduler.getInstance().run();
        telemetry.addData("Ta: ", limelight.getTa());
        telemetry.addData("Ty: ", limelight.getTy());
        telemetry.addData("RPM: ", shooter.getRPM());
        telemetry.addData("ID: ", limelight.getPrimaryTagId());
        telemetry.addData("Tx: ", limelight.getTx());
        telemetry.update();
    }
}
