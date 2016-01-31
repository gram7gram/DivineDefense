package ua.gram.controller.voter;

import java.util.List;

import ua.gram.model.enums.Voter;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AffirmativeVoterPolicy implements VoterPolicyInterface {

    @Override
    public boolean isGranted(List<Voter.Value> values) {
        return values.contains(Voter.Value.FOR);
    }
}
