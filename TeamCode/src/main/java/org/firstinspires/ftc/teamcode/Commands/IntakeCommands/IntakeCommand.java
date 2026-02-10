package org.firstinspires.ftc.teamcode.Commands.IntakeCommands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Intake;

public class IntakeCommand extends CommandBase {
    private final Intake intake;
    public IntakeCommand(Intake intake) {
        this.intake  = intake;
    }
    @Override
    public void execute(){
        intake.on();
    }
    @Override
    public void end(boolean interrupted){
        intake.off();
    }
    @Override
    public boolean isFinished(){
        return false;
    }

}
