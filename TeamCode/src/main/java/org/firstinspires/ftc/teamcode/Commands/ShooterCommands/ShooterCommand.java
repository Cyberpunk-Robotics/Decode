package org.firstinspires.ftc.teamcode.Commands.ShooterCommands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Shooter;

public class ShooterCommand extends CommandBase {
    private final Shooter shooter;
    public ShooterCommand(Shooter shooter){
        this.shooter = shooter;
        addRequirements(shooter);
    }
    @Override
    public void initialize(){
        shooter.on();
    }
    @Override
    public void end(boolean interrupted){
        shooter.off();
    }
}
