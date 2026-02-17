package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Turret;

public class TurretCommand extends CommandBase {
    private Turret turret;
    public TurretCommand(Turret turret){
        this.turret = turret;
        addRequirements(turret);
    }
    @Override
    public void initialize(){
        turret.init();
    }
    @Override
    public void execute() {
        turret.periodic();
    }
    @Override
    public boolean isFinished(){
        return false;
    }
}
