package org.firstinspires.ftc.teamcode.Commands.CombinedCommands;

import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.Commands.ShooterCommands.ShooterCommand;
import org.firstinspires.ftc.teamcode.Subsystems.Indexer;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;

public class ShootingSequence extends SequentialCommandGroup {
    private Intake intake;
    private Indexer indexer;
    private Shooter shooter;
    public ShootingSequence(Intake intake, Indexer indexer, Shooter shooter){
        addRequirements(intake, indexer, shooter);
        addCommands(
                new WaitCommand(100),
                new ShooterCommand(shooter)
        );

    }
}
