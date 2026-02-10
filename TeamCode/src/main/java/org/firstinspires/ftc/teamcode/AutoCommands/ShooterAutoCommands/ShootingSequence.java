package org.firstinspires.ftc.teamcode.AutoCommands.ShooterAutoCommands;

import com.seattlesolvers.solverslib.command.ParallelRaceGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.Commands.IndexerCommands.IndexerForwardCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.ShooterCommands.ShooterCommand;
import org.firstinspires.ftc.teamcode.Subsystems.Indexer;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;

public class ShootingSequence extends ParallelRaceGroup {
    private Shooter shooter;
    private Intake intake;
    private Indexer indexer;

    public ShootingSequence(Shooter shooter, Intake intake, Indexer indexer){
        addCommands(
                new ShooterCommand(shooter),
                new IntakeCommand(intake),
                new IndexerForwardCommand(indexer)
        );
        addRequirements(shooter, intake, indexer);
    }
    @Override
    public void end(boolean interrupted){
        shooter.off();
        intake.off();
        indexer.off();
    }
}
