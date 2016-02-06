package ua.gram.controller.voter;

import java.util.List;

import ua.gram.model.enums.Voter;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface VoterPolicyInterface {
    boolean isGranted(List<Voter.Value> values);
}