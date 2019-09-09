import Vue from 'vue'
import {
  PATTERNS, SITUATIONS
} from './mutation-types'

export default {
  [PATTERNS](state, { situations }) {
    state.situations = situations
  },
  [SITUATIONS](state, { patterns }) {
    state.patterns = patterns
  },
}