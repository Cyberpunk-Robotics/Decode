package org.firstinspires.ftc.teamcode.Commands.IntakeCommands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;

public class IntakeStopCommand extends CommandBase {
    private final Intake intake;

    public IntakeStopCommand(Intake intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.off();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
