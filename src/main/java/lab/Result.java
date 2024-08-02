package lab;

public class Result {

    private String name;
    private int score;

    public Result(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String getName(){
        return name;
    }

    public int getScore(){
        return score;
    }

    @Override
    public String toString(){
        return this.name + ": " + "0" + this.score + "00";
    }

    @Override
    public int hashCode(){
        return name.hashCode();
    };

   @Override
    public boolean equals(Object obj) {
        if(obj instanceof Result result){
            return result.getName().equals(this.name);
        }
        return false;
    }
}
