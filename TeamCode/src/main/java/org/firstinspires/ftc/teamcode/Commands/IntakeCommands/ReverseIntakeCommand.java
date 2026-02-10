package org.firstinspires.ftc.teamcode.Commands.IntakeCommands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Intake;

public class ReverseIntakeCommand extends CommandBase {
    private final Intake intake;
    public ReverseIntakeCommand(Intake intake){
        this.intake = intake;
        addRequirements(intake);
    }
    @Override
    public void execute(){
        intake.reverseIntake();
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
