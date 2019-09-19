
import storageUtils from '../utils/storageUtils'
export default {
  patterns: [],
  situations: [],
  flag: false,
  powers: storageUtils.readPowers(),
  outputs: storageUtils.readOutputs(),
  category: storageUtils.readCategory(),
}