package marsops.mission;

import marsops.core.AgentRequirement;

import marsops.environment.Condition;

import marsops.agent.*;
public class ExplorationMission extends Mission{
    public ExplorationMission(int id, String region) {
        super(id, MissionType.EXPLORATION);
        requireAgent(new AgentRequirement(Category.HUMAN, Capability.SCIENTIFIC_RESEARCH, 1));
        requireAgent(new AgentRequirement(Category.ROBOT, Capability.SURFACE_TRAVEL, 1));
    }
    @Override 
    public boolean isBlockedBy(Condition condition){
        return condition == Condition.DUST_STORM;
    }
}
