package ua.gram.controller.factory;

import ua.gram.controller.voter.policy.AffirmativeVoterPolicy;
import ua.gram.controller.voter.policy.ConsensusVoterPolicy;
import ua.gram.controller.voter.policy.UnanimousVoterPolicy;
import ua.gram.controller.voter.policy.VoterPolicy;
import ua.gram.model.enums.Voter;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class VoterPolicyFactory {

    public static VoterPolicy create(Voter.Policy policy) {
        switch (policy) {
            case AFFIRMATIVE:
                return new AffirmativeVoterPolicy();
            case CONSENSUS:
                return new ConsensusVoterPolicy();
            case UNANIMOUS:
                return new UnanimousVoterPolicy();
        }
        throw new IllegalStateException("Unknown policy: " + policy);
    }
}
