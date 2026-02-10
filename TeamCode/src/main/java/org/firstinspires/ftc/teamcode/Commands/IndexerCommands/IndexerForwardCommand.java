package org.firstinspires.ftc.teamcode.Commands.IndexerCommands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Indexer;

public class IndexerForwardCommand extends CommandBase {
    private final Indexer indexer;
    public IndexerForwardCommand(Indexer indexer){
        this.indexer = indexer;
        addRequirements(indexer);
    }
    @Override
    public void execute(){
        indexer.run();
    }
    @Override
    public void end(boolean interrupted){
        indexer.off();
    }
}
