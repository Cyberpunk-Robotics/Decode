package org.firstinspires.ftc.teamcode.AutoCommands.ShooterAutoCommands;

import com.seattlesolvers.solverslib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.Subsystems.Indexer;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;

public class FullShooterCommand extends SequentialCommandGroup {
    private Shooter shooter;
    private Intake intake;
    private Indexer indexer;
    public FullShooterCommand(Shooter shooter, Intake intake, Indexer indexer){
        addCommands(
                new SpinUpShooter(shooter),
                new ShootingSequence(shooter, intake, indexer)
                /// 200105466
        );
    }
    
}
