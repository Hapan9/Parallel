public class IterationsManager {
    public static Result iterate(MultiplicationStrategy strategy){
        for (int i = 0; i < strategy.getIterationsCount(); i++) {
            strategy.nextIteration();
        }
        return strategy.getResult();
    }
}
