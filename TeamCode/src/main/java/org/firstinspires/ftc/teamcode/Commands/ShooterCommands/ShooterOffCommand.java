package org.firstinspires.ftc.teamcode.Commands.ShooterCommands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Shooter;

public class ShooterOffCommand extends CommandBase {
    private Shooter shooter;
    public ShooterOffCommand(Shooter shooter){
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        shooter.off();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
