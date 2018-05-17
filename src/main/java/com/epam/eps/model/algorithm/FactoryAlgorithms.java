package com.epam.eps.model.algorithm;

import com.epam.eps.model.algorithm.searchdeep.Cycle;
import com.epam.eps.model.algorithm.searchdeep.Recursion;
import com.epam.eps.model.algorithm.searchwidth.MapUse;
import com.epam.eps.model.algorithm.searchwidth.SetUse;

public enum FactoryAlgorithms {
    DEEP_RECURSION {
        @Override
        protected Algorithm getAlgorithm(Object bean) {
            return new Recursion(bean);
        }
    },
    DEEP_CYCLE {
        @Override
        protected Algorithm getAlgorithm(Object bean) {
            return new Cycle(bean);
        }
    },
    WIDTH_SET {
        @Override
        protected Algorithm getAlgorithm(Object bean) {
            return new SetUse(bean);
        }
    },
    WIDTH_MAP {
        @Override
        protected Algorithm getAlgorithm(Object bean) {
            return new MapUse(bean);
        }
    },
    OPTIMAL {
        @Override
        protected Algorithm getAlgorithm(Object bean) {
            return new Optimal(bean);
        }
    },
    OPTIMAL2 {
        @Override
        protected Algorithm getAlgorithm(Object bean) {
            return new Optimal2(bean);
        }
    },
    OPTIMAL3 {
        @Override
        protected Algorithm getAlgorithm(Object bean) {
            return new Optimal3(bean);
        }
    };

    abstract protected Algorithm getAlgorithm(Object bean);

    public static Algorithm getTypeAlgorithm(FactoryAlgorithms algorithmType, Object bean){
        return algorithmType.getAlgorithm(bean);
    }
}




