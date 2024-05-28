package converter.music;

public record Time(int beats, int beatType) {

    @Override
    public String toString() {
        return beats + "/" + beatType;
    }
}
