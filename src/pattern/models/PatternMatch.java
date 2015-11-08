package pattern.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by malam on 11/8/15.
 */
public class PatternMatch {
    private String pattern;
    private int count;
    private List<Integer> positions = new ArrayList<Integer>();

    public String getPattern() {
        return pattern;
    }

    public int getCount() {
        return count;
    }

    public List<Integer> getPositions() {
        return positions;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;

    }
}
