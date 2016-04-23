package ua.gram.controller.voter.policy;

import java.util.List;

import ua.gram.model.enums.Voter;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class UnanimousVoterPolicy implements VoterPolicy {

    @Override
    public boolean isGranted(List<Voter.Value> values) {
        boolean containsFalse = values.contains(Voter.Value.AGAINST);
        if (!containsFalse) {
            int neutralCount = 0;
            for (Voter.Value value : values) {
                if (value == Voter.Value.NEUTRAL) {
                    ++neutralCount;
                }
            }
            return neutralCount != values.size();
        }
        return false;
    }
}
