package projekt.base;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A tuple for the x- and y-coordinates of a point.
 */
public final class Location implements Comparable<Location> {

    private final static Comparator<Location> COMPARATOR =
        Comparator.comparing(Location::getX).thenComparing(Location::getY);

    private final int x;
    private final int y;
    private final int hashcode;

    /**
     * Instantiates a new {@link Location} object using {@code x} and {@code y} as coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
        hashcode = (x << 16) | (0xFFFF & y);
    }

    /**
     * Returns the x-coordinate of this location.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of this location.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Adds the coordinates of this location and the other location and returns a new
     * {@link Location} object with the resulting coordinates.
     *
     * @param other the other {@link Location} object to get the second set of coordinates from
     * @return a new {@link Location} object with the sum of coordinates from both locations
     */
    public Location add(Location other) {
        return new Location(x + other.x, y + other.y);
    }

    /**
     * Subtracts the coordinates of this location from the other location and returns a new
     * {@link Location} object with the resulting coordinates.
     *
     * @param other the other {@link Location} object to get the second set of coordinates from
     * @return a new {@link Location} object with the difference of coordinates from both locations
     */
    public Location subtract(Location other) {
        return new Location(x - other.x, y - other.y);
    }

    /**
     * Compares this Location with another specified Location based on their x and y coordinates.
     *
     * @param o the Location to compare with this Location
     * @return -1 if this Location is less than the specified Location, 0 if they are equal, and 1 if this Location
     * is greater than the specified Location
     */
    @Override
    public int compareTo(@NotNull Location o) {
        return (o.getX() == this.getX() && o.getY() == this.getY()) ? 0 :
            (this.getX() < o.getX()|| (this.getX() == o.getX() && this.getY() < o.getY())) ? -1 : 1;
    }

    /**
     * Returns a hash code value for this Location based on its x and y coordinates. The hash code is calculated
     * using the formula (31 * xHash + 23 * yHash), where xHash is the hash code for the x-coordinate and yHash
     * is the hash code for the y-coordinate.
     *
     * @return the hash code value for this Location
     */
    @Override
    public int hashCode() {
        return hashcode;
    }

    /**
     Compares this Location object to the specified object for equality.
     @param o the object to compare to this Location object
     @return true if the specified object is a Location object with the same X value as this Location object, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Location) {
            return this.getX() == ((Location) o).getX() && this.getY() == ((Location) o).getY();
        }
        return false;
    }

    /**
     Returns a string representation of this Point object in the form "(X,Y)".
     @return a string representation of this Point object
     */
    @Override
    public String toString() {
        return String.format("(%d, %d)", this.getX(), this.getY());
    }
}
