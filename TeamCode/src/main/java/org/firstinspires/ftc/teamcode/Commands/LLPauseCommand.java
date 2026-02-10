package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Limelight;

public class LLPauseCommand extends CommandBase {
    private Limelight limelight;
    public LLPauseCommand(Limelight limelight) {
        this.limelight = limelight;
        addRequirements(limelight);
    }
    @Override
    public void initialize(){
        limelight.LLPause();
    }
    @Override
    public boolean isFinished(){
        return true;
    }


}
