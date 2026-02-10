package org.firstinspires.ftc.teamcode.Garbage;

import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.Commands.IndexerCommands.IndexerForwardCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.ShooterCommands.ShooterCommand;
import org.firstinspires.ftc.teamcode.Subsystems.Indexer;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;

public class AutoCommand extends SequentialCommandGroup {
    public AutoCommand(Shooter shooter, Intake intake, Indexer indexer){
        addCommands(
                new ShooterCommand(shooter),
                new WaitCommand(1000),
                new IntakeCommand(intake),
                new IndexerForwardCommand(indexer),
                new WaitCommand(3000)
        );
        addRequirements(shooter, intake, indexer);
    }
}
