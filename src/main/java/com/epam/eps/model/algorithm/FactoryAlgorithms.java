package com.epam.eps.model.algorithm;

import com.epam.eps.model.algorithm.searchdeep.Cycle;
import com.epam.eps.model.algorithm.searchdeep.Recursion;
import com.epam.eps.model.algorithm.searchwidth.Streams;
import com.epam.eps.model.algorithm.searchwidth.Cycles;

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
    WIDTH_CYCLES {
        @Override
        protected Algorithm getAlgorithm(Object bean) {
            return new Cycles(bean);
        }
    },
    WIDTH_STREAMS {
        @Override
        protected Algorithm getAlgorithm(Object bean) {
            return new Streams(bean);
        }
    };

    abstract protected Algorithm getAlgorithm(Object bean);

    public static Algorithm getTypeAlgorithm(FactoryAlgorithms algorithmType, Object bean){
        return algorithmType.getAlgorithm(bean);
    }
}

