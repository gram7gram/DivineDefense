package ua.gram.controller.voter.policy;

import java.util.List;

import ua.gram.model.enums.Voter;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AffirmativeVoterPolicy implements VoterPolicy {

    @Override
    public boolean isGranted(List<Voter.Value> values) {
        return values.contains(Voter.Value.FOR);
    }
}
