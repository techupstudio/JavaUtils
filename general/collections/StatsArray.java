package com.techupstudio.otc_chingy.mychurch.core.utils.general.collections;

import com.techupstudio.otc_chingy.mychurch.core.utils.general.Funcs;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.techupstudio.otc_chingy.mychurch.core.utils.general.Funcs.range;
import static com.techupstudio.otc_chingy.mychurch.core.utils.general.Funcs.shuffle;
import static com.techupstudio.otc_chingy.mychurch.core.utils.general.Funcs.toDouble;

public class StatsArray extends Items<Double> {

    public StatsArray() {
    }

    public StatsArray(Double[] collection) {
        super(collection);
    }

    public StatsArray(Collection<Double> collection) {
        super(collection);
    }

    private Double[] getSorted() {
        List<Double> list = super.asList();
        Collections.sort(list);
        Double[] doubles = new Double[list.size()];
        return list.toArray(doubles);
    }

    public Object[] getSortedArray() {
        return getSorted();
    }

    public Object[] getSet() {
        return super.asSet().toArray();
    }

    public Object[] getSortedSet() {
        return new StatsArray(getSorted()).asSet().toArray();
    }

    public Object getLCM() {
        return Funcs.getLCM(toDouble(super.asSet().toArray()));
    }

    public Object getHCF() {
        return Funcs.getHCF(toDouble(super.asSet().toArray()));
    }

    public Object[] getShuffle() {
        return shuffle(toArray());
    }

    public Object[] getSample(int end_index) {
        return super.sample(end_index).toArray();
    }

    public Object[] getSample(int start_index, int end_index) {
        return super.sample(start_index, end_index).toArray();
    }


    public double getMean() {
        return getSum() / super.size();
    }

    public double getMax() {
        return getSorted()[size() - 1];
    }

    public double getMin() {
        return getSorted()[0];
    }

    public double getSum() {
        double total = 0.0;
        for (int i : range(super.size())) {
            total += super.pop(i);
        }
        return total;
    }

    public double getMedian() {
        double mMedian = 0.0;
        if (size() > 1) {
            if (size() % 2 == 0) {
                mMedian = (getSorted()[(size() / 2) - 1] + getSorted()[(size() / 2)]) / 2;
            } else {
                mMedian = getSorted()[((size() - 1) / 2)];
            }
        } else {
            if (size() == 1) {
                mMedian = super.pop(0);
            }
        }
        return mMedian;
    }

    public String getMode() {
        int maxCount = 0;
        boolean duplicateMode = false;
        Object mMode = 0.0;
        if (size() > 1) {
            for (int i : range(super.toSet().size())) {
                int counter = count(super.toSet().pop(i));
                if (counter == maxCount) {
                    duplicateMode = true;
                }
                if (counter > maxCount) {
                    duplicateMode = false;
                    maxCount = counter;
                    mMode = pop(i);
                }
            }
            if (duplicateMode) {
                return null;
            }
            return mMode.toString();
        } else {
            if (size() == 1) {
                mMode = pop(0);
            }
        }
        return mMode.toString();
    }

    public String getModeCount() {
        if (getMode() != null) {
            return Funcs.toString(count(toDouble(getMode())));
        }
        return null;
    }


    public double getStandardDiv() {
        double stanDiv = 0;
        for (double num : toDouble(toArray())) {
            stanDiv += Math.pow(num - getMean(), 2);
        }
        return Math.sqrt(stanDiv / size());// or Math.sqrt(getVariance());
    }

    public double getVariance() {
        double mVariance = 0;
        for (double i : toDouble(toArray())) {
            mVariance += (i - getMean()) * (i - getMean());
        }
        mVariance /= size();
        return mVariance;// or getStandardDiv/size();
    }

    public double getCoVariance(Double[] array) {
        double sum = 0;
        if (size() == array.length) {
            StatsArray coArray = new StatsArray(array);
            for (int i : range(size())) {
                sum += (pop(i) - getMean()) * (coArray.pop(i) - coArray.getMean());
            }
        }
        return sum / (size() - 1);
    }
}