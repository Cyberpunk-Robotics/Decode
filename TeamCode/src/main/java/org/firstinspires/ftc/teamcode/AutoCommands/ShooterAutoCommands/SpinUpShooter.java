package org.firstinspires.ftc.teamcode.AutoCommands.ShooterAutoCommands;

import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.Commands.ShooterCommands.ShooterCommand;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;

public class SpinUpShooter extends SequentialCommandGroup {
    private Shooter shooter;
    public SpinUpShooter(Shooter shooter){
        addCommands(
                new ShooterCommand(shooter),
                new WaitCommand(500)
        );
    }
}
