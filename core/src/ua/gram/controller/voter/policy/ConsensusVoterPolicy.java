package ua.gram.controller.voter.policy;

import java.util.List;

import ua.gram.model.enums.Voter;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ConsensusVoterPolicy implements VoterPolicy {

    @Override
    public boolean isGranted(List<Voter.Value> values) {
        int countTrue = 0;
        int countFalse = 0;

        for (Voter.Value value : values) {
            if (value == Voter.Value.FOR) {
                ++countTrue;
            } else if (value == Voter.Value.AGAINST) {
                ++countFalse;
            }
        }

        return countTrue >= countFalse;
    }
}
