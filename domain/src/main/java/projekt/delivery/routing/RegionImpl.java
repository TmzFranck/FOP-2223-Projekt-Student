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
        return (locationA == null || locationB == null) ? null :
            edges.get(locationA.compareTo(locationB) <= 0 ? locationA : locationB)
                .get(locationA.compareTo(locationB) > 0 ? locationA : locationB);
    }

    @Override
    public Collection<Node> getNodes() {
        return Collections.unmodifiableCollection(nodes.values());
    }

    @Override
    public Collection<Edge> getEdges() {
        return Collections.unmodifiableCollection(allEdges);
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
        if (!this.equals(node.getRegion())) {
            throw new IllegalArgumentException(String.format("Node %s has incorrect region", node));
        }

        nodes.put(node.getLocation(), node);
    }

    /**
     * Adds the given {@link EdgeImpl} to this {@link RegionImpl}.
     * @param edge the {@link EdgeImpl} to add.
     */
    void putEdge(EdgeImpl edge) {
        if (edge == null) {
            throw new IllegalArgumentException("Cannot add a null edge");
        }
        if (!this.equals(edge.getRegion())) {
            throw new IllegalArgumentException( String.format("Edge %s has incorrect region", edge));
        }
        Node nodeA = edge.getNodeA();
        Node nodeB = edge.getNodeB();

        if (nodeA == null && nodeB == null) {
            throw new IllegalArgumentException(String.format("Node{A,B} {%s, %s} is not part of the region", edge.getLocationA(), edge.getLocationB()));
        } else if (nodeA == null) {
            throw new IllegalArgumentException(String.format("NodeA %s is not part of the region", edge.getLocationA()));
        } else if (nodeB == null) {
            throw new IllegalArgumentException(String.format("NodeB %s is not part of the region", edge.getLocationB()));
        }

        Location smaller = edge.getLocationA().compareTo(edge.getLocationB()) <= 0 ? edge.getLocationA() : edge.getLocationB();
        Location larger = smaller == edge.getLocationA() ? edge.getLocationB() : edge.getLocationA();

        Map<Location, EdgeImpl> innerMap = new HashMap<>();
        innerMap.put(larger, edge);
        edges.put(smaller, innerMap);
        allEdges.add(edge);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof RegionImpl other)) {
            return false;
        }

        if (o == this) {
            return true;
        }
        return Objects.equals(this.nodes, other.nodes) && Objects.equals(this.edges, other.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes, allEdges);
    }
}
