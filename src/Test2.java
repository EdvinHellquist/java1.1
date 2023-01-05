public class Test2 {
    private String redundant;

    public Test2 () {
     this.redundant = "Never used";
    }

    public String myArg () {
        return this.redundant;
    }
}
