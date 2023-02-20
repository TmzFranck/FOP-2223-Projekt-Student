package projekt.delivery.routing;

import org.jetbrains.annotations.Nullable;
import projekt.base.DistanceCalculator;
import projekt.base.EuclideanDistanceCalculator;
import projekt.base.Location;

import java.util.*;

import static org.tudalgo.algoutils.student.Student.crash;

class RegionImpl implements Region {

    private final Map<Location, NodeImpl> nodes = new HashMap<>();
    private final Map<Location, Map<Location, EdgeImpl>> edges = new HashMap<>();
    private final List<EdgeImpl> allEdges = new ArrayList<>();
    private final DistanceCalculator distanceCalculator;

    /**
     * Creates a new, empty {@link RegionImpl} instance using a {@link EuclideanDistanceCalculator}.
     */
    public RegionImpl() {
        this(new EuclideanDistanceCalculator());
    }

    /**
     * Creates a new, empty {@link RegionImpl} instance using the given {@link DistanceCalculator}.
     */
    public RegionImpl(DistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

    /**
     Returns the node associated with the specified location, or null if no node is associated with the location.
     @param location the location of the node to retrieve
     @return the node associated with the location, or null if no node is associated with the location
     */
    @Override
    public @Nullable Node getNode(Location location) {
        if (location != null)
            return nodes.get(location);
        return null;
    }

    @Override
    public @Nullable Edge getEdge(Location locationA, Location locationB) {
        return crash(); // TODO: H2.3 - remove if implemented
    }

    @Override
    public Collection<Node> getNodes() {
        return crash(); // TODO: H2.5 - remove if implemented
    }

    @Override
    public Collection<Edge> getEdges() {
        return crash(); // TODO: H2.5 - remove if implemented
    }

    @Override
    public DistanceCalculator getDistanceCalculator() {
        return distanceCalculator;
    }

    /**
     * Adds the given {@link NodeImpl} to this {@link RegionImpl}.
     * @param node the {@link NodeImpl} to add.
     */
    void putNode(NodeImpl node) {
        if (node.getRegion() != this) {
            throw new IllegalArgumentException(String.format("Node %s has incorrect region", node));
        }

        nodes.put(node.getLocation(), node);
    }

    /**
     * Adds the given {@link EdgeImpl} to this {@link RegionImpl}.
     * @param edge the {@link EdgeImpl} to add.
     */
    void putEdge(EdgeImpl edge) {
        crash(); // TODO: H2.4 - remove if implemented
    }

    @Override
    public boolean equals(Object o) {
        return crash(); // TODO: H2.6 - remove if implemented
    }

    @Override
    public int hashCode() {
        return crash(); // TODO: H2.7 - remove if implemented
    }
}
