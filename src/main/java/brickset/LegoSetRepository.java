package brickset;

import repository.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Represents a repository of {@code LegoSet} objects.
 */
public class LegoSetRepository extends Repository<LegoSet> {

    public LegoSetRepository() {
        super(LegoSet.class, "brickset.json");
    }

    /**
     * Returns the number of LEGO sets with the tag specified.
     *
     * @param tag a LEGO set tag
     * @return the number of LEGO sets with the tag specified
     */
    public long countLegoSetsWithTag(String tag) {
        return getAll().stream()
                .filter(legoSet -> legoSet.getTags() != null && legoSet.getTags().contains(tag))
                .count();
    }

    /**
     * Check if the theme exist.
     *
     * @param match the theme tag
     * @return true if the theme exist
     */
    public boolean themeExist(String match) {
        return getAll().stream()
                .anyMatch(legoSet -> Objects.equals(legoSet.getTheme(), match));
    }

    /**
     *  Writes out the name of the tags.
     */

    public void printTheListOfTags(){
        getAll().stream()
                .filter(legoSet -> legoSet.getTags() !=null )
                .flatMap(legoSet -> legoSet.getTags().stream())
                .distinct()
                .forEach(System.out::println);
    }

    /**
     * Sums the number of pieces.
     * @return the sum of pieces.
     */
    public int sumOfPieces(){
        return getAll().stream()
                .map(LegoSet::getPieces)
                .reduce(0,  (X , Y) -> X + Y );

    }

    /**
     * Check if the pieces are bigger than 100.
     * @return the numbers with a false or true value
     */
    public Map<Boolean, List<Integer>> biggerThanHundredPieces() {
        return getAll().stream()
                .map(LegoSet::getPieces)
                .filter(pieces -> pieces != 0 )
                .collect(Collectors.partitioningBy(num -> num > 100));
    }


    /**
     * Sums the occurrence of each tag.
     * @return the occurrence of each tag
     */
    public Map<String, Long> collectThemes(){
        return getAll().stream()
                .map(LegoSet::getTheme)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }


    public static void main(String[] args) {
        var repository = new LegoSetRepository();
        System.out.println(repository.countLegoSetsWithTag("Microscale"));

        System.out.println(repository.themeExist("Games"));
        repository.printTheListOfTags();
        System.out.println(repository.sumOfPieces());
        System.out.println(repository.biggerThanHundredPieces());
        System.out.println(repository.collectThemes());
    }

}
