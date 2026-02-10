package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Limelight;

public class LimelightCommand extends CommandBase {
    private final Limelight limelight;
    public LimelightCommand(Limelight limelight){
        this.limelight = limelight;
        addRequirements(limelight);
    }
    @Override
    public void execute(){
        limelight.periodic();
    }
    @Override
    public boolean isFinished(){
        return false;
    }


}
